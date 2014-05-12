/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.modeling.generator;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.codenvy.editor.api.editor.AbstractEditor;
import com.codenvy.editor.api.editor.EditorState;
import com.codenvy.editor.api.editor.EditorView;
import com.codenvy.editor.api.editor.SelectionManager;
import com.codenvy.editor.api.editor.elements.AbstractLink;
import com.codenvy.editor.api.editor.elements.AbstractShape;
import com.codenvy.editor.api.editor.elements.Shape;
import com.codenvy.editor.api.editor.elements.widgets.shape.ShapeWidget;
import com.codenvy.editor.api.editor.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.editor.api.editor.propertiespanel.PropertiesPanelManager;
import com.codenvy.editor.api.editor.propertiespanel.empty.EmptyPropertiesPanelPresenter;
import com.codenvy.editor.api.editor.workspace.AbstractWorkspaceView;
import com.codenvy.editor.api.mvp.AbstractView;
import com.codenvy.modeling.configuration.Configuration;
import com.codenvy.modeling.configuration.metamodel.diagram.Connection;
import com.codenvy.modeling.configuration.metamodel.diagram.Element;
import com.codenvy.modeling.configuration.metamodel.diagram.Property;
import com.codenvy.modeling.generator.builders.java.SourceCodeBuilder;
import com.codenvy.modeling.generator.builders.xml.api.GField;
import com.codenvy.modeling.generator.builders.xml.api.GStyle;
import com.codenvy.modeling.generator.builders.xml.api.UIXmlBuilder;
import com.codenvy.modeling.generator.builders.xml.api.widgets.GLabel;
import com.codenvy.modeling.generator.builders.xml.api.widgets.GPushButton;
import com.codenvy.modeling.generator.builders.xml.api.widgets.GTextBox;
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GDockLayoutPanel;
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GFlowPanel;
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GScrollPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.orange.links.client.DiagramController;
import com.orange.links.client.utils.LinksClientBundle;

import javax.annotation.Nonnull;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static com.codenvy.modeling.generator.GenerationController.Param.EDITOR_NAME;
import static com.codenvy.modeling.generator.GenerationController.Param.MAIN_PACKAGE;
import static com.codenvy.modeling.generator.GenerationController.Param.MAVEN_ARTIFACT_ID;
import static com.codenvy.modeling.generator.GenerationController.Param.MAVEN_ARTIFACT_NAME;
import static com.codenvy.modeling.generator.GenerationController.Param.MAVEN_GROUP_ID;
import static com.codenvy.modeling.generator.GenerationController.Param.TARGET_PATH;
import static com.codenvy.modeling.generator.GenerationController.Param.TEMPLATE_PATH;
import static com.codenvy.modeling.generator.builders.java.SourceCodeBuilder.Access.DEFAULT;
import static com.codenvy.modeling.generator.builders.java.SourceCodeBuilder.Access.PRIVATE;
import static com.codenvy.modeling.generator.builders.java.SourceCodeBuilder.Access.PROTECTED;
import static com.codenvy.modeling.generator.builders.java.SourceCodeBuilder.Access.PUBLIC;
import static com.codenvy.modeling.generator.builders.java.SourceCodeBuilder.Argument;
import static com.codenvy.modeling.generator.builders.xml.api.UIXmlBuilder.OFFSET;

/**
 * The main class that provides an ability to generate java source code from given params.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class SourceCodeGenerator {

    private static final String ARTIFACT_ID_MASK                = "artifact_id";
    private static final String GROUP_ID_MASK                   = "group_id";
    private static final String ARTIFACT_NAME_MASK              = "artifact_name";
    private static final String EDITOR_NAME_MASK                = "editor_name";
    private static final String ENTRY_POINT_CLASS_MASK          = "entry_point";
    private static final String CURRENT_PACKAGE_MASK            = "current_package";
    private static final String MAIN_PACKAGE_MASK               = "main_package";
    private static final String STATIC_IMPORT_MASK              = "static_import_elements";
    private static final String IMPORT_MASK                     = "import_elements";
    private static final String CREATE_GRAPHIC_ELEMENTS_MASK    = "create_graphic_elements";
    private static final String CREATE_GRAPHIC_CONNECTIONS_MASK = "create_graphic_connections";
    private static final String CHANGE_EDITOR_STATE_MASK        = "change_editor_states";

    private static final String POM_FILE_FULL_NAME        = "pom.xml";
    private static final String MAIN_HTML_FILE_FULL_NAME  = "Editor.html";
    private static final String MAIN_GWT_MODULE_FILE_NAME = "Editor.gwt.xml";
    private static final String MAIN_CSS_FILE_NAME        = "editor.css";

    private static final String MAIN_SOURCE_PATH        = "/src/main";
    private static final String JAVA_SOURCE_PATH        = "java";
    private static final String RESOURCES_SOURCE_PATH   = "resources";
    private static final String WEBAPP_SOURCE_PATH      = "webapp";
    private static final String CLIENT_PART_FOLDER      = "client";
    private static final String INJECT_FOLDER           = "inject";
    private static final String TOOLBAR_FOLDER          = "toolbar";
    private static final String WORKSPACE_FOLDER        = "workspace";
    private static final String PROPERTIES_PANEL_FOLDER = "propertiespanel";
    private static final String ELEMENTS_FOLDER         = "elements";

    private static final String ENTRY_POINT_NAME                       = "EditorEntryPoint";
    private static final String TOOLBAR_PRESENTER_NAME                 = "ToolbarPresenter";
    private static final String TOOLBAR_VIEW_NAME                      = "ToolbarView";
    private static final String TOOLBAR_VIEW_IMPL_BINDER_NAME          = "ToolbarViewImplUiBinder";
    private static final String TOOLBAR_VIEW_IMPL_NAME                 = "ToolbarViewImpl";
    private static final String ACTION_DELEGATE_NAME                   = "ActionDelegate";
    private static final String WORKSPACE_PRESENTER_NAME               = "WorkspacePresenter";
    private static final String WORKSPACE_VIEW_NAME                    = "WorkspaceView";
    private static final String WORKSPACE_VIEW_IMPL_NAME               = "WorkspaceViewImpl";
    private static final String WORKSPACE_VIEW_IMPL_BINDER_NAME        = "WorkspaceViewImplUiBinder";
    private static final String PROPERTIES_PANEL_PRESENTER_NAME        = "PropertiesPanelPresenter";
    private static final String PROPERTIES_PANEL_VIEW_NAME             = "PropertiesPanelView";
    private static final String PROPERTIES_PANEL_VIEW_IMPL_NAME        = "PropertiesPanelViewImpl";
    private static final String PROPERTIES_PANEL_VIEW_IMPL_BINDER_NAME = "PropertiesPanelViewImplUiBinder";
    private static final String EDITOR_STATE_NAME                      = "State";
    private static final String EDITOR_RESOURCES_NAME                  = "EditorResources";
    private static final String EDITOR_CSS_RESOURCE_NAME               = "EditorCSS";
    private static final String GIN_MODULE_NAME                        = "GinModule";
    private static final String GIN_INJECTOR_NAME                      = "Injector";
    private static final String EDITOR_FACTORY_NAME                    = "EditorFactory";

    private static final String CREATE_NOTING_STATE                   = "CREATING_NOTING";
    private static final String CREATE_ELEMENT_STATE_FORMAT           = "CREATING_%s";
    private static final String CREATE_CONNECTION_SOURCE_STATE_FORMAT = "CREATING_%s_SOURCE";
    private static final String CREATE_CONNECTION_TARGET_STATE_FORMAT = "CREATING_%s_TARGET";

    private final Provider<SourceCodeBuilder> sourceCodeBuilderProvider;
    private final Provider<UIXmlBuilder>      uiXmlBuilderProvider;
    private final Provider<GField>            fieldProvider;
    private final Provider<GScrollPanel>      scrollPanelProvider;
    private final Provider<GFlowPanel>        flowPanelProvider;
    private final Provider<GStyle>            styleProvider;
    private final Provider<GDockLayoutPanel>  dockLayoutPanelProvider;
    private final Provider<GPushButton>       pushButtonProvider;
    private final Provider<GLabel>            labelProvider;
    private final Provider<GTextBox>          textBoxProvider;

    @Inject
    public SourceCodeGenerator(Provider<SourceCodeBuilder> sourceCodeBuilderProvider,
                               Provider<UIXmlBuilder> uiXmlBuilderProvider,
                               Provider<GField> fieldProvider,
                               Provider<GScrollPanel> scrollPanelProvider,
                               Provider<GFlowPanel> flowPanelProvider,
                               Provider<GStyle> styleProvider,
                               Provider<GDockLayoutPanel> dockLayoutPanelProvider,
                               Provider<GPushButton> pushButtonProvider,
                               Provider<GLabel> labelProvider,
                               Provider<GTextBox> textBoxProvider) {
        this.sourceCodeBuilderProvider = sourceCodeBuilderProvider;
        this.uiXmlBuilderProvider = uiXmlBuilderProvider;
        this.fieldProvider = fieldProvider;
        this.scrollPanelProvider = scrollPanelProvider;
        this.flowPanelProvider = flowPanelProvider;
        this.styleProvider = styleProvider;
        this.dockLayoutPanelProvider = dockLayoutPanelProvider;
        this.pushButtonProvider = pushButtonProvider;
        this.labelProvider = labelProvider;
        this.textBoxProvider = textBoxProvider;
    }

    /**
     * Generates java source code of GWT editor.
     *
     * @param properties
     *         properties which are needed for code generation
     * @param configuration
     *         configuration that contains full information about GWT editor
     */
    public void generate(@Nonnull Properties properties, @Nonnull Configuration configuration) {
        String targetPath = properties.get(TARGET_PATH.name()).toString();
        String packageName = properties.get(MAIN_PACKAGE.name()).toString();
        String editorName = properties.get(EDITOR_NAME.name()).toString();

        try {
            copyProjectHierarchy(targetPath, properties.get(TEMPLATE_PATH.name()).toString());

            modifyPom(targetPath, properties.get(MAVEN_ARTIFACT_ID.name()).toString(), properties.get(MAVEN_GROUP_ID.name()).toString(),
                      properties.get(MAVEN_ARTIFACT_NAME.name()).toString());
            modifyMainHtmlFile(targetPath, editorName);

            generateResourcesFolder(targetPath, packageName, editorName);
            generateJavaFolder(targetPath, packageName, editorName, configuration);
        } catch (IOException e) {
            // TODO think about type of exception
            e.printStackTrace();
        }
    }

    private void copyProjectHierarchy(@Nonnull String targetPath, @Nonnull String templateDir) throws IOException {
        Path targetFolder = Paths.get(targetPath);
        Files.createDirectories(targetFolder);

        try (ZipFile zipFile = new ZipFile(templateDir)) {
            Enumeration<? extends ZipEntry> e = zipFile.entries();

            while (e.hasMoreElements()) {
                ZipEntry entry = e.nextElement();

                Path destinationPath = Paths.get(targetPath, entry.getName());
                Files.createDirectories(destinationPath.getParent());

                if (!entry.isDirectory()) {
                    unzipFile(zipFile, entry, destinationPath);
                }
            }
        }
    }

    private void unzipFile(@Nonnull ZipFile zipFile, @Nonnull ZipEntry entry, @Nonnull Path destinationPath) throws IOException {
        try (BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
             BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(destinationPath), 1024)) {
            int b;
            byte buffer[] = new byte[1024];

            while ((b = bis.read(buffer, 0, 1024)) != -1) {
                bos.write(buffer, 0, b);
            }

            bos.flush();
        }
    }

    private void modifyPom(@Nonnull String targetPath,
                           @Nonnull String artifactId,
                           @Nonnull String groupId,
                           @Nonnull String artifactName) throws IOException {
        Path pomPath = Paths.get(targetPath, POM_FILE_FULL_NAME);

        String pomContent = new String(Files.readAllBytes(pomPath));
        String newContent = pomContent.replaceAll(ARTIFACT_ID_MASK, artifactId)
                                      .replaceAll(GROUP_ID_MASK, groupId)
                                      .replaceAll(ARTIFACT_NAME_MASK, artifactName);

        Files.write(pomPath, newContent.getBytes());
    }

    private void modifyMainHtmlFile(@Nonnull String targetPath, @Nonnull String editorName) throws IOException {
        Path mainHtmlFilePath = Paths.get(targetPath, MAIN_SOURCE_PATH, WEBAPP_SOURCE_PATH, MAIN_HTML_FILE_FULL_NAME);

        String content = new String(Files.readAllBytes(mainHtmlFilePath));
        String newContent = content.replaceAll(EDITOR_NAME_MASK, editorName);

        Files.write(mainHtmlFilePath, newContent.getBytes());
    }

    @Nonnull
    private String convertPathToPackageName(@Nonnull String packagePath) {
        return packagePath.replace('.', '/');
    }

    private void generateJavaFolder(@Nonnull String targetPath,
                                    @Nonnull String packageName,
                                    @Nonnull String editorName,
                                    @Nonnull Configuration configuration) throws IOException {
        String mainPackageFolder = convertPathToPackageName(packageName);

        String javaFolder = targetPath + MAIN_SOURCE_PATH + File.separator + JAVA_SOURCE_PATH;
        String clientPackageFolder = mainPackageFolder + File.separator + CLIENT_PART_FOLDER;
        String clientFolder = javaFolder + File.separator + clientPackageFolder;

        createInjectModule(clientFolder, packageName, editorName);
        createElements(clientFolder, packageName, configuration);
        createMainGWTElements(clientFolder, packageName, editorName, configuration);
        createWorkspace(javaFolder, clientFolder, packageName, configuration);
        createToolbar(javaFolder, clientFolder, packageName, configuration);
        createPropertiesPanel(clientFolder, packageName, configuration);
    }

    private void createInjectModule(@Nonnull String clientPackageFolder,
                                    @Nonnull String packageName,
                                    @Nonnull String editorName) throws IOException {
        Path injectFolder = Paths.get(clientPackageFolder, INJECT_FOLDER);
        Files.createDirectories(injectFolder);

        String clientPackageName = packageName + '.' + CLIENT_PART_FOLDER + '.';
        String injectPackageName = clientPackageName + INJECT_FOLDER + '.';

        String editorFactory = sourceCodeBuilderProvider
                .get()
                .newClass(injectPackageName + EDITOR_FACTORY_NAME).makeInterface()

                .addImport(clientPackageName + TOOLBAR_FOLDER + '.' + TOOLBAR_PRESENTER_NAME)
                .addImport(clientPackageName + WORKSPACE_FOLDER + '.' + WORKSPACE_PRESENTER_NAME)
                .addImport(clientPackageName + EDITOR_STATE_NAME)
                .addImport(SelectionManager.class)
                .addImport(EditorState.class)
                .addImport(AcceptsOneWidget.class)

                .addMethod("createToolbar")
                .withReturnType(TOOLBAR_PRESENTER_NAME).withMethodArguments(new Argument("EditorState<State>", "editorState"))
                .withMethodAccessLevel(DEFAULT)

                .addMethod("createWorkspace")
                .withReturnType(WORKSPACE_PRESENTER_NAME).withMethodAccessLevel(DEFAULT)
                .withMethodArguments(new Argument("EditorState<State>", "editorState"),
                                     new Argument(SelectionManager.class.getSimpleName(), "selectionManager"))

                .addMethod("createPropertiesPanelManager")
                .withReturnType(PropertiesPanelManager.class).withMethodAccessLevel(DEFAULT)
                .withMethodArguments(new Argument(AcceptsOneWidget.class.getSimpleName(), "container"))

                .build();

        Path editorFactoryPath = Paths.get(clientPackageFolder, INJECT_FOLDER, EDITOR_FACTORY_NAME + ".java");
        Files.write(editorFactoryPath, editorFactory.getBytes());

        String ginModule = sourceCodeBuilderProvider
                .get()
                .newClass(injectPackageName + GIN_MODULE_NAME).baseClass(AbstractGinModule.class)

                .addImport(GinFactoryModuleBuilder.class)

                .addMethod("configure")
                .withMethodAccessLevel(PROTECTED).withMethodAnnotation("@Override")
                .withMethodBody("install(new GinFactoryModuleBuilder().build(EditorFactory.class));")

                .build();

        Path ginModulePath = Paths.get(clientPackageFolder, INJECT_FOLDER, GIN_MODULE_NAME + ".java");
        Files.write(ginModulePath, ginModule.getBytes());

        String ginInjector = sourceCodeBuilderProvider
                .get()
                .newClass(injectPackageName + GIN_INJECTOR_NAME).makeInterface()
                .withClassAnnotation("@GinModules(GinModule.class)").baseClass(Ginjector.class)

                .addImport(GinModules.class)
                .addImport(clientPackageName + editorName)

                .addMethod("getEditor").withMethodAccessLevel(DEFAULT).withReturnType(editorName)

                .build();

        Path ginInjectorPath = Paths.get(clientPackageFolder, INJECT_FOLDER, GIN_INJECTOR_NAME + ".java");
        Files.write(ginInjectorPath, ginInjector.getBytes());
    }

    private void createElements(@Nonnull String clientPackageFolder,
                                @Nonnull String packageName,
                                @Nonnull Configuration configuration) throws IOException {
        Path elementsFolder = Paths.get(clientPackageFolder, ELEMENTS_FOLDER);
        Files.createDirectories(elementsFolder);

        String elementsPackageName = packageName + '.' + CLIENT_PART_FOLDER + '.' + ELEMENTS_FOLDER + '.';

        for (Element element : configuration.getDiagramConfiguration().getElements()) {
            String elementName = element.getName();
            SourceCodeBuilder elementClassBuilder = sourceCodeBuilderProvider
                    .get()
                    .newClass(elementsPackageName + elementName).baseClass(AbstractShape.class);
            StringBuilder constructor = new StringBuilder("super();\n");

            for (Property property : element.getProperties()) {
                Class javaClass = convertPropertyTypeToJavaClass(property);
                String name = property.getName();
                String argumentName = changeFirstSymbolToLowCase(name);

                constructor.append(argumentName).append(" = ").append(property.getValue()).append(";\n");

                elementClassBuilder
                        .addField(argumentName, javaClass).withFieldAccessLevel(PRIVATE)

                        .addMethod("get" + name)
                        .withMethodAccessLevel(PUBLIC).withReturnType(javaClass).withMethodBody("return " + argumentName + ";")

                        .addMethod("set" + name)
                        .withMethodAccessLevel(PUBLIC).withMethodArguments(new Argument(javaClass.getSimpleName(), argumentName))
                        .withMethodBody("this." + argumentName + " = " + argumentName + ';');
            }

            elementClassBuilder.addConstructor().withConstructorBody(constructor.toString());

            Path elementJavaClassPath = Paths.get(clientPackageFolder, ELEMENTS_FOLDER, elementName + ".java");
            Files.write(elementJavaClassPath, elementClassBuilder.build().getBytes());
        }

        for (Connection connection : configuration.getDiagramConfiguration().getConnections()) {
            String connectionName = connection.getName();
            String connectionClass = sourceCodeBuilderProvider
                    .get()
                    .newClass(elementsPackageName + connectionName).baseClass(AbstractLink.class)

                    .addImport(Shape.class)

                    .addConstructor(new Argument(Shape.class.getSimpleName(), "source"),
                                    new Argument(Shape.class.getSimpleName(), "target"))
                    .withConstructorBody("super(source, target);")

                    .build();

            Path connectionJavaClassPath = Paths.get(clientPackageFolder, ELEMENTS_FOLDER, connectionName + ".java");
            Files.write(connectionJavaClassPath, connectionClass.getBytes());
        }
    }

    @Nonnull
    private Class convertPropertyTypeToJavaClass(@Nonnull Property property) {
        switch (property.getType()) {
            case INTEGER:
                return Integer.class;

            case FLOAT:
                return Double.class;

            case BOOLEAN:
                return Boolean.class;

            case STRING:
            default:
                return String.class;
        }
    }

    private void createMainGWTElements(@Nonnull String clientPackageFolder,
                                       @Nonnull String packageName,
                                       @Nonnull String editorName,
                                       @Nonnull Configuration configuration) throws IOException {
        String clientPackage = packageName + '.' + CLIENT_PART_FOLDER + '.';

        SourceCodeBuilder editorStateEnum = sourceCodeBuilderProvider
                .get()
                .newClass(clientPackage + EDITOR_STATE_NAME).makeEnum()
                .withEnumValue(CREATE_NOTING_STATE);

        for (Element element : configuration.getDiagramConfiguration().getElements()) {
            editorStateEnum.withEnumValue(String.format(CREATE_ELEMENT_STATE_FORMAT, element.getName().toUpperCase()));
        }

        for (Connection connection : configuration.getDiagramConfiguration().getConnections()) {
            String connectionName = connection.getName().toUpperCase();
            editorStateEnum.withEnumValue(String.format(CREATE_CONNECTION_SOURCE_STATE_FORMAT, connectionName));
            editorStateEnum.withEnumValue(String.format(CREATE_CONNECTION_TARGET_STATE_FORMAT, connectionName));
        }

        Path editorStateJavaClassPath = Paths.get(clientPackageFolder, EDITOR_STATE_NAME + ".java");
        Files.write(editorStateJavaClassPath, editorStateEnum.build().getBytes());

        String cssResource = sourceCodeBuilderProvider
                .get()
                .newClass(clientPackage + EDITOR_CSS_RESOURCE_NAME).makeInterface().baseClass(CssResource.class)

                .addMethod("fullSize")
                .withReturnType(String.class).withMethodAccessLevel(DEFAULT)
                        // TODO It is the place for adding custom style into client bundle

                .build();

        Path editorCSSResourceJavaClassPath = Paths.get(clientPackageFolder, EDITOR_CSS_RESOURCE_NAME + ".java");
        Files.write(editorCSSResourceJavaClassPath, cssResource.getBytes());

        String editorResources = sourceCodeBuilderProvider
                .get()
                .newClass(clientPackage + EDITOR_RESOURCES_NAME).makeInterface().baseClass(ClientBundle.class)

                .addMethod("editorCSS")
                .withReturnType(EDITOR_CSS_RESOURCE_NAME).withMethodAnnotation("@Source(\"editor.css\")").withMethodAccessLevel(DEFAULT)
                        // TODO It is the place for adding custom icons into client bundle

                .build();

        Path editorResourcesJavaClassPath = Paths.get(clientPackageFolder, EDITOR_RESOURCES_NAME + ".java");
        Files.write(editorResourcesJavaClassPath, editorResources.getBytes());

        String entryPointJavaClass = sourceCodeBuilderProvider
                .get()
                .newClass(clientPackage + ENTRY_POINT_NAME).implementInterface(EntryPoint.class)

                .addImport(GWT.class)
                .addImport(RootLayoutPanel.class)
                .addImport(SimpleLayoutPanel.class)
                .addImport(LinksClientBundle.class)
                .addImport(clientPackage + INJECT_FOLDER + '.' + GIN_INJECTOR_NAME)

                .addMethod("onModuleLoad")
                .withMethodAnnotation("@Override")
                .withMethodBody("LinksClientBundle.INSTANCE.css().ensureInjected();\n" +
                                "\n" +
                                "Injector injector = GWT.create(Injector.class);\n" +
                                editorName + " editor = injector.getEditor();\n" +
                                "\n" +
                                "SimpleLayoutPanel mainPanel = new SimpleLayoutPanel();\n" +
                                "editor.go(mainPanel);\n" +
                                "\n" +
                                "RootLayoutPanel.get().add(mainPanel);\n")

                .build();

        Path entryPointJavaClassPath = Paths.get(clientPackageFolder, ENTRY_POINT_NAME + ".java");
        Files.write(entryPointJavaClassPath, entryPointJavaClass.getBytes());

        SourceCodeBuilder editorPresenterBuilder = sourceCodeBuilderProvider
                .get()
                .newClass(clientPackage + editorName).baseClass(AbstractEditor.class)
                .implementInterface(EditorView.class.getSimpleName() + '.' + EditorView.ActionDelegate.class.getSimpleName())
                .addImport(EditorView.class)
                .addImport(clientPackage + INJECT_FOLDER + '.' + EDITOR_FACTORY_NAME)
                .addImport(SelectionManager.class)
                .addImport(EmptyPropertiesPanelPresenter.class)
                .addImport(EditorState.class)
                .addImport(PropertiesPanelManager.class);

        List<Argument> arguments = new ArrayList<>();
        arguments.add(new Argument(EditorView.class.getSimpleName(), "view"));
        arguments.add(new Argument("EditorFactory", "editorFactory"));
        arguments.add(new Argument(SelectionManager.class.getSimpleName(), "selectionManager"));
        arguments.add(new Argument(EmptyPropertiesPanelPresenter.class.getSimpleName(), "emptyPropertiesPanelPresenter"));

        StringBuilder constructorBody = new StringBuilder(
                "super(view);\n" +
                "\n" +
                "EditorState<State> state = new EditorState<>(State.CREATING_NOTING);\n" +
                "\n" +
                "this.workspace = editorFactory.createWorkspace(state, selectionManager);\n" +
                "this.toolbar = editorFactory.createToolbar(state);\n" +
                "PropertiesPanelManager propertiesPanelManager = editorFactory.createPropertiesPanelManager(view.getPropertiesPanel());\n" +
                "propertiesPanelManager.register(null, emptyPropertiesPanelPresenter);\n"
        );

        String propertiesPanelPackage = clientPackage + PROPERTIES_PANEL_FOLDER + '.';
        String elementsPackage = clientPackage + ELEMENTS_FOLDER + '.';

        for (Element element : configuration.getDiagramConfiguration().getElements()) {
            String elementName = element.getName();
            String elementPropertiesPanelName = elementName + PROPERTIES_PANEL_PRESENTER_NAME;
            String elementPropertiesPanelArgument = changeFirstSymbolToLowCase(elementPropertiesPanelName);

            editorPresenterBuilder.addImport(propertiesPanelPackage + elementName.toLowerCase() + '.' + elementPropertiesPanelName);
            editorPresenterBuilder.addImport(elementsPackage + elementName);

            arguments.add(new Argument(elementPropertiesPanelName, elementPropertiesPanelArgument));

            constructorBody.append("propertiesPanelManager.register(")
                           .append(elementName).append(".class, ")
                           .append(elementPropertiesPanelArgument)
                           .append(");\n");
        }

        // TODO We don't have connection properties for now
//        for (Connection connection : configurationKeeper.getDiagramConfiguration().getConnections()) {
//            String connectionName = connection.getName();
//            String connectionPropertiesPanelName = connectionName + PROPERTIES_PANEL_PRESENTER_NAME;
//            String connectionPropertiesPanelArgument = changeFirstSymbolToLowCase(connectionPropertiesPanelName);
//
//            editorPresenterBuilder.addImport(propertiesPanelPackage + connectionName.toLowerCase() + '.' + connectionName);
//
//            arguments.add(new Argument(connectionPropertiesPanelName, connectionPropertiesPanelArgument));
//
//            constructorBody.append("propertiesPanelManager.register(")
//                           .append(connectionName).append(".class, ")
//                           .append(connectionPropertiesPanelArgument)
//                           .append(");\n");
//        }

        constructorBody.append("selectionManager.addListener(propertiesPanelManager);\n");

        String editorPresenter = editorPresenterBuilder
                .addConstructor((Argument[])arguments.toArray(new Argument[arguments.size()]))
                .withConstructorBody(constructorBody.toString())

                .addMethod("serialize").withReturnType(String.class).withMethodAnnotation("@Override").withMethodBody("return \"\";")

                .addMethod("deserialize").withMethodAnnotation("@Override")
                .withMethodArguments(new Argument(String.class.getSimpleName(), "content"))

                .build();

        Path editorPresenterJavaClassPath = Paths.get(clientPackageFolder, editorName + ".java");
        Files.write(editorPresenterJavaClassPath, editorPresenter.getBytes());
    }

    @Nonnull
    private String changeFirstSymbolToLowCase(@Nonnull String name) {
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    private void createWorkspace(@Nonnull String javaFolder,
                                 @Nonnull String clientPackageFolder,
                                 @Nonnull String packageName,
                                 @Nonnull Configuration configuration) throws IOException {
        String workspacePackage = packageName + '.' + CLIENT_PART_FOLDER + '.' + WORKSPACE_FOLDER;

        Path workspacePresenterSource = Paths.get(javaFolder, WORKSPACE_PRESENTER_NAME + ".java");
        SourceCodeBuilder workspaceViewBuilder = sourceCodeBuilderProvider
                .get()
                .newClass(workspacePackage + '.' + WORKSPACE_VIEW_NAME).withAbstractClassPrefix()
                .baseClass(AbstractWorkspaceView.class).withClassAnnotation("@ImplementedBy(" + WORKSPACE_VIEW_IMPL_NAME + ".class)")
                .addImport(ImplementedBy.class);

        SourceCodeBuilder workspaceViewImplBuilder = sourceCodeBuilderProvider
                .get()
                .newClass(workspacePackage + '.' + WORKSPACE_VIEW_IMPL_NAME).baseClass(WORKSPACE_VIEW_NAME)

                .addImport(Inject.class)
                .addImport(Shape.class)
                .addImport(ShapeWidget.class)
                .addImport(NativeEvent.class)
                .addImport(ClickEvent.class)
                .addImport(ClickHandler.class)
                .addImport(ContextMenuEvent.class)
                .addImport(ContextMenuHandler.class)
                .addImport(MouseDownEvent.class)
                .addImport(MouseDownHandler.class)
                .addImport(MouseMoveEvent.class)
                .addImport(MouseMoveHandler.class)
                .addImport(UiBinder.class)
                .addImport(UiField.class)
                .addImport(Provider.class)
                .addImport(HashMap.class)
                .addImport(Map.class)
                .addImport(Widget.class)

                .addField("mainPanel", FlowPanel.class).withFieldAnnotation("@UiField").withFieldAccessLevel(DEFAULT)
                .addField("controller", DiagramController.class).withFieldAccessLevel(PRIVATE)
                .addField("dragController", PickupDragController.class).withFieldAccessLevel(PRIVATE)
                .addField("widgetProvider", "Provider<ShapeWidget>").withFieldAccessLevel(PRIVATE)
                .addField("elements", "Map<String, Widget>").withFieldAccessLevel(PRIVATE)

                .addConstructor(new Argument("WorkspaceViewImplUiBinder", "ourUiBinder"),
                                new Argument("Provider<ShapeWidget>", "widgetProvider"))
                .withConstructorAnnotation("@Inject")
                .withConstructorBody("this.widgetProvider = widgetProvider;\n" +
                                     "this.elements = new HashMap<>();\n" +
                                     "\n" +
                                     "widget = ourUiBinder.createAndBindUi(this);\n" +
                                     "\n" +
                                     "controller = new DiagramController(400, 400);\n" +
                                     "mainPanel.add(controller.getView());\n" +
                                     "controller.showGrid(true);\n" +
                                     "\n" +
                                     "dragController = new PickupDragController(controller.getView(), true);\n" +
                                     "controller.registerDragController(dragController);\n" +
                                     "\n" +
                                     "bind();")

                .addMethod("bind").withMethodAccessLevel(PRIVATE)
                .withMethodBody("mainPanel.addDomHandler(new ClickHandler() {\n" +
                                "    @Override\n" +
                                "    public void onClick(ClickEvent event) {\n" +
                                "        delegate.onLeftMouseButtonClicked(event.getClientX(), event.getClientY());\n" +
                                "    }\n" +
                                "}, ClickEvent.getType());\n" +
                                "\n" +
                                "mainPanel.addDomHandler(new ContextMenuHandler() {\n" +
                                "    @Override\n" +
                                "    public void onContextMenu(ContextMenuEvent event) {\n" +
                                "        NativeEvent nativeEvent = event.getNativeEvent();\n" +
                                "        delegate.onRightMouseButtonClicked(nativeEvent.getClientX(), nativeEvent.getClientY());\n" +
                                "    }\n" +
                                "}, ContextMenuEvent.getType());\n" +
                                "\n" +
                                "mainPanel.addDomHandler(new MouseMoveHandler() {\n" +
                                "   @Override\n" +
                                "   public void onMouseMove(MouseMoveEvent event) {\n" +
                                "        delegate.onMouseMoved(event.getClientX(), event.getClientY());\n" +
                                "   }\n" +
                                "}, MouseMoveEvent.getType());")

                .addMethod("addElement").withMethodAccessLevel(PRIVATE)
                .withMethodArguments(new Argument("int", "x"), new Argument("int", "y"), new Argument("final Shape", "element"))
                .withMethodBody("ShapeWidget elementWidget = widgetProvider.get();\n" +
                                "\n" +
                                "elementWidget.setTitle(element.getTitle());\n" +
                                "elementWidget.addDomHandler(new MouseDownHandler() {\n" +
                                "    @Override\n" +
                                "    public void onMouseDown(MouseDownEvent event) {\n" +
                                "        delegate.onDiagramElementClicked(element.getId());\n" +
                                "    }\n" +
                                "}, MouseDownEvent.getType());\n" +
                                "\n" +
                                "controller.addWidget(elementWidget, x - 60, y);\n" +
                                "dragController.makeDraggable(elementWidget);\n" +
                                "\n" +
                                "elements.put(element.getId(), elementWidget);");

        SourceCodeBuilder workspaceViewImplBinderBuilder = sourceCodeBuilderProvider
                .get()
                .newClass(workspacePackage + '.' + WORKSPACE_VIEW_IMPL_BINDER_NAME)
                .makeInterface().baseClass("UiBinder<Widget, " + WORKSPACE_VIEW_IMPL_NAME + ">")
                .addImport(Widget.class)
                .addImport(UiBinder.class);

        String clientPackage = packageName + '.' + CLIENT_PART_FOLDER + '.';
        String elementsPackage = clientPackage + ELEMENTS_FOLDER + '.';
        String stateClassImport = "import static " + clientPackage + EDITOR_STATE_NAME + '.';
        Argument argumentX = new Argument("int", "x");
        Argument argumentY = new Argument("int", "y");

        StringBuilder createElements = new StringBuilder();
        StringBuilder staticImports = new StringBuilder();
        StringBuilder imports = new StringBuilder();
        staticImports.append(stateClassImport).append(CREATE_NOTING_STATE).append(";\n");
        boolean firstStep = true;

        for (Element element : configuration.getDiagramConfiguration().getElements()) {
            String elementName = element.getName();

            String upperCaseName = elementName.toUpperCase();
            String argumentName = changeFirstSymbolToLowCase(elementName);

            String createElementState = String.format(CREATE_ELEMENT_STATE_FORMAT, upperCaseName);

            String methodName = "add" + elementName;
            String elementPackage = elementsPackage + elementName;

            Argument argumentElement = new Argument(elementName, "element");

            workspaceViewBuilder.addImport(elementPackage)
                                .addMethod(methodName).withAbstractMethodPrefix()
                                .withMethodArguments(argumentX, argumentY, argumentElement);

            workspaceViewImplBuilder.addImport(elementPackage)
                                    .addMethod(methodName).withMethodAnnotation("@Override")
                                    .withMethodArguments(argumentX, argumentY, argumentElement)
                                    .withMethodBody("addElement(x, y, element);\n");

            if (!firstStep) {
                createElements.append(OFFSET).append(OFFSET).append(OFFSET);
            } else {
                firstStep = false;
            }

            createElements.append("case ").append(createElementState).append(":\n")
                          .append(OFFSET).append(OFFSET).append(OFFSET).append(OFFSET)
                          .append(elementName).append(' ').append(argumentName).append(" = new ").append(elementName).append("();\n\n")
                          .append(OFFSET).append(OFFSET).append(OFFSET).append(OFFSET)
                          .append("((WorkspaceView)view).").append(methodName).append("(x, y, ").append(argumentName).append(");\n")
                          .append(OFFSET).append(OFFSET).append(OFFSET).append(OFFSET)
                          .append("addElement(").append(argumentName).append(");\n\n")
                          .append(OFFSET).append(OFFSET).append(OFFSET).append(OFFSET)
                          .append("setState(").append(CREATE_NOTING_STATE).append(");\n")
                          .append(OFFSET).append(OFFSET).append(OFFSET).append(OFFSET)
                          .append("break;\n");

            staticImports.append(stateClassImport).append(String.format(CREATE_ELEMENT_STATE_FORMAT, upperCaseName)).append(";\n");
            imports.append("import ").append(elementPackage).append(";\n");
        }

        StringBuilder createConnections = new StringBuilder();

        Argument sourceElementIDArgument = new Argument(String.class.getSimpleName(), "sourceElementID");
        Argument targetElementIDArgument = new Argument(String.class.getSimpleName(), "targetElementID");

        for (Connection connection : configuration.getDiagramConfiguration().getConnections()) {
            String connectionName = connection.getName();

            String upperCaseName = connectionName.toUpperCase();
            String argumentName = changeFirstSymbolToLowCase(connectionName);

            String createConnectionSourceState = String.format(CREATE_CONNECTION_SOURCE_STATE_FORMAT, upperCaseName);
            String createConnectionTargetState = String.format(CREATE_CONNECTION_TARGET_STATE_FORMAT, upperCaseName);

            String methodName = "add" + connectionName;
            String connectionPackage = elementsPackage + connectionName;

            workspaceViewBuilder.addImport(connectionPackage)
                                .addMethod(methodName).withAbstractMethodPrefix()
                                .withMethodArguments(sourceElementIDArgument, targetElementIDArgument);

            workspaceViewImplBuilder.addImport(connectionPackage)
                                    .addMethod(methodName).withMethodAnnotation("@Override")
                                    .withMethodArguments(sourceElementIDArgument, targetElementIDArgument)
                                    .withMethodBody("Widget sourceWidget = elements.get(sourceElementID);\n" +
                                                    "Widget targetWidget = elements.get(targetElementID);\n" +
                                                    "\n" +
                                                    "controller.drawStraightArrowConnection(sourceWidget, targetWidget);\n");

            if (!firstStep) {
                createElements.append(OFFSET).append(OFFSET).append(OFFSET);
            } else {
                firstStep = false;
            }

            createConnections.append("case ").append(createConnectionSourceState).append(":\n")
                             .append(OFFSET).append(OFFSET).append(OFFSET).append(OFFSET)
                             .append("setState(").append(createConnectionTargetState).append(");\n")
                             .append(OFFSET).append(OFFSET).append(OFFSET).append(OFFSET)
                             .append("break;\n")
                             .append(OFFSET).append(OFFSET).append(OFFSET)
                             .append("case ").append(createConnectionTargetState).append(":\n")
                             .append(OFFSET).append(OFFSET).append(OFFSET).append(OFFSET)
                             .append("((WorkspaceView)view).").append(methodName).append("(prevSelectedElement, selectedElement);\n")
                             .append(OFFSET).append(OFFSET).append(OFFSET).append(OFFSET)
                             .append("source = elements.get(prevSelectedElement);\n")
                             .append(OFFSET).append(OFFSET).append(OFFSET).append(OFFSET)
                             .append(connectionName).append(' ').append(argumentName).append(" = new ")
                             .append(connectionName).append("((Shape)source, (Shape)element);\n")
                             .append(OFFSET).append(OFFSET).append(OFFSET).append(OFFSET)
                             .append("elements.put(element.getId(), element);\n\n")
                             .append(OFFSET).append(OFFSET).append(OFFSET).append(OFFSET)
                             .append("parent = source.getParent();\n")
                             .append(OFFSET).append(OFFSET).append(OFFSET).append(OFFSET)
                             .append("if (parent != null) {\n")
                             .append(OFFSET).append(OFFSET).append(OFFSET).append(OFFSET).append(OFFSET)
                             .append("parent.addElement(").append(argumentName).append(");\n")
                             .append(OFFSET).append(OFFSET).append(OFFSET).append(OFFSET).append("}\n")
                             .append(OFFSET).append(OFFSET).append(OFFSET).append(OFFSET)
                             .append("setState(").append(CREATE_NOTING_STATE).append(");\n")
                             .append(OFFSET).append(OFFSET).append(OFFSET).append(OFFSET)
                             .append("break;\n");

            staticImports.append(stateClassImport).append(String.format(CREATE_CONNECTION_SOURCE_STATE_FORMAT, upperCaseName))
                         .append(";\n");
            staticImports.append(stateClassImport).append(String.format(CREATE_CONNECTION_TARGET_STATE_FORMAT, upperCaseName))
                         .append(";\n");

            imports.append("import ").append(connectionPackage).append(";\n");
        }

        UIXmlBuilder uiXmlBuilder = uiXmlBuilderProvider
                .get()
                .withXmlns("g", "urn:import:com.google.gwt.user.client.ui")

                .withField(fieldProvider.get().withName("res").withType(clientPackage + EDITOR_RESOURCES_NAME))

                .setWidget(
                        scrollPanelProvider.get()
                                           .withPrefix("g")
                                           .withAddStyle("res.editorCSS.fullSize")
                                           .withWidget(
                                                   flowPanelProvider
                                                           .get()
                                                           .withPrefix("g")
                                                           .withName("mainPanel")
                                                           .withAddStyle("res.editorCSS.fullSize")
                                                      )
                          );

        String workspacePresenterContent = new String(Files.readAllBytes(workspacePresenterSource))
                .replaceAll(MAIN_PACKAGE_MASK, packageName)
                .replaceAll(CURRENT_PACKAGE_MASK, workspacePackage)
                .replaceAll(STATIC_IMPORT_MASK, staticImports.toString())
                .replaceAll(IMPORT_MASK, imports.toString())
                .replaceAll(CREATE_GRAPHIC_ELEMENTS_MASK, createElements.toString())
                .replaceAll(CREATE_GRAPHIC_CONNECTIONS_MASK, createConnections.toString());
        Files.delete(workspacePresenterSource);

        Files.createDirectories(Paths.get(clientPackageFolder, WORKSPACE_FOLDER));

        Path workspacePresenterJavaClassPath = Paths.get(clientPackageFolder, WORKSPACE_FOLDER, WORKSPACE_PRESENTER_NAME + ".java");
        Files.write(workspacePresenterJavaClassPath, workspacePresenterContent.getBytes());

        Path workspaceViewJavaClassPath = Paths.get(clientPackageFolder, WORKSPACE_FOLDER, WORKSPACE_VIEW_NAME + ".java");
        Files.write(workspaceViewJavaClassPath, workspaceViewBuilder.build().getBytes());

        Path workspaceViewImplJavaClassPath = Paths.get(clientPackageFolder, WORKSPACE_FOLDER, WORKSPACE_VIEW_IMPL_NAME + ".java");
        Files.write(workspaceViewImplJavaClassPath, workspaceViewImplBuilder.build().getBytes());

        Path workspaceViewBinderJavaClassPath = Paths.get(clientPackageFolder, WORKSPACE_FOLDER, WORKSPACE_VIEW_IMPL_BINDER_NAME + ".java");
        Files.write(workspaceViewBinderJavaClassPath, workspaceViewImplBinderBuilder.build().getBytes());

        Path workspaceUiXMLPath = Paths.get(clientPackageFolder, WORKSPACE_FOLDER, WORKSPACE_VIEW_IMPL_NAME + ".ui.xml");
        Files.write(workspaceUiXMLPath, uiXmlBuilder.build().getBytes());
    }

    private void createToolbar(@Nonnull String javaFolder,
                               @Nonnull String clientPackageFolder,
                               @Nonnull String packageName,
                               @Nonnull Configuration configuration) throws IOException {
        String clientPackage = packageName + '.' + CLIENT_PART_FOLDER + '.';
        String toolbarPackage = clientPackage + TOOLBAR_FOLDER;
        String stateClassImport = "import static " + clientPackage + EDITOR_STATE_NAME + '.';
        Path toolbarPresenterSource = Paths.get(javaFolder, TOOLBAR_PRESENTER_NAME + ".java");

        SourceCodeBuilder toolbarViewBuilder = sourceCodeBuilderProvider
                .get()
                .newClass(toolbarPackage + '.' + TOOLBAR_VIEW_NAME).baseClass("AbstractView<" + ACTION_DELEGATE_NAME + ">")
                .withAbstractClassPrefix().withClassAnnotation("@ImplementedBy(" + TOOLBAR_VIEW_IMPL_NAME + ".class)")

                .addImport(ImplementedBy.class)
                .addImport(AbstractView.class);

        SourceCodeBuilder actionDelegateBuilder = sourceCodeBuilderProvider
                .get()
                .newClass(toolbarPackage + '.' + ACTION_DELEGATE_NAME).makeInterface().baseClass("AbstractView.ActionDelegate")
                .addImport(AbstractView.class);

        SourceCodeBuilder toolbarViewImplBuilder = sourceCodeBuilderProvider
                .get()
                .newClass(toolbarPackage + '.' + TOOLBAR_VIEW_IMPL_NAME).baseClass(TOOLBAR_VIEW_NAME)
                .addImport(UiField.class)
                .addImport(ClickEvent.class)
                .addImport(UiHandler.class)
                .addImport(Widget.class)
                .addImport(PushButton.class)
                .addImport(Inject.class)
                .addImport(clientPackage + EDITOR_RESOURCES_NAME);

        SourceCodeBuilder toolbarViewImplBinderBuilder = sourceCodeBuilderProvider
                .get()
                .newClass(toolbarPackage + '.' + TOOLBAR_VIEW_IMPL_BINDER_NAME)
                .makeInterface().baseClass("UiBinder<Widget, " + TOOLBAR_VIEW_IMPL_NAME + ">")
                .addImport(Widget.class)
                .addImport(UiBinder.class);

        GDockLayoutPanel dockLayoutPanel = dockLayoutPanelProvider.get().withPrefix("g");
        UIXmlBuilder uiXmlBuilder = uiXmlBuilderProvider.get()
                                                        .withXmlns("g", "urn:import:com.google.gwt.user.client.ui")
                                                        .withStyle(
                                                                styleProvider.get()
                                                                             .withStyle("fullSize", "width: 100%; height: 100%;")
                                                                  )
                                                        .setWidget(dockLayoutPanel);

        StringBuilder staticImports = new StringBuilder();
        StringBuilder changeStates = new StringBuilder();
        StringBuilder viewConstructor = new StringBuilder();
        Argument argumentClickEvent = new Argument("ClickEvent", "event");

        boolean firstStep = true;

        for (Element element : configuration.getDiagramConfiguration().getElements()) {
            String elementName = element.getName();

            String upperCaseName = elementName.toUpperCase();
            String argumentName = changeFirstSymbolToLowCase(elementName);

            String createElementState = String.format(CREATE_ELEMENT_STATE_FORMAT, upperCaseName);
            String methodName = "on" + elementName + "ButtonClicked";

            if (!firstStep) {
                changeStates.append(OFFSET);
            } else {
                firstStep = false;
            }

            changeStates.append("@Override\n")
                        .append(OFFSET).append("public void ").append(methodName).append("() {\n")
                        .append(OFFSET).append(OFFSET).append("setState(").append(createElementState).append(");\n")
                        .append(OFFSET).append("}\n");

            staticImports.append(stateClassImport).append(String.format(CREATE_ELEMENT_STATE_FORMAT, upperCaseName)).append(";\n");

            actionDelegateBuilder.addMethod(methodName).withMethodAccessLevel(DEFAULT);

            toolbarViewImplBuilder.addField(argumentName, PushButton.class)
                                  .withFieldAnnotation("@UiField(provided = true)").withFieldAccessLevel(DEFAULT)
                                  .addMethod(methodName)
                                  .withMethodAnnotation("@UiHandler(\"" + argumentName + "\")").withMethodArguments(argumentClickEvent)
                                  .withMethodBody("delegate." + methodName + "();");

            viewConstructor.append(argumentName).append(" = new PushButton();\n");

            dockLayoutPanel.withNorth(50, pushButtonProvider.get()
                                                            .withPrefix("g")
                                                            .withName(argumentName)
                                                            .withAddStyle("style.fullSize")
                                     );
        }

        for (Connection connection : configuration.getDiagramConfiguration().getConnections()) {
            String connectionName = connection.getName();

            String upperCaseName = connectionName.toUpperCase();
            String argumentName = changeFirstSymbolToLowCase(connectionName);

            String createConnectionSourceState = String.format(CREATE_CONNECTION_SOURCE_STATE_FORMAT, upperCaseName);
            String methodName = "on" + connectionName + "ButtonClicked";

            if (!firstStep) {
                changeStates.append(OFFSET);
            } else {
                firstStep = false;
            }

            changeStates.append("@Override\n")
                        .append(OFFSET).append("public void ").append(methodName).append("() {\n")
                        .append(OFFSET).append(OFFSET).append("setState(").append(createConnectionSourceState).append(");\n")
                        .append(OFFSET).append("}\n");

            staticImports.append(stateClassImport).append(String.format(CREATE_CONNECTION_SOURCE_STATE_FORMAT, upperCaseName))
                         .append(";\n");

            actionDelegateBuilder.addMethod(methodName).withMethodAccessLevel(DEFAULT);

            toolbarViewImplBuilder.addField(argumentName, PushButton.class)
                                  .withFieldAnnotation("@UiField(provided = true)").withFieldAccessLevel(DEFAULT)
                                  .addMethod(methodName)
                                  .withMethodAnnotation("@UiHandler(\"" + argumentName + "\")").withMethodArguments(argumentClickEvent)
                                  .withMethodBody("delegate." + methodName + "();");

            viewConstructor.append(argumentName).append(" = new PushButton();\n");

            dockLayoutPanel.withNorth(50, pushButtonProvider.get()
                                                            .withPrefix("g")
                                                            .withName(argumentName)
                                                            .withAddStyle("style.fullSize")
                                     );
        }

        viewConstructor.append("widget = ourUiBinder.createAndBindUi(this);\n");

        toolbarViewImplBuilder.addConstructor(new Argument(TOOLBAR_VIEW_IMPL_BINDER_NAME, "ourUiBinder"),
                                              new Argument(EDITOR_RESOURCES_NAME, "resources"))
                              .withConstructorAnnotation("@Inject")
                              .withConstructorBody(viewConstructor.toString());

        String toolbarPresenterContent = new String(Files.readAllBytes(toolbarPresenterSource))
                .replaceAll(MAIN_PACKAGE_MASK, packageName)
                .replaceAll(CURRENT_PACKAGE_MASK, toolbarPackage)
                .replaceAll(STATIC_IMPORT_MASK, staticImports.toString())
                .replaceAll(CHANGE_EDITOR_STATE_MASK, changeStates.toString());
        Files.delete(toolbarPresenterSource);

        Files.createDirectories(Paths.get(clientPackageFolder, TOOLBAR_FOLDER));

        Path toolbarPresenterJavaClassPath = Paths.get(clientPackageFolder, TOOLBAR_FOLDER, TOOLBAR_PRESENTER_NAME + ".java");
        Files.write(toolbarPresenterJavaClassPath, toolbarPresenterContent.getBytes());

        Path toolbarViewJavaClassPath = Paths.get(clientPackageFolder, TOOLBAR_FOLDER, TOOLBAR_VIEW_NAME + ".java");
        Files.write(toolbarViewJavaClassPath, toolbarViewBuilder.build().getBytes());

        Path toolbarViewImplJavaClassPath = Paths.get(clientPackageFolder, TOOLBAR_FOLDER, TOOLBAR_VIEW_IMPL_NAME + ".java");
        Files.write(toolbarViewImplJavaClassPath, toolbarViewImplBuilder.build().getBytes());

        Path toolbarViewImplBinderJavaClassPath = Paths.get(clientPackageFolder, TOOLBAR_FOLDER, TOOLBAR_VIEW_IMPL_BINDER_NAME + ".java");
        Files.write(toolbarViewImplBinderJavaClassPath, toolbarViewImplBinderBuilder.build().getBytes());

        Path actionDelegateJavaClassPath = Paths.get(clientPackageFolder, TOOLBAR_FOLDER, ACTION_DELEGATE_NAME + ".java");
        Files.write(actionDelegateJavaClassPath, actionDelegateBuilder.build().getBytes());

        Path toolbarUiXMLPath = Paths.get(clientPackageFolder, TOOLBAR_FOLDER, TOOLBAR_VIEW_IMPL_NAME + ".ui.xml");
        Files.write(toolbarUiXMLPath, uiXmlBuilder.build().getBytes());
    }

    private void createPropertiesPanel(@Nonnull String clientPackageFolder,
                                       @Nonnull String packageName,
                                       @Nonnull Configuration configuration) throws IOException {
        String clientPackage = packageName + '.' + CLIENT_PART_FOLDER + '.';
        String propertiesPanelPackage = clientPackage + PROPERTIES_PANEL_FOLDER + '.';
        String elementsPackage = clientPackage + ELEMENTS_FOLDER + '.';

        Files.createDirectories(Paths.get(clientPackageFolder, PROPERTIES_PANEL_FOLDER));

        for (Element element : configuration.getDiagramConfiguration().getElements()) {
            String elementName = element.getName();
            String lowerCaseName = elementName.toLowerCase();
            String propertiesPanelPresenterName = elementName + PROPERTIES_PANEL_PRESENTER_NAME;
            String propertiesPanelViewName = elementName + PROPERTIES_PANEL_VIEW_NAME;
            String propertiesPanelViewImplName = elementName + PROPERTIES_PANEL_VIEW_IMPL_NAME;
            String propertiesPanelViewBinderName = elementName + PROPERTIES_PANEL_VIEW_IMPL_BINDER_NAME;

            SourceCodeBuilder propertiesPanelPresenter = sourceCodeBuilderProvider
                    .get()
                    .newClass(propertiesPanelPackage + lowerCaseName + '.' + propertiesPanelPresenterName)
                    .baseClass("AbstractPropertiesPanel<" + elementName + ">").implementInterface(ACTION_DELEGATE_NAME)

                    .addImport(Inject.class)
                    .addImport(AbstractPropertiesPanel.class)
                    .addImport(AcceptsOneWidget.class)
                    .addImport(elementsPackage + elementName)

                    .addConstructor(new Argument(propertiesPanelViewName, "view"))
                    .withConstructorAnnotation("@Inject").withConstructorBody("super(view);");

            SourceCodeBuilder propertiesPanelView = sourceCodeBuilderProvider
                    .get()
                    .newClass(propertiesPanelPackage + lowerCaseName + '.' + propertiesPanelViewName)
                    .withAbstractClassPrefix().baseClass("AbstractView<" + ACTION_DELEGATE_NAME + ">")
                    .withClassAnnotation("@ImplementedBy(" + propertiesPanelViewImplName + ".class)")

                    .addImport(ImplementedBy.class)
                    .addImport(AbstractView.class);

            SourceCodeBuilder propertiesPanelViewImpl = sourceCodeBuilderProvider
                    .get()
                    .newClass(propertiesPanelPackage + lowerCaseName + '.' + propertiesPanelViewImplName).baseClass(propertiesPanelViewName)

                    .addConstructor(new Argument(propertiesPanelViewBinderName, "ourUiBinder"))
                    .withConstructorAnnotation("@Inject").withConstructorBody("widget = ourUiBinder.createAndBindUi(this);")

                    .addImport(Inject.class)
                    .addImport(KeyUpEvent.class)
                    .addImport(UiField.class)
                    .addImport(UiHandler.class)
                    .addImport(TextBox.class);

            SourceCodeBuilder propertiesPanelViewBinder = sourceCodeBuilderProvider
                    .get()
                    .newClass(propertiesPanelPackage + lowerCaseName + '.' + propertiesPanelViewBinderName)
                    .makeInterface().baseClass("UiBinder<Widget, " + propertiesPanelViewImplName + ">")
                    .addImport(Widget.class)
                    .addImport(UiBinder.class);

            SourceCodeBuilder actionDelegate = sourceCodeBuilderProvider
                    .get()
                    .newClass(propertiesPanelPackage + lowerCaseName + '.' + ACTION_DELEGATE_NAME)
                    .makeInterface().baseClass("AbstractView.ActionDelegate")
                    .addImport(AbstractView.class);

            GDockLayoutPanel dockLayoutPanel = dockLayoutPanelProvider.get().withPrefix("g");
            UIXmlBuilder uiXmlBuilder = uiXmlBuilderProvider
                    .get()
                    .withXmlns("g", "urn:import:com.google.gwt.user.client.ui")
                    .withField(
                            fieldProvider.get()
                                         .withName("res")
                                         .withType(clientPackage + EDITOR_RESOURCES_NAME)
                              )
                    .setWidget(dockLayoutPanel);

            StringBuilder presenterGoMethodBody = new StringBuilder("super.go(container);\n");
            Argument keyUpEventArgument = new Argument(KeyUpEvent.class.getSimpleName(), "event");

            for (Property property : element.getProperties()) {
                String propertyName = property.getName();
                String argumentName = changeFirstSymbolToLowCase(propertyName);
                Class javaClass = convertPropertyTypeToJavaClass(property);

                propertiesPanelPresenter
                        .addMethod("on" + propertyName + "Changed").withMethodAnnotation("@Override")
                        .withMethodBody(
                                "element.set" + propertyName + "(((" + propertiesPanelViewName + ")view).get" + propertyName + "());");

                presenterGoMethodBody.append("((").append(propertiesPanelViewName).append(")view).set").append(propertyName)
                                     .append("(element.get").append(propertyName).append("());\n");

                propertiesPanelView
                        .addMethod("get" + propertyName)
                        .withAbstractMethodPrefix().withReturnType(javaClass)

                        .addMethod("set" + propertyName)
                        .withAbstractMethodPrefix().withMethodArguments(new Argument(javaClass.getSimpleName(), argumentName));

                propertiesPanelViewImpl
                        .addField(argumentName, TextBox.class).withFieldAnnotation("@UiField").withFieldAccessLevel(DEFAULT)

                        .addMethod("get" + propertyName)
                        .withReturnType(javaClass).withMethodAnnotation("@Override")
                        .withMethodBody("return " + javaClass.getSimpleName() + ".valueOf(" + argumentName + ".getText());")

                        .addMethod("set" + propertyName)
                        .withMethodAnnotation("@Override").withMethodArguments(new Argument(javaClass.getSimpleName(), argumentName))
                        .withMethodBody("this." + argumentName + ".setText(" + argumentName + ".toString());")

                        .addMethod("on" + propertyName + "Changed")
                        .withMethodAnnotation("@UiHandler(\"" + argumentName + "\")")
                        .withMethodArguments(keyUpEventArgument).withMethodBody("delegate.on" + propertyName + "Changed();");

                actionDelegate.addMethod("on" + propertyName + "Changed").withMethodAccessLevel(DEFAULT);

                dockLayoutPanel.withNorth(50,
                                          flowPanelProvider
                                                  .get()
                                                  .withPrefix("g")
                                                  .withWidget(
                                                          labelProvider
                                                                  .get()
                                                                  .withPrefix("g")
                                                                  .withText(elementName)
                                                             )
                                                  .withWidget(
                                                          textBoxProvider
                                                                  .get()
                                                                  .withPrefix("g")
                                                                  .withName(argumentName)
                                                                  .withWidth("120px")
                                                             )
                                         );
            }

            propertiesPanelPresenter.addMethod("go").withMethodAnnotation("@Override")
                                    .withMethodArguments(new Argument(AcceptsOneWidget.class.getSimpleName(), "container"))
                                    .withMethodBody(presenterGoMethodBody.toString());

            Files.createDirectories(Paths.get(clientPackageFolder, PROPERTIES_PANEL_FOLDER, lowerCaseName));

            Path propertiesPanelPresenterJavaClassPath =
                    Paths.get(clientPackageFolder, PROPERTIES_PANEL_FOLDER, lowerCaseName, propertiesPanelPresenterName + ".java");
            Files.write(propertiesPanelPresenterJavaClassPath, propertiesPanelPresenter.build().getBytes());

            Path propertiesPanelViewJavaClassPath =
                    Paths.get(clientPackageFolder, PROPERTIES_PANEL_FOLDER, lowerCaseName, propertiesPanelViewName + ".java");
            Files.write(propertiesPanelViewJavaClassPath, propertiesPanelView.build().getBytes());

            Path propertiesPanelViewImplJavaClassPath =
                    Paths.get(clientPackageFolder, PROPERTIES_PANEL_FOLDER, lowerCaseName, propertiesPanelViewImplName + ".java");
            Files.write(propertiesPanelViewImplJavaClassPath, propertiesPanelViewImpl.build().getBytes());

            Path propertiesPanelViewBinderJavaClassPath =
                    Paths.get(clientPackageFolder, PROPERTIES_PANEL_FOLDER, lowerCaseName, propertiesPanelViewBinderName + ".java");
            Files.write(propertiesPanelViewBinderJavaClassPath, propertiesPanelViewBinder.build().getBytes());

            Path actionDelegateJavaClassPath =
                    Paths.get(clientPackageFolder, PROPERTIES_PANEL_FOLDER, lowerCaseName, ACTION_DELEGATE_NAME + ".java");
            Files.write(actionDelegateJavaClassPath, actionDelegate.build().getBytes());

            Path propertiesPanelUiXMLPath =
                    Paths.get(clientPackageFolder, PROPERTIES_PANEL_FOLDER, lowerCaseName, propertiesPanelViewImplName + ".ui.xml");
            Files.write(propertiesPanelUiXMLPath, uiXmlBuilder.build().getBytes());
        }
    }

    private void generateResourcesFolder(@Nonnull String targetPath,
                                         @Nonnull String packageName,
                                         @Nonnull String editorName) throws IOException {
        String resourceFolder = targetPath + File.separator + MAIN_SOURCE_PATH + File.separator + RESOURCES_SOURCE_PATH;
        String mainPackageFolder = resourceFolder + File.separator + convertPathToPackageName(packageName);

        Path gwtModuleSource = Paths.get(resourceFolder, MAIN_GWT_MODULE_FILE_NAME);
        Path gwtModuleTarget = Paths.get(mainPackageFolder, MAIN_GWT_MODULE_FILE_NAME);
        Files.createDirectories(Paths.get(mainPackageFolder));

        String gwtModuleContent = new String(Files.readAllBytes(gwtModuleSource));
        String newGwtModuleContent = gwtModuleContent.replaceAll(EDITOR_NAME_MASK, editorName)
                                                     .replaceAll(ENTRY_POINT_CLASS_MASK, packageName + '.' + ENTRY_POINT_NAME);

        Files.write(gwtModuleTarget, newGwtModuleContent.getBytes());
        Files.delete(gwtModuleSource);

        Path mainCssFileSource = Paths.get(resourceFolder, MAIN_CSS_FILE_NAME);
        Path mainCssFileTarget = Paths.get(mainPackageFolder, CLIENT_PART_FOLDER, MAIN_CSS_FILE_NAME);
        Files.createDirectories(Paths.get(mainPackageFolder, CLIENT_PART_FOLDER));

        // TODO It is the best place for adding custom styles
        Files.move(mainCssFileSource, mainCssFileTarget);
    }

}