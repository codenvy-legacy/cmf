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

import com.codenvy.editor.api.editor.SelectionManager;
import com.codenvy.editor.api.editor.elements.Element;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;

/**
 * @author Andrey Plotnikov
 */
public class PropertiesPanelManager implements SelectionManager.SelectionStateListener {

    private HashMap          panels;
    private AcceptsOneWidget container;


    public PropertiesPanelManager(@Nonnull AcceptsOneWidget container) {
        this.container = container;
        this.panels = new HashMap();
    }

    @SuppressWarnings("unchecked")
    public <T extends Element> void register(@Nullable Class<T> key, @Nonnull AbstractPropertiesPanel<T> value) {
        panels.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T extends Element> void show(@Nullable T element) {
        Object value = panels.get(element == null ? null : element.getClass());

        if (value != null && value instanceof AbstractPropertiesPanel) {
            AbstractPropertiesPanel<T> panel = (AbstractPropertiesPanel<T>)value;

            if (element != null) {
                panel.setElement(element);
            }

            panel.go(container);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onStateChanged(@Nullable Element element) {
        show(element);
    }

}