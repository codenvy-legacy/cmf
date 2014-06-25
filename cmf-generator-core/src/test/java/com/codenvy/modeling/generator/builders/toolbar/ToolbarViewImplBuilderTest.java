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
package com.codenvy.modeling.generator.builders.toolbar;

import com.codenvy.modeling.generator.AbstractBuilderTest;
import com.codenvy.modeling.generator.builders.xml.api.widgets.GPushButton;
import com.codenvy.modeling.generator.builders.xml.impl.GStyleImpl;
import com.codenvy.modeling.generator.builders.xml.impl.UIXmlBuilderImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GPushButtonImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GDockLayoutPanelImpl;
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
import static com.codenvy.modeling.generator.builders.PathConstants.TOOLBAR_PACKAGE;
import static com.codenvy.modeling.generator.builders.ResourceNameConstants.TOOLBAR_VIEW_IMPL;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

/**
 * @author Valeriy Svydenko
 */
public class ToolbarViewImplBuilderTest extends AbstractBuilderTest {
    @Mock
    private Provider<GPushButton> pushButtonProvider;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        when(pushButtonProvider.get()).thenAnswer(new Answer<GPushButton>() {
            @Override
            public GPushButton answer(InvocationOnMock invocation) throws Throwable {
                return new GPushButtonImpl();
            }
        });

        toolbarViewImplBuilder = new ToolbarViewImplBuilder(new UIXmlBuilderImpl(),
                                                            new GStyleImpl(),
                                                            new GDockLayoutPanelImpl(),
                                                            pushButtonProvider);

        generateSources();
    }

    @Test
    public void toolbarViewImplShouldBeGenerated() throws IOException {
        assertContent(File.separator + TOOLBAR_PACKAGE + File.separator + VIEW_IMPL, clientFolder, TOOLBAR_PACKAGE,
                      TOOLBAR_VIEW_IMPL + JAVA);
    }

    @Test
    public void toolbarViewImplBinderShouldBeGenerated() throws IOException {
        assertContent(File.separator + TOOLBAR_PACKAGE + File.separator + VIEW_BINDER_IMPL, clientFolder, TOOLBAR_PACKAGE,
                      TOOLBAR_VIEW_IMPL + UI_XML);
    }

    @Test
    public void toolbarPresenterTemplateShouldBeRemoved() {
        Path path = Paths.get(properties.getProperty(TARGET_PATH.name()),
                              MAIN_SOURCE_PATH,
                              JAVA_SOURCE_FOLDER,
                              TOOLBAR_PACKAGE,
                              TOOLBAR_VIEW_IMPL + JAVA);

        assertFalse(Files.exists(path));
    }
}