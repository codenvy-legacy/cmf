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

package com.codenvy.modeling.configuration.metamodel.diagram;

import com.codenvy.modeling.adapter.metamodel.diagram.DiagramConfigurationAdapter;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * @author Dmitry Kuleshov
 * @author Valeriy Svydenko
 */
public class DiagramAdapterTest {

    private static final String DIAGRAM_GRAMMAR_TEST_I = "/DiagramGrammarTest_I";

    private static List<Element> elements;

    private static List<Connection> connections;

    @BeforeClass
    public static void setUp() throws Exception {
        String configurationFile = DiagramAdapterTest.class.getResource(DIAGRAM_GRAMMAR_TEST_I).getFile();
        DiagramConfigurationAdapter diagramConfigurationAdapter = new DiagramConfigurationAdapter(configurationFile);
        DiagramConfiguration configuration = diagramConfigurationAdapter.getConfiguration();

        elements = configuration.getElements();
        connections = configuration.getConnections();
    }

    @Test
    public void allElementsMustHaveNames() {
        for (Element e : elements) {
            assertFalse(e.getName().isEmpty());
        }
    }

    @Test
    public void allPropertiesMustHaveNames() {
        for (Element e : elements) {
            if (e.getProperties().size() > 0) {
                for (Property prop : e.getProperties()) {
                    assertFalse(prop.getName().isEmpty());
                }
            }
        }
    }

    @Test
    public void allPropertiesMustHaveValue() {
        for (Element e : elements) {
            if (e.getProperties().size() > 0) {
                for (Property prop : e.getProperties()) {
                    assertFalse(prop.getValue().isEmpty());
                }
            }
        }
    }

    @Test
    public void allPropertiesMustHaveType() {
        for (Element e : elements) {
            if (e.getProperties().size() > 0) {
                for (Property prop : e.getProperties()) {
                    assertFalse(prop.getType().name().isEmpty());
                    assertNotNull(Property.Type.valueOf(prop.getType().name()));
                }
            }
        }
    }

    @Test
    public void allComponentsMustHaveNames() {
        for (Element e : elements) {
            if (e.getComponents().size() > 0) {
                for (Component component : e.getComponents()) {
                    assertFalse(component.getName().isEmpty());
                }
            }
        }
    }

    @Test
    public void allRelationsMustHaveValue() {
        for (Element e : elements) {
            if (e.getRelation() != null) {
                assertNotNull(Element.Relation.valueOf(e.getRelation().name()));
            }
        }
    }

    @Test
    public void allConnectionsMustHaveNames() {
        for (Connection connection : connections) {
            assertFalse(connection.getName().isEmpty());
        }
    }

    @Test
    public void allConnectionsMustHaveType() {
        for (Connection connection : connections) {
            assertNotNull(connection.getType());
        }
    }

    @Test
    public void allConnectionsMustHavePairs() {
        for (Connection connection : connections) {
            assertFalse(connection.getPairs().isEmpty());
        }
    }

    @Test
    public void allConnectionPairsMustHaveConnectionStartAndConnectionFinish() {
        for (Connection connection : connections) {
            for (Pair pair : connection.getPairs()) {
                assertFalse(pair.getFinish().isEmpty());
                assertFalse(pair.getStart().isEmpty());
            }
        }
    }
}