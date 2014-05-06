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

package com.codenvy.modeling.configuration.metamodel.serialization;

import com.codenvy.modeling.configuration.validation.ConfigurationConstraintsValidator;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Dmitry Kuleshov
 * @author Valeriy SWvydenko
 */
public class Element {
    @Pattern(regexp = ConfigurationConstraintsValidator.SIMPLE_TEXT)
    private String                name;
    @Pattern(regexp = ConfigurationConstraintsValidator.SIMPLE_TEXT)
    private String                template;
    @NotNull
    @Valid
    private ElementDesignation    elementDesignation;
    @NotNull
    @Valid
    private ConnectionDesignation connectionDesignation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public ElementDesignation getElementDesignation() {
        return elementDesignation;
    }

    public void setElementDesignation(ElementDesignation elementDesignation) {
        this.elementDesignation = elementDesignation;
    }

    public ConnectionDesignation getConnectionDesignation() {
        return connectionDesignation;
    }

    public void setConnectionDesignation(ConnectionDesignation connectionDesignation) {
        this.connectionDesignation = connectionDesignation;
    }
}
