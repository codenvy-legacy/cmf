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

package com.codenvy.modeling.generator.builders.xml.impl.widgets.containers;

import com.codenvy.modeling.generator.builders.xml.api.widgets.GWidget;
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GContainer;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.AbstractGWidget;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * The abstract presentation of GWT container builder. It contains general fields and method which need for all GWT container builders.
 * The main idea of this class is to simplify creating an implementation of GWT container builders.
 *
 * @author Andrey Plotnikov
 */
public class AbstractGContainer<T> extends AbstractGWidget<T> implements GContainer<T> {

    protected String                           closeTagFormat;
    private   List<GWidget<? extends GWidget>> widgets;

    /** {@inheritDoc} */
    @Override
    protected void clean() {
        super.clean();
        widgets = new ArrayList<>();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public T withWidget(@Nonnull GWidget<? extends GWidget> widget) {
        widgets.add(widget);
        return builder;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String build() throws IllegalStateException {
        StringBuilder result = new StringBuilder();

        result.append(super.build()).append('\n');

        for (GWidget<? extends GWidget> widget : widgets) {
            result.append(widget.withOffset(offset + 1).build()).append('\n');
        }

        result.append(getOffset(offset)).append(String.format(closeTagFormat, prefix));

        return result.toString();
    }

}