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
import com.codenvy.modeling.configuration.metamodel.diagram.Connection;
import com.codenvy.modeling.configuration.metamodel.diagram.Element;
import com.codenvy.modeling.configuration.metamodel.diagram.Property;
import com.codenvy.modeling.generator.builders.java.SourceCodeBuilder;
import com.codenvy.modeling.generator.builders.java.SourceCodeBuilderImpl;
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
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.codenvy.modeling.generator.GenerationController.Param;
import static com.codenvy.modeling.generator.GenerationController.Param.EDITOR_NAME;
import static com.codenvy.modeling.generator.GenerationController.Param.MAIN_PACKAGE;
import static com.codenvy.modeling.generator.GenerationController.Param.MAVEN_ARTIFACT_ID;
import static com.codenvy.modeling.generator.GenerationController.Param.MAVEN_ARTIFACT_NAME;
import static com.codenvy.modeling.generator.GenerationController.Param.MAVEN_GROUP_ID;
import static com.codenvy.modeling.generator.GenerationController.Param.TARGET_PATH;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Andrey Plotnikov
 */
@RunWith(MockitoJUnitRunner.class)
public class SourceCodeGeneratorTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Mock
    private Provider<SourceCodeBuilder> sourceCodeBuilderProvider;
    @Mock
    private Provider<UIXmlBuilder>      uiXmlBuilderProvider;
    @Mock
    private Provider<GField>            fieldProvider;
    @Mock
    private Provider<GScrollPanel>      scrollPanelProvider;
    @Mock
    private Provider<GFlowPanel>        flowPanelProvider;
    @Mock
    private Provider<GStyle>            styleProvider;
    @Mock
    private Provider<GDockLayoutPanel>  dockLayoutPanelProvider;
    @Mock
    private Provider<GPushButton>       pushButtonProvider;
    @Mock
    private Provider<GLabel>            labelProvider;
    @Mock
    private Provider<GTextBox>          textBoxProvider;
    @Mock(answer = RETURNS_DEEP_STUBS)
    private Configuration               configuration;
    private SourceCodeGenerator         generator;

    @Before
    public void setUp() throws Exception {
        generator = new SourceCodeGenerator(sourceCodeBuilderProvider,
                                            uiXmlBuilderProvider,
                                            fieldProvider,
                                            scrollPanelProvider,
                                            flowPanelProvider,
                                            styleProvider,
                                            dockLayoutPanelProvider,
                                            pushButtonProvider,
                                            labelProvider,
                                            textBoxProvider);
    }

    @Test
    public void projectShouldBeGenerated() throws Exception {
        when(sourceCodeBuilderProvider.get()).thenAnswer(new Answer<SourceCodeBuilder>() {
            @Override
            public SourceCodeBuilder answer(InvocationOnMock invocation) throws Throwable {
                return new SourceCodeBuilderImpl();
            }
        });
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

        Element element = mock(Element.class);
        when(element.getName()).thenReturn("Element1");

        Property property = new Property();
        property.setName("Property1");
        property.setType(Property.Type.INTEGER);
        property.setValue("10");

        when(element.getProperties()).thenReturn(Arrays.asList(property));

        Connection connection = mock(Connection.class);
        when(connection.getName()).thenReturn("Connection1");

        when(configuration.getDiagramConfiguration().getElements()).thenReturn(Arrays.asList(element));
        when(configuration.getDiagramConfiguration().getConnections()).thenReturn(Arrays.asList(connection));

        Map<Param, String> params = new HashMap<>();

        params.put(TARGET_PATH, temporaryFolder.getRoot().getAbsolutePath() + "/testProject");
        params.put(MAVEN_ARTIFACT_ID, "maven_artifact_id");
        params.put(MAVEN_GROUP_ID, "maven_group_id");
        params.put(MAVEN_ARTIFACT_NAME, "maven_artifact_name");
        params.put(EDITOR_NAME, "EditorName");
        params.put(MAIN_PACKAGE, "my.package");

        generator.generate(params, configuration);

        // TODO check content?
    }

}