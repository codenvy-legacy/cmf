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
import com.codenvy.modeling.generator.builders.xml.api.GField;
import com.codenvy.modeling.generator.builders.xml.api.GStyle;
import com.codenvy.modeling.generator.builders.xml.api.UIXmlBuilder;
import com.codenvy.modeling.generator.builders.xml.api.widgets.GLabel;
import com.codenvy.modeling.generator.builders.xml.api.widgets.GPushButton;
import com.codenvy.modeling.generator.builders.xml.api.widgets.GTextBox;
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GDockLayoutPanel;
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GFlowPanel;
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GScrollPanel;
import com.codenvy.modeling.generator.builders.xml.impl.GFieldImpl;
import com.codenvy.modeling.generator.builders.xml.impl.GStyleImpl;
import com.codenvy.modeling.generator.builders.xml.impl.UIXmlBuilderImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GLabelImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GPushButtonImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GTextBoxImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GDockLayoutPanelImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GFlowPanelImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GScrollPanelImpl;
import com.google.inject.Provider;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.rules.RuleChain;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Properties;

import static com.codenvy.modeling.generator.GenerationController.Param.MAIN_PACKAGE;
import static com.codenvy.modeling.generator.GenerationController.Param.MAVEN_ARTIFACT_ID;
import static com.codenvy.modeling.generator.GenerationController.Param.MAVEN_ARTIFACT_NAME;
import static com.codenvy.modeling.generator.GenerationController.Param.MAVEN_GROUP_ID;
import static com.codenvy.modeling.generator.GenerationController.Param.TARGET_PATH;
import static com.codenvy.modeling.generator.GenerationController.Param.TEMPLATE_PATH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
@RunWith(MockitoJUnitRunner.class)
public class SourceCodeGeneratorTest {
    private static final String RESOURCES_FOLDER          = "/generated";
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

    private static final String ENTRY_POINT_NAME                           = "EditorEntryPoint.java";
    private static final String TOOLBAR_PRESENTER_NAME                     = "ToolbarPresenter.java";
    private static final String TOOLBAR_VIEW_NAME                          = "ToolbarView.java";
    private static final String TOOLBAR_VIEW_IMPL_BINDER_XML_NAME          = "ToolbarViewImpl.ui.xml";
    private static final String TOOLBAR_VIEW_IMPL_NAME                     = "ToolbarViewImpl.java";
    private static final String WORKSPACE_PRESENTER_NAME                   = "WorkspacePresenter.java";
    private static final String WORKSPACE_VIEW_NAME                        = "WorkspaceView.java";
    private static final String WORKSPACE_VIEW_IMPL_NAME                   = "WorkspaceViewImpl.java";
    private static final String WORKSPACE_VIEW_IMPL_BINDER_XML_NAME        = "WorkspaceViewImpl.ui.xml";
    private static final String PROPERTIES_PANEL_PRESENTER_NAME            = "Element1PropertiesPanelPresenter.java";
    private static final String PROPERTIES_PANEL_VIEW_NAME                 = "Element1PropertiesPanelView.java";
    private static final String PROPERTIES_PANEL_VIEW_IMPL_NAME            = "Element1PropertiesPanelViewImpl.java";
    private static final String PROPERTIES_PANEL_VIEW_IMPL_BINDER_XML_NAME = "Element1PropertiesPanelViewImpl.ui.xml";
    private static final String EDITOR_STATE_NAME                          = "State.java";
    private static final String EDITOR_RESOURCES_NAME                      = "EditorResources.java";
    private static final String GIN_MODULE_NAME                            = "GinModule.java";
    private static final String EDITOR_NAME_CLASS                          = "EditorName.java";
    private static final String GIN_INJECTOR_NAME                          = "Injector.java";
    private static final String EDITOR_FACTORY_NAME                        = "EditorFactory.java";

    @Mock
    private Provider<UIXmlBuilder>                    uiXmlBuilderProvider;
    @Mock
    private Provider<GField>                          fieldProvider;
    @Mock
    private Provider<GScrollPanel>                    scrollPanelProvider;
    @Mock
    private Provider<GFlowPanel>                      flowPanelProvider;
    @Mock
    private Provider<GStyle>                          styleProvider;
    @Mock
    private Provider<GDockLayoutPanel>                dockLayoutPanelProvider;
    @Mock
    private Provider<GPushButton>                     pushButtonProvider;
    @Mock
    private Provider<GLabel>                          labelProvider;
    @Mock
    private Provider<GTextBox>                        textBoxProvider;
    @Mock
    private Provider<PropertiesPanelPresenterBuilder> propertiesPanelPresenterBuilderProvider;
    @Mock
    private Provider<PropertiesPanelViewBuilder>      propertiesPanelViewBuilderProvider;
    @Mock
    private Provider<PropertiesPanelViewImplBuilder>  propertiesPanelViewImplBuilderProvider;
    @Mock
    private Provider<ElementBuilder>                  elementBuilderProvider;
    @Mock
    private Provider<ConnectionBuilder>               connectionBuilderProvider;

    @Mock
    private ConfigurationFactory configurationFactory;
    @Mock(answer = RETURNS_DEEP_STUBS)
    private Configuration        configuration;
    public GeneratorRule   generatorRule   = new GeneratorRule();
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    @Rule
    public TestRule        chainGenerator  = RuleChain
            .outerRule(temporaryFolder)
            .around(generatorRule);

    private void assertContent(String expectedFilePath, String rootFolderPath, String... actualFilePath) throws IOException {
        String actualContent = new String(Files.readAllBytes(Paths.get(rootFolderPath, actualFilePath)));

        expectedFilePath = SourceCodeGeneratorTest.class.getResource(RESOURCES_FOLDER + expectedFilePath).getFile();
        String expectedContent = new String(Files.readAllBytes(Paths.get(expectedFilePath)));

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void editorResourcesShouldBeCreated() throws IOException {
        assertContent("/EditorResources", generatorRule.getClientFolder(), EDITOR_RESOURCES_NAME);
    }

    @Test
    public void editorNameShouldBeCreated() throws IOException {
        assertContent("/EditorName", generatorRule.getClientFolder(), EDITOR_NAME_CLASS);
    }

    @Test
    public void stateShouldBeCreated() throws IOException {
        assertContent("/State", generatorRule.getClientFolder(), EDITOR_STATE_NAME);
    }

    @Test
    public void editorEntryPointShouldBeCreated() throws IOException {
        assertContent("/EditorEntryPoint", generatorRule.getClientFolder(), ENTRY_POINT_NAME);
    }

    @Test
    public void editorFactoryShouldBeCreated() throws IOException {
        assertContent("/inject/EditorFactory", generatorRule.getClientFolder(), INJECT_FOLDER, EDITOR_FACTORY_NAME);
    }

    @Test
    public void injectorShouldBeCreated() throws IOException {
        assertContent("/inject/Injector", generatorRule.getClientFolder(), INJECT_FOLDER, GIN_INJECTOR_NAME);
    }

    @Test
    public void ginModuleShouldBeCreated() throws IOException {
        assertContent("/inject/GinModule", generatorRule.getClientFolder(), INJECT_FOLDER, GIN_MODULE_NAME);
    }

    @Test
    public void connection1ShouldBeCreated() throws IOException {
        assertContent("/elements/Connection1", generatorRule.getClientFolder(), ELEMENTS_FOLDER, "Connection1.java");
    }

    @Test
    public void element1ShouldBeCreated() throws IOException {
        assertContent("/elements/Element1", generatorRule.getClientFolder(), ELEMENTS_FOLDER, "Element1.java");
    }

    @Test
    public void mainElementShouldBeCreated() throws IOException {
        assertContent("/elements/MainElement", generatorRule.getClientFolder(), ELEMENTS_FOLDER, "MainElement.java");
    }

    @Test
    public void binderXMLShouldBeCreated() throws IOException {
        assertContent("/toolbar/BinderXML", generatorRule.getClientFolder(), TOOLBAR_FOLDER, TOOLBAR_VIEW_IMPL_BINDER_XML_NAME);
    }

    @Test
    public void toolbarPresenterShouldBeCreated() throws IOException {
        assertContent("/toolbar/Presenter", generatorRule.getClientFolder(), TOOLBAR_FOLDER, TOOLBAR_PRESENTER_NAME);
    }

    @Test
    public void toolbarViewShouldBeCreated() throws IOException {
        assertContent("/toolbar/View", generatorRule.getClientFolder(), TOOLBAR_FOLDER, TOOLBAR_VIEW_NAME);
    }

    @Test
    public void toolbarViewImplShouldBeCreated() throws IOException {
        assertContent("/toolbar/ViewImpl", generatorRule.getClientFolder(), TOOLBAR_FOLDER, TOOLBAR_VIEW_IMPL_NAME);
    }

    @Test
    public void workspacePresenterShouldBeCreated() throws IOException {
        assertContent("/workspace/Presenter", generatorRule.getClientFolder(), WORKSPACE_FOLDER, WORKSPACE_PRESENTER_NAME);
    }

    @Test
    public void workspaceViewShouldBeCreated() throws IOException {
        assertContent("/workspace/View", generatorRule.getClientFolder(), WORKSPACE_FOLDER, WORKSPACE_VIEW_NAME);
    }

    @Test
    public void workspaceViewImpBinderXMLShouldBeCreated() throws IOException {
        assertContent("/workspace/ViewBinderImplXml", generatorRule.getClientFolder(), WORKSPACE_FOLDER,
                      WORKSPACE_VIEW_IMPL_BINDER_XML_NAME);
    }

    @Test
    public void workspaceViewImplShouldBeCreated() throws IOException {
        assertContent("/workspace/ViewImpl", generatorRule.getClientFolder(), WORKSPACE_FOLDER, WORKSPACE_VIEW_IMPL_NAME);
    }

    @Test
    public void presenterPanelShouldBeCreated() throws IOException {
        assertContent("/propertiespanel/Presenter", generatorRule.getClientFolder(), PROPERTIES_PANEL_FOLDER, "element1",
                      PROPERTIES_PANEL_PRESENTER_NAME);
    }

    @Test
    public void viewPanelShouldBeCreated() throws IOException {
        assertContent("/propertiespanel/View", generatorRule.getClientFolder(), PROPERTIES_PANEL_FOLDER, "element1",
                      PROPERTIES_PANEL_VIEW_NAME);
    }

    @Test
    public void viewImplPanelShouldBeCreated() throws IOException {
        assertContent("/propertiespanel/ViewImpl", generatorRule.getClientFolder(), PROPERTIES_PANEL_FOLDER, "element1",
                      PROPERTIES_PANEL_VIEW_IMPL_NAME);
    }

    @Test
    public void editorGwtXmlShouldBeCreated() throws IOException {
        assertContent("/EditorGwtXml", prepareMainPackageFolderPath(), MAIN_GWT_MODULE_FILE_NAME);
    }

    @Test
    public void editorCssXmlShouldBeCreated() throws IOException {
        assertContent("/EditorGwtXml", prepareMainPackageFolderPath(), MAIN_GWT_MODULE_FILE_NAME);
    }

    @Test
    public void viewImplBinderXMLPanelShouldBeCreated() throws IOException {
        assertContent("/propertiespanel/ViewImplBinderXML", generatorRule.getClientFolder(), PROPERTIES_PANEL_FOLDER, "element1",
                      PROPERTIES_PANEL_VIEW_IMPL_BINDER_XML_NAME);
    }

    @Test
    public void editorResourcesCssXmlShouldBeCreated() throws IOException {
        assertContent("/EditorResourcesCss", prepareMainPackageFolderPath(), CLIENT_PART_FOLDER, MAIN_CSS_FILE_NAME);
    }

    private String prepareMainPackageFolderPath() {
        String targetPath = generatorRule.getProperties().getProperty(TARGET_PATH.name());
        String packageName = generatorRule.getProperties().getProperty(MAIN_PACKAGE.name());

        String resourceFolder = targetPath + File.separator + MAIN_SOURCE_PATH + File.separator + RESOURCES_SOURCE_PATH;

        return resourceFolder + File.separator + convertPathToPackageName(packageName);
    }

    @Test
    public void editorHtmlShouldBeCreated() throws IOException {
        String targetPath = generatorRule.getProperties().getProperty(TARGET_PATH.name());

        assertContent("/webapp/EditorHtml", targetPath, MAIN_SOURCE_PATH, WEBAPP_SOURCE_PATH, MAIN_HTML_FILE_FULL_NAME);
    }

    @Test
    public void webXmlShouldBeCreated() throws IOException {
        String targetPath = generatorRule.getProperties().getProperty(TARGET_PATH.name());

        assertContent("/webapp/webXml", targetPath, MAIN_SOURCE_PATH, WEBAPP_SOURCE_PATH, "/WEB-INF/web.xml");
    }

    @Test
    public void pomShouldBeModified() throws Exception {
        Path pomPath = Paths.get(temporaryFolder.getRoot().getAbsolutePath() + "/testProject", POM_FILE_FULL_NAME);

        String pomContent = new String(Files.readAllBytes(pomPath));

        assertTrue(pomContent.contains(generatorRule.getProperties().getProperty(MAVEN_ARTIFACT_ID.name())));
        assertTrue(pomContent.contains(generatorRule.getProperties().getProperty(MAVEN_GROUP_ID.name())));
        assertTrue(pomContent.contains(generatorRule.getProperties().getProperty(MAVEN_ARTIFACT_NAME.name())));
    }

    @Test
    public void mainHTMLFileShouldBeDefined() throws IOException {
        String editorName = generatorRule.getProperties().getProperty(GenerationController.Param.EDITOR_NAME.name());

        Path mainHtmlFilePath =
                Paths.get(temporaryFolder.getRoot().getAbsolutePath() + "/testProject", MAIN_SOURCE_PATH, WEBAPP_SOURCE_PATH,
                          MAIN_HTML_FILE_FULL_NAME);

        String content = new String(Files.readAllBytes(mainHtmlFilePath));

        assertTrue(content.contains(editorName));
    }

    private class GeneratorRule extends ExternalResource {
        private SourceCodeGenerator generator;
        private Properties          properties;
        private String              clientFolder;

        @Override
        protected void before() throws Throwable {
            generator = new SourceCodeGenerator(new WorkspacePresenterBuilder(),
                                                new WorkspaceViewBuilder(),
                                                new WorkspaceViewImplBuilder(new UIXmlBuilderImpl(),
                                                                             new GFieldImpl(),
                                                                             new GScrollPanelImpl(),
                                                                             new GFlowPanelImpl()),
                                                new ToolbarPresenterBuilder(),
                                                new ToolbarViewBuilder(),
                                                new ToolbarViewImplBuilder(new UIXmlBuilderImpl(),
                                                                           new GStyleImpl(),
                                                                           new GDockLayoutPanelImpl(),
                                                                           pushButtonProvider),
                                                propertiesPanelPresenterBuilderProvider,
                                                propertiesPanelViewBuilderProvider,
                                                propertiesPanelViewImplBuilderProvider,
                                                elementBuilderProvider,
                                                connectionBuilderProvider,
                                                new EditorFactoryBuilder(),
                                                new GinModuleBuilder(),
                                                new InjectorBuilder(),
                                                new ResourcesBuilder(),
                                                new EditorEntryPointBuilder(),
                                                new EditorPresenterBuilder(),
                                                new EditorResourcesBuilder(),
                                                new StateBuilder()
            );

            when(uiXmlBuilderProvider.get()).thenAnswer(new Answer<UIXmlBuilder>() {
                @Override
                public UIXmlBuilder answer(InvocationOnMock invocation) throws Throwable {
                    return new UIXmlBuilderImpl();
                }
            });
            when(fieldProvider.get()).thenAnswer(new Answer<GField>() {
                @Override
                public GField answer(InvocationOnMock invocation) throws Throwable {
                    return new GFieldImpl();
                }
            });
            when(scrollPanelProvider.get()).thenAnswer(new Answer<GScrollPanel>() {
                @Override
                public GScrollPanel answer(InvocationOnMock invocation) throws Throwable {
                    return new GScrollPanelImpl();
                }
            });
            when(flowPanelProvider.get()).thenAnswer(new Answer<GFlowPanel>() {
                @Override
                public GFlowPanel answer(InvocationOnMock invocation) throws Throwable {
                    return new GFlowPanelImpl();
                }
            });
            when(styleProvider.get()).thenAnswer(new Answer<GStyle>() {
                @Override
                public GStyle answer(InvocationOnMock invocation) throws Throwable {
                    return new GStyleImpl();
                }
            });
            when(dockLayoutPanelProvider.get()).thenAnswer(new Answer<GDockLayoutPanel>() {
                @Override
                public GDockLayoutPanel answer(InvocationOnMock invocation) throws Throwable {
                    return new GDockLayoutPanelImpl();
                }
            });
            when(pushButtonProvider.get()).thenAnswer(new Answer<GPushButton>() {
                @Override
                public GPushButton answer(InvocationOnMock invocation) throws Throwable {
                    return new GPushButtonImpl();
                }
            });
            when(labelProvider.get()).thenAnswer(new Answer<GLabel>() {
                @Override
                public GLabel answer(InvocationOnMock invocation) throws Throwable {
                    return new GLabelImpl();
                }
            });
            when(textBoxProvider.get()).thenAnswer(new Answer<GTextBox>() {
                @Override
                public GTextBox answer(InvocationOnMock invocation) throws Throwable {
                    return new GTextBoxImpl();
                }
            });
            when(propertiesPanelPresenterBuilderProvider.get()).thenAnswer(new Answer<PropertiesPanelPresenterBuilder>() {
                @Override
                public PropertiesPanelPresenterBuilder answer(InvocationOnMock invocation) throws Throwable {
                    return new PropertiesPanelPresenterBuilder();
                }
            });
            when(propertiesPanelViewBuilderProvider.get()).thenAnswer(new Answer<PropertiesPanelViewBuilder>() {
                @Override
                public PropertiesPanelViewBuilder answer(InvocationOnMock invocation) throws Throwable {
                    return new PropertiesPanelViewBuilder();
                }
            });
            when(propertiesPanelViewImplBuilderProvider.get()).thenAnswer(new Answer<PropertiesPanelViewImplBuilder>() {
                @Override
                public PropertiesPanelViewImplBuilder answer(InvocationOnMock invocation) throws Throwable {
                    return new PropertiesPanelViewImplBuilder(new UIXmlBuilderImpl(),
                                                              new GDockLayoutPanelImpl(),
                                                              new GFieldImpl(),
                                                              flowPanelProvider,
                                                              labelProvider,
                                                              textBoxProvider);
                }
            });
            when(elementBuilderProvider.get()).thenAnswer(new Answer<ElementBuilder>() {
                @Override
                public ElementBuilder answer(InvocationOnMock invocation) throws Throwable {
                    return new ElementBuilder();
                }
            });
            when(connectionBuilderProvider.get()).thenAnswer(new Answer<ConnectionBuilder>() {
                @Override
                public ConnectionBuilder answer(InvocationOnMock invocation) throws Throwable {
                    return new ConnectionBuilder();
                }
            });

            Property property = new Property();
            property.setName("Property1");
            property.setType(Property.Type.INTEGER);
            property.setValue("10");

            Element element = new Element();
            element.setName("Element1");
            element.setProperties(new HashSet<>(Arrays.asList(property)));

            Component component = new Component();
            component.setName("Element1");

            Element mainElement = new Element();
            mainElement.setName("MainElement");
            mainElement.setComponents(new HashSet<>(Arrays.asList(component)));

            Connection connection = mock(Connection.class);
            when(connection.getName()).thenReturn("Connection1");

            when(configurationFactory.getInstance(any(ConfigurationFactory.ConfigurationPaths.class))).thenReturn(configuration);

            when(configuration.getDiagramConfiguration().getElements())
                    .thenReturn(new LinkedHashSet<>(Arrays.asList(mainElement, element)));
            when(configuration.getDiagramConfiguration().getConnections()).thenReturn(new HashSet<>(Arrays.asList(connection)));

            properties = new Properties();

            properties.put(TARGET_PATH.name(), temporaryFolder.getRoot().getAbsolutePath() + "/testProject");
            properties.put(MAVEN_ARTIFACT_ID.name(), "maven_artifact_id");
            properties.put(MAVEN_GROUP_ID.name(), "maven_group_id");
            properties.put(MAVEN_ARTIFACT_NAME.name(), "maven_artifact_name");
            properties.put(GenerationController.Param.EDITOR_NAME.name(), "EditorName");
            properties.put(MAIN_PACKAGE.name(), "my.package");
            properties.put(TEMPLATE_PATH.name(), "target/classes/template.zip");
            properties.put(ConfigurationFactory.PathParameter.STYLE.name(), getClass().getResource("/style").getFile());

            clientFolder = prepareClientFolderPath();

            generator.generate(properties, configurationFactory);
        }

        protected Properties getProperties() {
            return properties;
        }

        protected SourceCodeGenerator getGenerator() {
            return generator;
        }

        protected String getClientFolder() {
            return clientFolder;
        }

        @Nonnull
        private String prepareClientFolderPath() {
            String packageName = generatorRule.getProperties().getProperty(MAIN_PACKAGE.name());
            String targetPath = generatorRule.getProperties().getProperty(TARGET_PATH.name());

            String mainPackageFolder = convertPathToPackageName(packageName);
            String javaFolder = targetPath + MAIN_SOURCE_PATH + File.separator + JAVA_SOURCE_PATH;
            String clientPackageFolder = mainPackageFolder + File.separator + CLIENT_PART_FOLDER;

            return javaFolder + File.separator + clientPackageFolder;
        }
    }

    @Nonnull
    private String convertPathToPackageName(@Nonnull String packagePath) {
        return packagePath.replace('.', '/');
    }
}