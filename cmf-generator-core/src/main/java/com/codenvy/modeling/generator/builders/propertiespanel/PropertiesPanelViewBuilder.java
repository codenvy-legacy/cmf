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
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.codenvy.modeling.generator.builders.FileExtensionConstants.JAVA;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.ARGUMENT_NAME_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.CURRENT_PACKAGE_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.ELEMENT_NAME_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.GETTER_AND_SETTER_PROPERTY_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.IMPORT_ELEMENTS;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.MAIN_PACKAGE_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.PROPERTY_CHANGE_METHODS_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.PROPERTY_NAME_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.PROPERTY_TYPE_MARKER;
import static com.codenvy.modeling.generator.builders.OffsetBuilderConstants.OFFSET;
import static com.codenvy.modeling.generator.builders.OffsetBuilderConstants.TWO_TABS;
import static com.codenvy.modeling.generator.builders.PathConstants.CLIENT_PACKAGE;
import static com.codenvy.modeling.generator.builders.PathConstants.JAVA_SOURCE_FOLDER;
import static com.codenvy.modeling.generator.builders.PathConstants.MAIN_SOURCE_PATH;
import static com.codenvy.modeling.generator.builders.PathConstants.PROPERTIES_PANEL_PACKAGE;

/**
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class PropertiesPanelViewBuilder extends AbstractBuilder<PropertiesPanelViewBuilder> {

    private static final String PROPERTY_CHANGED_METHOD = TWO_TABS + "void onpropertyNameChanged();\n\n";

    private static final String PROPERTIES_PANEL_VIEW_NAME = "PropertiesPanelView";

    private static final String GET_AND_SET_SIMPLE_PROPERTY =
            OFFSET + "public abstract propertyType getpropertyName();\n\n" +
            OFFSET + "public abstract void setpropertyName(propertyType argumentName);\n\n";

    private static final String GET_AND_SET_CUSTOM_PROPERTY =
            OFFSET + "public abstract propertyType getpropertyName();\n\n" +
            OFFSET + "public abstract void selectpropertyName(propertyType argumentName);\n\n" +
            OFFSET + "public abstract void setpropertyName(List<String> argumentName);\n\n";

    private String        mainPackage;
    private Set<Property> properties;
    private Element       element;
    private boolean       hasConfiguredProperty;

    @Inject
    public PropertiesPanelViewBuilder() {
        super();
        builder = this;
        hasConfiguredProperty = false;
    }

    @Nonnull
    public PropertiesPanelViewBuilder mainPackage(@Nonnull String mainPackage) {
        this.mainPackage = mainPackage;
        return this;
    }

    @Nonnull
    public PropertiesPanelViewBuilder element(@Nonnull Element element) {
        this.element = element;
        return this;
    }

    @Nonnull
    public PropertiesPanelViewBuilder properties(@Nonnull Set<Property> properties) {
        this.properties = properties;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public void build() throws IOException {
        String elementName = element.getName();
        String elementNameLowerCase = elementName.toLowerCase();
        String propertiesPanelPackage = mainPackage + '.' + CLIENT_PACKAGE + '.' + PROPERTIES_PANEL_PACKAGE + '.' + elementNameLowerCase;

        StringBuilder changedPropertiesMethods = new StringBuilder();
        StringBuilder propertyGetterAndSetters = new StringBuilder();
        StringBuilder importElements = new StringBuilder();

        for (Property property : properties) {
            String propertyName = property.getName();
            Class propertyType = convertPropertyTypeToJavaClass(property);

            if (isSimplePropertyType(property)) {
                propertyGetterAndSetters.append(createPropertyGetterAndSetterMethodCode(propertyName,
                                                                                        propertyType.getSimpleName(),
                                                                                        GET_AND_SET_SIMPLE_PROPERTY));
            } else {
                hasConfiguredProperty = true;

                propertyGetterAndSetters.append(createPropertyGetterAndSetterMethodCode(propertyName,
                                                                                        propertyType.getSimpleName(),
                                                                                        GET_AND_SET_CUSTOM_PROPERTY));
            }

            changedPropertiesMethods.append(createPropertyChangedMethodCode(propertyName));
        }

        source = Paths.get(path,
                           MAIN_SOURCE_PATH,
                           JAVA_SOURCE_FOLDER,
                           PROPERTIES_PANEL_PACKAGE,
                           PROPERTIES_PANEL_VIEW_NAME + JAVA);
        target = Paths.get(path,
                           MAIN_SOURCE_PATH,
                           JAVA_SOURCE_FOLDER,
                           convertPathToPackageName(mainPackage),
                           CLIENT_PACKAGE,
                           PROPERTIES_PANEL_PACKAGE,
                           elementNameLowerCase,
                           elementName + PROPERTIES_PANEL_VIEW_NAME + JAVA);

        replaceElements.put(MAIN_PACKAGE_MARKER, mainPackage);
        replaceElements.put(CURRENT_PACKAGE_MARKER, propertiesPanelPackage);
        replaceElements.put(ELEMENT_NAME_MARKER, elementName);
        replaceElements.put(PROPERTY_CHANGE_METHODS_MARKER, changedPropertiesMethods.toString());
        replaceElements.put(GETTER_AND_SETTER_PROPERTY_MARKER, propertyGetterAndSetters.toString());

        if (hasConfiguredProperty) {
            importElements.append("import ").append(List.class.getName()).append(";\n");
            replaceElements.put(IMPORT_ELEMENTS, importElements.toString());
            hasConfiguredProperty = false;
        } else {
            replaceElements.put(IMPORT_ELEMENTS, importElements.toString());
        }

        super.build();
    }

    @Nonnull
    private String createPropertyChangedMethodCode(@Nonnull String propertyName) {
        Map<String, String> masks = new LinkedHashMap<>(2);
        masks.put(PROPERTY_NAME_MARKER, propertyName);
        masks.put(ARGUMENT_NAME_MARKER, changeFirstSymbolToLowCase(propertyName));

        return ContentReplacer.replace(PROPERTY_CHANGED_METHOD, masks);
    }

    @Nonnull
    private String createPropertyGetterAndSetterMethodCode(@Nonnull String propertyName,
                                                           @Nonnull String propertyType,
                                                           @Nonnull String inputContent) {
        Map<String, String> masks = new LinkedHashMap<>(3);
        masks.put(PROPERTY_NAME_MARKER, propertyName);
        masks.put(PROPERTY_TYPE_MARKER, propertyType);
        masks.put(ARGUMENT_NAME_MARKER, changeFirstSymbolToLowCase(propertyName));

        return ContentReplacer.replace(inputContent, masks);
    }

}