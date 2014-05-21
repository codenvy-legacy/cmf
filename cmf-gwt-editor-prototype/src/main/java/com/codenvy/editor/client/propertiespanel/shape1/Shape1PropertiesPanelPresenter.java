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
package com.codenvy.editor.client.propertiespanel.shape1;

import com.codenvy.editor.api.editor.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.editor.client.elements.Shape1;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

/**
 * @author Andrey Plotnikov
 */
public class Shape1PropertiesPanelPresenter extends AbstractPropertiesPanel<Shape1> implements Shape1PropertiesPanelView.ActionDelegate {

    @Inject
    public Shape1PropertiesPanelPresenter(Shape1PropertiesPanelView view) {
        super(view);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        ((Shape1PropertiesPanelView)view).setProperty1(element.getProperty1());
    }

    /** {@inheritDoc} */
    @Override
    public void onProperty1Changed() {
        element.setProperty1(((Shape1PropertiesPanelView)view).getProperty1());
    }

}