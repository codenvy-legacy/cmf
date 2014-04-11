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

import com.codenvy.editor.api.editor.toolbar.AbstractToolbarPresenter;
import com.codenvy.editor.api.editor.workspace.AbstractWorkspacePresenter;
import com.codenvy.editor.api.mvp.AbstractPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import javax.annotation.Nonnull;


/**
 * @author Andrey Plotnikov
 */
public abstract class AbstractEditor<T> extends AbstractPresenter {

    protected AbstractWorkspacePresenter<T> workspace;
    protected AbstractToolbarPresenter<T>   toolbar;

    protected AbstractEditor(@Nonnull EditorView view) {
        super(view);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        toolbar.go(((EditorView)view).getToolbarPanel());
        workspace.go(((EditorView)view).getWorkspacePanel());

        super.go(container);
    }

    @Nonnull
    public abstract String serialize();

    public abstract void deserialize(@Nonnull String content);

}