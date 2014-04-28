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
import com.codenvy.modeling.configuration.metamodel.diagram.Pair;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

/** @author Dmitry Kuleshov */
public class ConnectionImpl implements Connection {

    @Nonnull
    private String name;

    @Nonnull
    private String destination;

    @Nonnull
    private Type type;

    @Nonnull
    private List<Pair> pairs = new LinkedList<>();

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    @Nonnull
    @Override
    public Type getType() {
        return type;
    }

    @Nonnull
    @Override
    public List<Pair> getPairs() {
        return pairs;
    }

    public void setName(@Nonnull String name) {
        this.name = name;
    }

    public void setDestination(@Nonnull String destination) {
        this.destination = destination;
    }

    public void setType(@Nonnull Type type) {
        this.type = type;
    }

    public void addPair(@Nonnull Pair pair) {
        pairs.add(pair);
    }
}