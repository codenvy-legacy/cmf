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
package com.codenvy.modeling.generator.builders.inject;

import com.codenvy.modeling.generator.builders.AbstractBuilder;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Paths;

import static com.codenvy.modeling.generator.builders.FileExtensionConstants.JAVA;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.CURRENT_PACKAGE_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.MAIN_PACKAGE_MARKER;
import static com.codenvy.modeling.generator.builders.PathConstants.CLIENT_PACKAGE;
import static com.codenvy.modeling.generator.builders.PathConstants.INJECT_PACKAGE;
import static com.codenvy.modeling.generator.builders.PathConstants.JAVA_SOURCE_FOLDER;
import static com.codenvy.modeling.generator.builders.PathConstants.MAIN_SOURCE_PATH;
import static com.codenvy.modeling.generator.builders.ResourceNameConstants.EDITOR_FACTORY_NAME;

/**
 * @author Valeriy Svydenko
 */
public class EditorFactoryBuilder extends AbstractBuilder<EditorFactoryBuilder> {

    private String mainPackage;

    @Inject
    public EditorFactoryBuilder() {
        super();
        builder = this;
    }

    @Override
    public void build() throws IOException {
        String clientPackage = mainPackage + '.' + CLIENT_PACKAGE;
        String injectPackage = clientPackage + '.' + INJECT_PACKAGE;

        source = Paths.get(path,
                           MAIN_SOURCE_PATH,
                           JAVA_SOURCE_FOLDER,
                           INJECT_PACKAGE,
                           EDITOR_FACTORY_NAME + JAVA);

        target = Paths.get(path,
                           MAIN_SOURCE_PATH,
                           JAVA_SOURCE_FOLDER,
                           convertPathToPackageName(mainPackage),
                           CLIENT_PACKAGE,
                           INJECT_PACKAGE,
                           EDITOR_FACTORY_NAME + JAVA);

        replaceElements.put(MAIN_PACKAGE_MARKER, mainPackage);
        replaceElements.put(CURRENT_PACKAGE_MARKER, injectPackage);

        super.build();
    }

    @Nonnull
    public EditorFactoryBuilder mainPackage(@Nonnull String mainPackage) {
        this.mainPackage = mainPackage;

        return this;
    }
}