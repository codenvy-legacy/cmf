/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codenvy.modeling.configuration.editor;

import com.google.inject.Inject;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Dmitry Kuleshov
 * @author Andrey Plotnikov
 */
public class EditorConfiguration {
    @NotNull
    @Valid
    private Toolbar   toolbar;
    @NotNull
    @Valid
    private Panel     panel;
    @NotNull
    @Valid
    private Workspace workspace;

    @Inject
    public EditorConfiguration() {
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    public Panel getPanel() {
        return panel;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }
}
