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
package com.codenvy.modeling.generator.builders.elements;

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
import static com.codenvy.modeling.generator.builders.PathConstants.ELEMENTS_PACKAGE;
import static com.codenvy.modeling.generator.builders.PathConstants.JAVA_SOURCE_FOLDER;
import static com.codenvy.modeling.generator.builders.PathConstants.MAIN_SOURCE_PATH;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

/**
 * @author Valeriy Svydenko
 */
public class ElementBuilderTest extends AbstractBuilderTest {
    private static final String ELEMENT1 = "Element1";

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        elementBuilder = new ElementBuilder();

        when(elementBuilderProvider.get()).thenAnswer(new Answer<ElementBuilder>() {
            @Override
            public ElementBuilder answer(InvocationOnMock invocation) throws Throwable {
                return elementBuilder;
            }
        });

        generateSources();
    }

    @Test
    public void elementShouldBeGenerated() throws IOException {
        assertContent(File.separator + ELEMENTS_PACKAGE + File.separator + ELEMENT1, clientFolder, ELEMENTS_PACKAGE, ELEMENT1 + JAVA);
    }

    @Test
    public void elementShouldBeRemoved() {
        Path path = Paths.get(properties.getProperty(TARGET_PATH.name()),
                              MAIN_SOURCE_PATH,
                              JAVA_SOURCE_FOLDER,
                              ELEMENTS_PACKAGE,
                              ELEMENT1 + JAVA);

        assertFalse(Files.exists(path));
    }

    @Test
    public void mainElementShouldBeGenerated() throws IOException {
        assertContent(File.separator + ELEMENTS_PACKAGE + File.separator + MAIN_ELEMENT, clientFolder, ELEMENTS_PACKAGE,
                      MAIN_ELEMENT + JAVA);
    }

    @Test
    public void mainElementTemplateShouldBeRemoved() {
        Path path = Paths.get(properties.getProperty(TARGET_PATH.name()),
                              MAIN_SOURCE_PATH,
                              JAVA_SOURCE_FOLDER,
                              ELEMENTS_PACKAGE,
                              MAIN_ELEMENT + JAVA);

        assertFalse(Files.exists(path));
    }
}