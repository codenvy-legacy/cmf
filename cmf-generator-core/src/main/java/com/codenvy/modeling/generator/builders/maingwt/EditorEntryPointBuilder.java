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
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

import static com.codenvy.modeling.generator.GenerationController.Param.EDITOR_NAME;
import static com.codenvy.modeling.generator.GenerationController.Param.MAIN_PACKAGE;
import static com.codenvy.modeling.generator.builders.ResourceNameConstants.ENTRY_POINT;
import static com.codenvy.modeling.generator.builders.FileExtensionConstants.JAVA;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.CURRENT_PACKAGE_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.EDITOR_NAME_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.MAIN_PACKAGE_MARKER;
import static com.codenvy.modeling.generator.builders.PathConstants.CLIENT_PACKAGE;
import static com.codenvy.modeling.generator.builders.PathConstants.JAVA_SOURCE_FOLDER;
import static com.codenvy.modeling.generator.builders.PathConstants.MAIN_SOURCE_PATH;
import static com.codenvy.modeling.generator.builders.PathConstants.MY_GWT_PACKAGE;

/**
 * @author Valeriy Svydenko
 */
public class EditorEntryPointBuilder extends AbstractBuilder<EditorEntryPointBuilder> {

    private Properties properties;

    @Inject
    public EditorEntryPointBuilder() {
        super();
        builder = this;
    }

    @Override
    public void build() throws IOException {
        String packageName = properties.getProperty(MAIN_PACKAGE.name());
        String clientPackage = packageName + '.' + CLIENT_PACKAGE + ';';
        String editorName = properties.getProperty(EDITOR_NAME.name());

        source = Paths.get(path,
                           MAIN_SOURCE_PATH,
                           JAVA_SOURCE_FOLDER,
                           MY_GWT_PACKAGE,
                           ENTRY_POINT + JAVA);

        target = Paths.get(path,
                           MAIN_SOURCE_PATH,
                           JAVA_SOURCE_FOLDER,
                           convertPathToPackageName(properties.getProperty(MAIN_PACKAGE.name())),
                           CLIENT_PACKAGE,
                           ENTRY_POINT + JAVA);

        replaceElements.put(CURRENT_PACKAGE_MARKER, clientPackage);
        replaceElements.put(MAIN_PACKAGE_MARKER, packageName + '.' + CLIENT_PACKAGE);
        replaceElements.put(EDITOR_NAME_MARKER, editorName);

        super.build();
    }

    @Nonnull
    public EditorEntryPointBuilder properties(@Nonnull Properties properties) {
        this.properties = properties;

        return this;
    }
}
