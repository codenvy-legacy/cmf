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
package com.codenvy.modeling.generator.builders.propertiespanel;

import com.codenvy.modeling.generator.AbstractBuilderTest;
import com.codenvy.modeling.generator.builders.xml.api.widgets.GLabel;
import com.codenvy.modeling.generator.builders.xml.api.widgets.GListBox;
import com.codenvy.modeling.generator.builders.xml.api.widgets.GTextBox;
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GFlowPanel;
import com.codenvy.modeling.generator.builders.xml.impl.GFieldImpl;
import com.codenvy.modeling.generator.builders.xml.impl.UIXmlBuilderImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GLabelImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GListBoxImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GTextBoxImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GDockLayoutPanelImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GFlowPanelImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GScrollPanelImpl;
import com.google.inject.Provider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.codenvy.modeling.generator.GenerationController.Param.TARGET_PATH;
import static com.codenvy.modeling.generator.builders.FileExtensionConstants.JAVA;
import static com.codenvy.modeling.generator.builders.FileExtensionConstants.UI_XML;
import static com.codenvy.modeling.generator.builders.PathConstants.JAVA_SOURCE_FOLDER;
import static com.codenvy.modeling.generator.builders.PathConstants.MAIN_SOURCE_PATH;
import static com.codenvy.modeling.generator.builders.PathConstants.PROPERTIES_PANEL_PACKAGE;
import static com.codenvy.modeling.generator.builders.ResourceNameConstants.PROPERTIES_PANEL_VIEW_IMPL;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

/**
 * @author Valeriy Svydenko
 */
public class PropertiesPanelViewImplBuilderTest extends AbstractBuilderTest {
    private static final String ELEMENT1        = "element1";
    private static final String ELEMENT2        = "element2";
    private static final String VIEW_IMPL_NAME  = "Element1PropertiesPanelViewImpl";
    private static final String VIEW_IMPL_NAME2 = "Element2PropertiesPanelViewImpl";

    @Mock
    private Provider<GFlowPanel> flowPanelProvider;
    @Mock
    private Provider<GLabel>     labelProvider;
    @Mock
    private Provider<GTextBox>   textBoxProvider;
    @Mock
    private Provider<GListBox>   listBoxProvider;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        when(flowPanelProvider.get()).thenAnswer(new Answer<GFlowPanel>() {
            @Override
            public GFlowPanel answer(InvocationOnMock invocation) throws Throwable {
                return new GFlowPanelImpl();
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
        when(listBoxProvider.get()).thenAnswer(new Answer<GListBox>() {
            @Override
            public GListBox answer(InvocationOnMock invocation) throws Throwable {
                return new GListBoxImpl();
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
                                                          textBoxProvider,
                                                          listBoxProvider,
                                                          new GScrollPanelImpl());
            }
        });

        generateSources();
    }

    @Test
    public void propertiesPanelViewImplShouldBeGenerated() throws IOException {
        assertContent(File.separator + PROPERTIES_PANEL_PACKAGE + File.separator + ELEMENT1 + File.separator + VIEW_IMPL, clientFolder,
                      PROPERTIES_PANEL_PACKAGE, ELEMENT1, VIEW_IMPL_NAME + JAVA);
    }

    @Test
    public void propertiesPanelViewImplBinderShouldBeGenerated() throws IOException {
        assertContent(File.separator + PROPERTIES_PANEL_PACKAGE + File.separator + ELEMENT1 + File.separator + VIEW_BINDER_IMPL,
                      clientFolder, PROPERTIES_PANEL_PACKAGE, ELEMENT1, VIEW_IMPL_NAME + UI_XML);
    }

    @Test
    public void element2PropertiesPanelViewImplShouldBeGenerated() throws IOException {
        assertContent(File.separator + PROPERTIES_PANEL_PACKAGE + File.separator + ELEMENT2 + File.separator + VIEW_IMPL, clientFolder,
                      PROPERTIES_PANEL_PACKAGE, ELEMENT2, VIEW_IMPL_NAME2 + JAVA);
    }

    @Test
    public void element2PropertiesPanelViewImplBinderShouldBeGenerated() throws IOException {
        assertContent(File.separator + PROPERTIES_PANEL_PACKAGE + File.separator + ELEMENT2 + File.separator + VIEW_BINDER_IMPL,
                      clientFolder, PROPERTIES_PANEL_PACKAGE, ELEMENT2, VIEW_IMPL_NAME2 + UI_XML);
    }

    @Test
    public void propertiesPanelViewImplTemplateShouldBeRemoved() {
        Path path = Paths.get(properties.getProperty(TARGET_PATH.name()),
                              MAIN_SOURCE_PATH,
                              JAVA_SOURCE_FOLDER,
                              PROPERTIES_PANEL_PACKAGE,
                              PROPERTIES_PANEL_VIEW_IMPL + JAVA);

        assertFalse(Files.exists(path));
    }
}