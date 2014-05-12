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
package com.codenvy.editor.client.propertiespanel.link1;

import com.codenvy.editor.api.editor.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.editor.client.elements.link1.Link1;
import com.google.inject.Inject;

/**
 * @author Andrey Plotnikov
 */
public class Link1PropertiesPanelPresenter extends AbstractPropertiesPanel<Link1> implements Link1PropertiesPanelView.ActionDelegate {

    @Inject
    public Link1PropertiesPanelPresenter(Link1PropertiesPanelView view) {
        super(view);
    }

}