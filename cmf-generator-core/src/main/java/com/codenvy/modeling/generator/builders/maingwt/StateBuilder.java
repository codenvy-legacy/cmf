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

import com.codenvy.modeling.configuration.metamodel.diagram.Connection;
import com.codenvy.modeling.configuration.metamodel.diagram.Element;
import com.codenvy.modeling.generator.builders.AbstractBuilder;
import com.codenvy.modeling.generator.builders.OffsetBuilderConstants;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Set;

import static com.codenvy.modeling.generator.GenerationController.Param.MAIN_PACKAGE;
import static com.codenvy.modeling.generator.builders.ResourceNameConstants.EDITOR_STATE;
import static com.codenvy.modeling.generator.builders.EditorStateConstants.CREATE_CONNECTION_SOURCE_STATE_FORMAT;
import static com.codenvy.modeling.generator.builders.EditorStateConstants.CREATE_CONNECTION_TARGET_STATE_FORMAT;
import static com.codenvy.modeling.generator.builders.EditorStateConstants.CREATE_ELEMENT_STATE_FORMAT;
import static com.codenvy.modeling.generator.builders.EditorStateConstants.CREATE_NOTHING_STATE;
import static com.codenvy.modeling.generator.builders.FileExtensionConstants.JAVA;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.ALL_ELEMENTS_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.CURRENT_PACKAGE_MARKER;
import static com.codenvy.modeling.generator.builders.PathConstants.CLIENT_PACKAGE;
import static com.codenvy.modeling.generator.builders.PathConstants.JAVA_SOURCE_FOLDER;
import static com.codenvy.modeling.generator.builders.PathConstants.MAIN_SOURCE_PATH;
import static com.codenvy.modeling.generator.builders.PathConstants.MY_GWT_PACKAGE;

/**
 * @author Valeriy Svydenko
 */
public class StateBuilder extends AbstractBuilder<StateBuilder> {

    private Properties      properties;
    private Set<Element>    elements;
    private Set<Connection> connections;

    @Inject
    public StateBuilder() {
        super();
        builder = this;
    }

    @Override
    public void build() throws IOException {
        String packageName = properties.getProperty(MAIN_PACKAGE.name());

        StringBuilder elementsEnum = new StringBuilder();

        Element rootElement = findRootElement(elements);

        for (Element element : elements) {
            if (!element.equals(rootElement)) {
                elementsEnum.append(String.format(CREATE_ELEMENT_STATE_FORMAT, element.getName().toUpperCase()))
                            .append(",\n").append(OffsetBuilderConstants.OFFSET);
            }
        }

        for (Connection connection : connections) {
            String connectionName = connection.getName().toUpperCase();
            elementsEnum.append(String.format(CREATE_CONNECTION_SOURCE_STATE_FORMAT, connectionName))
                        .append(",\n").append(OffsetBuilderConstants.OFFSET);
            elementsEnum.append(String.format(CREATE_CONNECTION_TARGET_STATE_FORMAT, connectionName))
                        .append(",\n").append(OffsetBuilderConstants.OFFSET);
        }

        elementsEnum.append(CREATE_NOTHING_STATE);

        source = Paths.get(path,
                           MAIN_SOURCE_PATH,
                           JAVA_SOURCE_FOLDER,
                           MY_GWT_PACKAGE,
                           EDITOR_STATE + JAVA);

        target = Paths.get(path,
                           MAIN_SOURCE_PATH,
                           JAVA_SOURCE_FOLDER,
                           convertPathToPackageName(properties.getProperty(MAIN_PACKAGE.name())),
                           CLIENT_PACKAGE,
                           EDITOR_STATE + JAVA);

        replaceElements.put(CURRENT_PACKAGE_MARKER, packageName + '.' + CLIENT_PACKAGE);
        replaceElements.put(ALL_ELEMENTS_MARKER, elementsEnum.toString());

        super.build();
    }

    @Nonnull
    public StateBuilder elements(@Nonnull Set<Element> elements) {
        this.elements = elements;

        return this;
    }

    @Nonnull
    public StateBuilder connections(@Nonnull Set<Connection> connections) {
        this.connections = connections;

        return this;
    }

    @Nonnull
    public StateBuilder properties(@Nonnull Properties properties) {
        this.properties = properties;

        return this;
    }
}