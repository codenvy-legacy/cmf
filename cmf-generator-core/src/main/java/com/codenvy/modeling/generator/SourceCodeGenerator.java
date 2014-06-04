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

import com.codenvy.editor.api.editor.AbstractEditor;
import com.codenvy.editor.api.editor.EditorState;
import com.codenvy.editor.api.editor.EditorView;
import com.codenvy.editor.api.editor.SelectionManager;
import com.codenvy.editor.api.editor.elements.AbstractShape;
import com.codenvy.editor.api.editor.propertiespanel.PropertiesPanelManager;
import com.codenvy.editor.api.editor.propertiespanel.empty.EmptyPropertiesPanelPresenter;
import com.codenvy.modeling.configuration.Configuration;
import com.codenvy.modeling.configuration.ConfigurationFactory;
import com.codenvy.modeling.configuration.metamodel.diagram.Component;
import com.codenvy.modeling.configuration.metamodel.diagram.Connection;
import com.codenvy.modeling.configuration.metamodel.diagram.Element;
import com.codenvy.modeling.configuration.metamodel.diagram.Property;
import com.codenvy.modeling.generator.builders.java.SourceCodeBuilder;
import com.codenvy.modeling.generator.builders.propertiespanel.PropertiesPanelPresenterBuilder;
import com.codenvy.modeling.generator.builders.propertiespanel.PropertiesPanelViewBuilder;
import com.codenvy.modeling.generator.builders.propertiespanel.PropertiesPanelViewImplBuilder;
import com.codenvy.modeling.generator.builders.toolbar.ToolbarPresenterBuilder;
import com.codenvy.modeling.generator.builders.toolbar.ToolbarViewBuilder;
import com.codenvy.modeling.generator.builders.toolbar.ToolbarViewImplBuilder;
import com.codenvy.modeling.generator.builders.workspace.WorkspacePresenterBuilder;
import com.codenvy.modeling.generator.builders.workspace.WorkspaceViewBuilder;
import com.codenvy.modeling.generator.builders.workspace.WorkspaceViewImplBuilder;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.orange.links.client.utils.LinksClientBundle;

import javax.annotation.Nonnull;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
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

    private static final String ARTIFACT_ID_MASK       = "artifact_id";
    private static final String GROUP_ID_MASK          = "group_id";
    private static final String ARTIFACT_NAME_MASK     = "artifact_name";
    private static final String EDITOR_NAME_MASK       = "editor_name";
    private static final String ENTRY_POINT_CLASS_MASK = "entry_point";
    private static final String CURRENT_PACKAGE_MASK   = "current_package";
    private static final String CONNECTION_NAME_MASK   = "connection_name";

    private static final String POM_FILE_FULL_NAME        = "pom.xml";
    private static final String MAIN_HTML_FILE_FULL_NAME  = "Editor.html";
    private static final String MAIN_GWT_MODULE_FILE_NAME = "Editor.gwt.xml";
    private static final String MAIN_CSS_FILE_NAME        = "editor.css";

    private static final String MAIN_SOURCE_PATH         = "/src/main";
    private static final String JAVA_SOURCE_PATH         = "java";
    private static final String RESOURCES_SOURCE_PATH    = "resources";
    private static final String WEBAPP_SOURCE_PATH       = "webapp";
    private static final String CLIENT_PART_FOLDER       = "client";
    private static final String INJECT_FOLDER            = "inject";
    private static final String TOOLBAR_FOLDER           = "toolbar";
    private static final String ICONS_FOLDER             = "icons";
    private static final String WORKSPACE_FOLDER         = "workspace";
    private static final String PROPERTIES_PANEL_FOLDER  = "propertiespanel";
    private static final String ELEMENTS_FOLDER          = "elements";
    private static final String CONNECTION_TEMPLATE_NAME = "Connection";

    private static final String ENTRY_POINT_NAME                = "EditorEntryPoint";
    private static final String TOOLBAR_PRESENTER_NAME          = "ToolbarPresenter";
    private static final String WORKSPACE_PRESENTER_NAME        = "WorkspacePresenter";
    private static final String PROPERTIES_PANEL_PRESENTER_NAME = "PropertiesPanelPresenter";
    private static final String EDITOR_STATE_NAME               = "State";
    private static final String EDITOR_RESOURCES_NAME           = "EditorResources";
    private static final String EDITOR_CSS_RESOURCE_NAME        = "EditorCSS";
    private static final String EDITOR_IMAGE_RESOURCE           = "ImageResource";
    private static final String GIN_MODULE_NAME                 = "GinModule";
    private static final String GIN_INJECTOR_NAME               = "Injector";
    private static final String EDITOR_FACTORY_NAME             = "EditorFactory";

    private static final String CREATE_NOTHING_STATE                  = "CREATING_NOTHING";
    private static final String CREATE_ELEMENT_STATE_FORMAT           = "CREATING_%s";
    private static final String CREATE_CONNECTION_SOURCE_STATE_FORMAT = "CREATING_%s_SOURCE";
    private static final String CREATE_CONNECTION_TARGET_STATE_FORMAT = "CREATING_%s_TARGET";

    private final Provider<SourceCodeBuilder>               sourceCodeBuilderProvider;
    private final WorkspacePresenterBuilder                 workspacePresenterBuilder;
    private final WorkspaceViewBuilder                      workspaceViewBuilder;
    private final WorkspaceViewImplBuilder                  workspaceViewImplBuilder;
    private final ToolbarPresenterBuilder                   toolbarPresenterBuilder;
    private final ToolbarViewBuilder                        toolbarViewBuilder;
    private final ToolbarViewImplBuilder                    toolbarViewImplBuilder;
    private final Provider<PropertiesPanelPresenterBuilder> propertiesPanelPresenterBuilder;
    private final Provider<PropertiesPanelViewBuilder>      propertiesPanelViewBuilder;
    private final Provider<PropertiesPanelViewImplBuilder>  propertiesPanelViewImplBuilder;

    private Element mainElement;

    @Inject
    public SourceCodeGenerator(Provider<SourceCodeBuilder> sourceCodeBuilderProvider,

                               WorkspacePresenterBuilder workspacePresenterBuilder,
                               WorkspaceViewBuilder workspaceViewBuilder,
                               WorkspaceViewImplBuilder workspaceViewImplBuilder,
                               ToolbarPresenterBuilder toolbarPresenterBuilder,
                               ToolbarViewBuilder toolbarViewBuilder,
                               ToolbarViewImplBuilder toolbarViewImplBuilder,
                               Provider<PropertiesPanelPresenterBuilder> propertiesPanelPresenterBuilder,
                               Provider<PropertiesPanelViewBuilder> propertiesPanelViewBuilder,
                               Provider<PropertiesPanelViewImplBuilder> propertiesPanelViewImplBuilder) {
// TODO need to clean fields
        this.sourceCodeBuilderProvider = sourceCodeBuilderProvider;

        this.workspacePresenterBuilder = workspacePresenterBuilder;
        this.workspaceViewBuilder = workspaceViewBuilder;
        this.workspaceViewImplBuilder = workspaceViewImplBuilder;
        this.toolbarPresenterBuilder = toolbarPresenterBuilder;
        this.toolbarViewBuilder = toolbarViewBuilder;
        this.toolbarViewImplBuilder = toolbarViewImplBuilder;
        this.propertiesPanelPresenterBuilder = propertiesPanelPresenterBuilder;
        this.propertiesPanelViewBuilder = propertiesPanelViewBuilder;
        this.propertiesPanelViewImplBuilder = propertiesPanelViewImplBuilder;
    }

    /**
     * Generates java source code of GWT editor.
     *
     * @param properties
     *         properties which are needed for code generation
     * @param configurationFactory
     *         provides configuration
     */
    public void generate(@Nonnull Properties properties, @Nonnull ConfigurationFactory configurationFactory) throws IOException {
        Configuration configuration = configurationFactory.getInstance(new ConfigurationFactory.ConfigurationPaths(properties));

        copyProjectHierarchy(properties);

        modifyPom(properties);
        modifyMainHtmlFile(properties);

        generateResourcesFolder(properties);
        generateJavaFolder(properties, configuration);
    }

    private void copyProjectHierarchy(@Nonnull Properties properties) throws IOException {
        String targetPath = properties.getProperty(TARGET_PATH.name());
        String templateDir = properties.getProperty(TEMPLATE_PATH.name());

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

    private void modifyPom(@Nonnull Properties properties) throws IOException {
        String targetPath = properties.getProperty(TARGET_PATH.name());
        String artifactId = properties.getProperty(MAVEN_ARTIFACT_ID.name());
        String groupId = properties.getProperty(MAVEN_GROUP_ID.name());
        String artifactName = properties.getProperty(MAVEN_ARTIFACT_NAME.name());

        Path pomPath = Paths.get(targetPath, POM_FILE_FULL_NAME);

        String pomContent = new String(Files.readAllBytes(pomPath));
        String newContent = pomContent
                .replaceAll(ARTIFACT_ID_MASK, artifactId)
                .replaceAll(GROUP_ID_MASK, groupId)
                .replaceAll(ARTIFACT_NAME_MASK, artifactName);

        Files.write(pomPath, newContent.getBytes());
    }

    private void modifyMainHtmlFile(@Nonnull Properties properties) throws IOException {
        String targetPath = properties.getProperty(TARGET_PATH.name());
        String editorName = properties.getProperty(EDITOR_NAME.name());

        Path mainHtmlFilePath = Paths.get(targetPath, MAIN_SOURCE_PATH, WEBAPP_SOURCE_PATH, MAIN_HTML_FILE_FULL_NAME);

        String content = new String(Files.readAllBytes(mainHtmlFilePath));
        String newContent = content.replaceAll(EDITOR_NAME_MASK, editorName);

        Files.write(mainHtmlFilePath, newContent.getBytes());
    }

    @Nonnull
    private String convertPathToPackageName(@Nonnull String packagePath) {
        return packagePath.replace('.', '/');
    }

    private void generateJavaFolder(@Nonnull Properties properties, @Nonnull Configuration configuration) throws IOException {
        String packageName = properties.getProperty(MAIN_PACKAGE.name());
        String targetPath = properties.getProperty(TARGET_PATH.name());
        String editorName = properties.getProperty(EDITOR_NAME.name());

        String mainPackageFolder = convertPathToPackageName(packageName);

        String javaFolder = targetPath + MAIN_SOURCE_PATH + File.separator + JAVA_SOURCE_PATH;
        String clientPackageFolder = mainPackageFolder + File.separator + CLIENT_PART_FOLDER;
        String clientFolder = javaFolder + File.separator + clientPackageFolder;

        createInjectModule(clientFolder, packageName, editorName);
        createElements(javaFolder, clientFolder, packageName, configuration);
        createMainGWTElements(properties, clientFolder, configuration);
        createWorkspace(targetPath, packageName, configuration);
        createToolbar(targetPath, packageName, configuration);
        createPropertiesPanel(targetPath, packageName, configuration);
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

    private void findMainElement(@Nonnull Configuration configuration) {
        Set<Element> elements = configuration.getDiagramConfiguration().getElements();
        Set<String> subElements = new HashSet<>();

        for (Element element : elements) {
            for (Component component : element.getComponents()) {
                subElements.add(component.getName());
            }
        }

        for (Element element : elements) {
            if (!subElements.contains(element.getName())) {
                mainElement = element;
            }
        }
    }

    private String createFindElementMethod(@Nonnull Configuration configuration) {
        StringBuilder result = new StringBuilder("switch (elementName) {\n");


        for (Iterator<Element> iterator = configuration.getDiagramConfiguration().getElements().iterator(); iterator.hasNext(); ) {
            Element element = iterator.next();

            if (!element.equals(mainElement)) {
                String elementName = element.getName();

                result.append(OFFSET).append("case \"").append(elementName).append("\":\n");
                if (!iterator.hasNext()) {
                    result.append(OFFSET).append("default:\n");
                }

                result.append(OFFSET).append(OFFSET).append("return new ").append(elementName).append("();\n");
            }
        }

        result.append("}\n");

        return result.toString();
    }

    private void createElements(@Nonnull String javaFolder,
                                @Nonnull String clientPackageFolder,
                                @Nonnull String packageName,
                                @Nonnull Configuration configuration) throws IOException {
        findMainElement(configuration);

        Path elementsFolder = Paths.get(clientPackageFolder, ELEMENTS_FOLDER);
        Files.createDirectories(elementsFolder);

        String elementsPackageName = packageName + '.' + CLIENT_PART_FOLDER + '.' + ELEMENTS_FOLDER;
        String mainElementName = mainElement.getName();

        for (Element element : configuration.getDiagramConfiguration().getElements()) {
            String elementName = element.getName();
            SourceCodeBuilder elementClassBuilder = sourceCodeBuilderProvider
                    .get()
                    .newClass(elementsPackageName + '.' + elementName);

            StringBuilder constructor;

            if (!element.equals(mainElement)) {
                elementClassBuilder.baseClass(mainElementName);

                constructor = new StringBuilder("super(\"" + elementName + "\", ");
            } else {
                elementClassBuilder.baseClass(AbstractShape.class)

                                   .addImport(List.class)

                                   .addConstructor(new Argument(String.class.getSimpleName(), "elementName"),
                                                   new Argument("List<String>", "properties"))
                                   .withConstructorBody("super(elementName, properties);\n")

                                   .addImport(NodeList.class)
                                   .addMethod("deserialize").withMethodAnnotation("@Override")
                                   .withMethodArguments(new Argument(Node.class.getSimpleName(), "node"))
                                   .withMethodBody("NodeList childNodes = node.getChildNodes();\n" +
                                                   "\n" +
                                                   "for (int i = 0; i < childNodes.getLength(); i++) {\n" +
                                                   "    Node item = childNodes.item(i);\n" +
                                                   "    String name = item.getNodeName();\n" +
                                                   "\n" +
                                                   "    if (isProperty(name)) {\n" +
                                                   "        applyProperty(item);\n" +
                                                   "    } else {\n" +
                                                   "        Element element = findElement(name);\n" +
                                                   "        element.deserialize(item);\n" +
                                                   "        addElement(element);\n" +
                                                   "    }\n" +
                                                   "}")

                                   .addMethod("findElement").withMethodAccessLevel(PRIVATE)
                                   .withReturnType(com.codenvy.editor.api.editor.elements.Element.class)
                                   .withMethodArguments(new Argument(String.class.getSimpleName(), "elementName"))
                                   .withMethodBody(createFindElementMethod(configuration));

                constructor = new StringBuilder("this(\"" + elementName + "\", ");
            }

            StringBuilder serializePropertiesMethodBody = new StringBuilder("StringBuilder properties = new StringBuilder();\n");
            StringBuilder properties = new StringBuilder();
            StringBuilder initializeFields = new StringBuilder();
            StringBuilder applyPropertyMethod = new StringBuilder("String nodeName = node.getNodeName();\n" +
                                                                  "String nodeValue = node.getChildNodes().item(0).getNodeValue();\n" +
                                                                  "\n" +
                                                                  "switch (nodeName) {\n");
            Set<Property> elementProperties = element.getProperties();

            for (Iterator<Property> iterator = elementProperties.iterator(); iterator.hasNext(); ) {
                Property property = iterator.next();

                Class javaClass = convertPropertyTypeToJavaClass(property);
                String name = property.getName();
                String argumentName = changeFirstSymbolToLowCase(name);

                properties.append('"').append(name).append('"');
                if (iterator.hasNext()) {
                    properties.append(", ");
                }

                applyPropertyMethod.append(OFFSET).append("case \"").append(name).append("\":\n")
                                   .append(OFFSET).append(OFFSET)
                                   .append(argumentName).append(" = ").append(javaClass.getSimpleName()).append(".valueOf(nodeValue);\n")
                                   .append(OFFSET).append("break;\n");

                initializeFields.append(argumentName).append(" = ");
                if (javaClass.equals(String.class)) {
                    initializeFields.append('"').append(property.getValue()).append('"');
                } else {
                    initializeFields.append(property.getValue());
                }
                initializeFields.append(";\n");

                elementClassBuilder
                        .addField(argumentName, javaClass).withFieldAccessLevel(PRIVATE)

                        .addMethod("get" + name)
                        .withMethodAccessLevel(PUBLIC).withReturnType(javaClass).withMethodBody("return " + argumentName + ";")

                        .addMethod("set" + name)
                        .withMethodAccessLevel(PUBLIC).withMethodArguments(new Argument(javaClass.getSimpleName(), argumentName))
                        .withMethodBody("this." + argumentName + " = " + argumentName + ';');

                serializePropertiesMethodBody.append("properties.append('<').append(\"").append(name).append("\").append(\">\\n\")\n")
                                             .append(".append(").append(argumentName).append(").append('\\n')\n")
                                             .append(".append(\"</\").append(\"").append(name).append("\").append(\">\\n\");\n");
            }

            applyPropertyMethod.append("}\n");

            if (elementProperties.isEmpty()) {
                constructor.append("new ArrayList<String>());\n");
                elementClassBuilder.addImport(ArrayList.class);
            } else {
                constructor.append("Arrays.asList(").append(properties).append("));\n");
                elementClassBuilder.addImport(Arrays.class);
            }
            constructor.append(initializeFields);

            serializePropertiesMethodBody.append("return properties.toString();\n");

            elementClassBuilder
                    .addMethod("serializeProperties")
                    .withReturnType(String.class).withMethodAccessLevel(PROTECTED).withMethodAnnotation("@Override")
                    .withMethodBody(serializePropertiesMethodBody.toString())

                    .addImport(Node.class)
                    .addMethod("applyProperty").withMethodAnnotation("@Override")
                    .withMethodArguments(new Argument(Node.class.getSimpleName(), "node"));

            if (!elementProperties.isEmpty()) {
                elementClassBuilder.withMethodBody(applyPropertyMethod.toString());
            }

            elementClassBuilder.addConstructor().withConstructorBody(constructor.toString());

            Path elementJavaClassPath = Paths.get(clientPackageFolder, ELEMENTS_FOLDER, elementName + ".java");
            Files.write(elementJavaClassPath, elementClassBuilder.build().getBytes());
        }

        Path connectionSource = Paths.get(javaFolder, CONNECTION_TEMPLATE_NAME + ".java");

        for (Connection connection : configuration.getDiagramConfiguration().getConnections()) {
            String connectionName = connection.getName();

            String connectionContent = new String(Files.readAllBytes(connectionSource))
                    .replaceAll(CURRENT_PACKAGE_MASK, elementsPackageName)
                    .replaceAll(CONNECTION_NAME_MASK, connectionName);

            Path connectionJavaClassPath = Paths.get(clientPackageFolder, ELEMENTS_FOLDER, connectionName + ".java");
            Files.write(connectionJavaClassPath, connectionContent.getBytes());
        }

        Files.delete(connectionSource);
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

    private void createMainGWTElements(@Nonnull Properties properties,
                                       @Nonnull String clientPackageFolder,
                                       @Nonnull Configuration configuration) throws IOException {
        String packageName = properties.getProperty(MAIN_PACKAGE.name());
        String editorName = properties.getProperty(EDITOR_NAME.name());

        String clientPackage = packageName + '.' + CLIENT_PART_FOLDER + '.';

        SourceCodeBuilder editorStateEnum = sourceCodeBuilderProvider
                .get()
                .newClass(clientPackage + EDITOR_STATE_NAME).makeEnum()
                .withEnumValue(CREATE_NOTHING_STATE);

        for (Element element : configuration.getDiagramConfiguration().getElements()) {
            if (!element.equals(mainElement)) {
                editorStateEnum.withEnumValue(String.format(CREATE_ELEMENT_STATE_FORMAT, element.getName().toUpperCase()));
            }
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

        SourceCodeBuilder editorResources = sourceCodeBuilderProvider
                .get()
                .newClass(clientPackage + EDITOR_RESOURCES_NAME).makeInterface().baseClass(ClientBundle.class)
                .addImport(ImageResource.class)
                .addMethod("editorCSS")
                .withReturnType(EDITOR_CSS_RESOURCE_NAME).withMethodAnnotation("@Source(\"editor.css\")").withMethodAccessLevel(DEFAULT);

        String targetPath = properties.getProperty(TARGET_PATH.name());
        String resourceFolder = targetPath + File.separator + MAIN_SOURCE_PATH + File.separator + RESOURCES_SOURCE_PATH;
        String mainPackageFolder = resourceFolder + File.separator + convertPathToPackageName(packageName);

        File iconsFolder = Paths.get(mainPackageFolder, CLIENT_PART_FOLDER, ICONS_FOLDER).toFile();

        File[] icons = iconsFolder.listFiles();
        if (null != icons) {
            for (File icon : icons) {
                editorResources.addMethod(changeFirstSymbolToLowCase(icon.getName().substring(0, icon.getName().indexOf('.'))))
                               .withMethodAccessLevel(DEFAULT)
                               .withReturnType(EDITOR_IMAGE_RESOURCE)
                               .withMethodAnnotation("@Source(\"" + ICONS_FOLDER + File.separator + icon.getName() + "\")");
            }
        }

        Path editorResourcesJavaClassPath = Paths.get(clientPackageFolder, EDITOR_RESOURCES_NAME + ".java");
        Files.write(editorResourcesJavaClassPath, editorResources.build().getBytes());

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
                .addImport(PropertiesPanelManager.class)
                .addImport(Inject.class);

        List<Argument> arguments = new ArrayList<>();
        arguments.add(new Argument(EditorView.class.getSimpleName(), "view"));
        arguments.add(new Argument("EditorFactory", "editorFactory"));
        arguments.add(new Argument(SelectionManager.class.getSimpleName(), "selectionManager"));
        arguments.add(new Argument(EmptyPropertiesPanelPresenter.class.getSimpleName(), "emptyPropertiesPanelPresenter"));

        StringBuilder constructorBody = new StringBuilder(
                "super(view);\n" +
                "\n" +
                "EditorState<State> state = new EditorState<>(State.CREATING_NOTHING);\n" +
                "\n" +
                "this.workspace = editorFactory.createWorkspace(state, selectionManager);\n" +
                "this.toolbar = editorFactory.createToolbar(state);\n" +
                "PropertiesPanelManager propertiesPanelManager = editorFactory.createPropertiesPanelManager(view.getPropertiesPanel());\n" +
                "propertiesPanelManager.register(null, emptyPropertiesPanelPresenter);\n" +
                "emptyPropertiesPanelPresenter.addListener(this);\n"
        );

        String propertiesPanelPackage = clientPackage + PROPERTIES_PANEL_FOLDER + '.';
        String elementsPackage = clientPackage + ELEMENTS_FOLDER + '.';

        for (Element element : configuration.getDiagramConfiguration().getElements()) {
            if (!element.equals(mainElement)) {
                String elementName = element.getName();
                String elementPropertiesPanelName = elementName + PROPERTIES_PANEL_PRESENTER_NAME;
                String elementPropertiesPanelArgument = changeFirstSymbolToLowCase(elementPropertiesPanelName);

                editorPresenterBuilder.addImport(propertiesPanelPackage + elementName.toLowerCase() + '.' + elementPropertiesPanelName);
                editorPresenterBuilder.addImport(elementsPackage + elementName);

                arguments.add(new Argument(elementPropertiesPanelName, elementPropertiesPanelArgument));

                constructorBody
                        .append("propertiesPanelManager.register(").append(elementName).append(".class, ")
                        .append(elementPropertiesPanelArgument).append(");\n")
                        .append(elementPropertiesPanelArgument).append(".addListener(this);\n");
            }
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

        constructorBody.append("selectionManager.addListener(propertiesPanelManager);\n")
                       .append("workspace.addListener(this);\n");

        String editorPresenter = editorPresenterBuilder
                .addConstructor((Argument[])arguments.toArray(new Argument[arguments.size()]))
                .withConstructorAnnotation("@Inject")
                .withConstructorBody(constructorBody.toString())

                .addMethod("serialize").withReturnType(String.class).withMethodAnnotation("@Override")
                .withMethodBody("return workspace.serialize();")

                .addMethod("deserialize").withMethodAnnotation("@Override")
                .withMethodArguments(new Argument(String.class.getSimpleName(), "content"))
                .withMethodBody("workspace.deserialize(content);")

                .build();

        Path editorPresenterJavaClassPath = Paths.get(clientPackageFolder, editorName + ".java");
        Files.write(editorPresenterJavaClassPath, editorPresenter.getBytes());
    }

    @Nonnull
    private String changeFirstSymbolToLowCase(@Nonnull String name) {
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    private void createWorkspace(@Nonnull String projectPath, @Nonnull String packageName, @Nonnull Configuration configuration)
            throws IOException {
        Set<Element> elements = configuration.getDiagramConfiguration().getElements();
        Set<Connection> connections = configuration.getDiagramConfiguration().getConnections();

        workspacePresenterBuilder.path(projectPath)

                                 .mainPackage(packageName)
                                 .rootElement(mainElement)
                                 .elements(elements)
                                 .connections(connections)

                                 .build();

        workspaceViewBuilder.path(projectPath)

                            .mainPackage(packageName)
                            .rootElement(mainElement)
                            .elements(elements)
                            .connections(connections)

                            .build();

        workspaceViewImplBuilder.path(projectPath)

                                .mainPackage(packageName)
                                .rootElement(mainElement)
                                .elements(elements)
                                .connections(connections)

                                .needRemoveTemplateParentFolder(true)
                                .build();
    }

    private void createToolbar(@Nonnull String projectPath, @Nonnull String packageName, @Nonnull Configuration configuration)
            throws IOException {
        Set<Element> elements = configuration.getDiagramConfiguration().getElements();
        Set<Connection> connections = configuration.getDiagramConfiguration().getConnections();

        toolbarPresenterBuilder.path(projectPath)

                               .mainPackage(packageName)
                               .rootElement(mainElement)
                               .elements(elements)
                               .connections(connections)

                               .build();

        toolbarViewBuilder.path(projectPath)

                          .mainPackage(packageName)
                          .rootElement(mainElement)
                          .elements(elements)
                          .connections(connections)

                          .build();

        toolbarViewImplBuilder.path(projectPath)

                              .mainPackage(packageName)
                              .rootElement(mainElement)
                              .elements(elements)
                              .connections(connections)

                              .needRemoveTemplateParentFolder(true)
                              .build();
    }

    private void createPropertiesPanel(@Nonnull String projectPath, @Nonnull String packageName, @Nonnull Configuration configuration)
            throws IOException {
        Set<Element> elements = configuration.getDiagramConfiguration().getElements();

        for (Iterator<Element> iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = iterator.next();
            Set<Property> properties = element.getProperties();

            boolean hasNext = iterator.hasNext();

            propertiesPanelPresenterBuilder.get()

                                           .needRemoveTemplate(!hasNext)

                                           .path(projectPath)

                                           .mainPackage(packageName)
                                           .element(element)
                                           .properties(properties)

                                           .build();

            propertiesPanelViewBuilder.get()

                                      .needRemoveTemplate(!hasNext)

                                      .path(projectPath)

                                      .mainPackage(packageName)
                                      .element(element)
                                      .properties(properties)

                                      .build();

            propertiesPanelViewImplBuilder.get()

                                          .needRemoveTemplate(!hasNext)
                                          .needRemoveTemplateParentFolder(!hasNext)

                                          .path(projectPath)

                                          .mainPackage(packageName)
                                          .element(element)
                                          .properties(properties)

                                          .build();
        }
    }

    private void generateResourcesFolder(@Nonnull Properties properties) throws IOException {
        String targetPath = properties.getProperty(TARGET_PATH.name());
        String packageName = properties.getProperty(MAIN_PACKAGE.name());
        String editorName = properties.getProperty(EDITOR_NAME.name());
        final String iconsName = properties.getProperty(ConfigurationFactory.PathParameter.STYLE.name());

        String resourceFolder = targetPath + File.separator + MAIN_SOURCE_PATH + File.separator + RESOURCES_SOURCE_PATH;
        final String mainPackageFolder = resourceFolder + File.separator + convertPathToPackageName(packageName);

        Path gwtModuleSource = Paths.get(resourceFolder, MAIN_GWT_MODULE_FILE_NAME);
        Path gwtModuleTarget = Paths.get(mainPackageFolder, MAIN_GWT_MODULE_FILE_NAME);
        Files.createDirectories(Paths.get(mainPackageFolder));

        String gwtModuleContent = new String(Files.readAllBytes(gwtModuleSource));
        String newGwtModuleContent = gwtModuleContent.replaceAll(EDITOR_NAME_MASK, editorName)
                                                     .replaceAll(ENTRY_POINT_CLASS_MASK,
                                                                 packageName + '.' + CLIENT_PART_FOLDER + '.' + ENTRY_POINT_NAME);

        Files.write(gwtModuleTarget, newGwtModuleContent.getBytes());
        Files.delete(gwtModuleSource);

        Files.createDirectories(Paths.get(mainPackageFolder, CLIENT_PART_FOLDER, ICONS_FOLDER));

        Files.walkFileTree(Paths.get(iconsName, ICONS_FOLDER), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path iconsDirectory = Paths.get(mainPackageFolder, CLIENT_PART_FOLDER, ICONS_FOLDER, file.getFileName().toString());
                if (!Files.exists(Paths.get(mainPackageFolder, CLIENT_PART_FOLDER, ICONS_FOLDER, file.getFileName().toString()))) {
                    Files.copy(Paths.get(iconsName, ICONS_FOLDER, file.getFileName().toString()), iconsDirectory);
                }

                return FileVisitResult.CONTINUE;
            }
        });

        Path mainCssFileSource = Paths.get(resourceFolder, MAIN_CSS_FILE_NAME);
        Path mainCssFileTarget = Paths.get(mainPackageFolder, CLIENT_PART_FOLDER, MAIN_CSS_FILE_NAME);
        // TODO It is the best place for adding custom styles
        Files.move(mainCssFileSource, mainCssFileTarget);
    }

}