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
package com.codenvy.editor.client.inject;

import com.codenvy.editor.api.editor.EditorState;
import com.codenvy.editor.api.editor.SelectionManager;
import com.codenvy.editor.api.editor.propertiespanel.PropertiesPanelManager;
import com.codenvy.editor.client.State;
import com.codenvy.editor.client.toolbar.ToolbarPresenter;
import com.codenvy.editor.client.workspace.WorkspacePresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import javax.annotation.Nonnull;

/**
 * @author Andrey Plotnikov
 */
public interface EditorFactory {

    ToolbarPresenter createToolbar(@Nonnull EditorState<State> editorState);

    WorkspacePresenter createWorkspace(@Nonnull EditorState<State> editorState, @Nonnull SelectionManager selectionManager);

    PropertiesPanelManager createPropertiesPanelManager(@Nonnull AcceptsOneWidget container);

}