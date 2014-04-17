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
package com.codenvy.editor.api.editor;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

/**
 * The implementation of {@link EditorView}.
 *
 * @author Andrey Plotnikov
 */
public class EditorViewImpl extends EditorView {

    interface EditorViewImplUiBinder extends UiBinder<Widget, EditorViewImpl> {
    }

    @UiField
    SimpleLayoutPanel toolbar;
    @UiField
    SimpleLayoutPanel propertiesPanel;
    @UiField
    SimpleLayoutPanel workspace;

    @Inject
    public EditorViewImpl(EditorViewImplUiBinder ourUiBinder) {
        widget = ourUiBinder.createAndBindUi(this);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public AcceptsOneWidget getToolbarPanel() {
        return toolbar;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public AcceptsOneWidget getPropertiesPanel() {
        return propertiesPanel;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public AcceptsOneWidget getWorkspacePanel() {
        return workspace;
    }

}