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

import com.codenvy.modeling.configuration.metamodel.diagram.Connection;
import com.codenvy.modeling.configuration.metamodel.diagram.Element;
import com.codenvy.modeling.generator.builders.AbstractBuilderHelper;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import static com.codenvy.modeling.generator.GenerationController.Param.MAIN_PACKAGE;
import static com.codenvy.modeling.generator.GenerationController.Param.TARGET_PATH;
import static com.codenvy.modeling.generator.builders.FileExtensionConstants.JAVA;
import static com.codenvy.modeling.generator.builders.PathConstants.JAVA_SOURCE_FOLDER;
import static com.codenvy.modeling.generator.builders.PathConstants.MAIN_SOURCE_PATH;
import static com.codenvy.modeling.generator.builders.PathConstants.TOOLBAR_PACKAGE;
import static com.codenvy.modeling.generator.builders.ResourceNameConstants.TOOLBAR_VIEW;
import static org.junit.Assert.assertFalse;

/**
 * @author Valeriy Svydenko
 */
public class ToolbarViewBuilderTest extends AbstractBuilderHelper {

    @Before
    public void setUp() throws Exception {
        ToolbarViewBuilder builder = new ToolbarViewBuilder();

        Set<Element> elements = configuration.getDiagramConfiguration().getElements();
        Set<Connection> connections = configuration.getDiagramConfiguration().getConnections();

        builder.path(properties.getProperty(TARGET_PATH.name()))

               .needRemoveTemplate(true)
               .mainPackage(properties.getProperty(MAIN_PACKAGE.name()))
               .elements(elements)
               .connections(connections)

               .build();

    }

    @Test
    public void toolbarPresenterShouldBeGenerated() throws IOException {
        assertContent(File.separator + TOOLBAR_PACKAGE + File.separator + VIEW, clientFolder, TOOLBAR_PACKAGE,
                      TOOLBAR_VIEW + JAVA);
    }

    @Test
    public void toolbarPresenterTemplateShouldBeRemoved() {
        Path path = Paths.get(properties.getProperty(TARGET_PATH.name()),
                              MAIN_SOURCE_PATH,
                              JAVA_SOURCE_FOLDER,
                              TOOLBAR_PACKAGE,
                              TOOLBAR_VIEW + JAVA);

        assertFalse(Files.exists(path));
    }
}