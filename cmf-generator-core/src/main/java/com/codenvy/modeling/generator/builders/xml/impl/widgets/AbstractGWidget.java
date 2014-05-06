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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.modeling.generator.builders.xml.api.UIXmlBuilder.OFFSET;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * The abstract presentation of GWT widget builder. It contains general fields and method which need for all GWT widget builders.
 * The main idea of this class is to simplify creating an implementation of GWT widget builders.
 *
 * @author Andrey Plotnikov
 */
public abstract class AbstractGWidget<T> implements GWidget<T>, HasEnable<T>, HasText<T>, HasFocus<T>, HasReadOnly<T> {

    @Nullable
    protected     String              prefix;
    @Nonnull
    private       List<String>        styles;
    @Nonnull
    private       List<String>        addStyles;
    protected     int                 offset;
    @Nonnull
    private       Map<String, String> params;
    @Nonnull
    protected     T                   builder;
    @Nonnull
    private final String              widgetFormat;

    protected AbstractGWidget(@Nonnull String widgetFormat) {
        this.widgetFormat = widgetFormat;

        clean();
    }

    /** Clean builder configuration */
    protected void clean() {
        prefix = null;
        offset = 0;

        params = new LinkedHashMap<>();
        styles = new ArrayList<>();
        addStyles = new ArrayList<>();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public T withText(@Nonnull String text) {
        addParam(TEXT_PARAM_NAME, text);
        return builder;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public T withTitle(@Nonnull String title) {
        addParam(TITLE_PARAM_NAME, title);
        return builder;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public T setDisable() {
        addParam(ENABLED_PARAM_NAME, FALSE.toString());
        return builder;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public T setInvisible() {
        addParam(VISIBLE_PARAM_NAME, FALSE.toString());
        return builder;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public T setFocus() {
        addParam(FOCUS_PARAM_NAME, TRUE.toString());
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
        addParam(NAME_PARAM_NAME, name);
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
        addParam(HEIGHT_PARAM_NAME, height);
        return builder;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public T withWidth(@Nonnull String width) {
        addParam(WIDTH_PARAM_NAME, width);
        return builder;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public T withDebugId(@Nonnull String debugId) {
        addParam(DEBUG_ID_PARAM_NAME, debugId);
        return builder;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public T setReadOnly() {
        addParam(READ_ONLY_PARAM_NAME, TRUE.toString());
        return builder;
    }

    /**
     * Add parameter to widget configuration.
     *
     * @param name
     *         parameter name
     * @param value
     *         parameter value
     */
    protected void addParam(@Nonnull String name, @Nonnull String value) {
        params.put(name, value);
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
        return getOffset(offset) + String.format(widgetFormat, prefix, getWidget());
    }

    /**
     * Return offset for current widget.
     *
     * @param offset
     *         count of offset
     * @return offset for current widget
     */
    @Nonnull
    protected String getOffset(int offset) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < offset; i++) {
            result.append(OFFSET);
        }

        return result.toString();
    }

    /**
     * Build a widget. It provides an ability to work with any kind of GWT widget.
     *
     * @return {@link String} that contains general parameters
     */
    private String getWidget() {
        if (prefix == null) {
            throw new IllegalStateException("The builder has no information about creating widget prefix. " +
                                            "You should execute withPrefix method and then this one.");
        }

        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> param : params.entrySet()) {
            result.append(' ').append(String.format(PARAM_FORMAT, param.getKey(), param.getValue()));
        }

        addListParam(result, "styleName", styles);
        addListParam(result, "addStyleNames", addStyles);

        return result.toString();
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