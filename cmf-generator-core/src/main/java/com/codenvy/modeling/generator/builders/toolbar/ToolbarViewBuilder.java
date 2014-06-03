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

import static com.codenvy.modeling.generator.builders.FileExtensionConstants.JAVA;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.ACTION_DELEGATES_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.CURRENT_PACKAGE_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.ELEMENT_NAME_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.MAIN_PACKAGE_MARKER;
import static com.codenvy.modeling.generator.builders.OffsetBuilderConstants.TWO_TABS;
import static com.codenvy.modeling.generator.builders.PathConstants.CLIENT_PACKAGE;
import static com.codenvy.modeling.generator.builders.PathConstants.JAVA_SOURCE_FOLDER;
import static com.codenvy.modeling.generator.builders.PathConstants.MAIN_SOURCE_PATH;
import static com.codenvy.modeling.generator.builders.PathConstants.TOOLBAR_PACKAGE;

/**
 * @author Andrey Plotnikov
 */
public class ToolbarViewBuilder extends AbstractBuilder<ToolbarViewBuilder> {

    private static final String CREATE_ON_ELEMENT_BUTTON_CLICKED_CODE_FORMAT = TWO_TABS + "void onelementNameButtonClicked();\n\n";

    private static final String TOOLBAR_VIEW_NAME = "ToolbarView";

    private String          mainPackage;
    private Element         rootElement;
    private Set<Element>    elements;
    private Set<Connection> connections;

    @Inject
    public ToolbarViewBuilder() {
        super();
        builder = this;
    }

    @Nonnull
    public ToolbarViewBuilder mainPackage(@Nonnull String mainPackage) {
        this.mainPackage = mainPackage;
        return this;
    }

    @Nonnull
    public ToolbarViewBuilder elements(@Nonnull Set<Element> elements) {
        this.elements = elements;
        return this;
    }

    @Nonnull
    public ToolbarViewBuilder rootElement(@Nonnull Element rootElement) {
        this.rootElement = rootElement;
        return this;
    }

    @Nonnull
    public ToolbarViewBuilder connections(@Nonnull Set<Connection> connections) {
        this.connections = connections;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public void build() throws IOException {
        String toolbarPackage = mainPackage + '.' + CLIENT_PACKAGE + '.' + TOOLBAR_PACKAGE;

        StringBuilder actionDelegates = new StringBuilder();

        for (Element element : elements) {
            if (!element.equals(rootElement)) {
                actionDelegates.append(createOnElementButtonClickedCode(element.getName()));
            }
        }

        for (Connection connection : connections) {
            actionDelegates.append(createOnElementButtonClickedCode(connection.getName()));
        }

        Path toolbarViewSource = Paths.get(path,
                                           MAIN_SOURCE_PATH,
                                           JAVA_SOURCE_FOLDER,
                                           TOOLBAR_PACKAGE,
                                           TOOLBAR_VIEW_NAME + JAVA);
        Path toolbarViewTarget = Paths.get(path,
                                           MAIN_SOURCE_PATH,
                                           JAVA_SOURCE_FOLDER,
                                           convertPathToPackageName(mainPackage),
                                           CLIENT_PACKAGE,
                                           TOOLBAR_PACKAGE,
                                           TOOLBAR_VIEW_NAME + JAVA);

        Map<String, String> replaceElements = new LinkedHashMap<>();
        replaceElements.put(CURRENT_PACKAGE_MARKER, toolbarPackage);
        replaceElements.put(MAIN_PACKAGE_MARKER, mainPackage);
        replaceElements.put(ACTION_DELEGATES_MARKER, actionDelegates.toString());

        createFile(toolbarViewSource, toolbarViewTarget, replaceElements);

        removeTemplate(toolbarViewSource);
        removeTemplateParentFolder(toolbarViewSource.getParent());
    }

    @Nonnull
    private String createOnElementButtonClickedCode(@Nonnull String elementName) {
        Map<String, String> masks = new HashMap<>();
        masks.put(ELEMENT_NAME_MARKER, elementName);

        return ContentReplacer.replace(CREATE_ON_ELEMENT_BUTTON_CLICKED_CODE_FORMAT, masks);
    }

}