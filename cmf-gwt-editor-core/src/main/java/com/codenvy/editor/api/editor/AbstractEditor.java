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

import com.codenvy.editor.api.editor.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.editor.api.editor.toolbar.AbstractToolbarPresenter;
import com.codenvy.editor.api.editor.workspace.AbstractWorkspacePresenter;
import com.codenvy.editor.api.mvp.AbstractPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;


/**
 * The abstract presentation of graphical editor. It represents main abilities of editor. Also It contains the implementation of general
 * methods which might not be changed.
 *
 * @author Andrey Plotnikov
 */
public abstract class AbstractEditor<T> extends AbstractPresenter implements AbstractWorkspacePresenter.DiagramChangeListener,
                                                                             AbstractPropertiesPanel.PropertyChangedListener {

    protected     AbstractWorkspacePresenter<T> workspace;
    protected     AbstractToolbarPresenter<T>   toolbar;
    private final List<EditorChangeListener>    listeners;

    protected AbstractEditor(@Nonnull EditorView view) {
        super(view);

        listeners = new ArrayList<>();
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        toolbar.go(((EditorView)view).getToolbarPanel());
        workspace.go(((EditorView)view).getWorkspacePanel());

        super.go(container);
    }

    /** {@inheritDoc} */
    @Override
    public void onChanged() {
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onPropertyChanged() {
        notifyListeners();
    }

    public void addListener(@Nonnull EditorChangeListener listener) {
        listeners.add(listener);
    }

    public void removeListener(@Nonnull EditorChangeListener listener) {
        listeners.remove(listener);
    }

    public void notifyListeners() {
        for (EditorChangeListener listener : listeners) {
            listener.onChanged();
        }
    }

    /** @return a serialized text type of diagram */
    @Nonnull
    public String serialize() {
        return workspace.serialize();
    }

    /** @return a serialized internal text type of diagram */
    @Nonnull
    public String serializeInternalFormat() {
        return workspace.serializeInternalFormat();
    }

    /**
     * Convert a text type of diagram to a graphical type.
     *
     * @param content
     *         content that need to be parsed
     */
    public void deserialize(@Nonnull String content) {
        workspace.deserialize(content);
    }

    /**
     * Convert an internal text type of diagram to a graphical type.
     *
     * @param content
     *         content that need to be parsed
     */
    public void deserializeInternalFormat(@Nonnull String content) {
        workspace.deserializeInternalFormat(content);
    }

    public interface EditorChangeListener {

        void onChanged();

    }

}