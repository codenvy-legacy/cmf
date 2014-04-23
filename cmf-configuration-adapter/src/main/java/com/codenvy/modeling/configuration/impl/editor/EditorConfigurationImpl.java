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

package com.codenvy.modeling.configuration.impl.editor;

import com.codenvy.modeling.configuration.editor.EditorConfiguration;
import com.codenvy.modeling.configuration.editor.Panel;
import com.codenvy.modeling.configuration.editor.Toolbar;
import com.codenvy.modeling.configuration.editor.Workspace;

import javax.annotation.Nonnull;

/**
 * @author Dmitry Kuleshov
 */
public class EditorConfigurationImpl implements EditorConfiguration {
    private Toolbar toolbar;
    private Panel panel;
    private Workspace workspace;

    @Nonnull
    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Nonnull
    @Override
    public Panel getPanel() {
        return panel;
    }

    @Nonnull
    @Override
    public Workspace getWorkspace() {
        return workspace;
    }

    public void setToolbar(@Nonnull Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    public void setPanel(@Nonnull Panel panel) {
        this.panel = panel;
    }

    public void setWorkspace(@Nonnull Workspace workspace) {
        this.workspace = workspace;
    }

    @Override
    public String toString() {
        return "\nEditorConfigurationImpl{" +
                "toolbar=" + toolbar +
                ", panel=" + panel +
                ", workspace=" + workspace +
                '}';
    }
}
