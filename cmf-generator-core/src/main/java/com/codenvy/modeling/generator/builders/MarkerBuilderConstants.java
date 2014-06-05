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
public interface MarkerBuilderConstants {

    String MAIN_PACKAGE_MARKER               = "main_package";
    String CURRENT_PACKAGE_MARKER            = "current_package";
    String STATIC_IMPORT_MARKER              = "static_import_elements";
    String ACTION_DELEGATES_MARKER           = "action_delegates";
    String IMPORT_MARKER                     = "import_elements";
    String UI_FIELDS_MARKER                  = "ui_fields";
    String PROPERTY_CHANGE_METHODS_MARKER    = "property_change_methods";
    String GETTER_AND_SETTER_PROPERTY_MARKER = "get_and_set_properties";

    String ELEMENT_NAME_MARKER          = "elementName";
    String CONNECTION_NAME_MARKER       = "connectionName";
    String PROPERTY_NAME_MARKER         = "propertyName";
    String PROPERTY_TYPE_MARKER         = "propertyType";
    String ARGUMENT_NAME_MARKER         = "argumentName";
    String ELEMENT_UPPER_NAME_MARKER    = "elementUpperName";
    String CONNECTION_UPPER_NAME_MARKER = "connectionUpperName";

}