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

import com.codenvy.modeling.generator.builders.xml.api.widgets.GButton;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The implementation of {@link GButton}.
 *
 * @author Andrey Plotnikov
 */
public class GButtonImpl extends AbstractGWidget implements GButton {

    private static final String BUTTON_FORMAT = "<%s:Button%s/>";

    @Nullable
    private String  text;
    private boolean enable;
    private boolean visible;
    private boolean focus;

    @Inject
    public GButtonImpl() {
        clean();
    }

    /** {@inheritDoc} */
    @Override
    protected void clean() {
        super.clean();

        text = null;
        enable = true;
        visible = true;
        focus = false;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public GButton withText(@Nonnull String text) {
        this.text = text;
        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public GButton withTitle(@Nonnull String title) {
        this.title = title;
        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public GButton setDisable() {
        enable = false;
        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public GButton setInvisible() {
        visible = false;
        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public GButton setFocus() {
        focus = true;
        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public GButton withPrefix(@Nonnull String prefix) {
        this.prefix = prefix;
        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public GButton withName(@Nonnull String name) {
        this.name = name;
        return this;
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Nonnull
    @Override
    public GButton withStyle(@Nonnull String style) {
        styles.add(style);
        return this;
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Nonnull
    @Override
    public GButton withAddStyle(@Nonnull String style) {
        addStyles.add(style);
        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public GButton withHeight(@Nonnull String height) {
        this.height = height;
        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public GButton withWidth(@Nonnull String width) {
        this.width = width;
        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public GButton withDebugId(@Nonnull String debugId) {
        this.debugId = debugId;
        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String build() throws IllegalStateException {
        StringBuilder content = generalBuild();

        addStringParam(content, "text", text);
        if (!enable) {
            addStringParam(content, "enabled", "false");
        }

        if (!visible) {
            addStringParam(content, "visible", "false");
        }

        if (focus) {
            addStringParam(content, "focus", "true");
        }

        return String.format(BUTTON_FORMAT, prefix, content);
    }

}