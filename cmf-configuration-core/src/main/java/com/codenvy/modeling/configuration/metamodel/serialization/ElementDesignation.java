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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Dmitry Kuleshov
 * @author Valeriy Svydenko
 */
public class ElementDesignation {
    @NotNull
    private Type   type;
    @Pattern(regexp = ConfigurationConstraintsValidator.SIMPLE_TEXT)
    private String referencePropertyName;
    @Pattern(regexp = ConfigurationConstraintsValidator.SIMPLE_TEXT)
    private String referenceTemplate;


    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getReferencePropertyName() {
        return referencePropertyName;
    }

    public void setReferencePropertyName(String referencePropertyName) {
        this.referencePropertyName = referencePropertyName;
    }

    public String getReferenceTemplate() {
        return referenceTemplate;
    }

    public void setReferenceTemplate(String referenceTemplate) {
        this.referenceTemplate = referenceTemplate;
    }

    public enum Type {
        INSERTION, REFERENCE
    }
}
