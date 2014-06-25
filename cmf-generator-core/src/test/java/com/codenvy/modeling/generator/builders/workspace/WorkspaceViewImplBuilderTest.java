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
package com.codenvy.modeling.generator.builders.workspace;

import com.codenvy.modeling.generator.AbstractBuilderTest;
import com.codenvy.modeling.generator.builders.xml.impl.GFieldImpl;
import com.codenvy.modeling.generator.builders.xml.impl.UIXmlBuilderImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GFlowPanelImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GScrollPanelImpl;

import org.junit.Before;
import org.junit.Test;

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
import static com.codenvy.modeling.generator.builders.PathConstants.WORKSPACE_PACKAGE;
import static com.codenvy.modeling.generator.builders.ResourceNameConstants.WORKSPACE_VIEW_IMPL;
import static org.junit.Assert.assertFalse;

/**
 * @author Valeriy Svydenko
 */
public class WorkspaceViewImplBuilderTest extends AbstractBuilderTest {
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        workspaceViewImplBuilder = new WorkspaceViewImplBuilder(new UIXmlBuilderImpl(),
                                                                new GFieldImpl(),
                                                                new GScrollPanelImpl(),
                                                                new GFlowPanelImpl());

        generateSources();
    }

    @Test
    public void workspaceViewImplShouldBeGenerated() throws IOException {
        assertContent(File.separator + WORKSPACE_PACKAGE + File.separator + VIEW_IMPL, clientFolder, WORKSPACE_PACKAGE,
                      WORKSPACE_VIEW_IMPL + JAVA);
    }

    @Test
    public void workspaceViewImplTemplateShouldBeRemoved() {
        Path path = Paths.get(properties.getProperty(TARGET_PATH.name()),
                              MAIN_SOURCE_PATH,
                              JAVA_SOURCE_FOLDER,
                              WORKSPACE_PACKAGE,
                              WORKSPACE_VIEW_IMPL + JAVA);

        assertFalse(Files.exists(path));
    }

    @Test
    public void workspaceViewImplBinderShouldBeGenerated() throws IOException {
        assertContent(File.separator + WORKSPACE_PACKAGE + File.separator + VIEW_BINDER_IMPL, clientFolder, WORKSPACE_PACKAGE,
                      WORKSPACE_VIEW_IMPL + UI_XML);
    }
}