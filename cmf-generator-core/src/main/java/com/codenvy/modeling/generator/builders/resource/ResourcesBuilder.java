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

import com.codenvy.modeling.configuration.ConfigurationFactory;
import com.codenvy.modeling.generator.builders.AbstractBuilder;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Properties;

import static com.codenvy.modeling.generator.GenerationController.Param.EDITOR_NAME;
import static com.codenvy.modeling.generator.GenerationController.Param.MAIN_PACKAGE;
import static com.codenvy.modeling.generator.GenerationController.Param.TARGET_PATH;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.EDITOR_NAME_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.ENTRY_POINT_CLASS_MARKER;
import static com.codenvy.modeling.generator.builders.PathConstants.CLIENT_PACKAGE;
import static com.codenvy.modeling.generator.builders.PathConstants.ICONS_FOLDER;
import static com.codenvy.modeling.generator.builders.PathConstants.MAIN_SOURCE_PATH;
import static com.codenvy.modeling.generator.builders.PathConstants.RESOURCES_SOURCE_FOLDER;
import static com.codenvy.modeling.generator.builders.ResourceNameConstants.ENTRY_POINT;
import static com.codenvy.modeling.generator.builders.ResourceNameConstants.MAIN_CSS_FULL_NAME;
import static com.codenvy.modeling.generator.builders.ResourceNameConstants.MAIN_GWT_MODULE_FULL_NAME;

/**
 * Class of generating resources. It provides an ability to read properties and generate resources.
 *
 * @author Valeriy Svydenko
 */
public class ResourcesBuilder extends AbstractBuilder<ResourcesBuilder> {

    private Properties properties;
    private String     packageName;
    private String     editorName;
    private String     iconsName;
    private String     resourceFolder;
    private String     mainPackageFolder;


    @Inject
    public ResourcesBuilder() {
        super();
        builder = this;
    }

    /** {@inheritDoc} */
    @Override
    public void build() throws IOException {

        readPropertiesData();

        createImageResources();

        createCSSResource();

        createGWTModuleResource();
    }

    @Nonnull
    public ResourcesBuilder properties(@Nonnull Properties properties) {
        this.properties = properties;

        return this;
    }

    /**
     * Create GWT module file.
     *
     * @throws IOException
     */
    private void createGWTModuleResource() throws IOException {
        Path gwtModuleSource = Paths.get(resourceFolder, MAIN_GWT_MODULE_FULL_NAME);
        Path gwtModuleTarget = Paths.get(mainPackageFolder, MAIN_GWT_MODULE_FULL_NAME);

        HashMap<String, String> replaceElements = new HashMap<>();
        replaceElements.put(EDITOR_NAME_MARKER, editorName);
        replaceElements.put(ENTRY_POINT_CLASS_MARKER, packageName + '.' + CLIENT_PACKAGE + '.' + ENTRY_POINT);

        createFile(gwtModuleSource, gwtModuleTarget, replaceElements);
        Files.delete(gwtModuleSource);
    }

    /**
     * Create the image resources.
     *
     * @throws IOException
     */
    private void createImageResources() throws IOException {
        Files.createDirectories(Paths.get(mainPackageFolder, CLIENT_PACKAGE, ICONS_FOLDER));

        Files.walkFileTree(Paths.get(iconsName, ICONS_FOLDER), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path iconsDirectory = Paths.get(mainPackageFolder, CLIENT_PACKAGE, ICONS_FOLDER, file.getFileName().toString());
                if (!Files.exists(Paths.get(mainPackageFolder, CLIENT_PACKAGE, ICONS_FOLDER, file.getFileName().toString()))) {
                    Files.copy(Paths.get(iconsName, ICONS_FOLDER, file.getFileName().toString()), iconsDirectory);
                }

                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Create a stylesheet file.
     *
     * @throws IOException
     */
    private void createCSSResource() throws IOException {
        Path mainCssFileSource = Paths.get(resourceFolder, MAIN_CSS_FULL_NAME);
        Path mainCssFileTarget = Paths.get(mainPackageFolder, CLIENT_PACKAGE, MAIN_CSS_FULL_NAME);

        // TODO It is the best place for adding custom styles
        Files.move(mainCssFileSource, mainCssFileTarget);
    }

    /**
     * Read properties and prepare paths and names of resources.
     */
    private void readPropertiesData() {
        String targetPath = properties.getProperty(TARGET_PATH.name());

        packageName = properties.getProperty(MAIN_PACKAGE.name());
        editorName = properties.getProperty(EDITOR_NAME.name());
        iconsName = properties.getProperty(ConfigurationFactory.PathParameter.STYLE.name());
        resourceFolder = targetPath + File.separator + MAIN_SOURCE_PATH + File.separator + RESOURCES_SOURCE_FOLDER;
        mainPackageFolder = resourceFolder + File.separator + convertPathToPackageName(packageName);
    }
}