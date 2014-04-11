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

package com.codenvy.editor.client;

import com.codenvy.editor.client.inject.Injector;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.orange.links.client.utils.LinksClientBundle;

public class EditorEntryPoint implements EntryPoint {

    public void onModuleLoad() {
        LinksClientBundle.INSTANCE.css().ensureInjected();

        Injector injector = GWT.create(Injector.class);
        EditorPresenter editor = injector.getEditor();

        SimpleLayoutPanel mainPanel = new SimpleLayoutPanel();
        editor.go(mainPanel);

        RootLayoutPanel.get().add(mainPanel);
    }

}