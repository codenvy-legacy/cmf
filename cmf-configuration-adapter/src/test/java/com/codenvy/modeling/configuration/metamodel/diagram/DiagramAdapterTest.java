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

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Dmitry Kuleshov
 * @author Valeriy Svydenko
 */
public class DiagramAdapterTest {

    private static final String DIAGRAM_CONFIGURATION  = "DiagramConfigurationImpl";
    private static final String ELEMENT                = "ElementImpl";
    private static final String PROPERTY               = "PropertyImpl";
    private static final String COMPONENT              = "ComponentImpl";
    private static final String CONNECTION             = "ConnectionImpl";
    private static final String DIAGRAM_GRAMMAR_TEST_I = "/DiagramGrammarTest_I";
    private static final String CONNECTIONS            = "connections";
    private static final String COMPONENTS             = "components";
    private static final String PROPERTIES             = "properties";

    private String      diagramConfiguration;
    private String[]    configurationElements;
    private Set<String> connectionType;
    private Set<String> connectionRelation;
    private Set<String> propertyType;

    @Before
    public void setUp() throws Exception {
        connectionType = new HashSet<>(Arrays.asList("DIRECTED", "NONDIRECTED", "POSITIONAL"));
        connectionRelation = new HashSet<>(Arrays.asList("SINGLE", "MULTIPLE"));
        propertyType = new HashSet<>(Arrays.asList("BOOLEAN", "INTEGER", "FLOAT", "STRING"));

        String configurationFile = getClass().getResource(DIAGRAM_GRAMMAR_TEST_I).getFile();
        DiagramConfigurationAdapter diagramConfigurationAdapter = new DiagramConfigurationAdapter(configurationFile);
        diagramConfiguration = diagramConfigurationAdapter.getConfiguration().toString();
        configurationElements = diagramConfiguration.split("\n");
    }


    @Test
    public void diagramConfigurationShouldBeContainedDiagramConfigurationString() {
        assertTrue(diagramConfiguration.contains(DIAGRAM_CONFIGURATION));
    }

    @Test
    public void diagramConfigurationShouldBeContainedElementString() {
        assertTrue(diagramConfiguration.contains(ELEMENT));
        assertTrue(diagramConfiguration.contains("elements"));
    }

    @Test
    public void elementShouldBeHadName() {
        String element = getDescriptionOfConfigurationElement(ELEMENT);

        int indexOfName = element.indexOf("name");
        String nameValue = element.substring(indexOfName);
        nameValue = nameValue.substring(nameValue.indexOf("=") + 2);
        nameValue = nameValue.substring(0, nameValue.indexOf('\''));

        assertTrue(indexOfName > -1);
        assertTrue(nameValue.length() > 0);
    }

    @Test
    public void diagramConfigurationShouldBeContainedPropertyString() {
        assertTrue(diagramConfiguration.contains(PROPERTIES));
    }

    @Test
    public void propertyShouldBeContainedThreeParameters() {
        String propertyImpl = getDescriptionOfConfigurationElement(PROPERTY);

        if (propertyImpl.length() > 0) {
            int indexOfName = propertyImpl.indexOf("name");
            String name = propertyImpl.substring(indexOfName);
            name = name.substring(name.indexOf("=") + 2);
            name = name.substring(0, name.indexOf('\''));

            int indexOfValue = propertyImpl.indexOf("value");
            String value = propertyImpl.substring(indexOfValue);
            value = value.substring(value.indexOf("=") + 2);
            value = value.substring(0, value.indexOf('\''));

            int indexOfType = propertyImpl.indexOf("type");
            String type = propertyImpl.substring(indexOfType);
            type = type.substring(type.indexOf("=") + 1);
            type = type.substring(0, type.indexOf('}'));

            assertTrue(indexOfName > -1);
            assertTrue(name.length() > 0);

            assertTrue(indexOfValue > -1);
            assertTrue(value.length() > 0);

            assertTrue(indexOfType > -1);
            assertTrue(propertyType.contains(type));
        } else {
            int indexOfProperties = diagramConfiguration.indexOf(PROPERTIES);
            String propValue =
                    diagramConfiguration.substring(indexOfProperties + PROPERTIES.length(), indexOfProperties + PROPERTIES.length() + 3);

            assertEquals("=[]", propValue);
        }
    }

    @Test
    public void diagramConfigurationShouldBeContainedConnectionString() {
        assertTrue(diagramConfiguration.contains(CONNECTIONS));
    }

    @Test
    public void connectionShouldBeContainedForParameters() {
        String connectionImpl = getDescriptionOfConfigurationElement(CONNECTION);

        if (connectionImpl.length() > 0) {
            int indexOfName = connectionImpl.indexOf("name");
            String name = connectionImpl.substring(indexOfName);
            name = name.substring(name.indexOf("=") + 2);
            name = name.substring(0, name.indexOf('\''));

            int indexOfDestination = connectionImpl.indexOf("destination");
            String destination = connectionImpl.substring(indexOfDestination);
            destination = destination.substring(destination.indexOf("=") + 2);
            destination = destination.substring(0, destination.indexOf('\''));

            int indexOfType = connectionImpl.indexOf("type");
            String type = connectionImpl.substring(indexOfType);
            type = type.substring(type.indexOf("=") + 1);
            type = type.substring(0, type.indexOf('}'));

            int indexOfRelation = connectionImpl.indexOf("relation");
            String relation = connectionImpl.substring(indexOfRelation);
            relation = relation.substring(relation.indexOf("=") + 1);
            relation = relation.substring(0, relation.indexOf(','));

            assertTrue(indexOfName > -1);
            assertTrue(name.length() > 0);

            assertTrue(indexOfDestination > -1);
            assertTrue(destination.length() > 0);

            assertTrue(indexOfType > -1);
            assertTrue(connectionType.contains(type));

            assertTrue(indexOfRelation > -1);
            assertTrue(connectionRelation.contains(relation));
        } else {
            int indexOfProperties = diagramConfiguration.indexOf(CONNECTIONS);
            String propValue =
                    diagramConfiguration.substring(indexOfProperties + CONNECTIONS.length(), indexOfProperties + CONNECTIONS.length() + 3);

            assertEquals("=[]", propValue);
        }
    }

    @Test
    public void diagramConfigurationShouldBeContainedComponentString() {
        assertTrue(diagramConfiguration.contains(COMPONENTS));
    }

    @Test
    public void componentShouldBeContainedOneParameters() {
        String componentImpl = getDescriptionOfConfigurationElement(COMPONENT);

        if (componentImpl.length() > 0) {
            int indexOfName = componentImpl.indexOf("name");
            String name = componentImpl.substring(indexOfName);
            name = name.substring(name.indexOf("=") + 2);
            name = name.substring(0, name.indexOf('\''));

            assertTrue(indexOfName > -1);
            assertTrue(name.length() > 0);
        } else {
            int indexOfProperties = diagramConfiguration.indexOf(COMPONENTS);
            String propValue =
                    diagramConfiguration.substring(indexOfProperties + COMPONENTS.length(), indexOfProperties + COMPONENTS.length() + 3);

            assertEquals("=[]", propValue);
        }
    }

    private String getDescriptionOfConfigurationElement(String element) {
        for (String configurationElement : configurationElements) {
            if (configurationElement.startsWith(element)) {
                return configurationElement;
            }
        }
        return "";
    }
}