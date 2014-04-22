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

package com.codenvy.modeling.generator.builders.xml.impl.widgets;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The abstract presentation of GWT widget builder. It contains general fields and method which need for all GWT widget builders.
 * The main idea of this class is to simplify creating an implementation of GWT widget builders.
 *
 * @author Andrey Plotnikov
 */
public abstract class AbstractGWidget {

    protected static final String PARAM_FORMAT = "%s=\"%s\"";
    protected static final String STYLE_FORMAT = "{%s}";

    @Nullable
    protected String       title;
    @Nullable
    protected String       prefix;
    @Nullable
    protected String       name;
    @Nonnull
    protected List<String> styles;
    @Nonnull
    protected List<String> addStyles;
    @Nullable
    protected String       height;
    @Nullable
    protected String       width;
    @Nullable
    protected String       debugId;

    /** Clean builder configuration */
    protected void clean() {
        title = null;
        prefix = null;
        styles = new ArrayList<>();
        addStyles = new ArrayList<>();
    }

    /**
     * Build a general part of widget. It's the same for all widget builders. In order to create own builder for some GWT widget one should
     * use it.
     *
     * @return {@link StringBuilder} that contains general parameters
     */
    protected StringBuilder generalBuild() {
        if (prefix == null) {
            throw new IllegalStateException("The builder doesn't have any information about creating button prefix. " +
                                            "You should execute withPrefix method and then this one.");
        }

        StringBuilder result = new StringBuilder();

        addStringParam(result, "title", title);
        addStringParam(result, "ui:field", name);
        addStringParam(result, "height", height);
        addStringParam(result, "width", width);
        addStringParam(result, "debugId", debugId);
        addListParam(result, "styleName", styles);
        addListParam(result, "addStyleNames", addStyles);

        return result;
    }

    /**
     * Add string parameter to GWT widget XML representation.
     *
     * @param str
     *         builder that will contain a given parameter
     * @param name
     *         parameter name
     * @param value
     *         parameter value
     */
    protected void addStringParam(@Nonnull StringBuilder str, @Nonnull String name, @Nullable String value) {
        if (value != null) {
            str.append(' ').append(String.format(PARAM_FORMAT, name, value));
        }
    }

    /**
     * Add parameter with multi value to GWT widget XML representation.
     *
     * @param str
     *         builder that will contain a given parameter
     * @param name
     *         parameter name
     * @param values
     *         parameter values
     */
    protected void addListParam(@Nonnull StringBuilder str, @Nonnull String name, @Nonnull List<String> values) {
        if (!values.isEmpty()) {
            StringBuilder content = new StringBuilder();

            for (Iterator<String> iterator = values.iterator(); iterator.hasNext(); ) {
                String value = iterator.next();

                content.append(String.format(STYLE_FORMAT, value));

                if (iterator.hasNext()) {
                    content.append(' ');
                }
            }

            str.append(' ').append(String.format(PARAM_FORMAT, name, content));
        }
    }

}