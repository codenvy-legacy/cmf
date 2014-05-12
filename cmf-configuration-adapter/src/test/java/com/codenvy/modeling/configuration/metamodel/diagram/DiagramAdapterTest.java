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

import com.codenvy.modeling.adapter.metamodel.diagram.DiagramConfigurationAdapterImpl;
import com.codenvy.modeling.configuration.parser.metamodel.diagram.DiagramConfigurationAdapterListener;
import com.google.inject.Provider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Dmitry Kuleshov
 * @author Valeriy Svydenko
 * @author Andrey Plotnikov
 */
@RunWith(Parameterized.class)
public class DiagramAdapterTest {

    private static final String DIAGRAM_GRAMMAR_TEST_I  = "/DiagramGrammarTest_I";
    private static final String DIAGRAM_GRAMMAR_TEST_II = "/DiagramGrammarTest_II";

    private static Set<Element>    elements;
    private static Set<Connection> connections;

    private int numberOfGrammar;

    @SuppressWarnings("unchecked")
    public DiagramAdapterTest(String grammar, int number) throws Exception {
        numberOfGrammar = number;

        Provider<DiagramConfigurationAdapterListener> diagramConfigurationAdapterListenerProvider = mock(Provider.class);
        when(diagramConfigurationAdapterListenerProvider.get())
                .thenReturn(new DiagramConfigurationAdapterListener(new DiagramConfiguration()));

        String configurationFile = DiagramAdapterTest.class.getResource(grammar).getFile();
        DiagramConfigurationAdapterImpl diagramConfigurationAdapter =
                new DiagramConfigurationAdapterImpl(diagramConfigurationAdapterListenerProvider, configurationFile);
        DiagramConfiguration configuration = diagramConfigurationAdapter.getConfiguration();

        elements = configuration.getElements();
        connections = configuration.getConnections();
    }

    @Parameters
    public static Iterable<Object[]> dataOfConfigurations() {
        return Arrays.asList(new Object[][]{{DIAGRAM_GRAMMAR_TEST_I, 1}, {DIAGRAM_GRAMMAR_TEST_II, 2}});
    }

    @Test
    public void allElementsMustHaveNames() {
        for (Element e : elements) {
            assertFalse(e.getName().isEmpty());
        }
    }

    @Test
    public void elementsNamesShouldBeDefined() {
        for (Element e : elements) {
            assertEquals("testElementName", e.getName());
        }
    }

    @Test
    public void propertiesDoesNotExist() {
        if (numberOfGrammar == 2) {
            for (Element e : elements) {
                assertTrue(e.getProperties().isEmpty());
            }
        }
    }

    @Test
    public void allPropertiesMustHaveNames() {
        if (numberOfGrammar == 1) {
            for (Element e : elements) {
                for (Property prop : e.getProperties()) {
                    assertFalse(prop.getName().isEmpty());
                }
            }
        }
    }

    @Test
    public void propertiesNamesShouldBeDefined() {
        for (Element e : elements) {
            if (!e.getProperties().isEmpty()) {
                for (Property prop : e.getProperties()) {
                    assertEquals("testPropertyName", prop.getName());
                }
            }
        }
    }

    @Test
    public void allPropertiesMustHaveValue() {
        for (Element e : elements) {
            if (!e.getProperties().isEmpty()) {
                for (Property prop : e.getProperties()) {
                    assertFalse(prop.getValue().isEmpty());
                }
            }
        }
    }

    @Test
    public void propertiesValueShouldBeDefined() {
        for (Element e : elements) {
            if (!e.getProperties().isEmpty()) {
                for (Property prop : e.getProperties()) {
                    assertEquals("testPropertyValue", prop.getValue());
                }
            }
        }
    }

    @Test
    public void allPropertiesMustHaveType() {
        for (Element e : elements) {
            if (!e.getProperties().isEmpty()) {
                for (Property prop : e.getProperties()) {
                    assertFalse(prop.getType().name().isEmpty());
                    assertNotNull(Property.Type.valueOf(prop.getType().name()));
                }
            }
        }
    }

    @Test
    public void propertiesTypeShouldBeDefined() {
        for (Element e : elements) {
            if (!e.getProperties().isEmpty()) {
                for (Property prop : e.getProperties()) {
                    assertEquals("INTEGER", prop.getType().name());
                }
            }
        }
    }

    @Test
    public void componentsDoesNotExist() {
        if (numberOfGrammar == 2) {
            for (Element e : elements) {
                assertTrue(e.getComponents().isEmpty());
            }
        }
    }

    @Test
    public void allComponentsMustHaveNames() {
        if (numberOfGrammar == 1) {
            for (Element e : elements) {
                for (Component component : e.getComponents()) {
                    assertFalse(component.getName().isEmpty());
                }
            }
        }
    }

    @Test
    public void componentsNamesShouldBeDefined() {
        for (Element e : elements) {
            if (!e.getComponents().isEmpty() & numberOfGrammar == 2) {
                for (Component component : e.getComponents()) {
                    assertEquals("test", component.getName());
                }
            }
        }
    }

    @Test
    public void allRelationsMustHaveValue() {
        for (Element e : elements) {
            if (numberOfGrammar == 1) {
                assertNotNull(Element.Relation.valueOf(e.getRelation().name()));
            }
        }
    }

    @Test
    public void relationsValuesShouldBeDefined() {
        for (Element e : elements) {
            if (numberOfGrammar == 1) {
                assertEquals("SINGLE", e.getRelation().name());
            }
        }
    }

    @Test
    public void relationDoesNotExist() {
        if (numberOfGrammar == 2) {
            for (Element e : elements) {
                assertNull(e.getRelation());
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
    public void connectionsNamesShouldBeDefined() {
        for (Connection connection : connections) {
            assertEquals("testConnectionName", connection.getName());
        }
    }

    @Test
    public void allConnectionsMustHaveType() {
        for (Connection connection : connections) {
            assertNotNull(connection.getType());
        }
    }

    @Test
    public void connectionsTypesShouldBeDefined() {
        for (Connection connection : connections) {
            assertEquals("DIRECTED", connection.getType().name());
        }
    }

    @Test
    public void allConnectionsMustHavePairs() {
        for (Connection connection : connections) {
            assertFalse(connection.getPairs().isEmpty());
        }
    }

    @Test
    public void connectionsPairsShouldBeDefined() {
        if (numberOfGrammar == 2) {
            for (Connection connection : connections) {
                for (Pair pair : connection.getPairs()) {
                    assertEquals("finish", pair.getFinish());
                    assertEquals("start", pair.getStart());
                }
            }
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