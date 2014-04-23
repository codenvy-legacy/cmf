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

/**
 * @author Dmitry Kuleshov
 */
public class ConnectionDesignationImpl implements ConnectionDesignation {

    private Type type;

    private String connectionReferencePropertyName;

    private String connectionReferenceTemplate;

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getConnectionReferencePropertyName() {
        return connectionReferencePropertyName;
    }

    @Override
    public String getConnectionReferenceTemplate() {
        return connectionReferenceTemplate;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setConnectionReferencePropertyName(String connectionReferencePropertyName) {
        this.connectionReferencePropertyName = connectionReferencePropertyName;
    }

    public void setConnectionReferenceTemplate(String connectionReferenceTemplate) {
        this.connectionReferenceTemplate = connectionReferenceTemplate;
    }

    @Override
    public String toString() {
        return "\nConnectionDesignationImpl{" +
                "type=" + type +
                ", connectionReferencePropertyName='" + connectionReferencePropertyName + '\'' +
                ", connectionReferenceTemplate='" + connectionReferenceTemplate + '\'' +
                '}';
    }
}
