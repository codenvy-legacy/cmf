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
import com.codenvy.modeling.generator.builders.xml.api.GField;
import com.codenvy.modeling.generator.builders.xml.api.UIXmlBuilder;
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GFlowPanel;
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GScrollPanel;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.codenvy.modeling.generator.builders.BuilderConstants.CLIENT_PART_FOLDER;
import static com.codenvy.modeling.generator.builders.BuilderConstants.CURRENT_PACKAGE_MASK;
import static com.codenvy.modeling.generator.builders.BuilderConstants.EDITOR_RESOURCES_NAME;
import static com.codenvy.modeling.generator.builders.BuilderConstants.ELEMENTS_FOLDER;
import static com.codenvy.modeling.generator.builders.BuilderConstants.JAVA_SOURCE_PATH;
import static com.codenvy.modeling.generator.builders.BuilderConstants.MAIN_SOURCE_PATH;
import static com.codenvy.modeling.generator.builders.BuilderConstants.OFFSET;
import static com.codenvy.modeling.generator.builders.BuilderConstants.TWO_TABS;
import static com.codenvy.modeling.generator.builders.BuilderConstants.WORKSPACE_FOLDER;

/**
 * @author Andrey Plotnikov
 */
public class WorkspaceViewImplBuilder extends AbstractBuilder<WorkspaceViewImplBuilder> {

    private static final String ADD_ELEMENT_CODE_FORMAT    =
            OFFSET + "@Override\n" +
            OFFSET + "public void addelementName(int x, int y,elementName element) {\n" +
            TWO_TABS + "addElement(x, y, element, resources.argumentName());\n" +
            OFFSET + "}\n\n";
    private static final String ADD_CONNECTION_CODE_FORMAT =
            OFFSET + "@Override\n" +
            OFFSET + "public void addconnectionName(String sourceElementID, String targetElementID) {\n" +
            TWO_TABS + "Widget sourceWidget = elements.get(sourceElementID);\n" +
            TWO_TABS + "Widget targetWidget = elements.get(targetElementID);\n" +
            TWO_TABS + "controller.drawStraightArrowConnection(sourceWidget, targetWidget);\n" +
            OFFSET + "}\n\n";

    private static final String ELEMENT_NAME_MASK    = "elementName";
    private static final String ARGUMENT_NAME_MASK   = "argumentName";
    private static final String CONNECTION_NAME_MASK = "connectionName";

    private static final String IMPORT_MASK           = "import_elements";
    private static final String ACTION_DELEGATES_MASK = "action_delegates";

    private static final String WORKSPACE_VIEW_IMPL_NAME = "WorkspaceViewImpl";

    private final UIXmlBuilder    uiXmlBuilder;
    private final GField          fieldBuilder;
    private final GScrollPanel    scrollPanelBuilder;
    private final GFlowPanel      flowPanelBuilder;
    private       String          mainPackage;
    private       Element         rootElement;
    private       Set<Element>    elements;
    private       Set<Connection> connections;

    @Inject
    public WorkspaceViewImplBuilder(UIXmlBuilder uiXmlBuilder,
                                    GField fieldBuilder,
                                    GScrollPanel scrollPanelBuilder,
                                    GFlowPanel flowPanelBuilder) {
        builder = this;

        this.uiXmlBuilder = uiXmlBuilder;
        this.fieldBuilder = fieldBuilder;
        this.scrollPanelBuilder = scrollPanelBuilder;
        this.flowPanelBuilder = flowPanelBuilder;
    }

    @Nonnull
    public WorkspaceViewImplBuilder mainPackage(@Nonnull String mainPackage) {
        this.mainPackage = mainPackage;
        return this;
    }

    @Nonnull
    public WorkspaceViewImplBuilder elements(@Nonnull Set<Element> elements) {
        this.elements = elements;
        return this;
    }

    @Nonnull
    public WorkspaceViewImplBuilder rootElement(@Nonnull Element rootElement) {
        this.rootElement = rootElement;
        return this;
    }

    @Nonnull
    public WorkspaceViewImplBuilder connections(@Nonnull Set<Connection> connections) {
        this.connections = connections;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public void build() throws IOException {
        String clientPackage = mainPackage + '.' + CLIENT_PART_FOLDER;
        String workspacePackage = clientPackage + '.' + WORKSPACE_FOLDER;
        String elementsPackage = clientPackage + '.' + ELEMENTS_FOLDER + '.';

        StringBuilder imports = new StringBuilder();
        StringBuilder actionDelegates = new StringBuilder();

        for (Element element : elements) {
            if (!element.equals(rootElement)) {
                String elementName = element.getName();

                imports.append("import ").append(elementsPackage).append(elementName).append(";\n");
                actionDelegates.append(createAddElementCode(elementName));
            }
        }

        for (Connection connection : connections) {
            String connectionName = connection.getName();

            imports.append("import ").append(elementsPackage).append(connectionName).append(";\n");
            actionDelegates.append(createAddConnectionCode(connectionName));
        }

        Path workspaceViewImplSource = Paths.get(sourcePath,
                                                 MAIN_SOURCE_PATH,
                                                 JAVA_SOURCE_PATH,
                                                 WORKSPACE_FOLDER,
                                                 WORKSPACE_VIEW_IMPL_NAME + ".java");
        Path workspaceViewImplTarget = Paths.get(targetPath,
                                                 MAIN_SOURCE_PATH,
                                                 JAVA_SOURCE_PATH,
                                                 convertPathToPackageName(mainPackage),
                                                 CLIENT_PART_FOLDER,
                                                 WORKSPACE_FOLDER,
                                                 WORKSPACE_VIEW_IMPL_NAME + ".java");

        Map<String, String> replaceElements = new HashMap<>();
        replaceElements.put(CURRENT_PACKAGE_MASK, workspacePackage);
        replaceElements.put(IMPORT_MASK, imports.toString());
        replaceElements.put(ACTION_DELEGATES_MASK, actionDelegates.toString());

        createFile(workspaceViewImplSource, workspaceViewImplTarget, replaceElements);

        Files.delete(workspaceViewImplSource);

        uiXmlBuilder.withXmlns("g", "urn:import:com.google.gwt.user.client.ui")
                    .withField(fieldBuilder.withName("res").withType(clientPackage + '.' + EDITOR_RESOURCES_NAME))

                    .setWidget(
                            scrollPanelBuilder.withPrefix("g")
                                              .withAddStyle("res.editorCSS.fullSize")
                                              .withWidget(
                                                      flowPanelBuilder.withPrefix("g")
                                                                      .withName("mainPanel")
                                                                      .withAddStyle("res.editorCSS.fullSize")
                                                         )
                              );

        Path workspaceUiXMLPath = Paths.get(targetPath,
                                            MAIN_SOURCE_PATH,
                                            JAVA_SOURCE_PATH,
                                            convertPathToPackageName(mainPackage),
                                            CLIENT_PART_FOLDER,
                                            WORKSPACE_FOLDER,
                                            WORKSPACE_VIEW_IMPL_NAME + ".ui.xml");
        Files.write(workspaceUiXMLPath, uiXmlBuilder.build().getBytes());
    }

    @Nonnull
    private String createAddElementCode(@Nonnull String elementName) {
        String argumentName = changeFirstSymbolToLowCase(elementName);

        Map<String, String> createElementMasks = new HashMap<>();
        createElementMasks.put(ELEMENT_NAME_MASK, elementName);
        createElementMasks.put(ARGUMENT_NAME_MASK, argumentName);

        return ContentReplacer.replace(ADD_ELEMENT_CODE_FORMAT, createElementMasks);
    }

    @Nonnull
    private String createAddConnectionCode(@Nonnull String connectionName) {
        Map<String, String> createConnectionMasks = new HashMap<>();
        createConnectionMasks.put(CONNECTION_NAME_MASK, connectionName);

        return ContentReplacer.replace(ADD_CONNECTION_CODE_FORMAT, createConnectionMasks);
    }

}