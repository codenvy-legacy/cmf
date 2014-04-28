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

import com.codenvy.modeling.adapter.metamodel.serialization.SerializationConfigurationAdapter;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/** @author Dmitry Kuleshov */
public class SerializationAdapterTest {

    private static final String SERIALIZATION_GRAMMAR_TEST_I = "/SerializationGrammarTest_I";

    private static List<Element> elements;

    @BeforeClass
    public static void setUp() throws Exception {
        String configurationFile = SerializationAdapterTest.class.getResource(SERIALIZATION_GRAMMAR_TEST_I).getFile();
        SerializationConfigurationAdapter serializationConfigurationAdapter = new SerializationConfigurationAdapter(configurationFile);
        SerializationConfiguration configuration = serializationConfigurationAdapter.getConfiguration();

        elements = configuration.getElements();
    }

    @Test
    public void elementsShouldBeHadNameAndTemplate() {
        for (Element e : elements) {
            assertTrue(e.getName().length() > 0);

            assertTrue(e.getTemplate().length() > 0);
            assertTrue(e.getTemplate().contains("."));
        }
    }

    @Test
    public void elementsShouldBeHadElementDesignation() {
        for (Element e : elements) {
            ElementDesignation designation = e.getElementDesignation();

            assertTrue(designation.getType().name().length() > 0);
            assertNotNull(ElementDesignation.Type.valueOf(designation.getType().name()));

            if (ElementDesignation.Type.REFERENCE.name().equals(designation.getType().name())) {
                assertTrue(designation.getElementReferencePropertyName().length() > 0);
                assertTrue(designation.getElementReferenceTemplate().length() > 0);
            }
        }
    }

    @Test
    public void elementsShouldBeHadElementConDesignation() {
        for (Element e : elements) {
            ConnectionDesignation designation = e.getConnectionDesignation();

            assertTrue(designation.getType().name().length() > 0);
            assertNotNull(ConnectionDesignation.Type.valueOf(designation.getType().name()));

            if (ConnectionDesignation.Type.REFERENCE.name().equals(designation.getType().name())) {
                assertTrue(designation.getConnectionReferencePropertyName().length() > 0);

                assertTrue(designation.getConnectionReferenceTemplate().length() > 0);
                assertTrue(designation.getConnectionReferenceTemplate().contains("."));
                assertTrue(designation.getConnectionReferenceTemplate().indexOf(".") > 0);
            }
        }
    }
}