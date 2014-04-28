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

import com.codenvy.modeling.configuration.metamodel.diagram.Property;

import javax.annotation.Nonnull;

/**
 * @author Dmitry Kuleshov
 */
public class PropertyImpl implements Property {

    @Nonnull
    private String name;

    @Nonnull
    private String value;

    @Nonnull
    private Type type;

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    @Nonnull
    @Override
    public String getValue() {
        return value;
    }

    @Nonnull
    @Override
    public Type getType() {
        return type;
    }

    public void setName(@Nonnull String name) {
        this.name = name;
    }

    public void setValue(@Nonnull String value) {
        this.value = value;
    }

    public void setType(@Nonnull Type type) {
        this.type = type;
    }
}
