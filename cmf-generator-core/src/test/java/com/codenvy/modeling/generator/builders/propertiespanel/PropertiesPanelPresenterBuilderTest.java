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

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.codenvy.modeling.generator.GenerationController.Param.TARGET_PATH;
import static com.codenvy.modeling.generator.builders.FileExtensionConstants.JAVA;
import static com.codenvy.modeling.generator.builders.PathConstants.JAVA_SOURCE_FOLDER;
import static com.codenvy.modeling.generator.builders.PathConstants.MAIN_SOURCE_PATH;
import static com.codenvy.modeling.generator.builders.PathConstants.PROPERTIES_PANEL_PACKAGE;
import static com.codenvy.modeling.generator.builders.ResourceNameConstants.PROPERTIES_PANEL_PRESENTER;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

/**
 * @author Valeriy Svydenko
 */
public class PropertiesPanelPresenterBuilderTest extends AbstractBuilderTest {
    private static final String ELEMENT1                         = "element1";
    private static final String ELEMENT2                         = "element2";
    private static final String PROPERTIES_PANEL_PRESENTER_NAME  = "Element1PropertiesPanelPresenter.java";
    private static final String PROPERTIES_PANEL_PRESENTER_NAME2 = "Element2PropertiesPanelPresenter.java";

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        propertiesPanelPresenterBuilder = new PropertiesPanelPresenterBuilder();

        when(propertiesPanelPresenterBuilderProvider.get()).thenAnswer(new Answer<PropertiesPanelPresenterBuilder>() {
            @Override
            public PropertiesPanelPresenterBuilder answer(InvocationOnMock invocation) throws Throwable {
                return propertiesPanelPresenterBuilder;
            }
        });

        generateSources();
    }

    @Test
    public void element1PropertiesPanelPresenterShouldBeGenerated() throws IOException {
        assertContent(File.separator + PROPERTIES_PANEL_PACKAGE + File.separator + ELEMENT1 + File.separator + PRESENTER, clientFolder,
                      PROPERTIES_PANEL_PACKAGE,
                      ELEMENT1, PROPERTIES_PANEL_PRESENTER_NAME);
    }

    @Test
    public void element2propertiesPanelPresenterShouldBeGenerated() throws IOException {
        assertContent(File.separator + PROPERTIES_PANEL_PACKAGE + File.separator + ELEMENT2 + File.separator + PRESENTER, clientFolder,
                      PROPERTIES_PANEL_PACKAGE, ELEMENT2, PROPERTIES_PANEL_PRESENTER_NAME2);
    }

    @Test
    public void propertiesPanelPresenterTemplateShouldBeRemoved() {
        Path path = Paths.get(properties.getProperty(TARGET_PATH.name()),
                              MAIN_SOURCE_PATH,
                              JAVA_SOURCE_FOLDER,
                              PROPERTIES_PANEL_PACKAGE,
                              PROPERTIES_PANEL_PRESENTER + JAVA);

        assertFalse(Files.exists(path));
    }
}