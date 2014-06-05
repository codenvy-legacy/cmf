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
import java.util.Map;
import java.util.Set;

import static com.codenvy.modeling.generator.builders.FileExtensionConstants.JAVA;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.ARGUMENT_NAME_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.CURRENT_PACKAGE_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.ELEMENT_NAME_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.GETTER_AND_SETTER_PROPERTY_MARKER;
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
 */
public class PropertiesPanelViewBuilder extends AbstractBuilder<PropertiesPanelViewBuilder> {

    private static final String GET_AND_SET_PROPERTY    = OFFSET + "public abstract propertyType getpropertyName();\n\n" +
                                                          OFFSET + "public abstract void setpropertyName(propertyType argumentName);\n\n";
    private static final String PROPERTY_CHANGED_METHOD = TWO_TABS + "void onpropertyNameChanged();\n";

    private static final String PROPERTIES_PANEL_VIEW_NAME = "PropertiesPanelView";

    private String        mainPackage;
    private Set<Property> properties;
    private Element       element;

    @Inject
    public PropertiesPanelViewBuilder() {
        super();
        builder = this;
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

        for (Property property : properties) {
            String propertyName = property.getName();
            Class propertyType = convertPropertyTypeToJavaClass(property);

            changedPropertiesMethods.append(createPropertyChangedMethodCode(propertyName));
            propertyGetterAndSetters.append(createPropertyGetterAndSetterMethodCode(propertyName, propertyType.getSimpleName()));
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
    private String createPropertyGetterAndSetterMethodCode(@Nonnull String propertyName, @Nonnull String propertyType) {
        Map<String, String> masks = new LinkedHashMap<>(3);
        masks.put(PROPERTY_NAME_MARKER, propertyName);
        masks.put(PROPERTY_TYPE_MARKER, propertyType);
        masks.put(ARGUMENT_NAME_MARKER, changeFirstSymbolToLowCase(propertyName));

        return ContentReplacer.replace(GET_AND_SET_PROPERTY, masks);
    }

}