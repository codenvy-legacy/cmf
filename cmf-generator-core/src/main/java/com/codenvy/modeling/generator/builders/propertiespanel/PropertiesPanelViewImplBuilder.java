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

package com.codenvy.modeling.generator.builders.propertiespanel;

import com.codenvy.modeling.configuration.metamodel.diagram.Element;
import com.codenvy.modeling.configuration.metamodel.diagram.Property;
import com.codenvy.modeling.generator.builders.AbstractBuilder;
import com.codenvy.modeling.generator.builders.ContentReplacer;
import com.codenvy.modeling.generator.builders.xml.api.GField;
import com.codenvy.modeling.generator.builders.xml.api.UIXmlBuilder;
import com.codenvy.modeling.generator.builders.xml.api.widgets.GLabel;
import com.codenvy.modeling.generator.builders.xml.api.widgets.GTextBox;
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GDockLayoutPanel;
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GFlowPanel;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static com.codenvy.modeling.generator.builders.FileExtensionConstants.JAVA;
import static com.codenvy.modeling.generator.builders.FileExtensionConstants.UI_XML;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.ACTION_DELEGATES_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.ARGUMENT_NAME_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.CURRENT_PACKAGE_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.ELEMENT_NAME_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.PROPERTY_NAME_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.PROPERTY_TYPE_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.UI_FIELDS_MARKER;
import static com.codenvy.modeling.generator.builders.OffsetBuilderConstants.OFFSET;
import static com.codenvy.modeling.generator.builders.OffsetBuilderConstants.TWO_TABS;
import static com.codenvy.modeling.generator.builders.PathConstants.CLIENT_PACKAGE;
import static com.codenvy.modeling.generator.builders.PathConstants.JAVA_SOURCE_FOLDER;
import static com.codenvy.modeling.generator.builders.PathConstants.MAIN_SOURCE_PATH;
import static com.codenvy.modeling.generator.builders.PathConstants.PROPERTIES_PANEL_PACKAGE;
import static com.codenvy.modeling.generator.builders.ResourceNameConstants.EDITOR_RESOURCES;

/**
 * @author Andrey Plotnikov
 */
public class PropertiesPanelViewImplBuilder extends AbstractBuilder<PropertiesPanelViewImplBuilder> {

    private static final String PROPERTY_FIELD = OFFSET + "@UiField\n" +
                                                 OFFSET + "TextBox argumentName;\n";

    private static final String CHANGE_PROPERTY_METHODS = OFFSET + "@Override\n" +
                                                          OFFSET + "public propertyType getpropertyName() {\n" +
                                                          TWO_TABS + "return propertyType.valueOf(argumentName.getText());\n" +
                                                          OFFSET + "}\n\n" +
                                                          OFFSET + "@Override\n" +
                                                          OFFSET + "public void setpropertyName(propertyType argumentName) {\n" +
                                                          TWO_TABS + "this.argumentName.setText(argumentName.toString());\n" +
                                                          OFFSET + "}\n\n" +
                                                          OFFSET + "@UiHandler(\"argumentName\")\n" +
                                                          OFFSET + "public void onpropertyNameChanged(KeyUpEvent event) {\n" +
                                                          TWO_TABS + "delegate.onpropertyNameChanged();\n" +
                                                          OFFSET + "}\n\n";

    private static final String PROPERTIES_PANEL_VIEW_IMPL_NAME = "PropertiesPanelViewImpl";

    private final GDockLayoutPanel     dockLayoutPanelBuilder;
    private final GField               fieldBuilder;
    private final Provider<GFlowPanel> flowPanelProvider;
    private final Provider<GLabel>     labelProvider;
    private final Provider<GTextBox>   textBoxProvider;
    private       UIXmlBuilder         uiXmlBuilder;
    private       String               mainPackage;
    private       Set<Property>        properties;
    private       Element              element;

    @Inject
    public PropertiesPanelViewImplBuilder(UIXmlBuilder uiXmlBuilder,
                                          GDockLayoutPanel dockLayoutPanelBuilder,
                                          GField fieldBuilder,
                                          Provider<GFlowPanel> flowPanelProvider,
                                          Provider<GLabel> labelProvider,
                                          Provider<GTextBox> textBoxProvider) {
        super();
        builder = this;

        this.fieldBuilder = fieldBuilder;
        this.flowPanelProvider = flowPanelProvider;
        this.labelProvider = labelProvider;
        this.textBoxProvider = textBoxProvider;
        this.uiXmlBuilder = uiXmlBuilder;
        this.dockLayoutPanelBuilder = dockLayoutPanelBuilder;
    }

    @Nonnull
    public PropertiesPanelViewImplBuilder mainPackage(@Nonnull String mainPackage) {
        this.mainPackage = mainPackage;
        return this;
    }

    @Nonnull
    public PropertiesPanelViewImplBuilder element(@Nonnull Element element) {
        this.element = element;
        return this;
    }

    @Nonnull
    public PropertiesPanelViewImplBuilder properties(@Nonnull Set<Property> properties) {
        this.properties = properties;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public void build() throws IOException {
        String clientPackage = mainPackage + '.' + CLIENT_PACKAGE + '.';
        String elementName = element.getName();
        String elementNameLowerCase = elementName.toLowerCase();
        String propertiesPanelPackage = mainPackage + '.' + CLIENT_PACKAGE + '.' + PROPERTIES_PANEL_PACKAGE + '.' + elementNameLowerCase;

        dockLayoutPanelBuilder.withPrefix("g");
        uiXmlBuilder.withXmlns("g", "urn:import:com.google.gwt.user.client.ui")
                    .withField(
                            fieldBuilder.withName("res")
                                        .withType(clientPackage + EDITOR_RESOURCES)
                              )
                    .setWidget(dockLayoutPanelBuilder);

        StringBuilder propertyFields = new StringBuilder();
        StringBuilder changePropertyMethods = new StringBuilder();

        for (Property property : properties) {
            String propertyName = property.getName();
            Class propertyType = convertPropertyTypeToJavaClass(property);

            propertyFields.append(createPropertyFieldCode(propertyName));
            changePropertyMethods.append(createChangePropertyMethodsCode(propertyName, propertyType.getSimpleName(), elementName));

            addPropertyGraphicalElements(propertyName, dockLayoutPanelBuilder);
        }

        source = Paths.get(path,
                           MAIN_SOURCE_PATH,
                           JAVA_SOURCE_FOLDER,
                           PROPERTIES_PANEL_PACKAGE,
                           PROPERTIES_PANEL_VIEW_IMPL_NAME + JAVA);
        target = Paths.get(path,
                           MAIN_SOURCE_PATH,
                           JAVA_SOURCE_FOLDER,
                           convertPathToPackageName(mainPackage),
                           CLIENT_PACKAGE,
                           PROPERTIES_PANEL_PACKAGE,
                           elementNameLowerCase,
                           elementName + PROPERTIES_PANEL_VIEW_IMPL_NAME + JAVA);

        replaceElements.put(CURRENT_PACKAGE_MARKER, propertiesPanelPackage);
        replaceElements.put(UI_FIELDS_MARKER, propertyFields.toString());
        replaceElements.put(ACTION_DELEGATES_MARKER, changePropertyMethods.toString());
        replaceElements.put(ELEMENT_NAME_MARKER, elementName);

        super.build();

        Path propertiesPanelUiXMLPath = Paths.get(path,
                                                  MAIN_SOURCE_PATH,
                                                  JAVA_SOURCE_FOLDER,
                                                  convertPathToPackageName(mainPackage),
                                                  CLIENT_PACKAGE,
                                                  PROPERTIES_PANEL_PACKAGE,
                                                  elementNameLowerCase,
                                                  elementName + PROPERTIES_PANEL_VIEW_IMPL_NAME + UI_XML);
        Files.write(propertiesPanelUiXMLPath, uiXmlBuilder.build().getBytes());
    }

    @Nonnull
    private String createPropertyFieldCode(@Nonnull String propertyName) {
        Map<String, String> masks = new LinkedHashMap<>(1);
        masks.put(ARGUMENT_NAME_MARKER, changeFirstSymbolToLowCase(propertyName));

        return ContentReplacer.replace(PROPERTY_FIELD, masks);
    }

    @Nonnull
    private String createChangePropertyMethodsCode(@Nonnull String propertyName,
                                                   @Nonnull String propertyType,
                                                   @Nonnull String elementName) {
        Map<String, String> masks = new LinkedHashMap<>(4);
        masks.put(PROPERTY_NAME_MARKER, propertyName);
        masks.put(ARGUMENT_NAME_MARKER, changeFirstSymbolToLowCase(propertyName));
        masks.put(PROPERTY_TYPE_MARKER, propertyType);
        masks.put(ELEMENT_NAME_MARKER, elementName);

        return ContentReplacer.replace(CHANGE_PROPERTY_METHODS, masks);
    }

    private void addPropertyGraphicalElements(@Nonnull String propertyName, @Nonnull GDockLayoutPanel dockLayoutPanel) {
        dockLayoutPanel.withNorth(50,
                                  flowPanelProvider
                                          .get()
                                          .withPrefix("g")
                                          .withWidget(
                                                  labelProvider
                                                          .get()
                                                          .withPrefix("g")
                                                          .withText(propertyName)
                                                     )
                                          .withWidget(
                                                  textBoxProvider
                                                          .get()
                                                          .withPrefix("g")
                                                          .withName(changeFirstSymbolToLowCase(propertyName))
                                                          .withWidth("120px")
                                                     )
                                 );
    }

}