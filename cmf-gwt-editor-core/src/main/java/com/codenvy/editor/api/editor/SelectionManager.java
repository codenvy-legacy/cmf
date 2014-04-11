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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrey Plotnikov
 */
public class SelectionManager {

    public interface SelectionStateListener {

        void onStateChanged(@Nullable Element element);

    }

    private Element                      element;
    private List<SelectionStateListener> listeners;

    public SelectionManager() {
        this.listeners = new ArrayList<>();
    }

    @Nullable
    public Element getElement() {
        return element;
    }

    public void setElement(@Nullable Element element) {
        this.element = element;

        notifyListeners();
    }

    public void addListener(@Nonnull SelectionStateListener listener) {
        listeners.add(listener);
    }

    public void notifyListeners() {
        for (SelectionStateListener listener : listeners) {
            listener.onStateChanged(element);
        }
    }

}