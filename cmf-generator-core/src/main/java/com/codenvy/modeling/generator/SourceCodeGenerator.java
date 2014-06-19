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

import com.codenvy.modeling.configuration.Configuration;
import com.codenvy.modeling.configuration.ConfigurationFactory;
import com.codenvy.modeling.configuration.metamodel.diagram.Connection;
import com.codenvy.modeling.configuration.metamodel.diagram.Element;
import com.codenvy.modeling.configuration.metamodel.diagram.Property;
import com.codenvy.modeling.generator.builders.elements.ConnectionBuilder;
import com.codenvy.modeling.generator.builders.elements.ElementBuilder;
import com.codenvy.modeling.generator.builders.inject.EditorFactoryBuilder;
import com.codenvy.modeling.generator.builders.inject.GinModuleBuilder;
import com.codenvy.modeling.generator.builders.inject.InjectorBuilder;
import com.codenvy.modeling.generator.builders.maingwt.EditorEntryPointBuilder;
import com.codenvy.modeling.generator.builders.maingwt.EditorPresenterBuilder;
import com.codenvy.modeling.generator.builders.maingwt.EditorResourcesBuilder;
import com.codenvy.modeling.generator.builders.maingwt.StateBuilder;
import com.codenvy.modeling.generator.builders.propertiespanel.PropertiesPanelPresenterBuilder;
import com.codenvy.modeling.generator.builders.propertiespanel.PropertiesPanelViewBuilder;
import com.codenvy.modeling.generator.builders.propertiespanel.PropertiesPanelViewImplBuilder;
import com.codenvy.modeling.generator.builders.resource.ResourcesBuilder;
import com.codenvy.modeling.generator.builders.toolbar.ToolbarPresenterBuilder;
import com.codenvy.modeling.generator.builders.toolbar.ToolbarViewBuilder;
import com.codenvy.modeling.generator.builders.toolbar.ToolbarViewImplBuilder;
import com.codenvy.modeling.generator.builders.workspace.WorkspacePresenterBuilder;
import com.codenvy.modeling.generator.builders.workspace.WorkspaceViewBuilder;
import com.codenvy.modeling.generator.builders.workspace.WorkspaceViewImplBuilder;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Iterator;
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
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.ARTIFACT_ID_MASK;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.ARTIFACT_NAME_MASK;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.EDITOR_NAME_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.GROUP_ID_MASK;
import static com.codenvy.modeling.generator.builders.PathConstants.MAIN_SOURCE_PATH;
import static com.codenvy.modeling.generator.builders.PathConstants.WEBAPP_FOLDER;
import static com.codenvy.modeling.generator.builders.ResourceNameConstants.MAIN_HTML_FILE_FULL_NAME;
import static com.codenvy.modeling.generator.builders.ResourceNameConstants.POM_FILE_FULL_NAME;

/**
 * The main class that provides an ability to generate java source code from given params.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class SourceCodeGenerator {
    private final WorkspacePresenterBuilder                 workspacePresenterBuilder;
    private final WorkspaceViewBuilder                      workspaceViewBuilder;
    private final WorkspaceViewImplBuilder                  workspaceViewImplBuilder;
    private final ToolbarPresenterBuilder                   toolbarPresenterBuilder;
    private final ToolbarViewBuilder                        toolbarViewBuilder;
    private final ToolbarViewImplBuilder                    toolbarViewImplBuilder;
    private final Provider<PropertiesPanelPresenterBuilder> propertiesPanelPresenterBuilder;
    private final Provider<PropertiesPanelViewBuilder>      propertiesPanelViewBuilder;
    private final Provider<PropertiesPanelViewImplBuilder>  propertiesPanelViewImplBuilder;
    private final Provider<ElementBuilder>                  elementBuilderProvider;
    private final Provider<ConnectionBuilder>               connectionBuilderProvider;
    private final EditorFactoryBuilder                      editorFactoryBuilder;
    private final GinModuleBuilder                          ginModuleBuilder;
    private final InjectorBuilder                           injectorBuilder;
    private final ResourcesBuilder                          resourcesBuilder;
    private final EditorEntryPointBuilder                   editorEntryPointBuilder;
    private final EditorPresenterBuilder                    editorPresenterBuilder;
    private final EditorResourcesBuilder                    editorResourcesBuilder;
    private final StateBuilder                              stateBuilder;


    @Inject
    public SourceCodeGenerator(WorkspacePresenterBuilder workspacePresenterBuilder,
                               WorkspaceViewBuilder workspaceViewBuilder,
                               WorkspaceViewImplBuilder workspaceViewImplBuilder,
                               ToolbarPresenterBuilder toolbarPresenterBuilder,
                               ToolbarViewBuilder toolbarViewBuilder,
                               ToolbarViewImplBuilder toolbarViewImplBuilder,
                               Provider<PropertiesPanelPresenterBuilder> propertiesPanelPresenterBuilder,
                               Provider<PropertiesPanelViewBuilder> propertiesPanelViewBuilder,
                               Provider<PropertiesPanelViewImplBuilder> propertiesPanelViewImplBuilder,
                               Provider<ElementBuilder> elementBuilderProvider,
                               Provider<ConnectionBuilder> connectionBuilderProvider,
                               EditorFactoryBuilder editorFactoryBuilder,
                               GinModuleBuilder ginModuleBuilder,
                               InjectorBuilder injectorBuilder,
                               ResourcesBuilder resourcesBuilder,
                               EditorEntryPointBuilder editorEntryPointBuilder,
                               EditorPresenterBuilder editorPresenterBuilder,
                               EditorResourcesBuilder editorResourcesBuilder,
                               StateBuilder stateBuilder) {
        this.workspacePresenterBuilder = workspacePresenterBuilder;
        this.workspaceViewBuilder = workspaceViewBuilder;
        this.workspaceViewImplBuilder = workspaceViewImplBuilder;
        this.toolbarPresenterBuilder = toolbarPresenterBuilder;
        this.toolbarViewBuilder = toolbarViewBuilder;
        this.toolbarViewImplBuilder = toolbarViewImplBuilder;
        this.propertiesPanelPresenterBuilder = propertiesPanelPresenterBuilder;
        this.propertiesPanelViewBuilder = propertiesPanelViewBuilder;
        this.propertiesPanelViewImplBuilder = propertiesPanelViewImplBuilder;
        this.elementBuilderProvider = elementBuilderProvider;
        this.connectionBuilderProvider = connectionBuilderProvider;
        this.editorFactoryBuilder = editorFactoryBuilder;
        this.ginModuleBuilder = ginModuleBuilder;
        this.injectorBuilder = injectorBuilder;
        this.resourcesBuilder = resourcesBuilder;
        this.editorEntryPointBuilder = editorEntryPointBuilder;
        this.editorPresenterBuilder = editorPresenterBuilder;
        this.editorResourcesBuilder = editorResourcesBuilder;
        this.stateBuilder = stateBuilder;
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

        Path mainHtmlFilePath =
                Paths.get(targetPath, MAIN_SOURCE_PATH, WEBAPP_FOLDER, MAIN_HTML_FILE_FULL_NAME);

        String content = new String(Files.readAllBytes(mainHtmlFilePath));
        String newContent = content.replaceAll(EDITOR_NAME_MARKER, editorName);

        Files.write(mainHtmlFilePath, newContent.getBytes());
    }

    private void generateJavaFolder(@Nonnull Properties properties, @Nonnull Configuration configuration) throws IOException {
        String packageName = properties.getProperty(MAIN_PACKAGE.name());
        String targetPath = properties.getProperty(TARGET_PATH.name());
        String editorName = properties.getProperty(EDITOR_NAME.name());

        createInjectModule(targetPath, packageName, editorName);
        createElements(targetPath, packageName, configuration);
        createMainGWTElements(properties, configuration);
        createWorkspace(targetPath, packageName, configuration);
        createToolbar(targetPath, packageName, configuration);
        createPropertiesPanel(targetPath, packageName, configuration);
    }

    private void createInjectModule(@Nonnull String projectPath,
                                    @Nonnull String packageName,
                                    @Nonnull String editorName) throws IOException {

        editorFactoryBuilder.path(projectPath)

                            .mainPackage(packageName)

                            .needRemoveTemplate(true)

                            .build();

        ginModuleBuilder.path(projectPath)

                        .mainPackage(packageName)

                        .needRemoveTemplate(true)

                        .build();

        injectorBuilder.path(projectPath)

                       .mainPackage(packageName)
                       .editorName(editorName)

                       .needRemoveTemplate(true)
                       .needRemoveTemplateParentFolder(true)

                       .build();
    }

    private void createElements(@Nonnull String projectPath,
                                @Nonnull String packageName,
                                @Nonnull Configuration configuration) throws IOException {

        Set<Element> elements = configuration.getDiagramConfiguration().getElements();

        for (Iterator<Element> iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = iterator.next();

            elementBuilderProvider.get()

                                  .path(projectPath)

                                  .needRemoveTemplate(!iterator.hasNext())

                                  .mainPackage(packageName)
                                  .elements(elements)

                                  .currentElement(element)

                                  .build();
        }

        Set<Connection> connections = configuration.getDiagramConfiguration().getConnections();

        for (Iterator<Connection> iterator = connections.iterator(); iterator.hasNext(); ) {
            Connection connection = iterator.next();

            connectionBuilderProvider.get()

                                     .path(projectPath)

                                     .needRemoveTemplate(!iterator.hasNext())
                                     .needRemoveTemplateParentFolder(!iterator.hasNext())

                                     .mainPackage(packageName)

                                     .connection(connection)

                                     .build();
        }
    }

    private void createMainGWTElements(@Nonnull Properties properties,
                                       @Nonnull Configuration configuration) throws IOException {

        String targetPath = properties.getProperty(TARGET_PATH.name());
        Set<Element> elements = configuration.getDiagramConfiguration().getElements();

        stateBuilder.path(targetPath)

                    .properties(properties)
                    .connections(configuration.getDiagramConfiguration().getConnections())
                    .elements(elements)

                    .needRemoveTemplate(true)

                    .build();

        editorResourcesBuilder.path(targetPath)

                              .properties(properties)

                              .needRemoveTemplate(true)

                              .build();

        editorEntryPointBuilder.path(targetPath)

                               .properties(properties)

                               .needRemoveTemplate(true)

                               .build();

        editorPresenterBuilder.path(targetPath)

                              .properties(properties)
                              .elements(elements)

                              .needRemoveTemplate(true)
                              .needRemoveTemplateParentFolder(true)

                              .build();
    }

    private void createWorkspace(@Nonnull String projectPath,
                                 @Nonnull String packageName,
                                 @Nonnull Configuration configuration) throws IOException {
        Set<Element> elements = configuration.getDiagramConfiguration().getElements();
        Set<Connection> connections = configuration.getDiagramConfiguration().getConnections();

        workspacePresenterBuilder.path(projectPath)

                                 .mainPackage(packageName)
                                 .elements(elements)
                                 .connections(connections)

                                 .build();

        workspaceViewBuilder.path(projectPath)

                            .mainPackage(packageName)
                            .elements(elements)
                            .connections(connections)

                            .build();

        workspaceViewImplBuilder.path(projectPath)

                                .mainPackage(packageName)
                                .elements(elements)
                                .connections(connections)

                                .needRemoveTemplateParentFolder(true)
                                .build();
    }

    private void createToolbar(@Nonnull String projectPath,
                               @Nonnull String packageName,
                               @Nonnull Configuration configuration) throws IOException {
        Set<Element> elements = configuration.getDiagramConfiguration().getElements();
        Set<Connection> connections = configuration.getDiagramConfiguration().getConnections();

        toolbarPresenterBuilder.path(projectPath)

                               .mainPackage(packageName)
                               .elements(elements)
                               .connections(connections)

                               .build();

        toolbarViewBuilder.path(projectPath)

                          .mainPackage(packageName)
                          .elements(elements)
                          .connections(connections)

                          .build();

        toolbarViewImplBuilder.path(projectPath)

                              .mainPackage(packageName)
                              .elements(elements)
                              .connections(connections)

                              .needRemoveTemplateParentFolder(true)
                              .build();
    }

    private void createPropertiesPanel(@Nonnull String projectPath,
                                       @Nonnull String packageName,
                                       @Nonnull Configuration configuration) throws IOException {
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
        resourcesBuilder.properties(properties)

                        .needRemoveTemplate(true)

                        .build();
    }
}