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
package com.codenvy.editor.api.editor.propertiespanel;

import com.codenvy.editor.api.editor.elements.Element;
import com.codenvy.editor.api.mvp.AbstractPresenter;
import com.codenvy.editor.api.mvp.AbstractView;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * The general presentation of properties panel.
 *
 * @param <T>
 *         type of diagram element that this panel supports
 * @author Andrey Plotnikov
 */
public abstract class AbstractPropertiesPanel<T extends Element> extends AbstractPresenter {

    protected     T                             element;
    private final List<PropertyChangedListener> listeners;

    protected AbstractPropertiesPanel(@Nonnull AbstractView view) {
        super(view);
        listeners = new ArrayList<>();
    }

    /**
     * Set diagram element for showing its properties.
     *
     * @param element
     *         element which properties need to be shown
     */
    public void setElement(@Nonnull T element) {
        this.element = element;
    }

    public void addListener(@Nonnull PropertyChangedListener listener) {
        listeners.add(listener);
    }

    public void removeListener(@Nonnull PropertyChangedListener listener) {
        listeners.remove(listener);
    }

    public void notifyListeners() {
        for (PropertyChangedListener listener : listeners) {
            listener.onPropertyChanged();
        }
    }

    public interface PropertyChangedListener {

        void onPropertyChanged();

    }

}