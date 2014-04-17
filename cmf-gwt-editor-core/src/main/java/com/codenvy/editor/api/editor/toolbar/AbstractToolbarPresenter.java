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
package com.codenvy.editor.api.editor.toolbar;

import com.codenvy.editor.api.editor.EditorState;
import com.codenvy.editor.api.editor.HasState;
import com.codenvy.editor.api.mvp.AbstractPresenter;
import com.codenvy.editor.api.mvp.AbstractView;

import javax.annotation.Nonnull;

/**
 * The abstract presentation of an editor toolbar. It contains the implementation of general methods which might not be changed.
 *
 * @author Andrey Plotnikov
 */
public abstract class AbstractToolbarPresenter<T> extends AbstractPresenter implements HasState<T> {

    private EditorState<T> state;

    protected AbstractToolbarPresenter(@Nonnull AbstractView view, @Nonnull EditorState<T> state) {
        super(view);

        this.state = state;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public T getState() {
        return state.getState();
    }

    /** {@inheritDoc} */
    @Override
    public void setState(@Nonnull T state) {
        this.state.setState(state);
    }

}