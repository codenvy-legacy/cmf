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
import com.codenvy.modeling.configuration.validation.constraints.ConfigurationConstraintsValidator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import java.util.LinkedHashSet;
import java.util.Set;

import static com.codenvy.modeling.configuration.metamodel.diagram.Property.Type.BOOLEAN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Valeriy Svydenko
 */
public class DiagramValidatorTest {
    public static final String SIMPLE_STRING = "simple_text";

    public PropertyInitializer    propertyInitializer    = new PropertyInitializer();
    public ComponentInitializer   componentInitializer   = new ComponentInitializer();
    public ElementInitializer     elementInitializer     = new ElementInitializer();
    public PairInitializer        pairInitializer        = new PairInitializer();
    public ConnectionInitializer  connectionInitializer  = new ConnectionInitializer();
    public RootElementInitializer rootElementInitializer = new RootElementInitializer();

    @Rule
    public TestRule chainElement = RuleChain
            .outerRule(propertyInitializer)
            .around(componentInitializer)
            .around(elementInitializer);

    @Rule
    public TestRule chainRootElement = RuleChain
            .outerRule(propertyInitializer)
            .around(componentInitializer)
            .around(rootElementInitializer);

    @Rule
    public TestRule chainConnection = RuleChain
            .outerRule(pairInitializer)
            .around(connectionInitializer);

    //tests property
    @Test
    public void propertyValidationShouldResultInErrorIfNameIsNotValid() {
        propertyInitializer.getProperty().setName("");

        Report report = ConfigurationConstraintsValidator.validate(propertyInitializer.getProperty());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void propertyValidationShouldResultInErrorIfValueIsNotValid() {
        propertyInitializer.getProperty().setValue("");

        Report report = ConfigurationConstraintsValidator.validate(propertyInitializer.getProperty());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void propertyValidationShouldResultInErrorIfTypeIsEmpty() {
        propertyInitializer.getProperty().setType("");

        Report report = ConfigurationConstraintsValidator.validate(propertyInitializer.getProperty());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void propertyValidationShouldResultWithoutError() {
        Report report = ConfigurationConstraintsValidator.validate(propertyInitializer.getProperty());

        assertFalse(report.hasErrors());
    }

    //tests component
    @Test
    public void componentValidationShouldResultInErrorIfNameIsNotValid() {
        componentInitializer.getComponent().setName("");

        Report report = ConfigurationConstraintsValidator.validate(componentInitializer.getComponent());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void componentValidationShouldResultWithoutError() {
        Report report = ConfigurationConstraintsValidator.validate(componentInitializer.getComponent());

        assertFalse(report.hasErrors());
    }

    //test root element
    @Test
    public void rootElementValidationShouldResultInErrorIfRelationIsNull() {
        rootElementInitializer.getElement().setRelation(null);

        Report report = ConfigurationConstraintsValidator.validate(rootElementInitializer.getElement());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void rootElementValidationShouldResultInErrorIfPropertiesIsEmpty() {
        rootElementInitializer.getElement().setProperties(new LinkedHashSet<Property>());

        Report report = ConfigurationConstraintsValidator.validate(rootElementInitializer.getElement());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void rootElementValidationShouldResultInErrorIfComponentsIsEmpty() {
        rootElementInitializer.getElement().setComponents(new LinkedHashSet<Component>());

        Report report = ConfigurationConstraintsValidator.validate(rootElementInitializer.getElement());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void rootElementValidationShouldResultWithoutError() {
        Report report = ConfigurationConstraintsValidator.validate(elementInitializer.getElement());

        assertFalse(report.hasErrors());
    }

    //tests element
    @Test
    public void elementValidationShouldResultInErrorIfNameIsNotValid() {
        elementInitializer.getElement().setName("");

        Report report = ConfigurationConstraintsValidator.validate(elementInitializer.getElement());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void elementValidationShouldResultInErrorIfRelationIsNull() {
        elementInitializer.getElement().setRelation(null);

        Report report = ConfigurationConstraintsValidator.validate(elementInitializer.getElement());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void elementValidationShouldResultInErrorIfPropertiesIsEmpty() {
        elementInitializer.getElement().setProperties(new LinkedHashSet<Property>());

        Report report = ConfigurationConstraintsValidator.validate(elementInitializer.getElement());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void elementValidationShouldResultInErrorIfComponentsIsEmpty() {
        elementInitializer.getElement().setComponents(new LinkedHashSet<Component>());

        Report report = ConfigurationConstraintsValidator.validate(elementInitializer.getElement());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void elementValidationShouldResultWithoutError() {
        Report report = ConfigurationConstraintsValidator.validate(elementInitializer.getElement());

        assertFalse(report.hasErrors());
    }

    //tests pair
    @Test
    public void pairValidationShouldResultInErrorIfStartIsNotValid() {
        pairInitializer.getPair().setStart("");

        Report report = ConfigurationConstraintsValidator.validate(pairInitializer.getPair());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void pairValidationShouldResultInErrorIfFinishIsNotValid() {
        pairInitializer.getPair().setFinish("");

        Report report = ConfigurationConstraintsValidator.validate(pairInitializer.getPair());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void pairValidationShouldResultWithoutError() {
        Report report = ConfigurationConstraintsValidator.validate(pairInitializer.getPair());

        assertFalse(report.hasErrors());
    }

    //tests connection
    @Test
    public void connectionValidationShouldResultInErrorIfNameIsNotValid() {
        connectionInitializer.getConnection().setName("");

        Report report = ConfigurationConstraintsValidator.validate(connectionInitializer.getConnection());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void connectionValidationShouldResultInErrorIfTypeIsNull() {
        connectionInitializer.getConnection().setType(null);

        Report report = ConfigurationConstraintsValidator.validate(connectionInitializer.getConnection());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void connectionValidationShouldResultInErrorIfPairsIsEmpty() {
        Set<Pair> pairs = new LinkedHashSet<>();

        connectionInitializer.getConnection().setPairs(pairs);

        Report report = ConfigurationConstraintsValidator.validate(connectionInitializer.getConnection());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void connectionValidationShouldResultWithoutError() {
        Report report = ConfigurationConstraintsValidator.validate(connectionInitializer.getConnection());

        assertFalse(report.hasErrors());
    }

    //tests diagram
    @Test
    public void diagramValidationShouldResultWithoutError() {
        DiagramConfiguration diagramConfiguration = new DiagramConfiguration();

        Set<Element> elements = new LinkedHashSet<>();
        elements.add(elementInitializer.getElement());
        Set<Connection> connections = new LinkedHashSet<>();
        connections.add(connectionInitializer.getConnection());

        diagramConfiguration.setElements(elements);
        diagramConfiguration.setConnections(connections);

        Report report = ConfigurationConstraintsValidator.validate(connectionInitializer.getConnection());

        assertFalse(report.hasErrors());
    }

    private class PropertyInitializer extends ExternalResource {
        private Property property;

        @Override
        protected void before() throws Throwable {
            property = new Property();

            property.setName(SIMPLE_STRING);
            property.setType(BOOLEAN.name());
            property.setValue(SIMPLE_STRING);
        }

        protected Property getProperty() {
            return property;
        }
    }

    private class ComponentInitializer extends ExternalResource {
        private Component component;

        protected Component getComponent() {
            return component;
        }

        @Override
        protected void before() throws Throwable {
            component = new Component();

            component.setName(SIMPLE_STRING);
        }
    }

    private class ElementInitializer extends ExternalResource {
        private Element element;

        @Override
        protected void before() throws Throwable {
            element = new Element();

            Set<Property> properties = new LinkedHashSet<>();
            properties.add(propertyInitializer.getProperty());

            Set<Component> components = new LinkedHashSet<>();
            components.add(componentInitializer.getComponent());

            element.setName(SIMPLE_STRING);
            element.setRelation(Element.Relation.MULTIPLE);
            element.setComponents(components);
            element.setProperties(properties);
        }

        protected Element getElement() {
            return element;
        }
    }

    private class RootElementInitializer extends ExternalResource {
        private Element rootElement;

        @Override
        protected void before() throws Throwable {
            rootElement = new Element();

            Set<Property> properties = new LinkedHashSet<>();
            properties.add(propertyInitializer.getProperty());

            Set<Component> components = new LinkedHashSet<>();
            components.add(componentInitializer.getComponent());

            rootElement.setName(SIMPLE_STRING);
            rootElement.setRelation(Element.Relation.MULTIPLE);
            rootElement.setComponents(components);
            rootElement.setProperties(properties);
        }

        protected Element getElement() {
            return rootElement;
        }
    }

    private class PairInitializer extends ExternalResource {
        private Pair pair;

        @Override
        protected void before() throws Throwable {
            pair = new Pair();

            pair.setFinish(SIMPLE_STRING);
            pair.setStart(SIMPLE_STRING);
        }

        protected Pair getPair() {
            return pair;
        }
    }

    private class ConnectionInitializer extends ExternalResource {
        private Connection connection;

        @Override
        protected void before() throws Throwable {
            connection = new Connection();

            Set<Pair> pairs = new LinkedHashSet<>();
            pairs.add(pairInitializer.getPair());

            connection.setName(SIMPLE_STRING);
            connection.setType(Connection.Type.DIRECTED);
            connection.setPairs(pairs);
        }

        protected Connection getConnection() {
            return connection;
        }
    }
}