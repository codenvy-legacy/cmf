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
package com.codenvy.editor.client.toolbar;

import com.codenvy.editor.api.editor.EditorState;
import com.codenvy.editor.api.editor.toolbar.AbstractToolbarPresenter;
import com.codenvy.editor.client.State;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import static com.codenvy.editor.client.State.CREATING_LINK1_SOURCE;
import static com.codenvy.editor.client.State.CREATING_SHAPE1;
import static com.codenvy.editor.client.State.CREATING_SHAPE2;

/**
 * @author Andrey Plotnikov
 */
public class ToolbarPresenter extends AbstractToolbarPresenter<State> implements ToolbarView.ActionDelegate {

    @Inject
    public ToolbarPresenter(ToolbarView view, @Assisted EditorState<State> editorState) {
        super(view, editorState);
    }

    /** {@inheritDoc} */
    @Override
    public void onShape1ButtonClicked() {
        state.setState(CREATING_SHAPE1);
    }

    /** {@inheritDoc} */
    @Override
    public void onShape2ButtonClicked() {
        state.setState(CREATING_SHAPE2);
    }

    /** {@inheritDoc} */
    @Override
    public void onLink1ButtonClicked() {
        state.setState(CREATING_LINK1_SOURCE);
    }

}