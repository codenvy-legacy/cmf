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
package com.codenvy.editor.api.editor.workspace;

import com.codenvy.editor.api.editor.EditorState;
import com.codenvy.editor.api.editor.HasState;
import com.codenvy.editor.api.editor.SelectionManager;
import com.codenvy.editor.api.editor.common.ContentFormatter;
import com.codenvy.editor.api.editor.elements.Element;
import com.codenvy.editor.api.editor.elements.Shape;
import com.codenvy.editor.api.mvp.AbstractPresenter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The abstract presentation of an editor workspace. It contains the implementation of general methods which might not be changed.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public abstract class AbstractWorkspacePresenter<T> extends AbstractPresenter implements AbstractWorkspaceView.ActionDelegate, HasState<T> {

    private EditorState<T> state;

    protected       String                      selectedElement;
    protected final Shape                       mainElement;
    protected       SelectionManager            selectionManager;
    protected final Map<String, Element>        elements;
    private final   List<DiagramChangeListener> listeners;

    protected AbstractWorkspacePresenter(@Nonnull AbstractWorkspaceView view,
                                         @Nonnull EditorState<T> state,
                                         @Nonnull Shape mainElement,
                                         @Nonnull SelectionManager selectionManager) {
        super(view);

        this.state = state;
        this.selectionManager = selectionManager;
        this.mainElement = mainElement;
        this.elements = new HashMap<>();
        this.listeners = new ArrayList<>();

        this.selectionManager.setElement(mainElement);
        this.selectedElement = mainElement.getId();
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

    public void addListener(@Nonnull DiagramChangeListener listener) {
        listeners.add(listener);
    }

    public void removeListener(@Nonnull DiagramChangeListener listener) {
        listeners.remove(listener);
    }

    public void notifyListeners() {
        for (DiagramChangeListener listener : listeners) {
            listener.onChanged();
        }
    }

    @Nonnull
    public String serialize() {
        return ContentFormatter.formatXML(ContentFormatter.trimXML(mainElement.serialize()));
    }

    public void deserialize(@Nonnull String content) {
        ((AbstractWorkspaceView)view).clearDiagram();
        elements.clear();

        selectionManager.setElement(null);
        selectedElement = null;

        mainElement.deserialize(ContentFormatter.trimXML(content));
    }

    public interface DiagramChangeListener {

        void onChanged();

    }

}