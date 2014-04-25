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
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GSimpleLayoutPanel;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The implementation of {@link GSimpleLayoutPanel}.
 *
 * @author Andrey Plotnikov
 */
public class GSimpleLayoutPanelImpl extends AbstractGContainer<GSimpleLayoutPanel> implements GSimpleLayoutPanel {

    @Nullable
    private GWidget<? extends GWidget> widget;

    @Inject
    public GSimpleLayoutPanelImpl() {
        super(SIMPLE_LAYOUT_PANEL_OPEN_TAG_FORMAT, SIMPLE_LAYOUT_PANEL_CLOSE_TAG_FORMAT);

        builder = this;
    }

    /** {@inheritDoc} */
    @Override
    protected void clean() {
        super.clean();
        widget = null;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public GSimpleLayoutPanel withWidget(@Nonnull GWidget<? extends GWidget> widget) {
        this.widget = widget;
        return builder;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String build() throws IllegalStateException {
        if (widget != null) {
            super.withWidget(widget);
        }

        return super.build();
    }

}