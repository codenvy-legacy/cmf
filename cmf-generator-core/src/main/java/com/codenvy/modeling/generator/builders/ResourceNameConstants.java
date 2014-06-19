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

package com.codenvy.modeling.generator.builders;

/**
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public interface ResourceNameConstants {

    String EDITOR_STATE               = "State";
    String EDITOR_RESOURCES           = "EditorResources";
    String PROPERTIES_PANEL_PRESENTER = "PropertiesPanelPresenter";
    String PROPERTIES_PANEL_VIEW      = "PropertiesPanelView";
    String PROPERTIES_PANEL_VIEW_IMPL = "PropertiesPanelViewImpl";
    String ENTRY_POINT                = "EditorEntryPoint";
    String EDITOR_PRESENTER           = "EditorPresenter";
    String TOOLBAR_PRESENTER          = "ToolbarPresenter";
    String TOOLBAR_VIEW               = "ToolbarView";
    String TOOLBAR_VIEW_IMPL          = "ToolbarViewImpl";
    String WORKSPACE_PRESENTER        = "WorkspacePresenter";
    String WORKSPACE_VIEW             = "WorkspaceView";
    String WORKSPACE_VIEW_IMPL        = "WorkspaceViewImpl";
    String GIN_MODULE                 = "GinModule";
    String INJECTOR                   = "Injector";
    String EDITOR_FACTORY_NAME        = "EditorFactory";

    String POM_FILE_FULL_NAME        = "pom.xml";
    String MAIN_HTML_FILE_FULL_NAME  = "Editor.html";
    String MAIN_GWT_MODULE_FULL_NAME = "Editor.gwt.xml";
    String MAIN_CSS_FULL_NAME        = "editor.css";

}