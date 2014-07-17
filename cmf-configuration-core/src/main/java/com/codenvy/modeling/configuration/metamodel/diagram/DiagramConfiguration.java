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

import com.google.inject.Inject;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Dmitry Kuleshov
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class DiagramConfiguration {
    @Size(min = 1)
    @Valid
    private Set<Element> elements;

    @NotNull
    private Element rootElement;

    @Size(min = 1)
    @Valid
    private Set<Connection> connections;

    @Size(min = 1)
    @Valid
    private Set<PropertyType> propertyTypes;

    @Inject
    public DiagramConfiguration() {
        elements = new LinkedHashSet<>();
        connections = new LinkedHashSet<>();
        propertyTypes = new LinkedHashSet<>();
    }

    public Element getRootElement() {
        return rootElement;
    }

    public void setRootElement(Element rootElement) {
        this.rootElement = rootElement;
    }

    public Set<Element> getElements() {
        return elements;
    }

    public void setElements(Set<Element> elements) {
        this.elements = elements;
    }

    public void addElement(Element element) {
        elements.add(element);
    }

    public Set<Connection> getConnections() {
        return connections;
    }

    public void setConnections(Set<Connection> connections) {
        this.connections = connections;
    }

    public void addConnection(Connection connection) {
        connections.add(connection);
    }

    public Set<PropertyType> getPropertyTypes() {
        return propertyTypes;
    }

    public void setPropertyTypes(Set<PropertyType> propertyTypes) {
        this.propertyTypes = propertyTypes;
    }

    public void addPropertyType(PropertyType propertyType) {
        propertyTypes.add(propertyType);
    }
}
