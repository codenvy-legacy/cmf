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

import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GScrollPanel;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

import static java.lang.Boolean.TRUE;

/**
 * The implementation of {@link GScrollPanel}.
 *
 * @author Andrey Plotnikov
 */
public class GScrollPanelImpl extends AbstractGContainer<GScrollPanel> implements GScrollPanel {

    @Inject
    public GScrollPanelImpl() {
        super(SCROLL_PANEL_OPEN_TAG_FORMAT, SCROLL_PANEL_CLOSE_TAG_FORMAT);

        builder = this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public GScrollPanel alwaysShowScrollBars() {
        addParam(ALWAYS_SHOW_SCROLL_BARS_PARAM_NAME, TRUE.toString());
        return builder;
    }

}