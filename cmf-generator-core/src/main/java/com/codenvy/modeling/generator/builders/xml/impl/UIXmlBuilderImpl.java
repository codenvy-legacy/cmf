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
import com.codenvy.modeling.generator.builders.xml.api.GStyle;
import com.codenvy.modeling.generator.builders.xml.api.UIXmlBuilder;
import com.codenvy.modeling.generator.builders.xml.api.widgets.GWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * The implementation of {@link UIXmlBuilder}.
 *
 * @author Andrey Plotnikov
 */
public class UIXmlBuilderImpl implements UIXmlBuilder {

    @Nonnull
    private List<GField>               fields;
    @Nonnull
    private Map<String, String>        xmlns;
    @Nullable
    private GStyle                     style;
    @Nullable
    private GWidget<? extends GWidget> widget;

    @Inject
    public UIXmlBuilderImpl() {
        /*
         * Setting formatter locale to US just to have XML compliant format for floating point numbers.
         * E.g. some locales uses comma instead of dot to separate integer and fractional parts.
         */
        Locale.setDefault(Locale.US);

        clean();
    }

    /** Clean builder configuration */
    private void clean() {
        style = null;
        widget = null;
        fields = new ArrayList<>();

        xmlns = new LinkedHashMap<>();
        xmlns.put("ui", "urn:ui:com.google.gwt.uibinder");
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public UIXmlBuilder withXmlns(@Nonnull String prefix, @Nonnull String importPath) {
        xmlns.put(prefix, importPath);
        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public UIXmlBuilder withField(@Nonnull GField field) {
        fields.add(field);
        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public UIXmlBuilder withStyle(@Nonnull GStyle style) {
        this.style = style;
        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public UIXmlBuilder setWidget(@Nonnull GWidget<? extends GWidget> widget) {
        this.widget = widget;
        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String build() throws IllegalStateException {
        StringBuilder xmlns = new StringBuilder();

        for (Map.Entry<String, String> xmln : this.xmlns.entrySet()) {
            xmlns.append(String.format(XMLNS_FORMAT, xmln.getKey(), xmln.getValue()));
        }

        StringBuilder content = new StringBuilder();

        for (GField field : fields) {
            content.append(field.build()).append('\n');
        }

        if (style != null) {
            content.append(style.build()).append('\n');
        }

        if (widget != null) {
            content.append(widget.withOffset(1).build()).append('\n');
        }

        clean();

        return String.format(UI_XML_FORMAT, xmlns, content);
    }

}