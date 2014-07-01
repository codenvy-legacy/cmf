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

import com.codenvy.editor.api.editor.elements.AbstractShape;
import com.codenvy.editor.api.editor.elements.Element;
import com.codenvy.editor.api.editor.elements.Link;
import com.codenvy.editor.api.editor.elements.Shape;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Andrey Plotnikov
 */
public class MainElement extends AbstractShape {

    public MainElement() {
        this("MainElement", new ArrayList<String>(), Arrays.asList("x", "y", "uuid"));

        components.add("Shape1");
        components.add("Shape2");
    }

    public MainElement(@Nonnull String elementName, @Nonnull List<String> properties, @Nonnull List<String> internalProperties) {
        super(elementName, properties, internalProperties);
    }

    /** {@inheritDoc} */
    @Override
    public void deserialize(@Nonnull Node node) {
        NodeList childNodes = node.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            String name = item.getNodeName();

            if (isProperty(name)) {
                applyProperty(item);
            } else {
                Element element = findElement(name);
                element.deserialize(item);

                if (element instanceof Shape) {
                    addShape((Shape)element);
                } else {
                    addLink((Link)element);
                }
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void deserializeInternalFormat(@Nonnull Node node) {
        NodeList childNodes = node.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            String name = item.getNodeName();

            if (isInternalProperty(name)) {
                applyProperty(item);
            } else {
                Element element = findElement(name);
                element.deserializeInternalFormat(item);

                if (element instanceof Shape) {
                    addShape((Shape)element);
                } else {
                    addLink((Link)element);
                }
            }
        }
    }

    private Element findElement(@Nonnull String elementName) {
        switch (elementName) {
            case "Shape1":
                return new Shape1();

            case "Shape2":
                return new Shape2();

            case "Link1":
            default:
                return new Link1();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void applyProperty(@Nonnull Node node) {
        // this class doesn't have any properties. That's why this method is empty
    }

}