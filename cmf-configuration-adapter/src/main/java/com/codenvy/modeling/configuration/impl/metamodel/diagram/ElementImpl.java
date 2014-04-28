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
import com.codenvy.modeling.configuration.metamodel.diagram.Element;
import com.codenvy.modeling.configuration.metamodel.diagram.Property;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Dmitry Kuleshov
 */
public class ElementImpl implements Element {

    @Nonnull
    private String name;

    @Nonnull
    private Relation relation;

    @Nonnull
    private List<Property> properties = new LinkedList<>();

    @Nonnull
    private List<Component> components = new LinkedList<>();

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    @Nonnull
    @Override
    public Relation getRelation() {
        return relation;
    }

    @Nonnull
    @Override
    public List<Property> getProperties() {
        return properties;
    }

    @Nonnull
    @Override
    public List<Component> getComponents() {
        return components;
    }

    public void setName(@Nonnull String name) {
        this.name = name;
    }

    public void setRelation(@Nonnull Relation relation) {
        this.relation = relation;
    }

    public void addProperty(@Nonnull Property property) {
        properties.add(property);
    }

    public void addComponent(@Nonnull Component component) {
        components.add(component);
    }
}
