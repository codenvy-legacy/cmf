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

import com.codenvy.modeling.configuration.validation.constraints.ConfigurationConstraintsValidator;
import com.codenvy.modeling.configuration.validation.integrity.MustBeContained;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Dmitry Kuleshov
 * @author Valeriy Svydenko
 */
public class Connection {
    @Pattern(regexp = ConfigurationConstraintsValidator.SIMPLE_TEXT)
    private String name;
    @NotNull
    @Valid
    private Type   type;
    @Size(min = 1)
    @Valid
    private Set<Pair> pairs = new LinkedHashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Set<Pair> getPairs() {
        return pairs;
    }

    public void setPairs(Set<Pair> pairs) {
        this.pairs = pairs;
    }

    public void addPair(Pair pair) {
        pairs.add(pair);
    }

    public enum Type {
        DIRECTED, NONDIRECTED, POSITIONAL
    }
}
