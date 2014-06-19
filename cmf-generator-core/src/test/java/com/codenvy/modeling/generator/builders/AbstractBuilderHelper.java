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
package com.codenvy.modeling.generator.builders;

import com.codenvy.modeling.configuration.Configuration;
import com.codenvy.modeling.configuration.ConfigurationFactory;
import com.codenvy.modeling.configuration.metamodel.diagram.Component;
import com.codenvy.modeling.configuration.metamodel.diagram.Connection;
import com.codenvy.modeling.configuration.metamodel.diagram.Element;
import com.codenvy.modeling.configuration.metamodel.diagram.Property;
import com.codenvy.modeling.generator.builders.elements.ConnectionBuilder;
import com.codenvy.modeling.generator.builders.elements.ElementBuilder;
import com.codenvy.modeling.generator.builders.propertiespanel.PropertiesPanelPresenterBuilder;
import com.codenvy.modeling.generator.builders.propertiespanel.PropertiesPanelViewBuilder;
import com.codenvy.modeling.generator.builders.propertiespanel.PropertiesPanelViewImplBuilder;
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

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import javax.annotation.Nonnull;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static com.codenvy.modeling.configuration.ConfigurationFactory.PathParameter.STYLE;
import static com.codenvy.modeling.generator.GenerationController.Param.EDITOR_NAME;
import static com.codenvy.modeling.generator.GenerationController.Param.MAIN_PACKAGE;
import static com.codenvy.modeling.generator.GenerationController.Param.MAVEN_ARTIFACT_ID;
import static com.codenvy.modeling.generator.GenerationController.Param.MAVEN_ARTIFACT_NAME;
import static com.codenvy.modeling.generator.GenerationController.Param.MAVEN_GROUP_ID;
import static com.codenvy.modeling.generator.GenerationController.Param.TARGET_PATH;
import static com.codenvy.modeling.generator.GenerationController.Param.TEMPLATE_PATH;
import static com.codenvy.modeling.generator.builders.PathConstants.CLIENT_PACKAGE;
import static com.codenvy.modeling.generator.builders.PathConstants.GENERATED_FOLDER;
import static com.codenvy.modeling.generator.builders.PathConstants.JAVA_SOURCE_FOLDER;
import static com.codenvy.modeling.generator.builders.PathConstants.MAIN_SOURCE_PATH;
import static com.codenvy.modeling.generator.builders.PathConstants.RESOURCES_SOURCE_FOLDER;
import static org.junit.Assert.assertEquals;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Valeriy Svydenko
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractBuilderHelper {

    protected static final String CONNECTION1      = "Connection1";
    protected static final String ELEMENT1         = "Element1";
    protected static final String MAIN_ELEMENT     = "MainElement";
    protected static final String VIEW             = "View";
    protected static final String PRESENTER        = "Presenter";
    protected static final String VIEW_IMPL        = "ViewImpl";
    protected static final String VIEW_BINDER_IMPL = "ViewBinderImplXml";

    protected Properties properties;
    protected String     clientFolder;

    @Mock
    private   ConfigurationFactory                      configurationFactory;
    @Mock(answer = RETURNS_DEEP_STUBS)
    protected Configuration                             configuration;
    @Mock
    protected Provider<ElementBuilder>                  elementBuilderProvider;
    @Mock
    protected Provider<ConnectionBuilder>               connectionBuilderProvider;
    @Mock
    protected Provider<PropertiesPanelPresenterBuilder> propertiesPanelPresenterBuilderProvider;
    @Mock
    protected Provider<PropertiesPanelViewBuilder>      propertiesPanelViewBuilderProvider;
    @Mock
    protected Provider<PropertiesPanelViewImplBuilder>  propertiesPanelViewImplBuilderProvider;
    @Mock
    protected Provider<UIXmlBuilder>                    uiXmlBuilderProvider;
    @Mock
    protected Provider<GField>                          fieldProvider;
    @Mock
    protected Provider<GScrollPanel>                    scrollPanelProvider;
    @Mock
    protected Provider<GFlowPanel>                      flowPanelProvider;
    @Mock
    protected Provider<GStyle>                          styleProvider;
    @Mock
    protected Provider<GDockLayoutPanel>                dockLayoutPanelProvider;
    @Mock
    protected Provider<GPushButton>                     pushButtonProvider;
    @Mock
    protected Provider<GLabel>                          labelProvider;
    @Mock
    protected Provider<GTextBox>                        textBoxProvider;
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setUpAbstract() throws Exception {
        Property property = new Property();
        property.setName("Property1");
        property.setType(Property.Type.INTEGER);
        property.setValue("10");

        Element element = new Element();
        element.setName(ELEMENT1);
        element.setProperties(new HashSet<>(Arrays.asList(property)));

        Component component = new Component();
        component.setName(ELEMENT1);

        Element mainElement = new Element();
        mainElement.setName(MAIN_ELEMENT);
        mainElement.setComponents(new HashSet<>(Arrays.asList(component)));

        Connection connection = mock(Connection.class);
        when(connection.getName()).thenReturn(CONNECTION1);

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
        when(configurationFactory.getInstance(any(ConfigurationFactory.ConfigurationPaths.class))).thenReturn(configuration);

        when(configuration.getDiagramConfiguration().getElements())
                .thenReturn(new LinkedHashSet<>(Arrays.asList(mainElement, element)));
        when(configuration.getDiagramConfiguration().getConnections()).thenReturn(new HashSet<>(Arrays.asList(connection)));
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

        properties = new Properties();

        properties.put(TARGET_PATH.name(), temporaryFolder.getRoot().getAbsolutePath() + "/testProject");
        properties.put(MAVEN_ARTIFACT_ID.name(), "maven_artifact_id");
        properties.put(MAVEN_GROUP_ID.name(), "maven_group_id");
        properties.put(MAVEN_ARTIFACT_NAME.name(), "maven_artifact_name");
        properties.put(EDITOR_NAME.name(), "EditorName");
        properties.put(MAIN_PACKAGE.name(), "my.package");
        properties.put(TEMPLATE_PATH.name(), "target/classes/template.zip");
        properties.put(STYLE.name(), getClass().getResource("/style").getFile());

        copyProjectHierarchy(properties);

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

    protected void assertContent(String expectedFilePath, String rootFolderPath, String... actualFilePath) throws IOException {
        String actualContent = new String(Files.readAllBytes(Paths.get(rootFolderPath, actualFilePath)));

        expectedFilePath = AbstractBuilderHelper.class.getResource(GENERATED_FOLDER + expectedFilePath).getFile();
        String expectedContent = new String(Files.readAllBytes(Paths.get(expectedFilePath)));

        assertEquals(expectedContent, actualContent);
    }

    @Nonnull
    private String convertPathToPackageName(@Nonnull String packagePath) {
        return packagePath.replace('.', '/');
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

    protected String prepareMainPackageFolderPath() {
        String targetPath = properties.getProperty(TARGET_PATH.name());
        String packageName = properties.getProperty(MAIN_PACKAGE.name());

        String resourceFolder = targetPath + File.separator + MAIN_SOURCE_PATH + File.separator + RESOURCES_SOURCE_FOLDER;

        return resourceFolder + File.separator + convertPathToPackageName(packageName);
    }
}