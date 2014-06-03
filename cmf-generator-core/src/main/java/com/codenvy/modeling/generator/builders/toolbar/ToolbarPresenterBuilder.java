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

package com.codenvy.modeling.generator.builders.toolbar;

import com.codenvy.modeling.configuration.metamodel.diagram.Connection;
import com.codenvy.modeling.configuration.metamodel.diagram.Element;
import com.codenvy.modeling.generator.builders.AbstractBuilder;
import com.codenvy.modeling.generator.builders.ContentReplacer;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static com.codenvy.modeling.generator.builders.ClassNameConstants.EDITOR_STATE;
import static com.codenvy.modeling.generator.builders.EditorStateConstants.CREATE_CONNECTION_SOURCE_STATE_FORMAT;
import static com.codenvy.modeling.generator.builders.EditorStateConstants.CREATE_ELEMENT_STATE_FORMAT;
import static com.codenvy.modeling.generator.builders.FileExtensionConstants.JAVA;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.CONNECTION_NAME_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.CONNECTION_UPPER_NAME_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.CURRENT_PACKAGE_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.ELEMENT_NAME_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.ELEMENT_UPPER_NAME_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.MAIN_PACKAGE_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.STATIC_IMPORT_MARKER;
import static com.codenvy.modeling.generator.builders.OffsetBuilderConstants.OFFSET;
import static com.codenvy.modeling.generator.builders.OffsetBuilderConstants.TWO_TABS;
import static com.codenvy.modeling.generator.builders.PathConstants.CLIENT_PACKAGE;
import static com.codenvy.modeling.generator.builders.PathConstants.JAVA_SOURCE_FOLDER;
import static com.codenvy.modeling.generator.builders.PathConstants.MAIN_SOURCE_PATH;
import static com.codenvy.modeling.generator.builders.PathConstants.TOOLBAR_PACKAGE;

/**
 * @author Andrey Plotnikov
 */
public class ToolbarPresenterBuilder extends AbstractBuilder<ToolbarPresenterBuilder> {

    private static final String CREATE_ON_ELEMENT_BUTTON_CLICKED_CODE_FORMAT    =
            OFFSET + "@Override\n" +
            OFFSET + "public void onelementNameButtonClicked() {\n" +
            TWO_TABS + "setState(CREATING_elementUpperName);\n" +
            OFFSET + "}\n\n";
    private static final String CREATE_ON_CONNECTION_BUTTON_CLICKED_CODE_FORMAT =
            OFFSET + "@Override\n" +
            OFFSET + "public void onconnectionNameButtonClicked() {\n" +
            TWO_TABS + "setState(CREATING_connectionUpperName_SOURCE);\n" +
            OFFSET + "}\n\n";

    private static final String CHANGE_EDITOR_STATE_MASK = "change_editor_states";

    private static final String TOOLBAR_PRESENTER_NAME = "ToolbarPresenter";

    private String          mainPackage;
    private Element         rootElement;
    private Set<Element>    elements;
    private Set<Connection> connections;

    @Inject
    public ToolbarPresenterBuilder() {
        super();
        builder = this;
    }

    @Nonnull
    public ToolbarPresenterBuilder mainPackage(@Nonnull String mainPackage) {
        this.mainPackage = mainPackage;
        return this;
    }

    @Nonnull
    public ToolbarPresenterBuilder elements(@Nonnull Set<Element> elements) {
        this.elements = elements;
        return this;
    }

    @Nonnull
    public ToolbarPresenterBuilder rootElement(@Nonnull Element rootElement) {
        this.rootElement = rootElement;
        return this;
    }

    @Nonnull
    public ToolbarPresenterBuilder connections(@Nonnull Set<Connection> connections) {
        this.connections = connections;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public void build() throws IOException {
        String clientPackage = mainPackage + '.' + CLIENT_PACKAGE;
        String toolbarPackage = clientPackage + '.' + TOOLBAR_PACKAGE;
        String stateClassImport = "import static " + clientPackage + '.' + EDITOR_STATE + '.';

        StringBuilder staticImports = new StringBuilder();
        StringBuilder methods = new StringBuilder();

        for (Element element : elements) {
            if (!element.equals(rootElement)) {
                String elementName = element.getName();

                staticImports.append(stateClassImport)
                             .append(String.format(CREATE_ELEMENT_STATE_FORMAT, elementName.toUpperCase()))
                             .append(";\n");

                methods.append(createOnElementButtonClickedCode(elementName));
            }
        }

        for (Connection connection : connections) {
            String connectionName = connection.getName();

            staticImports.append(stateClassImport)
                         .append(String.format(CREATE_CONNECTION_SOURCE_STATE_FORMAT, connectionName.toUpperCase()))
                         .append(";\n");

            methods.append(createOnConnectionButtonClickedCode(connectionName));
        }

        Path toolbarPresenterSource = Paths.get(path,
                                                MAIN_SOURCE_PATH,
                                                JAVA_SOURCE_FOLDER,
                                                TOOLBAR_PACKAGE,
                                                TOOLBAR_PRESENTER_NAME + JAVA);
        Path toolbarPresenterTarget = Paths.get(path,
                                                MAIN_SOURCE_PATH,
                                                JAVA_SOURCE_FOLDER,
                                                convertPathToPackageName(mainPackage),
                                                CLIENT_PACKAGE,
                                                TOOLBAR_PACKAGE,
                                                TOOLBAR_PRESENTER_NAME + JAVA);

        Map<String, String> replaceElements = new LinkedHashMap<>();
        replaceElements.put(MAIN_PACKAGE_MARKER, mainPackage);
        replaceElements.put(CURRENT_PACKAGE_MARKER, toolbarPackage);
        replaceElements.put(STATIC_IMPORT_MARKER, staticImports.toString());
        replaceElements.put(CHANGE_EDITOR_STATE_MASK, methods.toString());

        createFile(toolbarPresenterSource, toolbarPresenterTarget, replaceElements);

        removeTemplate(toolbarPresenterSource);
        removeTemplateParentFolder(toolbarPresenterSource.getParent());
    }

    @Nonnull
    private String createOnElementButtonClickedCode(@Nonnull String elementName) {
        Map<String, String> masks = new HashMap<>();
        masks.put(ELEMENT_NAME_MARKER, elementName);
        masks.put(ELEMENT_UPPER_NAME_MARKER, elementName.toUpperCase());

        return ContentReplacer.replace(CREATE_ON_ELEMENT_BUTTON_CLICKED_CODE_FORMAT, masks);
    }

    @Nonnull
    private String createOnConnectionButtonClickedCode(@Nonnull String connectionName) {
        Map<String, String> masks = new HashMap<>();
        masks.put(CONNECTION_NAME_MARKER, connectionName);
        masks.put(CONNECTION_UPPER_NAME_MARKER, connectionName.toUpperCase());

        return ContentReplacer.replace(CREATE_ON_CONNECTION_BUTTON_CLICKED_CODE_FORMAT, masks);
    }

}