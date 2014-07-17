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

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class Shape1PropertiesPanelViewImpl extends Shape1PropertiesPanelView {

    interface Shape1PropertiesPanelViewImplUiBinder extends UiBinder<Widget, Shape1PropertiesPanelViewImpl> {
    }

    @UiField
    ListBox property1;

    @Inject
    public Shape1PropertiesPanelViewImpl(Shape1PropertiesPanelViewImplUiBinder ourUiBinder) {
        widget = ourUiBinder.createAndBindUi(this);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getProperty1() {
        int index = property1.getSelectedIndex();
        return index != -1 ? property1.getValue(property1.getSelectedIndex()) : "";
    }

    /** {@inheritDoc} */
    @Override
    public void selectProperty1(@Nonnull String property1) {
        for (int i = 0; i < this.property1.getItemCount(); i++) {
            if (this.property1.getValue(i).equals(property1)) {
                this.property1.setItemSelected(i, true);
                return;
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void setProperty1(@Nullable List<String> property1) {
        if (property1 == null) {
            return;
        }
        this.property1.clear();
        for (String value : property1) {
            this.property1.addItem(value);
        }
    }

    @UiHandler("property1")
    public void onProperty1Changed(ChangeEvent event) {
        delegate.onProperty1Changed();
    }

}