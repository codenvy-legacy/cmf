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
 */
public interface BuilderConstants {

    String OFFSET     = "    ";
    String TWO_TABS   = OFFSET + OFFSET;
    String THREE_TABS = OFFSET + OFFSET + OFFSET;
    String FOUR_TABS  = OFFSET + OFFSET + OFFSET + OFFSET;
    String FIVE_TABS  = OFFSET + OFFSET + OFFSET + OFFSET + OFFSET;

    String MAIN_PACKAGE_MASK    = "main_package";
    String CURRENT_PACKAGE_MASK = "current_package";
    String STATIC_IMPORT_MASK   = "static_import_elements";

    String CREATE_NOTING_STATE                   = "CREATING_NOTING";
    String CREATE_ELEMENT_STATE_FORMAT           = "CREATING_%s";
    String CREATE_CONNECTION_SOURCE_STATE_FORMAT = "CREATING_%s_SOURCE";
    String CREATE_CONNECTION_TARGET_STATE_FORMAT = "CREATING_%s_TARGET";

    String MAIN_SOURCE_PATH      = "/src/main";
    String JAVA_SOURCE_PATH      = "java";
    String RESOURCES_SOURCE_PATH = "resources";
    String WEBAPP_SOURCE_PATH    = "webapp";

    String CLIENT_PART_FOLDER = "client";
    String ELEMENTS_FOLDER    = "elements";
    String WORKSPACE_FOLDER   = "workspace";

    String EDITOR_STATE_NAME     = "State";
    String EDITOR_RESOURCES_NAME = "EditorResources";

}