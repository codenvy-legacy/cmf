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
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Andrey Plotnikov
 */
public class ToolbarViewImpl extends ToolbarView {

    interface ToolbarViewImplUiBinder extends UiBinder<Widget, ToolbarViewImpl> {
    }

    private static final int ITEM_HEIGHT = 37;

    @UiField(provided = true)
    PushButton      shape1;
    @UiField(provided = true)
    PushButton      shape2;
    @UiField
    PushButton      link1;
    @UiField
    DockLayoutPanel mainPanel;

    private final Map<String, PushButton> buttons;

    @Inject
    public ToolbarViewImpl(ToolbarViewImplUiBinder ourUiBinder, EditorResources resources) {
        buttons = new LinkedHashMap<>();

        shape1 = new PushButton(new Image(resources.shape1()));
        shape2 = new PushButton(new Image(resources.shape2()));

        buttons.put("Shape1", shape1);
        buttons.put("Shape2", shape2);

        widget = ourUiBinder.createAndBindUi(this);
    }

    /** {@inheritDoc} */
    @Override
    public void showButtons(@Nonnull Set<String> components) {
        mainPanel.clear();

        for (Map.Entry<String, PushButton> entry : buttons.entrySet()) {
            String elementName = entry.getKey();
            PushButton button = entry.getValue();

            if (components.contains(elementName)) {
                mainPanel.addNorth(button, ITEM_HEIGHT);
            }
        }

        mainPanel.addNorth(link1, ITEM_HEIGHT);
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