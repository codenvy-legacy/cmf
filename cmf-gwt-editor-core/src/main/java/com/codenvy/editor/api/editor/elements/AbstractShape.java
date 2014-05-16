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
package com.codenvy.editor.api.editor.elements;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * The abstract implementation of {@link Shape}. It contains the implementation of general methods which might not be changed.
 *
 * @author Andrey Plotnikov
 */
public abstract class AbstractShape extends AbstractElement implements Shape {

    private final List<Element> elements;

    protected AbstractShape(String elementName) {
        super(elementName);
        this.elements = new ArrayList<>();
    }

    /** {@inheritDoc} */
    @Override
    public void addElement(@Nonnull Element element) {
        element.setParent(this);
        elements.add(element);
    }

    /** {@inheritDoc} */
    @Override
    public void removeElement(@Nonnull Element element) {
        element.setParent(null);
        elements.remove(element);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public List<Element> getElements() {
        return elements;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasElements() {
        return !elements.isEmpty();
    }

    @Nonnull
    @Override
    public String serialize() {
        StringBuilder content = new StringBuilder("<" + getElementName() + ">\n");
        content.append(serializeProperties());

        for (Element element : elements) {
            content.append(element.serialize());
        }

        content.append("</").append(getElementName()).append(">\n");

        return content.toString();
    }

    @Nonnull
    protected String serializeProperties() {
        return "";
    }

}