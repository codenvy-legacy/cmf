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

package com.codenvy.modeling.generator.builders.elements;

import com.codenvy.editor.api.editor.elements.AbstractShape;
import com.codenvy.editor.api.editor.elements.Link;
import com.codenvy.editor.api.editor.elements.Shape;
import com.codenvy.modeling.configuration.metamodel.diagram.Component;
import com.codenvy.modeling.configuration.metamodel.diagram.Connection;
import com.codenvy.modeling.configuration.metamodel.diagram.Element;
import com.codenvy.modeling.configuration.metamodel.diagram.Property;
import com.codenvy.modeling.generator.builders.AbstractBuilder;
import com.codenvy.modeling.generator.builders.ContentReplacer;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.codenvy.modeling.generator.builders.FileExtensionConstants.JAVA;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.ARGUMENT_NAME_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.CURRENT_PACKAGE_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.ELEMENT_NAME_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.GETTER_AND_SETTER_PROPERTY_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.IMPORT_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.PROPERTY_NAME_MARKER;
import static com.codenvy.modeling.generator.builders.MarkerBuilderConstants.PROPERTY_TYPE_MARKER;
import static com.codenvy.modeling.generator.builders.OffsetBuilderConstants.FIVE_TABS;
import static com.codenvy.modeling.generator.builders.OffsetBuilderConstants.FOUR_TABS;
import static com.codenvy.modeling.generator.builders.OffsetBuilderConstants.OFFSET;
import static com.codenvy.modeling.generator.builders.OffsetBuilderConstants.THREE_TABS;
import static com.codenvy.modeling.generator.builders.OffsetBuilderConstants.TWO_TABS;
import static com.codenvy.modeling.generator.builders.PathConstants.CLIENT_PACKAGE;
import static com.codenvy.modeling.generator.builders.PathConstants.ELEMENTS_PACKAGE;
import static com.codenvy.modeling.generator.builders.PathConstants.JAVA_SOURCE_FOLDER;
import static com.codenvy.modeling.generator.builders.PathConstants.MAIN_SOURCE_PATH;

/**
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class ElementBuilder extends AbstractBuilder<ElementBuilder> {

    private static final String MAIN_ELEMENT_SUPER_CONSTRUCTOR =
            OFFSET +
            "public elementName(@Nonnull String elementName, @Nonnull List<String> properties, @Nonnull List<String> internalProperties) {\n" +
            TWO_TABS + "super(elementName, properties, internalProperties);\n" +
            OFFSET + "}\n\n";

    private static final String ELEMENT_CONSTRUCTOR = OFFSET + "public elementName() {\n" +
                                                      TWO_TABS +
                                                      "generalConstructor(\"elementName\", propertiesList, internalPropertiesList);\n\n" +
                                                      "propertiesInitialization" +
                                                      "components" +
                                                      OFFSET + "}\n\n";

    private static final String PROPERTY_SETTER_AND_GETTER = OFFSET + "public propertyType getpropertyName() {\n" +
                                                             TWO_TABS + "return argumentName;\n" +
                                                             OFFSET + "}\n\n" +
                                                             OFFSET + "public void setpropertyName(propertyType argumentName) {\n" +
                                                             TWO_TABS + "this.argumentName = argumentName;\n" +
                                                             OFFSET + "}\n\n";

    private static final String DESERIALIZE_METHOD = OFFSET + "@Override\n" +
                                                     OFFSET + "public void deserialize(Node node) {\n" +
                                                     TWO_TABS + "NodeList childNodes = node.getChildNodes();\n\n" +
                                                     TWO_TABS + "for (int i = 0; i < childNodes.getLength(); i++) {\n" +
                                                     THREE_TABS + "Node item = childNodes.item(i);\n" +
                                                     THREE_TABS + "String name = item.getNodeName();\n\n" +
                                                     THREE_TABS + "if (isProperty(name)) {\n" +
                                                     FOUR_TABS + "applyProperty(item);\n" +
                                                     THREE_TABS + "} else {\n" +
                                                     FOUR_TABS + "Element element = findElement(name);\n" +
                                                     FOUR_TABS + "element.deserialize(item);\n\n" +
                                                     FOUR_TABS + "if (element instanceof Shape) {\n" +
                                                     FIVE_TABS + "addShape((Shape)element);\n" +
                                                     FOUR_TABS + "} else {\n" +
                                                     FIVE_TABS + "addLink((Link)element);\n" +
                                                     FOUR_TABS + "}\n" +
                                                     THREE_TABS + "}\n" +
                                                     TWO_TABS + "}\n" +
                                                     OFFSET + "}\n\n";

    private static final String DESERIALIZE_INTERNAL_FORMAT_METHOD = OFFSET + "@Override\n" +
                                                                     OFFSET + "public void deserializeInternalFormat(Node node) {\n" +
                                                                     TWO_TABS + "NodeList childNodes = node.getChildNodes();\n\n" +
                                                                     TWO_TABS + "for (int i = 0; i < childNodes.getLength(); i++) {\n" +
                                                                     THREE_TABS + "Node item = childNodes.item(i);\n" +
                                                                     THREE_TABS + "String name = item.getNodeName();\n\n" +
                                                                     THREE_TABS + "if (isInternalProperty(name)) {\n" +
                                                                     FOUR_TABS + "applyProperty(item);\n" +
                                                                     THREE_TABS + "} else {\n" +
                                                                     FOUR_TABS + "Element element = findElement(name);\n" +
                                                                     FOUR_TABS + "element.deserializeInternalFormat(item);\n\n" +
                                                                     FOUR_TABS + "if (element instanceof Shape) {\n" +
                                                                     FIVE_TABS + "addShape((Shape)element);\n" +
                                                                     FOUR_TABS + "} else {\n" +
                                                                     FIVE_TABS + "addLink((Link)element);\n" +
                                                                     FOUR_TABS + "}\n" +
                                                                     THREE_TABS + "}\n" +
                                                                     TWO_TABS + "}\n" +
                                                                     OFFSET + "}\n\n";

    private static final String SERIALIZE_PROPERTIES_METHOD = OFFSET + "@Override\n" +
                                                              OFFSET + "protected String serializeProperties() {\n" +
                                                              TWO_TABS + "return serialize_code;\n" +
                                                              OFFSET + "}\n\n";

    public static final String SEVEN_WHITE_SPACE = "       ";

    private static final String SERIALIZE_PROPERTY_CODE = "\"<propertyName>\" +\n" +
                                                          THREE_TABS + SEVEN_WHITE_SPACE + "argumentName +\n" +
                                                          TWO_TABS + SEVEN_WHITE_SPACE + "\"</propertyName>\"";

    private static final String FIND_ELEMENT_METHOD = OFFSET + "private Element findElement(@Nonnull String elementName) {\n" +
                                                      TWO_TABS + "switch (elementName) {\n" +
                                                      "findElements" +
                                                      TWO_TABS + "}\n" +
                                                      OFFSET + "}\n\n";

    private static final String FIND_LAST_ELEMENT_CODE = THREE_TABS + "case \"elementName\":\n" +
                                                         THREE_TABS + "default:\n" +
                                                         FOUR_TABS + "return new elementName();\n";

    private static final String FIND_ELEMENT_CODE = THREE_TABS + "case \"elementName\":\n" +
                                                    FOUR_TABS + "return new elementName();\n";

    private static final String APPLY_PROPERTIES_METHOD = OFFSET + "@Override\n" +
                                                          OFFSET + "public void applyProperty(Node node) {\napplyProperties" +
                                                          OFFSET + "}\n\n";

    private static final String APPLY_PROPERTIES_CODE = TWO_TABS + "String nodeName = node.getNodeName();\n" +
                                                        TWO_TABS + "String nodeValue = node.getChildNodes().item(0).getNodeValue();\n\n" +
                                                        TWO_TABS + "switch (nodeName) {\n" +
                                                        THREE_TABS + "case \"x\":\n" +
                                                        FOUR_TABS + "setX(Integer.valueOf(nodeValue));\n" +
                                                        THREE_TABS + "break;\n" +
                                                        THREE_TABS + "case \"y\":\n" +
                                                        FOUR_TABS + "setY(Integer.valueOf(nodeValue));\n" +
                                                        THREE_TABS + "break;\n" +
                                                        THREE_TABS + "case \"uuid\":\n" +
                                                        FOUR_TABS + "id = nodeValue;\n" +
                                                        THREE_TABS + "break;\n" +
                                                        "findProperty" +
                                                        TWO_TABS + "}\n";

    private static final String ADD_COMPONENT_CODE = TWO_TABS + "components.add(\"elementName\");\n";

    private static final String SET_PROPERTY_VALUE = THREE_TABS + "case \"propertyName\":\n" +
                                                     FOUR_TABS + "argumentName = propertyType.valueOf(nodeValue);\n" +
                                                     THREE_TABS + "break;\n";

    private static final String INITIALIZE_PROPERTY = TWO_TABS + "argumentName = propertyValue;\n";
    private static final String FIELD_PROPERTY      = OFFSET + "private propertyType argumentName;\n";

    private static final String FIND_ELEMENT_MARKER              = "findElements";
    private static final String PROPERTIES_FIELDS_MARKER         = "properties_fields";
    private static final String PROPERTIES_MARKER                = "propertiesList";
    private static final String INTERNAL_PROPERTIES_MARKER       = "internalPropertiesList";
    private static final String PROPERTY_VALUE_MARKER            = "propertyValue";
    private static final String PROPERTIES_INITIALIZATION_MARKER = "propertiesInitialization";
    private static final String APPLY_PROPERTIES_MARKER          = "applyProperties";
    private static final String FIND_PROPERTY_MARKER             = "findProperty";
    private static final String GENERAL_CONSTRUCTOR_MARKER       = "generalConstructor";
    private static final String CONSTRUCTOR_MARKER               = "constructor";
    private static final String SERIALIZE_CODE_MARKER            = "serialize_code";
    private static final String DESERIALIZE_CODE_MARKER          = "deserialize_method";
    private static final String APPLY_PROPERTIES_METHOD_MARKER   = "apply_property_method";
    private static final String EXTEND_ELEMENT_MARKER            = "extend_element";
    private static final String COMPONENTS_MARKER                = "components";

    private static final String ELEMENT_CLASS_NAME = "Element";

    private Element         element;
    private String          mainPackage;
    private Set<Element>    elements;
    private Element         rootElement;
    private Set<Connection> connections;

    @Inject
    public ElementBuilder() {
        super();
        builder = this;
    }

    @Nonnull
    public ElementBuilder mainPackage(@Nonnull String mainPackage) {
        this.mainPackage = mainPackage;
        return this;
    }

    @Nonnull
    public ElementBuilder elements(@Nonnull Set<Element> elements) {
        this.elements = elements;
        return this;
    }

    @Nonnull
    public ElementBuilder connections(@Nonnull Set<Connection> connections) {
        this.connections = connections;
        return this;
    }

    @Nonnull
    public ElementBuilder rootElement(@Nonnull Element element) {
        this.rootElement = element;
        return this;
    }

    @Nonnull
    public ElementBuilder currentElement(@Nonnull Element element) {
        this.element = element;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public void build() throws IOException {
        String elementName = element.getName();
        Set<Property> properties = element.getProperties();

        String elementsPackage = mainPackage + '.' + CLIENT_PACKAGE + '.' + ELEMENTS_PACKAGE;

        StringBuilder fields = new StringBuilder();
        StringBuilder propertiesInitialization = new StringBuilder();
        StringBuilder applyPropertiesValue = new StringBuilder();
        StringBuilder propertyNames = new StringBuilder();
        StringBuilder internalPropertyNames = new StringBuilder("\"x\", \"y\", \"uuid\"");
        StringBuilder propertiesGetterAndSetters = new StringBuilder();
        StringBuilder imports = new StringBuilder();
        StringBuilder serializationCode = new StringBuilder();

        boolean isFirst = true;

        for (Iterator<Property> iterator = properties.iterator(); iterator.hasNext(); ) {
            Property property = iterator.next();

            String propertyName = property.getName();
            Class javaClass = convertPropertyTypeToJavaClass(property);
            String propertyType = javaClass.getSimpleName();

            fields.append(createPropertyFieldCode(propertyName, propertyType));

            propertiesInitialization.append(createInitializePropertyCode(propertyName, javaClass.equals(String.class) ?
                                                                                       '"' + property.getValue() + '"' :
                                                                                       property.getValue()));

            applyPropertiesValue.append(createInitializePropertiesCode(propertyName, propertyType));

            internalPropertyNames.append(", ").append('"').append(propertyName).append('"');

            propertyNames.append('"').append(propertyName).append('"');
            if (iterator.hasNext()) {
                propertyNames.append(", ");
            }

            propertiesGetterAndSetters.append(createPropertyGetterAndSetterCode(propertyName, propertyType));

            if (isFirst) {
                isFirst = false;
            } else {
                serializationCode.append(SEVEN_WHITE_SPACE);
            }

            serializationCode.append(createSerializePropertyCode(propertyName));

            if (iterator.hasNext()) {
                serializationCode.append(" +\n").append(TWO_TABS);
            }
        }

        imports.append("import ").append(Arrays.class.getName()).append(";\n");

        String propertiesList;
        String applyPropertiesMethod;
        String serializeProperties;

        if (properties.isEmpty()) {
            propertiesList = "new ArrayList<String>()";
            imports.append("import ").append(ArrayList.class.getName()).append(";\n");

            applyPropertiesMethod = createApplyPropertiesMethodCode("");

            serializeProperties = createSerializePropertiesMethodCode("\"\"");
        } else {
            propertiesList = "Arrays.asList(" + propertyNames + ")";

            applyPropertiesMethod = createApplyPropertiesMethodCode(createApplyPropertiesCode(applyPropertiesValue.toString()));

            serializeProperties = createSerializePropertiesMethodCode(serializationCode.toString());
        }

        StringBuilder components = new StringBuilder();

        for (Component component : element.getComponents()) {
            components.append(createAddComponentCode(component.getName()));
        }

        String constructor;
        String extendClass;
        String deserializeCode = "";

        if (element.equals(rootElement)) {
            constructor = createMainElementSuperConstructorCode(elementName) +
                          createElementConstructorCode(elementName,
                                                       "this",
                                                       propertiesList,
                                                       "Arrays.asList(" + internalPropertyNames + ")",
                                                       propertiesInitialization.toString(),
                                                       components.toString());
            imports.append("import ").append(List.class.getName()).append(";\n");

            extendClass = AbstractShape.class.getSimpleName();
            imports.append("import ").append(AbstractShape.class.getName()).append(";\n");

            imports.append("import ").append(com.codenvy.editor.api.editor.elements.Element.class.getName()).append(";\n");
            imports.append("import ").append(Link.class.getName()).append(";\n");

            StringBuilder code = new StringBuilder();

            Iterator<Connection> connectionIterator = connections.iterator();

            for (Iterator<Element> iterator = elements.iterator(); iterator.hasNext(); ) {
                Element element = iterator.next();

                code.append(createFindElementCode(element.getName(), !iterator.hasNext() && !connectionIterator.hasNext()));
            }

            for (; connectionIterator.hasNext(); ) {
                Connection connection = connectionIterator.next();
                code.append(createFindElementCode(connection.getName(), !connectionIterator.hasNext()));
            }

            deserializeCode = DESERIALIZE_METHOD + DESERIALIZE_INTERNAL_FORMAT_METHOD + createFindElementMethodCode(code.toString());
            imports.append("import ").append(NodeList.class.getName()).append(";\n");
            imports.append("import ").append(Shape.class.getName()).append(";\n");
        } else {
            constructor = createElementConstructorCode(elementName,
                                                       "super",
                                                       propertiesList,
                                                       "Arrays.asList(" + internalPropertyNames + ")",
                                                       propertiesInitialization.toString(),
                                                       components.toString());

            extendClass = rootElement.getName();
        }

        source = Paths.get(path,
                           MAIN_SOURCE_PATH,
                           JAVA_SOURCE_FOLDER,
                           ELEMENTS_PACKAGE,
                           ELEMENT_CLASS_NAME + JAVA);
        target = Paths.get(path,
                           MAIN_SOURCE_PATH,
                           JAVA_SOURCE_FOLDER,
                           convertPathToPackageName(mainPackage),
                           CLIENT_PACKAGE,
                           ELEMENTS_PACKAGE,
                           elementName + JAVA);

        replaceElements.put(CURRENT_PACKAGE_MARKER, elementsPackage);
        replaceElements.put(IMPORT_MARKER, imports.toString());
        replaceElements.put(ELEMENT_NAME_MARKER, elementName);
        replaceElements.put(EXTEND_ELEMENT_MARKER, extendClass);
        replaceElements.put(PROPERTIES_FIELDS_MARKER, fields.toString());
        replaceElements.put(CONSTRUCTOR_MARKER, constructor);
        replaceElements.put(GETTER_AND_SETTER_PROPERTY_MARKER, propertiesGetterAndSetters.toString());
        replaceElements.put(SERIALIZE_CODE_MARKER, serializeProperties);
        replaceElements.put(DESERIALIZE_CODE_MARKER, deserializeCode);
        replaceElements.put(APPLY_PROPERTIES_METHOD_MARKER, applyPropertiesMethod);

        super.build();
    }

    @Nonnull
    private String createMainElementSuperConstructorCode(@Nonnull String elementName) {
        Map<String, String> masks = new LinkedHashMap<>(1);
        masks.put(ELEMENT_NAME_MARKER, elementName);

        return ContentReplacer.replace(MAIN_ELEMENT_SUPER_CONSTRUCTOR, masks);
    }

    @Nonnull
    private String createElementConstructorCode(@Nonnull String elementName,
                                                @Nonnull String generalConstructor,
                                                @Nonnull String properties,
                                                @Nonnull String internalProperties,
                                                @Nonnull String propertiesInitialization,
                                                @Nonnull String components) {
        Map<String, String> masks = new LinkedHashMap<>(5);
        masks.put(ELEMENT_NAME_MARKER, elementName);
        masks.put(GENERAL_CONSTRUCTOR_MARKER, generalConstructor);
        masks.put(PROPERTIES_INITIALIZATION_MARKER, propertiesInitialization);
        masks.put(PROPERTIES_MARKER, properties);
        masks.put(INTERNAL_PROPERTIES_MARKER, internalProperties);
        masks.put(COMPONENTS_MARKER, components);

        return ContentReplacer.replace(ELEMENT_CONSTRUCTOR, masks);
    }

    @Nonnull
    private String createFindElementMethodCode(@Nonnull String findElementCode) {
        Map<String, String> masks = new LinkedHashMap<>(1);
        masks.put(FIND_ELEMENT_MARKER, findElementCode);

        return ContentReplacer.replace(FIND_ELEMENT_METHOD, masks);
    }

    @Nonnull
    private String createFindElementCode(@Nonnull String elementName, boolean isLast) {
        Map<String, String> masks = new LinkedHashMap<>(1);
        masks.put(ELEMENT_NAME_MARKER, elementName);

        return ContentReplacer.replace(isLast ? FIND_LAST_ELEMENT_CODE : FIND_ELEMENT_CODE, masks);
    }

    @Nonnull
    private String createSerializePropertiesMethodCode(@Nonnull String serializePropertiesCode) {
        Map<String, String> masks = new LinkedHashMap<>(1);
        masks.put(SERIALIZE_CODE_MARKER, serializePropertiesCode);

        return ContentReplacer.replace(SERIALIZE_PROPERTIES_METHOD, masks);
    }

    @Nonnull
    private String createSerializePropertyCode(@Nonnull String propertyName) {
        Map<String, String> masks = new LinkedHashMap<>(2);
        masks.put(PROPERTY_NAME_MARKER, propertyName);
        masks.put(ARGUMENT_NAME_MARKER, changeFirstSymbolToLowCase(propertyName));

        return ContentReplacer.replace(SERIALIZE_PROPERTY_CODE, masks);
    }

    @Nonnull
    private String createApplyPropertiesMethodCode(@Nonnull String applyPropertiesCode) {
        Map<String, String> masks = new LinkedHashMap<>(1);
        masks.put(APPLY_PROPERTIES_MARKER, applyPropertiesCode);

        return ContentReplacer.replace(APPLY_PROPERTIES_METHOD, masks);
    }

    @Nonnull
    private String createApplyPropertiesCode(@Nonnull String findProperties) {
        Map<String, String> masks = new LinkedHashMap<>(1);
        masks.put(FIND_PROPERTY_MARKER, findProperties);

        return ContentReplacer.replace(APPLY_PROPERTIES_CODE, masks);
    }

    @Nonnull
    private String createInitializePropertiesCode(@Nonnull String propertyName, @Nonnull String propertyType) {
        Map<String, String> masks = new LinkedHashMap<>(3);
        masks.put(PROPERTY_NAME_MARKER, propertyName);
        masks.put(PROPERTY_TYPE_MARKER, propertyType);
        masks.put(ARGUMENT_NAME_MARKER, changeFirstSymbolToLowCase(propertyName));

        return ContentReplacer.replace(SET_PROPERTY_VALUE, masks);
    }

    @Nonnull
    private String createInitializePropertyCode(@Nonnull String propertyName, @Nonnull String propertyValue) {
        Map<String, String> masks = new LinkedHashMap<>(2);
        masks.put(ARGUMENT_NAME_MARKER, changeFirstSymbolToLowCase(propertyName));
        masks.put(PROPERTY_VALUE_MARKER, propertyValue);

        return ContentReplacer.replace(INITIALIZE_PROPERTY, masks);
    }

    @Nonnull
    private String createPropertyFieldCode(@Nonnull String propertyName, @Nonnull String propertyType) {
        Map<String, String> masks = new LinkedHashMap<>(2);
        masks.put(ARGUMENT_NAME_MARKER, changeFirstSymbolToLowCase(propertyName));
        masks.put(PROPERTY_TYPE_MARKER, propertyType);

        return ContentReplacer.replace(FIELD_PROPERTY, masks);
    }

    @Nonnull
    private String createPropertyGetterAndSetterCode(@Nonnull String propertyName, @Nonnull String propertyType) {
        Map<String, String> masks = new LinkedHashMap<>(3);
        masks.put(PROPERTY_NAME_MARKER, propertyName);
        masks.put(PROPERTY_TYPE_MARKER, propertyType);
        masks.put(ARGUMENT_NAME_MARKER, changeFirstSymbolToLowCase(propertyName));

        return ContentReplacer.replace(PROPERTY_SETTER_AND_GETTER, masks);
    }

    @Nonnull
    private String createAddComponentCode(@Nonnull String elementName) {
        Map<String, String> masks = new LinkedHashMap<>(1);
        masks.put(ELEMENT_NAME_MARKER, elementName);

        return ContentReplacer.replace(ADD_COMPONENT_CODE, masks);
    }

}