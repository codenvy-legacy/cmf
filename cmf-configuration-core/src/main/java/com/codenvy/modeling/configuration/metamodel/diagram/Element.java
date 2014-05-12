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

import com.codenvy.modeling.configuration.validation.constraints.ConfigurationConstraintsValidator;
import com.codenvy.modeling.configuration.validation.integrity.MustBeContained;
import com.codenvy.modeling.configuration.validation.integrity.MustContain;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Dmitry Kuleshov
 * @author Valeriy Svydenko
 */
public class Element {
    @Pattern(regexp = ConfigurationConstraintsValidator.SIMPLE_TEXT)
    private String   name;
    @NotNull
    private Relation relation;
    @Size(min = 1)
    @Valid
    private Set<Property>  properties = new LinkedHashSet<>();
    @Size(min = 1)
    @Valid
    private Set<Component> components = new LinkedHashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    public Set<Property> getProperties() {
        return properties;
    }

    public void setProperties(Set<Property> properties) {
        this.properties = properties;
    }

    public Set<Component> getComponents() {
        return components;
    }

    public void setComponents(Set<Component> components) {
        this.components = components;
    }

    public void addProperty(Property property) {
        properties.add(property);
    }

    public void addComponent(Component component) {
        components.add(component);
    }

    public enum Relation {
        SINGLE, MULTIPLE
    }

}
