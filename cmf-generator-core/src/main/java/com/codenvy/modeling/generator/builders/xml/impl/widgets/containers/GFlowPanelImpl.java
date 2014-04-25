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

import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GFlowPanel;
import com.google.inject.Inject;

/**
 * The implementation of {@link GFlowPanel}.
 *
 * @author Andrey Plotnikov
 */
public class GFlowPanelImpl extends AbstractGContainer<GFlowPanel> implements GFlowPanel {

    @Inject
    public GFlowPanelImpl() {
        super(FLOW_PANEL_OPEN_TAG_FORMAT, FLOW_PANEL_CLOSE_TAG_FORMAT);

        builder = this;
    }

}