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

import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** @author Valeriy Svydenko */
public class SerializationValidatorTest {
    public static final String SIMPLE_STRING = "simple_text";

    private ConnectionDesignation      connectionDesignation;
    private Element                    element;
    private ElementDesignation         elementDesignation;
    private SerializationConfiguration serializationConfiguration;

    //test connection
    @Test
    public void connectionValidationShouldResultInErrorIfNameIsNotValid() {
        initializationConnection();

        connectionDesignation.setReferencePropertyName("");

        Report report = ConfigurationConstraintsValidator.validate(connectionDesignation);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void connectionValidationShouldResultInErrorIfTemplateIsNotValid() {
        initializationConnection();

        connectionDesignation.setReferenceTemplate("");

        Report report = ConfigurationConstraintsValidator.validate(connectionDesignation);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void connectionValidationShouldResultInErrorIfTypeIsNull() {
        initializationConnection();

        connectionDesignation.setType(null);

        Report report = ConfigurationConstraintsValidator.validate(connectionDesignation);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void connectionValidationShouldResultWithoutError() {
        initializationConnection();

        Report report = ConfigurationConstraintsValidator.validate(connectionDesignation);

        assertFalse(report.hasErrors());
    }

    private void initializationConnection() {
        connectionDesignation = new ConnectionDesignation();

        connectionDesignation.setType(ConnectionDesignation.Type.ORDERING);
        connectionDesignation.setReferencePropertyName(SIMPLE_STRING);
        connectionDesignation.setReferenceTemplate(SIMPLE_STRING);
    }

    //test ElementDesignation
    @Test
    public void elementDesignationValidationShouldResultInErrorIfNameIsNotValid() {
        initializationElementDesignation();

        elementDesignation.setReferencePropertyName("");

        Report report = ConfigurationConstraintsValidator.validate(elementDesignation);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void elementDesignationValidationShouldResultInErrorIfTemplateIsNotValid() {
        initializationElementDesignation();

        elementDesignation.setReferenceTemplate("");

        Report report = ConfigurationConstraintsValidator.validate(elementDesignation);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void elementDesignationValidationShouldResultInErrorIfTypeIsNull() {
        initializationElementDesignation();

        elementDesignation.setType(null);

        Report report = ConfigurationConstraintsValidator.validate(elementDesignation);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void elementDesignationValidationShouldResultWithoutError() {
        initializationElementDesignation();

        Report report = ConfigurationConstraintsValidator.validate(elementDesignation);

        assertFalse(report.hasErrors());
    }

    private void initializationElementDesignation() {
        elementDesignation = new ElementDesignation();

        elementDesignation.setType(ElementDesignation.Type.INSERTION);
        elementDesignation.setReferenceTemplate(SIMPLE_STRING);
        elementDesignation.setReferencePropertyName(SIMPLE_STRING);
    }

    //test
    private void initializationElement() {
        element = new Element();

        initializationElementDesignation();
        initializationConnection();

        element.setName(SIMPLE_STRING);
        element.setConnectionDesignation(connectionDesignation);
        element.setTemplate(SIMPLE_STRING);
        element.setElementDesignation(elementDesignation);
    }

    @Test
    public void elementValidationShouldResultInErrorIfNameIsNotValid() {
        initializationElement();

        element.setName("");

        Report report = ConfigurationConstraintsValidator.validate(element);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void elementValidationShouldResultInErrorIfTemplateIsNotValid() {
        initializationElement();

        element.setTemplate("");

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

    //tests serialization
    @Test
    public void serializationValidationShouldResultWithoutError() {
        serializationConfiguration = new SerializationConfiguration();

        initializationElement();

        LinkedList<Element> elements = new LinkedList<>();
        elements.add(element);

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
}