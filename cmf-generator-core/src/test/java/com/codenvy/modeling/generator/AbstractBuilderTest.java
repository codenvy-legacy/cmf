/*
 * Copyright [2014] Codenvy, S.A.
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
import com.codenvy.modeling.configuration.metamodel.diagram.Component;
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
import com.google.inject.Provider;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

import static com.codenvy.modeling.configuration.ConfigurationFactory.PathParameter.STYLE;
import static com.codenvy.modeling.configuration.metamodel.diagram.Property.Type.BOOLEAN;
import static com.codenvy.modeling.configuration.metamodel.diagram.Property.Type.FLOAT;
import static com.codenvy.modeling.configuration.metamodel.diagram.Property.Type.INTEGER;
import static com.codenvy.modeling.configuration.metamodel.diagram.Property.Type.STRING;
import static com.codenvy.modeling.generator.GenerationController.Param.EDITOR_NAME;
import static com.codenvy.modeling.generator.GenerationController.Param.MAIN_PACKAGE;
import static com.codenvy.modeling.generator.GenerationController.Param.MAVEN_ARTIFACT_ID;
import static com.codenvy.modeling.generator.GenerationController.Param.MAVEN_ARTIFACT_NAME;
import static com.codenvy.modeling.generator.GenerationController.Param.MAVEN_GROUP_ID;
import static com.codenvy.modeling.generator.GenerationController.Param.TARGET_PATH;
import static com.codenvy.modeling.generator.GenerationController.Param.TEMPLATE_PATH;
import static com.codenvy.modeling.generator.builders.PathConstants.CLIENT_PACKAGE;
import static com.codenvy.modeling.generator.builders.PathConstants.JAVA_SOURCE_FOLDER;
import static com.codenvy.modeling.generator.builders.PathConstants.MAIN_SOURCE_PATH;
import static com.codenvy.modeling.generator.builders.PathConstants.RESOURCES_SOURCE_FOLDER;
import static org.junit.Assert.assertEquals;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anySet;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Valeriy Svydenko
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractBuilderTest {
    protected static final String MY_PACKAGE       = "my.package";
    protected static final String GENERATED_FOLDER = "/generated";
    protected static final String MAIN_ELEMENT     = "MainElement";
    protected static final String VIEW             = "View";
    protected static final String PRESENTER        = "Presenter";
    protected static final String VIEW_IMPL        = "ViewImpl";
    protected static final String VIEW_BINDER_IMPL = "ViewBinderImplXml";

    @Mock
    protected InjectorBuilder                           injectorBuilder;
    @Mock
    protected WorkspacePresenterBuilder                 workspacePresenterBuilder;
    @Mock
    protected WorkspaceViewBuilder                      workspaceViewBuilder;
    @Mock
    protected WorkspaceViewImplBuilder                  workspaceViewImplBuilder;
    @Mock
    protected ToolbarPresenterBuilder                   toolbarPresenterBuilder;
    @Mock
    protected ToolbarViewBuilder                        toolbarViewBuilder;
    @Mock
    protected ToolbarViewImplBuilder                    toolbarViewImplBuilder;
    @Mock
    protected ResourcesBuilder                          resourcesBuilder;
    @Mock
    protected EditorFactoryBuilder                      editorFactoryBuilder;
    @Mock
    protected GinModuleBuilder                          ginModuleBuilder;
    @Mock
    protected StateBuilder                              stateBuilder;
    @Mock
    protected EditorResourcesBuilder                    editorResourcesBuilder;
    @Mock
    protected EditorEntryPointBuilder                   editorEntryPointBuilder;
    @Mock
    protected EditorPresenterBuilder                    editorPresenterBuilder;
    @Mock
    protected PropertiesPanelPresenterBuilder           propertiesPanelPresenterBuilder;
    @Mock
    protected PropertiesPanelViewBuilder                propertiesPanelViewBuilder;
    @Mock
    protected PropertiesPanelViewImplBuilder            propertiesPanelViewImplBuilder;
    @Mock
    protected ElementBuilder                            elementBuilder;
    @Mock
    protected ConnectionBuilder                         connectionBuilder;
    @Mock
    protected Provider<PropertiesPanelPresenterBuilder> propertiesPanelPresenterBuilderProvider;
    @Mock
    protected Provider<PropertiesPanelViewBuilder>      propertiesPanelViewBuilderProvider;
    @Mock
    protected Provider<PropertiesPanelViewImplBuilder>  propertiesPanelViewImplBuilderProvider;
    @Mock
    protected Provider<ElementBuilder>                  elementBuilderProvider;
    @Mock
    protected Provider<ConnectionBuilder>               connectionBuilderProvider;
    @Mock
    protected ConfigurationFactory                      configurationFactory;

    @Mock(answer = RETURNS_DEEP_STUBS)
    protected Configuration configuration;

    protected SourceCodeGenerator generator;
    protected Properties          properties;
    protected String              clientFolder;
    protected String              targetPath;
    protected Set<Connection>     connections;
    protected Set<Element>        elements;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        Property property1 = new Property();
        property1.setName("Property1");
        property1.setType(INTEGER);
        property1.setValue("10");

        Property property2 = new Property();
        property2.setName("Property2");
        property2.setType(BOOLEAN);
        property2.setValue("true");

        Property property3 = new Property();
        property3.setName("Property3");
        property3.setType(STRING);
        property3.setValue("some string");

        Property property4 = new Property();
        property4.setName("Property4");
        property4.setType(FLOAT);
        property4.setValue("12.3f");

        Element element1 = new Element();
        element1.setName("Element1");
        element1.setProperties(new LinkedHashSet<>(Arrays.asList(property1, property2, property3, property4)));

        Element element2 = new Element();
        element2.setName("Element2");
        element2.setProperties(new LinkedHashSet<>(Arrays.asList(property1, property2, property3, property4)));

        Component component1 = new Component();
        component1.setName("Element1");

        Component component2 = new Component();
        component2.setName("Element2");

        Element mainElement = new Element();
        mainElement.setName("MainElement");
        mainElement.setComponents(new LinkedHashSet<>(Arrays.asList(component1, component2)));

        Connection connection = mock(Connection.class);
        when(connection.getName()).thenReturn("Connection1");

        when(configurationFactory.getInstance(any(ConfigurationFactory.ConfigurationPaths.class))).thenReturn(configuration);

        connections = new HashSet<>(Arrays.asList(connection));
        elements = new LinkedHashSet<>(Arrays.asList(mainElement, element1, element2));

        when(configuration.getDiagramConfiguration().getElements()).thenReturn(elements);
        when(configuration.getDiagramConfiguration().getConnections()).thenReturn(connections);

        when(elementBuilderProvider.get()).thenReturn(elementBuilder);
        when(elementBuilder.path(anyString())).thenReturn(elementBuilder);
        when(elementBuilder.mainPackage(anyString())).thenReturn(elementBuilder);
        when(elementBuilder.elements(anySet())).thenReturn(elementBuilder);
        when(elementBuilder.currentElement((Element)anyObject())).thenReturn(elementBuilder);
        when(elementBuilder.needRemoveTemplate(anyBoolean())).thenReturn(elementBuilder);

        when(connectionBuilderProvider.get()).thenReturn(connectionBuilder);
        when(connectionBuilder.path(anyString())).thenReturn(connectionBuilder);
        when(connectionBuilder.mainPackage(anyString())).thenReturn(connectionBuilder);
        when(connectionBuilder.connection((Connection)anyObject())).thenReturn(connectionBuilder);
        when(connectionBuilder.needRemoveTemplate(anyBoolean())).thenReturn(connectionBuilder);
        when(connectionBuilder.needRemoveTemplateParentFolder(anyBoolean())).thenReturn(connectionBuilder);

        when(propertiesPanelPresenterBuilderProvider.get()).thenReturn(propertiesPanelPresenterBuilder);
        when(propertiesPanelPresenterBuilder.path(anyString())).thenReturn(propertiesPanelPresenterBuilder);
        when(propertiesPanelPresenterBuilder.mainPackage(anyString())).thenReturn(propertiesPanelPresenterBuilder);
        when(propertiesPanelPresenterBuilder.properties(anySet())).thenReturn(propertiesPanelPresenterBuilder);
        when(propertiesPanelPresenterBuilder.element((Element)anyObject())).thenReturn(propertiesPanelPresenterBuilder);
        when(propertiesPanelPresenterBuilder.needRemoveTemplate(anyBoolean())).thenReturn(propertiesPanelPresenterBuilder);

        when(propertiesPanelViewBuilderProvider.get()).thenReturn(propertiesPanelViewBuilder);
        when(propertiesPanelViewBuilder.path(anyString())).thenReturn(propertiesPanelViewBuilder);
        when(propertiesPanelViewBuilder.mainPackage(anyString())).thenReturn(propertiesPanelViewBuilder);
        when(propertiesPanelViewBuilder.properties(anySet())).thenReturn(propertiesPanelViewBuilder);
        when(propertiesPanelViewBuilder.element((Element)anyObject())).thenReturn(propertiesPanelViewBuilder);
        when(propertiesPanelViewBuilder.needRemoveTemplate(anyBoolean())).thenReturn(propertiesPanelViewBuilder);

        when(propertiesPanelViewImplBuilderProvider.get()).thenReturn(propertiesPanelViewImplBuilder);
        when(propertiesPanelViewImplBuilder.path(anyString())).thenReturn(propertiesPanelViewImplBuilder);
        when(propertiesPanelViewImplBuilder.mainPackage(anyString())).thenReturn(propertiesPanelViewImplBuilder);
        when(propertiesPanelViewImplBuilder.properties(anySet())).thenReturn(propertiesPanelViewImplBuilder);
        when(propertiesPanelViewImplBuilder.element((Element)anyObject())).thenReturn(propertiesPanelViewImplBuilder);
        when(propertiesPanelViewImplBuilder.needRemoveTemplate(anyBoolean())).thenReturn(propertiesPanelViewImplBuilder);
        when(propertiesPanelViewImplBuilder.needRemoveTemplateParentFolder(anyBoolean())).thenReturn(propertiesPanelViewImplBuilder);

        when(stateBuilder.path(anyString())).thenReturn(stateBuilder);
        when(stateBuilder.properties((Properties)anyObject())).thenReturn(stateBuilder);
        when(stateBuilder.elements(anySet())).thenReturn(stateBuilder);
        when(stateBuilder.connections(anySet())).thenReturn(stateBuilder);
        when(stateBuilder.needRemoveTemplate(anyBoolean())).thenReturn(stateBuilder);

        when(editorResourcesBuilder.path(anyString())).thenReturn(editorResourcesBuilder);
        when(editorResourcesBuilder.properties((Properties)anyObject())).thenReturn(editorResourcesBuilder);
        when(editorResourcesBuilder.needRemoveTemplate(anyBoolean())).thenReturn(editorResourcesBuilder);

        when(editorEntryPointBuilder.path(anyString())).thenReturn(editorEntryPointBuilder);
        when(editorEntryPointBuilder.properties((Properties)anyObject())).thenReturn(editorEntryPointBuilder);
        when(editorEntryPointBuilder.needRemoveTemplate(anyBoolean())).thenReturn(editorEntryPointBuilder);

        when(editorPresenterBuilder.path(anyString())).thenReturn(editorPresenterBuilder);
        when(editorPresenterBuilder.properties((Properties)anyObject())).thenReturn(editorPresenterBuilder);
        when(editorPresenterBuilder.elements(anySet())).thenReturn(editorPresenterBuilder);
        when(editorPresenterBuilder.needRemoveTemplate(anyBoolean())).thenReturn(editorPresenterBuilder);
        when(editorPresenterBuilder.needRemoveTemplateParentFolder(anyBoolean())).thenReturn(editorPresenterBuilder);

        when(injectorBuilder.path(anyString())).thenReturn(injectorBuilder);
        when(injectorBuilder.mainPackage(anyString())).thenReturn(injectorBuilder);
        when(injectorBuilder.editorName(anyString())).thenReturn(injectorBuilder);
        when(injectorBuilder.needRemoveTemplate(anyBoolean())).thenReturn(injectorBuilder);
        when(injectorBuilder.needRemoveTemplateParentFolder(anyBoolean())).thenReturn(injectorBuilder);

        when(ginModuleBuilder.path(anyString())).thenReturn(ginModuleBuilder);
        when(ginModuleBuilder.mainPackage(anyString())).thenReturn(ginModuleBuilder);
        when(ginModuleBuilder.needRemoveTemplate(anyBoolean())).thenReturn(ginModuleBuilder);

        when(editorFactoryBuilder.path(anyString())).thenReturn(editorFactoryBuilder);
        when(editorFactoryBuilder.mainPackage(anyString())).thenReturn(editorFactoryBuilder);
        when(editorFactoryBuilder.needRemoveTemplate(anyBoolean())).thenReturn(editorFactoryBuilder);

        when(workspacePresenterBuilder.path(anyString())).thenReturn(workspacePresenterBuilder);
        when(workspacePresenterBuilder.mainPackage(anyString())).thenReturn(workspacePresenterBuilder);
        when(workspacePresenterBuilder.elements(anySet())).thenReturn(workspacePresenterBuilder);
        when(workspacePresenterBuilder.connections(anySet())).thenReturn(workspacePresenterBuilder);

        when(workspaceViewBuilder.path(anyString())).thenReturn(workspaceViewBuilder);
        when(workspaceViewBuilder.mainPackage(anyString())).thenReturn(workspaceViewBuilder);
        when(workspaceViewBuilder.elements(anySet())).thenReturn(workspaceViewBuilder);
        when(workspaceViewBuilder.connections(anySet())).thenReturn(workspaceViewBuilder);

        when(workspaceViewImplBuilder.path(anyString())).thenReturn(workspaceViewImplBuilder);
        when(workspaceViewImplBuilder.mainPackage(anyString())).thenReturn(workspaceViewImplBuilder);
        when(workspaceViewImplBuilder.elements(anySet())).thenReturn(workspaceViewImplBuilder);
        when(workspaceViewImplBuilder.connections(anySet())).thenReturn(workspaceViewImplBuilder);
        when(workspaceViewImplBuilder.needRemoveTemplateParentFolder(anyBoolean())).thenReturn(workspaceViewImplBuilder);

        when(toolbarPresenterBuilder.path(anyString())).thenReturn(toolbarPresenterBuilder);
        when(toolbarPresenterBuilder.mainPackage(anyString())).thenReturn(toolbarPresenterBuilder);
        when(toolbarPresenterBuilder.elements(anySet())).thenReturn(toolbarPresenterBuilder);
        when(toolbarPresenterBuilder.connections(anySet())).thenReturn(toolbarPresenterBuilder);

        when(toolbarViewBuilder.path(anyString())).thenReturn(toolbarViewBuilder);
        when(toolbarViewBuilder.mainPackage(anyString())).thenReturn(toolbarViewBuilder);
        when(toolbarViewBuilder.elements(anySet())).thenReturn(toolbarViewBuilder);
        when(toolbarViewBuilder.connections(anySet())).thenReturn(toolbarViewBuilder);

        when(toolbarViewImplBuilder.path(anyString())).thenReturn(toolbarViewImplBuilder);
        when(toolbarViewImplBuilder.mainPackage(anyString())).thenReturn(toolbarViewImplBuilder);
        when(toolbarViewImplBuilder.elements(anySet())).thenReturn(toolbarViewImplBuilder);
        when(toolbarViewImplBuilder.connections(anySet())).thenReturn(toolbarViewImplBuilder);
        when(toolbarViewImplBuilder.needRemoveTemplateParentFolder(anyBoolean())).thenReturn(toolbarViewImplBuilder);

        when(resourcesBuilder.properties((Properties)anyObject())).thenReturn(resourcesBuilder);
        when(resourcesBuilder.needRemoveTemplate(anyBoolean())).thenReturn(resourcesBuilder);

        targetPath = temporaryFolder.getRoot().getAbsolutePath() + "/testProject";

        properties = new Properties();

        properties.put(TARGET_PATH.name(), targetPath);
        properties.put(MAVEN_ARTIFACT_ID.name(), "maven_artifact_id");
        properties.put(MAVEN_GROUP_ID.name(), "maven_group_id");
        properties.put(MAVEN_ARTIFACT_NAME.name(), "maven_artifact_name");
        properties.put(EDITOR_NAME.name(), "EditorName");
        properties.put(MAIN_PACKAGE.name(), "my.package");
        properties.put(TEMPLATE_PATH.name(), "target/classes/template.zip");
        properties.put(STYLE.name(), getClass().getResource("/style").getFile());

        clientFolder = prepareClientFolderPath();
    }

    @Nonnull
    private String prepareClientFolderPath() {
        String packageName = properties.getProperty(MAIN_PACKAGE.name());
        String targetPath = properties.getProperty(TARGET_PATH.name());

        String mainPackageFolder = convertPathToPackageName(packageName);
        String javaFolder = targetPath + MAIN_SOURCE_PATH + File.separator + JAVA_SOURCE_FOLDER;
        String clientPackageFolder = mainPackageFolder + File.separator + CLIENT_PACKAGE;

        return javaFolder + File.separator + clientPackageFolder;
    }

    @Nonnull
    public String convertPathToPackageName(@Nonnull String packagePath) {
        return packagePath.replace('.', '/');
    }

    protected void assertContent(String expectedFilePath, String rootFolderPath, String... actualFilePath) throws IOException {
        String actualContent = new String(Files.readAllBytes(Paths.get(rootFolderPath, actualFilePath)));

        expectedFilePath = AbstractBuilderTest.class.getResource(GENERATED_FOLDER + expectedFilePath).getFile();
        String expectedContent = new String(Files.readAllBytes(Paths.get(expectedFilePath)));

        assertEquals(expectedContent, actualContent);
    }

    @Nonnull
    protected String prepareMainPackageFolderPath() {
        String targetPath = properties.getProperty(TARGET_PATH.name());
        String packageName = properties.getProperty(MAIN_PACKAGE.name());

        String resourceFolder = targetPath + File.separator + MAIN_SOURCE_PATH + File.separator + RESOURCES_SOURCE_FOLDER;

        return resourceFolder + File.separator + convertPathToPackageName(packageName);
    }

    protected void generateSources() throws IOException {
        generator = new SourceCodeGenerator(workspacePresenterBuilder,
                                            workspaceViewBuilder,
                                            workspaceViewImplBuilder,
                                            toolbarPresenterBuilder,
                                            toolbarViewBuilder,
                                            toolbarViewImplBuilder,
                                            propertiesPanelPresenterBuilderProvider,
                                            propertiesPanelViewBuilderProvider,
                                            propertiesPanelViewImplBuilderProvider,
                                            elementBuilderProvider,
                                            connectionBuilderProvider,
                                            editorFactoryBuilder,
                                            ginModuleBuilder,
                                            injectorBuilder,
                                            resourcesBuilder,
                                            editorEntryPointBuilder,
                                            editorPresenterBuilder,
                                            editorResourcesBuilder,
                                            stateBuilder
        );

        generator.generate(properties, configurationFactory);
    }
}