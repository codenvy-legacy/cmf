/*
 * Copyright 2014 Codenvy, S.A.
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

import com.codenvy.modeling.configuration.metamodel.diagram.Connection;
import com.codenvy.modeling.generator.builders.AbstractBuilder;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Paths;

import static com.codenvy.modeling.generator.builders.FileExtensionConstants.JAVA;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.CONNECTION_NAME_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.CURRENT_PACKAGE_MARKER;
import static com.codenvy.modeling.generator.builders.PathConstants.CLIENT_PACKAGE;
import static com.codenvy.modeling.generator.builders.PathConstants.ELEMENTS_PACKAGE;
import static com.codenvy.modeling.generator.builders.PathConstants.JAVA_SOURCE_FOLDER;
import static com.codenvy.modeling.generator.builders.PathConstants.MAIN_SOURCE_PATH;

/**
 * @author Andrey Plotnikov
 */
public class ConnectionBuilder extends AbstractBuilder<ConnectionBuilder> {

    private static final String CONNECTION_CLASS_NAME = "Connection";

    private String     mainPackage;
    private Connection connection;

    @Inject
    public ConnectionBuilder() {
        super();
        builder = this;
    }

    @Nonnull
    public ConnectionBuilder mainPackage(@Nonnull String mainPackage) {
        this.mainPackage = mainPackage;
        return this;
    }

    @Nonnull
    public ConnectionBuilder connection(@Nonnull Connection connection) {
        this.connection = connection;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public void build() throws IOException {
        String connectionName = connection.getName();
        String elementsPackage = mainPackage + '.' + CLIENT_PACKAGE + '.' + ELEMENTS_PACKAGE;

        source = Paths.get(path,
                           MAIN_SOURCE_PATH,
                           JAVA_SOURCE_FOLDER,
                           ELEMENTS_PACKAGE,
                           CONNECTION_CLASS_NAME + JAVA);
        target = Paths.get(path,
                           MAIN_SOURCE_PATH,
                           JAVA_SOURCE_FOLDER,
                           convertPathToPackageName(mainPackage),
                           CLIENT_PACKAGE,
                           ELEMENTS_PACKAGE,
                           connectionName + JAVA);

        replaceElements.put(CURRENT_PACKAGE_MARKER, elementsPackage);
        replaceElements.put(CONNECTION_NAME_MARKER, connectionName);

        super.build();
    }

}