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
package com.codenvy.modeling.generator.builders.maingwt;

import com.codenvy.modeling.generator.builders.AbstractBuilder;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

import static com.codenvy.modeling.generator.GenerationController.Param.MAIN_PACKAGE;
import static com.codenvy.modeling.generator.GenerationController.Param.TARGET_PATH;
import static com.codenvy.modeling.generator.builders.FileExtensionConstants.JAVA;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.CURRENT_PACKAGE_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.CUSTOM_STYLE;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.IMAGE_RESOURCES;
import static com.codenvy.modeling.generator.builders.OffsetBuilderConstants.OFFSET;
import static com.codenvy.modeling.generator.builders.PathConstants.CLIENT_PACKAGE;
import static com.codenvy.modeling.generator.builders.PathConstants.ICONS_FOLDER;
import static com.codenvy.modeling.generator.builders.PathConstants.JAVA_SOURCE_FOLDER;
import static com.codenvy.modeling.generator.builders.PathConstants.MAIN_SOURCE_PATH;
import static com.codenvy.modeling.generator.builders.PathConstants.MY_GWT_PACKAGE;
import static com.codenvy.modeling.generator.builders.PathConstants.RESOURCES_SOURCE_FOLDER;
import static com.codenvy.modeling.generator.builders.ResourceNameConstants.EDITOR_RESOURCES;

/**
 * @author Valeriy Svydenko
 */
public class EditorResourcesBuilder extends AbstractBuilder<EditorResourcesBuilder> {

    private Properties properties;

    @Inject
    public EditorResourcesBuilder() {
        super();
        builder = this;
    }

    @Override
    public void build() throws IOException {
        String packageName = properties.getProperty(MAIN_PACKAGE.name());
        String clientPackage = packageName + '.' + CLIENT_PACKAGE;

        String imageResources = prepareImageResource(packageName);

        source = Paths.get(path,
                           MAIN_SOURCE_PATH,
                           JAVA_SOURCE_FOLDER,
                           MY_GWT_PACKAGE,
                           EDITOR_RESOURCES + JAVA);

        target = Paths.get(path,
                           MAIN_SOURCE_PATH,
                           JAVA_SOURCE_FOLDER,
                           convertPathToPackageName(properties.getProperty(MAIN_PACKAGE.name())),
                           CLIENT_PACKAGE,
                           EDITOR_RESOURCES + JAVA);

        replaceElements.put(CURRENT_PACKAGE_MARKER, clientPackage);
        replaceElements.put(CUSTOM_STYLE, "String fullSize();");
        replaceElements.put(IMAGE_RESOURCES, imageResources);

        super.build();
    }

    @Nonnull
    public EditorResourcesBuilder properties(@Nonnull Properties properties) {
        this.properties = properties;

        return this;
    }

    @Nonnull
    private String prepareImageResource(String packageName) {
        StringBuilder imageResources = new StringBuilder();

        String targetPath = properties.getProperty(TARGET_PATH.name());
        String resourceFolder = targetPath + File.separator + MAIN_SOURCE_PATH + File.separator + RESOURCES_SOURCE_FOLDER;
        String mainPackageFolder = resourceFolder + File.separator + convertPathToPackageName(packageName);
        File iconsFolder = Paths.get(mainPackageFolder, CLIENT_PACKAGE, ICONS_FOLDER).toFile();

        File[] icons = iconsFolder.listFiles();
        if (icons != null) {
            for (File icon : icons) {
                imageResources.append(OFFSET)
                              .append("@Source(\"")
                              .append(ICONS_FOLDER).append(File.separator).append(icon.getName()).append("\")\n")
                              .append(OFFSET)
                              .append("ImageResource ")
                              .append(changeFirstSymbolToLowCase(icon.getName().substring(0, icon.getName().indexOf('.'))))
                              .append("();\n\n");
            }
        }

        if (imageResources.lastIndexOf("\n") > 0) {
            imageResources.deleteCharAt(imageResources.lastIndexOf("\n"));
        }

        return imageResources.toString();
    }
}