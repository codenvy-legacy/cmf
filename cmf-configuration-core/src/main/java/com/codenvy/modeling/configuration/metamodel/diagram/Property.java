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

package com.codenvy.modeling.configuration.metamodel.diagram;

import com.codenvy.modeling.configuration.validation.ConfigurationConstraintsValidator;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Dmitry Kuleshov
 * @author Valeriy Svydenko
 */
public class Property {
    @Pattern(regexp = ConfigurationConstraintsValidator.SIMPLE_TEXT)
    private String name;
    @Pattern(regexp = ConfigurationConstraintsValidator.SIMPLE_TEXT)
    private String value;
    @NotNull
    private Type   type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        BOOLEAN, INTEGER, FLOAT, STRING
    }
}
