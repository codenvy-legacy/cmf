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

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

/**
 * @author Andrey Plotnikov
 */
public class Shape1PropertiesPanelViewImpl extends Shape1PropertiesPanelView {

    interface Shape1PropertiesPanelViewImplUiBinder extends UiBinder<Widget, Shape1PropertiesPanelViewImpl> {
    }

    @UiField
    TextBox property1;

    @Inject
    public Shape1PropertiesPanelViewImpl(Shape1PropertiesPanelViewImplUiBinder ourUiBinder) {
        widget = ourUiBinder.createAndBindUi(this);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getProperty1() {
        return property1.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setProperty1(@Nonnull String property1) {
        this.property1.setText(property1);
    }

    @UiHandler("property1")
    public void onProperty1Changed(KeyUpEvent event) {
        delegate.onProperty1Changed();
    }

}