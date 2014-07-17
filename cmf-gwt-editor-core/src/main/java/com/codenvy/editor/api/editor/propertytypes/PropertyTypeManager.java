/*
 * Copyright [2014] Codenvy, S.A.
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
package com.codenvy.editor.api.editor.propertytypes;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

/**
 * The manager of a property type. It provides an ability to contain registered property types.
 *
 * @author Valeriy Svydenko
 */
@Singleton
public class PropertyTypeManager {
    private HashMap<String, List<String>> propertyTypes;

    @Inject
    public PropertyTypeManager() {
        propertyTypes = new HashMap<>();
    }

    /**
     * Register a new property type.
     *
     * @param name
     *         the name of a new property type
     * @param values
     *         values of a new property type
     */
    public void register(@Nonnull String name, List<String> values) {
        propertyTypes.put(name, values);
    }

    /**
     * @param name
     * @return values of property type by name. The method can return <code>null</code> in case no property type has this name.
     */
    @Nullable
    public List<String> getValuesOfTypeByName(@Nonnull String name) {
        return propertyTypes.get(name);
    }
}
