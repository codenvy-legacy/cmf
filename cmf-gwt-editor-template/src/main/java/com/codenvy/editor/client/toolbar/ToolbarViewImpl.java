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
package com.codenvy.editor.client.toolbar;

import com.codenvy.editor.client.EditorResources;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * @author Andrey Plotnikov
 */
public class ToolbarViewImpl extends ToolbarView {

    interface ToolbarViewImplUiBinder extends UiBinder<Widget, ToolbarViewImpl> {
    }

    @UiField(provided = true)
    PushButton shape1;
    @UiField(provided = true)
    PushButton shape2;
    @UiField
    PushButton link1;

    @Inject
    public ToolbarViewImpl(ToolbarViewImplUiBinder ourUiBinder, EditorResources resources) {
        shape1 = new PushButton(new Image(resources.shape1()));
        shape2 = new PushButton(new Image(resources.shape2()));

        widget = ourUiBinder.createAndBindUi(this);
    }

    @UiHandler("shape1")
    public void onShape1ButtonClicked(ClickEvent event) {
        delegate.onShape1ButtonClicked();
    }

    @UiHandler("shape2")
    public void onShape2ButtonClicked(ClickEvent event) {
        delegate.onShape2ButtonClicked();
    }

    @UiHandler("link1")
    public void onLink1ButtonClicked(ClickEvent event) {
        delegate.onLink1ButtonClicked();
    }

}