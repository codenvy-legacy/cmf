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

package com.codenvy.modeling.configuration.metamodel.serialization;

import com.codenvy.modeling.adapter.metamodel.serialization.SerializationConfigurationAdapterImpl;
import com.codenvy.modeling.configuration.parser.metamodel.serialization.SerializationConfigurationAdapterListener;
import com.google.inject.Provider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Dmitry Kuleshov
 * @author Valeriy Svydenko
 * @author Andrey Plotnikov
 */
@RunWith(Parameterized.class)
public class SerializationAdapterTest {

    private static final String SERIALIZATION_GRAMMAR_TEST_I  = "/SerializationGrammarTest_I";
    private static final String SERIALIZATION_GRAMMAR_TEST_II = "/SerializationGrammarTest_II";

    private static List<Element> elements;

    private int numberOfGrammar;

    @Parameters
    public static Iterable<Object[]> dataOfConfigurations() {
        return Arrays.asList(new Object[][]{{SERIALIZATION_GRAMMAR_TEST_I, 1}, {SERIALIZATION_GRAMMAR_TEST_II, 2}});
    }

    @SuppressWarnings("unchecked")
    public SerializationAdapterTest(String grammar, int number) throws IOException {
        numberOfGrammar = number;

        Provider<SerializationConfigurationAdapterListener> serializationConfigurationAdapterListenerProvider = mock(Provider.class);
        when(serializationConfigurationAdapterListenerProvider.get())
                .thenReturn(new SerializationConfigurationAdapterListener(new SerializationConfiguration()));

        String configurationFile = SerializationAdapterTest.class.getResource(grammar).getFile();
        SerializationConfigurationAdapterImpl serializationConfigurationAdapter =
                new SerializationConfigurationAdapterImpl(serializationConfigurationAdapterListenerProvider, configurationFile);
        SerializationConfiguration configuration = serializationConfigurationAdapter.getConfiguration();

        elements = configuration.getElements();
    }

    @Test
    public void allElementsMustHaveNames() {
        for (Element e : elements) {
            assertFalse(e.getName().isEmpty());

            if (numberOfGrammar == 2) {
                assertEquals("testName", e.getName());
            }
        }
    }

    @Test
    public void allElementsMustHaveTemplate() {
        for (Element e : elements) {
            assertFalse(e.getTemplate().isEmpty());

            if (numberOfGrammar == 2) {
                assertEquals("template.name", e.getTemplate());
            }
        }
    }

    @Test
    public void allTemplatesNamesMustHaveDot() {
        for (Element e : elements) {
            assertTrue(e.getTemplate().contains("."));
            assertTrue(e.getTemplate().indexOf(".") > 0);
        }
    }

    @Test
    public void allElementsMustHaveDesignation() {
        for (Element e : elements) {
            ElementDesignation designation = e.getElementDesignation();

            assertFalse(designation.getType().name().isEmpty());
            assertNotNull(ElementDesignation.Type.valueOf(designation.getType().name()));
        }
    }

    @Test
    public void allReferenceDesignationsMustHaveName() {
        for (Element e : elements) {
            ElementDesignation designation = e.getElementDesignation();
            if (ElementDesignation.Type.REFERENCE.name().equals(designation.getType().name())) {
                assertFalse(designation.getReferencePropertyName().isEmpty());

                assertEquals("propertyName", designation.getReferencePropertyName());
            }
        }
    }

    @Test
    public void allReferenceDesignationsMustHaveTemplate() {
        for (Element e : elements) {
            ElementDesignation designation = e.getElementDesignation();
            if (ElementDesignation.Type.REFERENCE.name().equals(designation.getType().name())) {
                assertFalse(designation.getReferenceTemplate().isEmpty());

                assertEquals("template.name", designation.getReferenceTemplate());
            }
        }
    }

    @Test
    public void allElementsMustHaveConDesignation() {
        for (Element e : elements) {
            ConnectionDesignation designation = e.getConnectionDesignation();

            assertFalse(designation.getType().name().isEmpty());
            assertNotNull(ConnectionDesignation.Type.valueOf(designation.getType().name()));
        }
    }

    @Test
    public void allReferenceConnectionDesignationMustHaveNames() {
        for (Element e : elements) {
            ConnectionDesignation designation = e.getConnectionDesignation();

            if (ConnectionDesignation.Type.REFERENCE.name().equals(designation.getType().name())) {
                assertFalse(designation.getReferencePropertyName().isEmpty());

                assertEquals("propertyName", designation.getReferencePropertyName());
            }
        }
    }

    @Test
    public void allReferenceConnectionDesignationMustHaveTemplate() {
        for (Element e : elements) {
            ConnectionDesignation designation = e.getConnectionDesignation();

            if (ConnectionDesignation.Type.REFERENCE.name().equals(designation.getType().name())) {
                assertFalse(designation.getReferenceTemplate().isEmpty());
                assertTrue(designation.getReferenceTemplate().contains("."));
                assertTrue(designation.getReferenceTemplate().indexOf(".") > 0);

                assertEquals("template.name", designation.getReferenceTemplate());
            }
        }
    }
}