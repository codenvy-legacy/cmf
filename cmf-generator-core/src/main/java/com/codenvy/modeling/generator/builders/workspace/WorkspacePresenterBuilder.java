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

package com.codenvy.modeling.generator.builders.workspace;

import com.codenvy.modeling.configuration.metamodel.diagram.Connection;
import com.codenvy.modeling.configuration.metamodel.diagram.Element;
import com.codenvy.modeling.generator.builders.AbstractBuilder;
import com.codenvy.modeling.generator.builders.ContentReplacer;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static com.codenvy.modeling.generator.builders.ClassNameConstants.EDITOR_STATE;
import static com.codenvy.modeling.generator.builders.EditorStateConstants.CREATE_CONNECTION_SOURCE_STATE_FORMAT;
import static com.codenvy.modeling.generator.builders.EditorStateConstants.CREATE_CONNECTION_TARGET_STATE_FORMAT;
import static com.codenvy.modeling.generator.builders.EditorStateConstants.CREATE_ELEMENT_STATE_FORMAT;
import static com.codenvy.modeling.generator.builders.EditorStateConstants.CREATE_NOTHING_STATE;
import static com.codenvy.modeling.generator.builders.FileExtensionConstants.JAVA;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.CURRENT_PACKAGE_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.MAIN_PACKAGE_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.STATIC_IMPORT_MARKER;
import static com.codenvy.modeling.generator.builders.OffsetBuilderConstants.FIVE_TABS;
import static com.codenvy.modeling.generator.builders.OffsetBuilderConstants.FOUR_TABS;
import static com.codenvy.modeling.generator.builders.OffsetBuilderConstants.THREE_TABS;
import static com.codenvy.modeling.generator.builders.PathConstants.CLIENT_PACKAGE;
import static com.codenvy.modeling.generator.builders.PathConstants.ELEMENTS_PACKAGE;
import static com.codenvy.modeling.generator.builders.PathConstants.JAVA_SOURCE_FOLDER;
import static com.codenvy.modeling.generator.builders.PathConstants.MAIN_SOURCE_PATH;
import static com.codenvy.modeling.generator.builders.PathConstants.WORKSPACE_PACKAGE;

/**
 * @author Andrey Plotnikov
 */
public class WorkspacePresenterBuilder extends AbstractBuilder<WorkspacePresenterBuilder> {

    private static final String CREATE_ELEMENT_CODE_FORMAT =
            THREE_TABS + "case CREATING_createState:\n" +
            FOUR_TABS + "elementName argumentName = new elementName();\n\n" +
            FOUR_TABS +
            "((WorkspaceView)view).addelementName(x, y, argumentName);\n" +
            FOUR_TABS + "addElement(argumentName);\n\n" +
            FOUR_TABS + "setState(CREATING_NOTING);\n" +
            FOUR_TABS + "break;\n";

    private static final String CREATE_GRAPHICAL_ELEMENT_CODE_FORMAT =
            THREE_TABS + "if (element instanceof elementName) {\n" +
            FOUR_TABS + "((WorkspaceView)view).addelementName(x, y, (elementName)element);\n" +
            THREE_TABS + "}\n";

    private static final String CREATE_CONNECTION_CODE_FORMAT =
            THREE_TABS + "case CREATING_connectionUpperName_SOURCE:\n" +
            FOUR_TABS + "setState(CREATING_connectionUpperName_TARGET);\n" +
            FOUR_TABS + "break;\n" +
            THREE_TABS + "case CREATING_connectionUpperName_TARGET:\n" +
            FOUR_TABS + "((WorkspaceView)view).addconnectionName(prevSelectedElement, selectedElement);\n" +
            FOUR_TABS + "source = elements.get(prevSelectedElement);\n" +
            FOUR_TABS + "connectionName argumentName = new connectionName((Shape)source, (Shape)element);\n" +
            FOUR_TABS + "elements.put(element.getId(), element);\n\n" +
            FOUR_TABS + "parent = source.getParent();\n" +
            FOUR_TABS + "if (parent != null) {\n" +
            FIVE_TABS + "parent.addElement(argumentName);\n" +
            FOUR_TABS + "}\n" +
            FOUR_TABS + "setState(CREATING_NOTING);\n" +
            FOUR_TABS + "break;\n";

    private static final String ELEMENT_NAME_MARKER          = "elementName";
    private static final String ARGUMENT_NAME_MARKER         = "argumentName";
    private static final String CREATE_STATE_MARKER          = "createState";
    private static final String CONNECTION_NAME_MARKER       = "connectionName";
    private static final String CONNECTION_UPPER_NAME_MARKER = "connectionUpperName";

    private static final String IMPORT_MARKER                     = "import_elements";
    private static final String MAIN_ELEMENT_NAME_MARKER          = "main_element_name";
    private static final String CREATE_GRAPHIC_ELEMENTS_MARKER    = "create_graphic_elements";
    private static final String CREATE_GRAPHIC_CONNECTIONS_MARKER = "create_graphic_connections";
    private static final String CREATE_GRAPHICAL_ELEMENTS_MARKER  = "create_graphical_elements";

    private static final String WORKSPACE_PRESENTER_NAME = "WorkspacePresenter";

    private String          mainPackage;
    private Element         rootElement;
    private Set<Element>    elements;
    private Set<Connection> connections;

    @Inject
    public WorkspacePresenterBuilder() {
        builder = this;
    }

    @Nonnull
    public WorkspacePresenterBuilder mainPackage(@Nonnull String mainPackage) {
        this.mainPackage = mainPackage;
        return this;
    }

    @Nonnull
    public WorkspacePresenterBuilder elements(@Nonnull Set<Element> elements) {
        this.elements = elements;
        return this;
    }

    @Nonnull
    public WorkspacePresenterBuilder rootElement(@Nonnull Element rootElement) {
        this.rootElement = rootElement;
        return this;
    }

    @Nonnull
    public WorkspacePresenterBuilder connections(@Nonnull Set<Connection> connections) {
        this.connections = connections;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public void build() throws IOException {
        String clientPackage = mainPackage + '.' + CLIENT_PACKAGE;
        String workspacePackage = clientPackage + '.' + WORKSPACE_PACKAGE;
        String elementsPackage = clientPackage + '.' + ELEMENTS_PACKAGE + '.';
        String stateClassImport = "import static " + clientPackage + '.' + EDITOR_STATE + '.';

        StringBuilder createElements = new StringBuilder();
        StringBuilder staticImports = new StringBuilder();
        StringBuilder imports = new StringBuilder();
        StringBuilder createConnections = new StringBuilder();
        StringBuilder createGraphicalElements = new StringBuilder();

        staticImports.append(stateClassImport).append(CREATE_NOTHING_STATE).append(";\n");

        for (Element element : elements) {
            String elementName = element.getName();
            String elementPackage = elementsPackage + elementName;

            imports.append("import ").append(elementPackage).append(";\n");

            if (!element.equals(rootElement)) {
                createElements.append(createElementCode(elementName));

                staticImports.append(stateClassImport).append(String.format(CREATE_ELEMENT_STATE_FORMAT, elementName.toUpperCase()))
                             .append(";\n");

                createGraphicalElements.append(createGraphicalElementCode(elementName));
            }
        }

        for (Connection connection : connections) {
            String connectionName = connection.getName();
            String upperCaseName = connectionName.toUpperCase();
            String connectionPackage = elementsPackage + connectionName;

            createConnections.append(createConnectionCode(connectionName));

            staticImports
                    .append(stateClassImport).append(String.format(CREATE_CONNECTION_SOURCE_STATE_FORMAT, upperCaseName)).append(";\n");
            staticImports
                    .append(stateClassImport).append(String.format(CREATE_CONNECTION_TARGET_STATE_FORMAT, upperCaseName)).append(";\n");

            imports.append("import ").append(connectionPackage).append(";\n");
        }

        Path workspacePresenterSource = Paths.get(path,
                                                  MAIN_SOURCE_PATH,
                                                  JAVA_SOURCE_FOLDER,
                                                  WORKSPACE_PACKAGE,
                                                  WORKSPACE_PRESENTER_NAME + JAVA);
        Path workspacePresenterTarget = Paths.get(path,
                                                  MAIN_SOURCE_PATH,
                                                  JAVA_SOURCE_FOLDER,
                                                  convertPathToPackageName(mainPackage),
                                                  CLIENT_PACKAGE,
                                                  WORKSPACE_PACKAGE,
                                                  WORKSPACE_PRESENTER_NAME + JAVA);

        Map<String, String> replaceElements = new LinkedHashMap<>();
        replaceElements.put(MAIN_PACKAGE_MARKER, mainPackage);
        replaceElements.put(CURRENT_PACKAGE_MARKER, workspacePackage);
        replaceElements.put(STATIC_IMPORT_MARKER, staticImports.toString());
        replaceElements.put(IMPORT_MARKER, imports.toString());
        replaceElements.put(MAIN_ELEMENT_NAME_MARKER, rootElement.getName());
        replaceElements.put(CREATE_GRAPHIC_ELEMENTS_MARKER, createElements.toString());
        replaceElements.put(CREATE_GRAPHIC_CONNECTIONS_MARKER, createConnections.toString());
        replaceElements.put(CREATE_GRAPHICAL_ELEMENTS_MARKER, createGraphicalElements.toString());

        createFile(workspacePresenterSource, workspacePresenterTarget, replaceElements);

        Files.delete(workspacePresenterSource);
    }

    @Nonnull
    private String createElementCode(@Nonnull String elementName) {
        String argumentName = changeFirstSymbolToLowCase(elementName);

        Map<String, String> createElementMasks = new HashMap<>();
        createElementMasks.put(ELEMENT_NAME_MARKER, elementName);
        createElementMasks.put(ARGUMENT_NAME_MARKER, argumentName);
        createElementMasks.put(CREATE_STATE_MARKER, elementName.toUpperCase());

        return ContentReplacer.replace(CREATE_ELEMENT_CODE_FORMAT, createElementMasks);
    }

    @Nonnull
    private String createGraphicalElementCode(@Nonnull String elementName) {
        Map<String, String> createGraphicalElementMasks = new HashMap<>();
        createGraphicalElementMasks.put(ELEMENT_NAME_MARKER, elementName);

        return ContentReplacer.replace(CREATE_GRAPHICAL_ELEMENT_CODE_FORMAT, createGraphicalElementMasks);
    }

    @Nonnull
    private String createConnectionCode(@Nonnull String connectionName) {
        String argumentName = changeFirstSymbolToLowCase(connectionName);

        Map<String, String> createConnectionMasks = new HashMap<>();
        createConnectionMasks.put(CONNECTION_NAME_MARKER, connectionName);
        createConnectionMasks.put(CONNECTION_UPPER_NAME_MARKER, connectionName.toUpperCase());
        createConnectionMasks.put(ARGUMENT_NAME_MARKER, argumentName);

        return ContentReplacer.replace(CREATE_CONNECTION_CODE_FORMAT, createConnectionMasks);
    }

}