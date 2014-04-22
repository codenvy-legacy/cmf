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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The implementation of {@link GField}.
 *
 * @author Andrey Plotnikov
 */
public class GFieldImpl implements GField {

    private static final String FIELD_FORMAT = "    <ui:with field='%s' type='%s'/>";

    @Nullable
    private String name;
    @Nullable
    private Class  type;

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
    public String build() throws IllegalStateException {
        if (name == null) {
            throw new IllegalStateException("The builder doesn't have any information about creating field name. " +
                                            "You should execute withName method and then this one.");
        }

        if (type == null) {
            throw new IllegalStateException("The builder doesn't have any information about creating field type. " +
                                            "You should execute withType method and then this one.");
        }

        return String.format(FIELD_FORMAT, name, type.getName());
    }

}