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
package com.codenvy.editor.client;

import com.codenvy.editor.api.editor.AbstractEditor;
import com.codenvy.editor.api.editor.EditorState;
import com.codenvy.editor.api.editor.EditorView;
import com.codenvy.editor.api.editor.SelectionManager;
import com.codenvy.editor.api.editor.propertiespanel.PropertiesPanelManager;
import com.codenvy.editor.api.editor.propertiespanel.empty.EmptyPropertiesPanelPresenter;
import com.codenvy.editor.client.elements.link1.Link1;
import com.codenvy.editor.client.elements.shape1.Shape1;
import com.codenvy.editor.client.elements.shape2.Shape2;
import com.codenvy.editor.client.inject.EditorFactory;
import com.codenvy.editor.client.propertiespanel.link1.Link1PropertiesPanelPresenter;
import com.codenvy.editor.client.propertiespanel.shape1.Shape1PropertiesPanelPresenter;
import com.codenvy.editor.client.propertiespanel.shape2.Shape2PropertiesPanelPresenter;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

/**
 * @author Andrey Plotnikov
 */
public class EditorPresenter extends AbstractEditor implements EditorView.ActionDelegate {

    @Inject
    public EditorPresenter(EditorView view,
                           EditorFactory editorFactory,
                           SelectionManager selectionManager,
                           Shape1PropertiesPanelPresenter shape1PropertiesPanelPresenter,
                           Shape2PropertiesPanelPresenter shape2PropertiesPanelPresenter,
                           Link1PropertiesPanelPresenter link1PropertiesPanelPresenter,
                           EmptyPropertiesPanelPresenter emptyPropertiesPanelPresenter) {
        super(view);

        EditorState<State> state = new EditorState<>(State.CREATING_NOTING);

        this.workspace = editorFactory.createWorkspace(state, selectionManager);
        this.toolbar = editorFactory.createToolbar(state);

        PropertiesPanelManager propertiesPanelManager = editorFactory.createPropertiesPanelManager(view.getPropertiesPanel());
        propertiesPanelManager.register(Shape1.class, shape1PropertiesPanelPresenter);
        propertiesPanelManager.register(Shape2.class, shape2PropertiesPanelPresenter);
        propertiesPanelManager.register(Link1.class, link1PropertiesPanelPresenter);
        propertiesPanelManager.register(null, emptyPropertiesPanelPresenter);

        selectionManager.addListener(propertiesPanelManager);
        workspace.addListener(this);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String serialize() {
        return workspace.getMainElement().serialize();
    }

    /** {@inheritDoc} */
    @Override
    public void deserialize(@Nonnull String content) {
        // TODO add deserialize
    }

}