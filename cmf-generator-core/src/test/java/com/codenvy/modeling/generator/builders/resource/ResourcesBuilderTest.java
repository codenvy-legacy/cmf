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
package com.codenvy.modeling.generator.builders.resource;

import com.codenvy.modeling.generator.AbstractBuilderTest;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.codenvy.modeling.generator.GenerationController.Param.TARGET_PATH;
import static com.codenvy.modeling.generator.builders.PathConstants.CLIENT_PACKAGE;
import static com.codenvy.modeling.generator.builders.PathConstants.ICONS_FOLDER;
import static com.codenvy.modeling.generator.builders.PathConstants.MAIN_SOURCE_PATH;
import static com.codenvy.modeling.generator.builders.PathConstants.RESOURCES_SOURCE_FOLDER;
import static com.codenvy.modeling.generator.builders.ResourceNameConstants.MAIN_CSS_FULL_NAME;
import static com.codenvy.modeling.generator.builders.ResourceNameConstants.MAIN_GWT_MODULE_FULL_NAME;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Valeriy Svydenko
 */
public class ResourcesBuilderTest extends AbstractBuilderTest {
    public static final String EDITOR_RESOURCES = "/EditorResourcesCss";
    public static final String EDITOR_GWT_XML   = "/EditorGwtXml";
    public static final String IMAGE_NAME       = "element1.png";

    private String resourceFolder;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        String targetPath = properties.getProperty(TARGET_PATH.name());
        resourceFolder = targetPath + File.separator + MAIN_SOURCE_PATH + File.separator + RESOURCES_SOURCE_FOLDER;

        resourcesBuilder = new ResourcesBuilder();

        generateSources();
    }

    @Test
    public void cssFileShouldBeGenerated() throws IOException {
        assertContent(EDITOR_RESOURCES, prepareMainPackageFolderPath(), CLIENT_PACKAGE, MAIN_CSS_FULL_NAME);
    }

    @Test
    public void cssFileTemplateShouldBeRemoved() {
        Path path = Paths.get(resourceFolder, MAIN_CSS_FULL_NAME);

        assertFalse(Files.exists(path));
    }

    @Test
    public void gwtModuleShouldBeGenerated() throws IOException {
        assertContent(EDITOR_GWT_XML, prepareMainPackageFolderPath(), MAIN_GWT_MODULE_FULL_NAME);
    }

    @Test
    public void gwtModuleTemplateShouldBeRemoved() {
        Path path = Paths.get(resourceFolder, MAIN_GWT_MODULE_FULL_NAME);

        assertFalse(Files.exists(path));
    }

    @Test
    public void imageResourcesShouldBeGenerated() throws IOException {
        assertTrue(Files.exists(Paths.get(prepareMainPackageFolderPath(), CLIENT_PACKAGE, ICONS_FOLDER, IMAGE_NAME)));
    }
}