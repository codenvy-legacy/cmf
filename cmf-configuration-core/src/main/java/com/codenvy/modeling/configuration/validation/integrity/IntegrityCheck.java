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

package com.codenvy.modeling.configuration.validation.integrity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Dmitry Kuleshov
 */
public class IntegrityCheck {
    private Collection<String> collection;
    private MustContain.Match  match;
    private Set<String> items = new HashSet<>();


    public Collection<String> getCollection() {
        return collection;
    }

    public void setCollection(Collection<String> collection) {
        this.collection = collection;
    }

    public Set<String> getItems() {
        return items;
    }

    public void addItem(String item) {
        items.add(item);
    }

    public MustContain.Match getMatch() {
        return match;
    }

    public void setMatch(MustContain.Match match) {
        this.match = match;
    }
}