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
package com.codenvy.modeling.configuration.validation;

import com.codenvy.modeling.configuration.metamodel.diagram.Component;
import com.codenvy.modeling.configuration.metamodel.diagram.Connection;
import com.codenvy.modeling.configuration.metamodel.diagram.DiagramConfiguration;
import com.codenvy.modeling.configuration.metamodel.diagram.Element;
import com.codenvy.modeling.configuration.metamodel.diagram.Pair;
import com.codenvy.modeling.configuration.metamodel.diagram.Property;

import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** @author Valeriy Svydenko */
public class DiagramValidatorTest {
    public static final String SIMPLE_STRING = "simple_text";

    private Property             property;
    private Component            component;
    private Element              element;
    private Connection           connection;
    private Pair                 pair;

    //tests property
    @Test
    public void propertyValidationShouldResultInErrorIfNameIsNotValid() {
        initializationProperty();

        property.setName("");

        Report report = ConfigurationConstraintsValidator.validate(property);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void propertyValidationShouldResultInErrorIfValueIsNotValid() {
        initializationProperty();

        property.setValue("");

        Report report = ConfigurationConstraintsValidator.validate(property);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void propertyValidationShouldResultInErrorIfTypeIsNull() {
        initializationProperty();

        property.setType(null);

        Report report = ConfigurationConstraintsValidator.validate(property);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void propertyValidationShouldResultWithoutError() {
        initializationProperty();

        Report report = ConfigurationConstraintsValidator.validate(property);

        assertFalse(report.hasErrors());
    }

    private void initializationProperty() {
        property = new Property();

        property.setName(SIMPLE_STRING);
        property.setType(Property.Type.BOOLEAN);
        property.setValue(SIMPLE_STRING);
    }

    //tests component
    @Test
    public void componentValidationShouldResultInErrorIfNameIsNotValid() {
        initializationComponent();

        component.setName("");

        Report report = ConfigurationConstraintsValidator.validate(component);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void componentValidationShouldResultWithoutError() {
        initializationComponent();

        Report report = ConfigurationConstraintsValidator.validate(component);

        assertFalse(report.hasErrors());
    }

    private void initializationComponent() {
        component = new Component();

        component.setName(SIMPLE_STRING);
    }

    //tests element
    @Test
    public void elementValidationShouldResultInErrorIfNameIsNotValid() {
        initializationElement();

        element.setName("");

        Report report = ConfigurationConstraintsValidator.validate(element);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void elementValidationShouldResultInErrorIfRelationIsNull() {
        initializationElement();

        element.setRelation(null);

        Report report = ConfigurationConstraintsValidator.validate(element);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void elementValidationShouldResultInErrorIfPropertiesIsEmpty() {
        initializationElement();

        element.setProperties(new LinkedList<Property>());

        Report report = ConfigurationConstraintsValidator.validate(element);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void elementValidationShouldResultInErrorIfComponentsIsEmpty() {
        initializationElement();

        element.setComponents(new LinkedList<Component>());

        Report report = ConfigurationConstraintsValidator.validate(element);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void elementValidationShouldResultWithoutError() {
        initializationElement();

        Report report = ConfigurationConstraintsValidator.validate(element);

        assertFalse(report.hasErrors());
    }

    private void initializationElement() {
        element = new Element();

        initializationComponent();
        initializationProperty();

        LinkedList<Property> properties = new LinkedList<>();
        properties.add(property);

        LinkedList<Component> components = new LinkedList<>();
        components.add(component);

        element.setName(SIMPLE_STRING);
        element.setRelation(Element.Relation.MULTIPLE);
        element.setComponents(components);
        element.setProperties(properties);
    }

    //tests pair
    private void initializationPair() {
        pair = new Pair();

        pair.setFinish(SIMPLE_STRING);
        pair.setStart(SIMPLE_STRING);
    }

    @Test
    public void pairValidationShouldResultInErrorIfStartIsNotValid() {
        initializationPair();

        pair.setStart("");

        Report report = ConfigurationConstraintsValidator.validate(pair);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void pairValidationShouldResultInErrorIfFinishIsNotValid() {
        initializationPair();

        pair.setFinish("");

        Report report = ConfigurationConstraintsValidator.validate(pair);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void pairValidationShouldResultWithoutError() {
        initializationPair();

        Report report = ConfigurationConstraintsValidator.validate(pair);

        assertFalse(report.hasErrors());
    }

    //tests connection
    @Test
    public void connectionValidationShouldResultInErrorIfNameIsNotValid() {
        initializationConnection();

        connection.setName("");

        Report report = ConfigurationConstraintsValidator.validate(connection);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void connectionValidationShouldResultInErrorIfTypeIsNull() {
        initializationConnection();

        connection.setType(null);

        Report report = ConfigurationConstraintsValidator.validate(connection);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void connectionValidationShouldResultInErrorIfPairsIsEmpty() {
        initializationConnection();

        LinkedList<Pair> pairs = new LinkedList<>();

        connection.setPairs(pairs);

        Report report = ConfigurationConstraintsValidator.validate(connection);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void connectionValidationShouldResultWithoutError() {
        initializationConnection();

        Report report = ConfigurationConstraintsValidator.validate(connection);

        assertFalse(report.hasErrors());
    }

    private void initializationConnection() {
        initializationPair();

        connection = new Connection();

        LinkedList<Pair> pairs = new LinkedList<>();
        pairs.add(pair);

        connection.setName(SIMPLE_STRING);
        connection.setType(Connection.Type.DIRECTED);
        connection.setPairs(pairs);
    }

    //tests diagram
    @Test
    public void diagramValidationShouldResultWithoutError() {
        initializationConnection();
        initializationElement();

        DiagramConfiguration diagramConfiguration = new DiagramConfiguration();

        LinkedList<Element> elements = new LinkedList<>();
        elements.add(element);
        LinkedList<Connection> connections = new LinkedList<>();
        connections.add(connection);

        diagramConfiguration.setElements(elements);
        diagramConfiguration.setConnections(connections);

        Report report = ConfigurationConstraintsValidator.validate(connection);

        assertFalse(report.hasErrors());
    }
}