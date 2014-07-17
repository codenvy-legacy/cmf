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
public interface MarkerBuilderConstants {

    String MAIN_PACKAGE_MARKER               = "main_package";
    String CURRENT_PACKAGE_MARKER            = "current_package";
    String EDITOR_NAME_MARKER                = "editor_name";
    String STATIC_IMPORT_MARKER              = "static_import_elements";
    String ACTION_DELEGATES_MARKER           = "action_delegates";
    String IMPORT_MARKER                     = "import_elements";
    String UI_FIELDS_MARKER                  = "ui_fields";
    String PROPERTY_CHANGE_METHODS_MARKER    = "property_change_methods";
    String GETTER_AND_SETTER_PROPERTY_MARKER = "get_and_set_properties";

    String ARTIFACT_ID_MASK   = "artifact_id";
    String GROUP_ID_MASK      = "group_id";
    String ARTIFACT_NAME_MASK = "artifact_name";

    String ELEMENT_NAME_MARKER             = "elementName";
    String CONNECTION_NAME_MARKER          = "connectionName";
    String PROPERTY_NAME_MARKER            = "propertyName";
    String PROPERTY_TYPE_MARKER            = "propertyType";
    String CONFIGURED_PROPERTY_TYPE_MARKER = "configuredPropertyType";
    String ARGUMENT_NAME_MARKER            = "argumentName";
    String ELEMENT_UPPER_NAME_MARKER       = "elementUpperName";
    String CONNECTION_UPPER_NAME_MARKER    = "connectionUpperName";
    String ENTRY_POINT_CLASS_MARKER        = "entry_point";
    String ALL_ELEMENTS_MARKER             = "all_elements";
    String IMAGE_RESOURCES                 = "image_resources";
    String CUSTOM_STYLE                    = "custom_style";

    String IMPORT_PROPERTIES_PANEL_ELEMENTS = "import_properties_panel_elements";
    String IMPORT_ELEMENTS                  = "import_elements";
    String CONSTRUCTOR_ARGUMENTS            = "constructor_arguments";
    String CONSTRUCTOR_BODY                 = "constructor_body";

}