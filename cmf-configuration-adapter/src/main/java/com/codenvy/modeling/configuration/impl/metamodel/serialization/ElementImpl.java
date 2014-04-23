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

import com.codenvy.modeling.configuration.metamodel.serialization.ConnectionDesignation;
import com.codenvy.modeling.configuration.metamodel.serialization.Element;
import com.codenvy.modeling.configuration.metamodel.serialization.ElementDesignation;

import javax.annotation.Nonnull;

/**
 * @author Dmitry Kuleshov
 */
public class ElementImpl implements Element {

    private String name;

    private String template;

    private ElementDesignation elementDesignation;

    private ConnectionDesignation connectionDesignation;

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    @Nonnull
    @Override
    public String getTemplate() {
        return template;
    }

    @Nonnull
    @Override
    public ElementDesignation getElementDesignation() {
        return elementDesignation;
    }

    @Nonnull
    @Override
    public ConnectionDesignation getConnectionDesignation() {
        return connectionDesignation;
    }

    public void setName(@Nonnull String name) {
        this.name = name;
    }

    public void setTemplate(@Nonnull String template) {
        this.template = template;
    }

    public void setElementDesignation(@Nonnull ElementDesignation elementDesignation) {
        this.elementDesignation = elementDesignation;
    }

    public void setConnectionDesignation(@Nonnull ConnectionDesignation connectionDesignation) {
        this.connectionDesignation = connectionDesignation;
    }

    @Override
    public String toString() {
        return "\nElementImpl{" +
                "name='" + name + '\'' +
                ", template='" + template + '\'' +
                ", elementDesignation=" + elementDesignation +
                ", connectionDesignation=" + connectionDesignation +
                '}';
    }
}
