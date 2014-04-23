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

package com.codenvy.modeling.configuration.impl.metamodel.diagram;

import com.codenvy.modeling.configuration.metamodel.diagram.Connection;

import javax.annotation.Nonnull;

/**
 * @author Dmitry Kuleshov
 */
public class ConnectionImpl implements Connection {

    private String name;

    private String destination;

    private Relation relation;

    private Type type;

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    @Nonnull
    @Override
    public String getDestination() {
        return destination;
    }

    @Nonnull
    @Override
    public Relation getRelation() {
        return relation;
    }

    @Nonnull
    @Override
    public Type getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "\nConnectionImpl{" +
                "name='" + name + '\'' +
                ", destination='" + destination + '\'' +
                ", relation=" + relation +
                ", type=" + type +
                '}';
    }
}
