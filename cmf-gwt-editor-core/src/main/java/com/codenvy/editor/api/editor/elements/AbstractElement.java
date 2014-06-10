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
import javax.annotation.Nullable;
import java.util.List;

/**
 * The abstract implementation of {@link Element}. It contains the implementation of general methods which might not be changed.
 *
 * @author Andrey Plotnikov
 */
public abstract class AbstractElement implements Element {

    private final String       id;
    private       Shape        parent;
    private       String       title;
    private       String       elementName;
    private final List<String> properties;

    protected AbstractElement(@Nonnull String elementName, @Nonnull List<String> properties) {
        this.elementName = elementName;
        this.properties = properties;
        this.title = elementName;
        id = UUID.get();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getId() {
        return id;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getTitle() {
        return title;
    }

    /** {@inheritDoc} */
    @Override
    public void setTitle(@Nonnull String title) {
        this.title = title;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public Shape getParent() {
        return parent;
    }

    /** {@inheritDoc} */
    @Override
    public void setParent(@Nullable Shape parent) {
        this.parent = parent;
    }

    /** {@inheritDoc} */
    @Override
    public String getElementName() {
        return elementName;
    }

    /**
     * Returns <code>true</code> if a given XML tag name is property name.
     *
     * @param name
     *         XML tag name
     * @return <code>true</code> if a given XML tag name is property name, <code>false</code> if it is not
     */
    protected boolean isProperty(@Nonnull String name) {
        return properties.contains(name);
    }

}