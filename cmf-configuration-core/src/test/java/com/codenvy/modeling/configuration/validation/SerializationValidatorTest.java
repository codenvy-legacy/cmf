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

import com.codenvy.modeling.configuration.metamodel.serialization.ConnectionDesignation;
import com.codenvy.modeling.configuration.metamodel.serialization.Element;
import com.codenvy.modeling.configuration.metamodel.serialization.ElementDesignation;
import com.codenvy.modeling.configuration.metamodel.serialization.SerializationConfiguration;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** @author Valeriy Svydenko */
public class SerializationValidatorTest {
    public static final String SIMPLE_STRING = "simple_text";

    public ConnectionInitializer         connectionInitializer = new ConnectionInitializer();
    public ElementDesignationInitializer elementDesignation    = new ElementDesignationInitializer();
    public ElementInitializer            elementInitializer    = new ElementInitializer();
    public SerializationConfiguration serializationConfiguration;

    @Rule
    public TestRule chainElement = RuleChain
            .outerRule(elementDesignation)
            .around(connectionInitializer)
            .around(elementInitializer);

    //tests connection
    @Test
    public void connectionValidationShouldResultInErrorIfNameIsNotValid() {
        connectionInitializer.getConnectionDesignation().setReferencePropertyName("");

        Report report = ConfigurationConstraintsValidator.validate(connectionInitializer.getConnectionDesignation());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void connectionValidationShouldResultInErrorIfTemplateIsNotValid() {
        connectionInitializer.getConnectionDesignation().setReferenceTemplate("");

        Report report = ConfigurationConstraintsValidator.validate(connectionInitializer.getConnectionDesignation());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void connectionValidationShouldResultInErrorIfTypeIsNull() {
        connectionInitializer.getConnectionDesignation().setType(null);

        Report report = ConfigurationConstraintsValidator.validate(connectionInitializer.getConnectionDesignation());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void connectionValidationShouldResultWithoutError() {
        Report report = ConfigurationConstraintsValidator.validate(connectionInitializer.getConnectionDesignation());

        assertFalse(report.hasErrors());
    }

    //tests ElementDesignation
    @Test
    public void elementDesignationValidationShouldResultInErrorIfNameIsNotValid() {
        elementDesignation.getElementDesignation().setReferencePropertyName("");

        Report report = ConfigurationConstraintsValidator.validate(elementDesignation.getElementDesignation());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void elementDesignationValidationShouldResultInErrorIfTemplateIsNotValid() {
        elementDesignation.getElementDesignation().setReferenceTemplate("");

        Report report = ConfigurationConstraintsValidator.validate(elementDesignation.getElementDesignation());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void elementDesignationValidationShouldResultInErrorIfTypeIsNull() {
        elementDesignation.getElementDesignation().setType(null);

        Report report = ConfigurationConstraintsValidator.validate(elementDesignation.getElementDesignation());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void elementDesignationValidationShouldResultWithoutError() {
        Report report = ConfigurationConstraintsValidator.validate(elementDesignation.getElementDesignation());

        assertFalse(report.hasErrors());
    }

    //tests ElementDesignation
    @Test
    public void elementValidationShouldResultInErrorIfNameIsNotValid() {
        elementInitializer.getElement().setName("");

        Report report = ConfigurationConstraintsValidator.validate(elementInitializer.getElement());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void elementValidationShouldResultInErrorIfTemplateIsNotValid() {
        elementInitializer.getElement().setTemplate("");

        Report report = ConfigurationConstraintsValidator.validate(elementInitializer.getElement());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void elementValidationShouldResultWithoutError() {
        Report report = ConfigurationConstraintsValidator.validate(elementInitializer.getElement());

        assertFalse(report.hasErrors());
    }

    //tests serialization
    @Test
    public void serializationValidationShouldResultWithoutError() {
        serializationConfiguration = new SerializationConfiguration();

        LinkedList<Element> elements = new LinkedList<>();
        elements.add(elementInitializer.getElement());

        serializationConfiguration.setElements(elements);

        Report report = ConfigurationConstraintsValidator.validate(serializationConfiguration);

        assertFalse(report.hasErrors());
    }

    @Test
    public void serializationValidationShouldResultInErrorIfNoElements() {
        serializationConfiguration = new SerializationConfiguration();

        LinkedList<Element> elements = new LinkedList<>();

        serializationConfiguration.setElements(elements);

        Report report = ConfigurationConstraintsValidator.validate(serializationConfiguration);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    private class ConnectionInitializer extends ExternalResource {
        private ConnectionDesignation connectionDesignation;

        @Override
        protected void before() throws Throwable {
            connectionDesignation = new ConnectionDesignation();

            connectionDesignation.setType(ConnectionDesignation.Type.ORDERING);
            connectionDesignation.setReferencePropertyName(SIMPLE_STRING);
            connectionDesignation.setReferenceTemplate(SIMPLE_STRING);
        }

        protected ConnectionDesignation getConnectionDesignation() {
            return connectionDesignation;
        }
    }

    private class ElementDesignationInitializer extends ExternalResource {
        private ElementDesignation elementDesignation;

        @Override
        protected void before() throws Throwable {
            elementDesignation = new ElementDesignation();

            elementDesignation.setType(ElementDesignation.Type.INSERTION);
            elementDesignation.setReferenceTemplate(SIMPLE_STRING);
            elementDesignation.setReferencePropertyName(SIMPLE_STRING);
        }

        protected ElementDesignation getElementDesignation() {
            return elementDesignation;
        }
    }

    private class ElementInitializer extends ExternalResource {
        private Element element;

        @Override
        protected void before() throws Throwable {
            element = new Element();

            element.setName(SIMPLE_STRING);
            element.setConnectionDesignation(connectionInitializer.getConnectionDesignation());
            element.setTemplate(SIMPLE_STRING);
            element.setElementDesignation(elementDesignation.getElementDesignation());
        }

        protected Element getElement() {
            return element;
        }
    }
}