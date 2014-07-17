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
package com.codenvy.modeling.configuration.metamodel.diagram;

import com.codenvy.modeling.configuration.validation.constraints.ConfigurationConstraintsValidator;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Valeriy Svydenko
 */
public class PropertyType {
    @Pattern(regexp = ConfigurationConstraintsValidator.SIMPLE_TEXT)
    private String name;
    @Size(min = 1)
    @Valid
    private Set<String> values = new LinkedHashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getValues() {
        return values;
    }

    public void setValues(Set<String> values) {
        this.values = values;
    }

    public void addValue(String value) {
        values.add(value);
    }

}
