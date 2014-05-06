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

import com.google.inject.Inject;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Dmitry Kuleshov
 * @author Andrey Plotnikov
 */
public class SerializationConfiguration {
    @Size(min = 1)
    @Valid
    private List<Element> elements;

    @Inject
    public SerializationConfiguration() {
        elements = new LinkedList<>();
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public void addElement(Element element) {
        elements.add(element);
    }
}
