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

import com.codenvy.modeling.configuration.metamodel.diagram.Element;
import com.codenvy.modeling.configuration.metamodel.diagram.Property;
import com.codenvy.modeling.generator.builders.AbstractBuilderHelper;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Set;

import static com.codenvy.modeling.generator.GenerationController.Param.MAIN_PACKAGE;
import static com.codenvy.modeling.generator.GenerationController.Param.TARGET_PATH;
import static com.codenvy.modeling.generator.builders.FileExtensionConstants.JAVA;
import static com.codenvy.modeling.generator.builders.PathConstants.JAVA_SOURCE_FOLDER;
import static com.codenvy.modeling.generator.builders.PathConstants.MAIN_SOURCE_PATH;
import static com.codenvy.modeling.generator.builders.PathConstants.PROPERTIES_PANEL_PACKAGE;
import static com.codenvy.modeling.generator.builders.ResourceNameConstants.PROPERTIES_PANEL_PRESENTER;
import static org.junit.Assert.assertFalse;

/**
 * @author Valeriy Svydenko
 */
public class PropertiesPanelPresenterBuilderTest extends AbstractBuilderHelper {
    private static final String ELEMENT1                        = "element1";
    private static final String PROPERTIES_PANEL_PRESENTER_NAME = "Element1PropertiesPanelPresenter.java";

    @Before
    public void setUp() throws Exception {
        Set<Element> elements = configuration.getDiagramConfiguration().getElements();

        for (Iterator<Element> iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = iterator.next();
            Set<Property> prop = element.getProperties();

            boolean hasNext = iterator.hasNext();

            propertiesPanelPresenterBuilderProvider.get()

                                                   .path(properties.getProperty(TARGET_PATH.name()))

                                                   .needRemoveTemplate(!hasNext)

                                                   .mainPackage(properties.getProperty(MAIN_PACKAGE.name()))
                                                   .properties(prop)
                                                   .element(element)

                                                   .build();

        }
    }

    @Test
    public void propertiesPanelPresenterShouldBeGenerated() throws IOException {
        assertContent(File.separator + PROPERTIES_PANEL_PACKAGE + File.separator + PRESENTER, clientFolder, PROPERTIES_PANEL_PACKAGE,
                      ELEMENT1, PROPERTIES_PANEL_PRESENTER_NAME);
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