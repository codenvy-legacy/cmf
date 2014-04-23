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

package com.codenvy.modeling.configuration.impl.metamodel.serialization;

import com.codenvy.modeling.configuration.metamodel.serialization.ElementDesignation;

import javax.annotation.Nonnull;

/**
 * @author Dmitry Kuleshov
 */
public class ElementDesignationImpl implements ElementDesignation{

    private Type type;

    private String elementReferencePropertyName;

    private String elementReferenceTemplate;

    @Nonnull
    @Override
    public Type getType() {
        return type;
    }

    @Nonnull
    @Override
    public String getElementReferencePropertyName() {
        return elementReferencePropertyName;
    }

    @Nonnull
    @Override
    public String getElementReferenceTemplate() {
        return elementReferenceTemplate;
    }

    public void setType(@Nonnull Type type) {
        this.type = type;
    }

    public void setElementReferencePropertyName(@Nonnull String elementReferencePropertyName) {
        this.elementReferencePropertyName = elementReferencePropertyName;
    }

    public void setElementReferenceTemplate(@Nonnull String elementReferenceTemplate) {
        this.elementReferenceTemplate = elementReferenceTemplate;
    }

    @Override
    public String toString() {
        return "\nElementDesignationImpl{" +
                "type=" + type +
                ", elementReferencePropertyName='" + elementReferencePropertyName + '\'' +
                ", elementReferenceTemplate='" + elementReferenceTemplate + '\'' +
                '}';
    }
}
