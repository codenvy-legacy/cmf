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

import com.codenvy.modeling.configuration.metamodel.diagram.Element;
import com.codenvy.modeling.generator.builders.AbstractBuilder;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Set;

import static com.codenvy.modeling.generator.GenerationController.Param.EDITOR_NAME;
import static com.codenvy.modeling.generator.GenerationController.Param.MAIN_PACKAGE;
import static com.codenvy.modeling.generator.builders.FileExtensionConstants.JAVA;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.CONSTRUCTOR_ARGUMENTS;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.CONSTRUCTOR_BODY;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.CURRENT_PACKAGE_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.EDITOR_NAME_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.IMPORT_ELEMENTS;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.IMPORT_PROPERTIES_PANEL_ELEMENTS;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.MAIN_PACKAGE_MARKER;
import static com.codenvy.modeling.generator.builders.OffsetBuilderConstants.SIX_TABS;
import static com.codenvy.modeling.generator.builders.OffsetBuilderConstants.TWO_TABS;
import static com.codenvy.modeling.generator.builders.PathConstants.CLIENT_PACKAGE;
import static com.codenvy.modeling.generator.builders.PathConstants.ELEMENTS_PACKAGE;
import static com.codenvy.modeling.generator.builders.PathConstants.JAVA_SOURCE_FOLDER;
import static com.codenvy.modeling.generator.builders.PathConstants.MAIN_SOURCE_PATH;
import static com.codenvy.modeling.generator.builders.PathConstants.MY_GWT_PACKAGE;
import static com.codenvy.modeling.generator.builders.PathConstants.PROPERTIES_PANEL_PACKAGE;
import static com.codenvy.modeling.generator.builders.ResourceNameConstants.EDITOR_PRESENTER;
import static com.codenvy.modeling.generator.builders.ResourceNameConstants.PROPERTIES_PANEL_PRESENTER;

/**
 * @author Valeriy Svydenko
 */
public class EditorPresenterBuilder extends AbstractBuilder<EditorPresenterBuilder> {

    private Properties   properties;
    private Set<Element> elements;
    private Element      rootElement;

    @Inject
    public EditorPresenterBuilder() {
        super();
        builder = this;
    }

    @Override
    public void build() throws IOException {
        String packageName = properties.getProperty(MAIN_PACKAGE.name());

        StringBuilder importPropertiesPanelElements = new StringBuilder();
        StringBuilder importElements = new StringBuilder();
        StringBuilder constructorArguments = new StringBuilder();
        StringBuilder constructorBody = new StringBuilder();

        String clientPackage = packageName + '.' + CLIENT_PACKAGE;
        String propertiesPanelPackage = clientPackage + '.' + PROPERTIES_PANEL_PACKAGE + '.';
        String elementsPackage = clientPackage + '.' + ELEMENTS_PACKAGE + '.';

        for (Element element : elements) {
            if (!element.equals(rootElement)) {
                String elementName = element.getName();
                String elementPropertiesPanelName = elementName + PROPERTIES_PANEL_PRESENTER;
                String elementPropertiesPanelArgument = changeFirstSymbolToLowCase(elementPropertiesPanelName);

                importPropertiesPanelElements.append("import ").append(propertiesPanelPackage).append(elementName.toLowerCase())
                                             .append('.').append(elementPropertiesPanelName).append(";\n");

                importElements.append("import ").append(elementsPackage).append(elementName).append(";\n");

                constructorArguments.append(SIX_TABS)
                                    .append(elementPropertiesPanelName).append(' ')
                                    .append(elementPropertiesPanelArgument).append(",\n");

                constructorBody.append("\n")
                               .append(TWO_TABS)
                               .append("propertiesPanelManager.register(").append(elementName).append(".class, ")
                               .append(elementPropertiesPanelArgument).append(");\n")
                               .append(TWO_TABS)
                               .append(elementPropertiesPanelArgument).append(".addListener(this);\n");

            }
        }

        constructorArguments.delete(constructorArguments.lastIndexOf(","), constructorArguments.length());
        constructorBody.deleteCharAt(constructorBody.lastIndexOf("\n"));
        importElements.deleteCharAt(importElements.lastIndexOf("\n"));

        source = Paths.get(path,
                           MAIN_SOURCE_PATH,
                           JAVA_SOURCE_FOLDER,
                           MY_GWT_PACKAGE,
                           EDITOR_PRESENTER + JAVA);

        target = Paths.get(path,
                           MAIN_SOURCE_PATH,
                           JAVA_SOURCE_FOLDER,
                           convertPathToPackageName(properties.getProperty(MAIN_PACKAGE.name())),
                           CLIENT_PACKAGE,
                           properties.getProperty(EDITOR_NAME.name()) + JAVA);

        replaceElements.put(CURRENT_PACKAGE_MARKER, clientPackage);
        replaceElements.put(IMPORT_PROPERTIES_PANEL_ELEMENTS, importPropertiesPanelElements.toString());
        replaceElements.put(IMPORT_ELEMENTS, importElements.toString());
        replaceElements.put(CONSTRUCTOR_ARGUMENTS, constructorArguments.toString());
        replaceElements.put(CONSTRUCTOR_BODY, constructorBody.toString());
        replaceElements.put(MAIN_PACKAGE_MARKER, packageName);
        replaceElements.put(EDITOR_NAME_MARKER, properties.getProperty(EDITOR_NAME.name()));

        super.build();
    }

    @Nonnull
    public EditorPresenterBuilder elements(@Nonnull Set<Element> elements) {
        this.elements = elements;

        return this;
    }

    @Nonnull
    public EditorPresenterBuilder rootElement(@Nonnull Element element) {
        this.rootElement = element;

        return this;
    }

    @Nonnull
    public EditorPresenterBuilder properties(Properties properties) {
        this.properties = properties;

        return this;
    }
}