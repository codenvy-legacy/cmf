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

import com.codenvy.editor.api.editor.elements.Element;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * The manager of a selected diagram element. It provides an ability to know a selected diagram element for the current moment.
 *
 * @author Andrey Plotnikov
 */
@Singleton
public class SelectionManager {

    /** The listener for detecting a moment of changing selected diagram element. */
    public interface SelectionStateListener {
        /**
         * Performs any actions appropriate in response to the user having  selected a diagram element.
         *
         * @param element
         *         diagram element that was selected
         */
        void onStateChanged(@Nullable Element element);
    }

    private       Element                      element;
    private final List<SelectionStateListener> listeners;

    @Inject
    public SelectionManager() {
        this.listeners = new ArrayList<>();
    }

    /** @return selected diagram element. The method can return <code>null</code> in case no diagram element is selected. */
    @Nullable
    public Element getElement() {
        return element;
    }

    /**
     * Change selected diagram element.
     *
     * @param element
     *         a new selected diagram element
     */
    public void setElement(@Nullable Element element) {
        this.element = element;

        notifyListeners();
    }

    /**
     * Add a listener for detecting a moment of changing selected diagram element.
     *
     * @param listener
     *         listener that need to be added
     */
    public void addListener(@Nonnull SelectionStateListener listener) {
        listeners.add(listener);
    }

    /** Notify all listeners about changing selected diagram element. */
    public void notifyListeners() {
        for (SelectionStateListener listener : listeners) {
            listener.onStateChanged(element);
        }
    }

}