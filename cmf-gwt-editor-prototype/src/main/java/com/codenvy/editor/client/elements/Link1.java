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
package com.codenvy.editor.client.elements;

import com.codenvy.editor.api.editor.elements.AbstractLink;
import com.codenvy.editor.api.editor.elements.Shape;
import com.google.gwt.xml.client.Node;

import javax.annotation.Nonnull;
import java.util.ArrayList;

/**
 * @author Andrey Plotnikov
 */
public class Link1 extends AbstractLink {

    private String color;

    public Link1(@Nonnull Shape source, @Nonnull Shape target) {
        this(target, source, "GREEN");
    }

    public Link1(@Nonnull Shape source, @Nonnull Shape target, @Nonnull String color) {
        super(source, target, "Link1", new ArrayList<String>());

        this.color = color;
    }

    @Nonnull
    public String getColor() {
        return color;
    }

    public void setColor(@Nonnull String color) {
        this.color = color;
    }

    @Override
    public void deserialize(@Nonnull String content) {
        // it isn't implemented for now.
    }

    @Override
    public void deserialize(@Nonnull Node node) {
        // it isn't implemented for now.
    }

    @Override
    public void applyProperty(@Nonnull Node node) {
        // this class doesn't have any properties. That's why this method is empty
    }

}