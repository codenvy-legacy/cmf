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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.codenvy.modeling.generator.builders.FileExtensionConstants.JAVA;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.CONNECTION_NAME_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.CURRENT_PACKAGE_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.ELEMENT_NAME_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.IMPORT_MARKER;
import static com.codenvy.modeling.generator.builders.OffsetBuilderConstants.OFFSET;
import static com.codenvy.modeling.generator.builders.PathConstants.CLIENT_PACKAGE;
import static com.codenvy.modeling.generator.builders.PathConstants.ELEMENTS_PACKAGE;
import static com.codenvy.modeling.generator.builders.PathConstants.JAVA_SOURCE_FOLDER;
import static com.codenvy.modeling.generator.builders.PathConstants.MAIN_SOURCE_PATH;
import static com.codenvy.modeling.generator.builders.PathConstants.WORKSPACE_PACKAGE;

/**
 * @author Andrey Plotnikov
 */
public class WorkspaceViewBuilder extends AbstractBuilder<WorkspaceViewBuilder> {

    private static final String ADD_ELEMENT_CODE_FORMAT    =
            OFFSET + "public abstract void addelementName(int x, int y, @Nonnull elementName element);\n\n";
    private static final String ADD_CONNECTION_CODE_FORMAT =
            OFFSET + "public abstract void addconnectionName(@Nonnull String sourceElementID, @Nonnull String targetElementID);\n\n";

    private static final String METHODS_MARKER = "methods";

    private static final String WORKSPACE_VIEW_NAME = "WorkspaceView";

    private String          mainPackage;
    private Element         rootElement;
    private Set<Element>    elements;
    private Set<Connection> connections;

    @Inject
    public WorkspaceViewBuilder() {
        super();
        builder = this;
    }

    @Nonnull
    public WorkspaceViewBuilder mainPackage(@Nonnull String mainPackage) {
        this.mainPackage = mainPackage;
        return this;
    }

    @Nonnull
    public WorkspaceViewBuilder elements(@Nonnull Set<Element> elements) {
        this.elements = elements;
        return this;
    }

    @Nonnull
    public WorkspaceViewBuilder rootElement(@Nonnull Element rootElement) {
        this.rootElement = rootElement;
        return this;
    }

    @Nonnull
    public WorkspaceViewBuilder connections(@Nonnull Set<Connection> connections) {
        this.connections = connections;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public void build() throws IOException {
        String clientPackage = mainPackage + '.' + CLIENT_PACKAGE + '.';
        String workspacePackage = clientPackage + WORKSPACE_PACKAGE;
        String elementsPackage = clientPackage + ELEMENTS_PACKAGE + '.';

        StringBuilder imports = new StringBuilder();
        StringBuilder methods = new StringBuilder();

        for (Element element : elements) {
            if (!element.equals(rootElement)) {
                String elementName = element.getName();

                imports.append("import ").append(elementsPackage).append(elementName).append(";\n");
                methods.append(createAddElementMethodCode(elementName));
            }
        }

        for (Connection connection : connections) {
            methods.append(createAddConnectionMethodCode(connection.getName()));
        }

        Path workspaceViewSource = Paths.get(path,
                                             MAIN_SOURCE_PATH,
                                             JAVA_SOURCE_FOLDER,
                                             WORKSPACE_PACKAGE,
                                             WORKSPACE_VIEW_NAME + JAVA);
        Path workspaceViewTarget = Paths.get(path,
                                             MAIN_SOURCE_PATH,
                                             JAVA_SOURCE_FOLDER,
                                             convertPathToPackageName(mainPackage),
                                             CLIENT_PACKAGE,
                                             WORKSPACE_PACKAGE,
                                             WORKSPACE_VIEW_NAME + JAVA);

        Map<String, String> replaceElements = new HashMap<>();
        replaceElements.put(CURRENT_PACKAGE_MARKER, workspacePackage);
        replaceElements.put(IMPORT_MARKER, imports.toString());
        replaceElements.put(METHODS_MARKER, methods.toString());

        createFile(workspaceViewSource, workspaceViewTarget, replaceElements);

        removeTemplate(workspaceViewSource);
        removeTemplateParentFolder(workspaceViewSource.getParent());
    }

    @Nonnull
    private String createAddElementMethodCode(@Nonnull String elementName) {
        Map<String, String> masks = new HashMap<>();
        masks.put(ELEMENT_NAME_MARKER, elementName);

        return ContentReplacer.replace(ADD_ELEMENT_CODE_FORMAT, masks);
    }

    @Nonnull
    private String createAddConnectionMethodCode(@Nonnull String connectionName) {
        Map<String, String> masks = new HashMap<>();
        masks.put(CONNECTION_NAME_MARKER, connectionName);

        return ContentReplacer.replace(ADD_CONNECTION_CODE_FORMAT, masks);
    }

}