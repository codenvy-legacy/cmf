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

package com.codenvy.modeling.configuration.impl.metamodel.diagram;

import com.codenvy.modeling.configuration.metamodel.diagram.Component;
import com.codenvy.modeling.configuration.metamodel.diagram.Connection;
import com.codenvy.modeling.configuration.metamodel.diagram.Element;
import com.codenvy.modeling.configuration.metamodel.diagram.Property;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Dmitry Kuleshov
 */
public class ElementImpl implements Element {

    private String name;

    private List<Property> properties = new LinkedList<>();

    private List<Connection> connections = new LinkedList<>();

    private List<Component> components = new LinkedList<>();

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    @Nonnull
    @Override
    public List<Property> getElementProperties() {
        return properties;
    }

    @Nonnull
    @Override
    public List<Connection> geElementConnections() {
        return connections;
    }

    @Nonnull
    @Override
    public List<Component> getElementComponents() {
        return components;
    }

    public void setName(@Nonnull String name) {
        this.name = name;
    }

    public void addProperty(@Nonnull Property property) {
        properties.add(property);
    }

    public void addConnection(@Nonnull Connection connection) {
        connections.add(connection);
    }

    public void addComponent(@Nonnull Component component) {
        components.add(component);
    }

    @Override
    public String toString() {
        return "\nElementImpl{" +
                "name='" + name + '\'' +
                ", properties=" + properties +
                ", connections=" + connections +
                ", components=" + components +
                '}';
    }
}
