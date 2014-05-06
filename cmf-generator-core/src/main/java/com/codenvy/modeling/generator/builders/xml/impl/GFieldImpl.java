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

package com.codenvy.modeling.generator.builders.xml.impl;

import com.codenvy.modeling.generator.builders.xml.api.GField;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The implementation of {@link GField}.
 *
 * @author Andrey Plotnikov
 */
public class GFieldImpl implements GField {

    @Nullable
    private String name;
    @Nullable
    private Class  type;
    @Nullable
    private String typeText;

    @Inject
    public GFieldImpl() {
        // empty constructor
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public GField withName(@Nonnull String name) {
        this.name = name;
        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public GField withType(@Nonnull Class type) {
        this.type = type;
        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public GField withType(@Nonnull String type) {
        typeText = type;
        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String build() throws IllegalStateException {
        if (name == null) {
            throw new IllegalStateException("The builder has no information about creating field name. " +
                                            "You should execute withName method and then this one.");
        }

        if (type == null && typeText == null) {
            throw new IllegalStateException("The builder has no information about creating field type. " +
                                            "You should execute withType method and then this one.");
        }

        String content = String.format(FIELD_FORMAT, name, type != null ? type.getName() : typeText);

        // Clean builder configuration
        name = null;
        type = null;
        typeText = null;

        return content;
    }

}