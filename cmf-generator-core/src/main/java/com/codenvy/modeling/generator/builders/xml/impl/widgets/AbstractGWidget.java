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

import com.codenvy.modeling.generator.builders.xml.api.widgets.GWidget;
import com.codenvy.modeling.generator.builders.xml.api.widgets.HasEnable;
import com.codenvy.modeling.generator.builders.xml.api.widgets.HasFocus;
import com.codenvy.modeling.generator.builders.xml.api.widgets.HasReadOnly;
import com.codenvy.modeling.generator.builders.xml.api.widgets.HasText;

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
public abstract class AbstractGWidget<T> implements GWidget<T>, HasEnable<T>, HasText<T>, HasFocus<T>, HasReadOnly<T> {

    private static final String PARAM_FORMAT = "%s=\"%s\"";
    private static final String STYLE_FORMAT = "{%s}";
    public static final  String OFFSET       = "    ";

    @Nullable
    private String       title;
    @Nullable
    private String       prefix;
    @Nullable
    private String       name;
    @Nonnull
    private List<String> styles;
    @Nonnull
    private List<String> addStyles;
    @Nullable
    private String       height;
    @Nullable
    private String       width;
    @Nullable
    private String       debugId;
    @Nullable
    private String       text;
    private boolean      enable;
    private boolean      visible;
    private boolean      focus;
    private boolean      readOnly;
    private int          offset;

    protected T      builder;
    protected String widgetFormat;

    /** Clean builder configuration */
    protected void clean() {
        text = null;
        title = null;
        prefix = null;

        enable = true;
        visible = true;
        focus = false;
        readOnly = false;
        offset = 0;

        styles = new ArrayList<>();
        addStyles = new ArrayList<>();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public T withText(@Nonnull String text) {
        this.text = text;
        return builder;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public T withTitle(@Nonnull String title) {
        this.title = title;
        return builder;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public T setDisable() {
        enable = false;
        return builder;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public T setInvisible() {
        visible = false;
        return builder;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public T setFocus() {
        focus = true;
        return builder;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public T withPrefix(@Nonnull String prefix) {
        this.prefix = prefix;
        return builder;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public T withName(@Nonnull String name) {
        this.name = name;
        return builder;
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Nonnull
    @Override
    public T withStyle(@Nonnull String style) {
        styles.add(style);
        return builder;
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Nonnull
    @Override
    public T withAddStyle(@Nonnull String style) {
        addStyles.add(style);
        return builder;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public T withHeight(@Nonnull String height) {
        this.height = height;
        return builder;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public T withWidth(@Nonnull String width) {
        this.width = width;
        return builder;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public T withDebugId(@Nonnull String debugId) {
        this.debugId = debugId;
        return builder;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public T setReadOnly() {
        readOnly = true;
        return builder;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public T withOffset(int offset) {
        this.offset = offset;
        return builder;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String build() throws IllegalStateException {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < offset; i++) {
            result.append(OFFSET);
        }

        result.append(String.format(widgetFormat, prefix, getWidget()));

        return result.toString();
    }

    /**
     * Build a widget. It provides an ability to work with any kind of GWT widget.
     *
     * @return {@link String} that contains general parameters
     */
    protected String getWidget() {
        if (prefix == null) {
            throw new IllegalStateException("The builder doesn't have any information about creating widget prefix. " +
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
        addStringParam(result, "text", text);

        if (!enable) {
            addStringParam(result, "enabled", "false");
        }

        if (!visible) {
            addStringParam(result, "visible", "false");
        }

        if (focus) {
            addStringParam(result, "focus", "true");
        }

        if (readOnly) {
            addStringParam(result, "readOnly", "true");
        }

        return result.toString();
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
    private void addStringParam(@Nonnull StringBuilder str, @Nonnull String name, @Nullable String value) {
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
    private void addListParam(@Nonnull StringBuilder str, @Nonnull String name, @Nonnull List<String> values) {
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