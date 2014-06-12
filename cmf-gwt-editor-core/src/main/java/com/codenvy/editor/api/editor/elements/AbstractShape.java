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

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The abstract implementation of {@link Shape}. It contains the implementation of general methods which might not be changed.
 *
 * @author Andrey Plotnikov
 */
public abstract class AbstractShape extends AbstractElement implements Shape, Comparable<AbstractShape> {

    private final List<AbstractShape> shapes;
    private final List<Link>          links;
    private       int                 x;
    private       int                 y;

    protected AbstractShape(String elementName, List<String> properties) {
        super(elementName, properties);

        this.shapes = new ArrayList<>();
        this.links = new ArrayList<>();
    }

    /** {@inheritDoc} */
    @Override
    public void addShape(@Nonnull Shape shape) {
        shape.setParent(this);
        shapes.add((AbstractShape)shape);
    }

    /** {@inheritDoc} */
    @Override
    public void removeShape(@Nonnull Shape shape) {
        shape.setParent(null);
        shapes.remove(shape);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public List<Shape> getShapes() {
        ArrayList<Shape> list = new ArrayList<>();
        list.addAll(shapes);

        return list;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasShapes() {
        return !shapes.isEmpty();
    }

    /** {@inheritDoc} */
    @Override
    public void addLink(@Nonnull Link link) {
        link.setParent(this);
        links.add(link);
    }

    /** {@inheritDoc} */
    @Override
    public void removeLink(@Nonnull Link link) {
        link.setParent(null);
        links.remove(link);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public List<Link> getLinks() {
        return links;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasLinks() {
        return !links.isEmpty();
    }

    /** {@inheritDoc} */
    @Override
    public int getX() {
        return x;
    }

    /** {@inheritDoc} */
    @Override
    public void setX(int x) {
        this.x = x;
    }

    /** {@inheritDoc} */
    @Override
    public int getY() {
        return y;
    }

    /** {@inheritDoc} */
    @Override
    public void setY(int y) {
        this.y = y;
    }

    /** {@inheritDoc} */
    @Override
    public int compareTo(AbstractShape shape) {
        if (x < shape.getX() || (x == shape.getX() && y < shape.getY())) {
            return -1;
        } else if (x > shape.getX() || (x == shape.getX() && y > shape.getY())) {
            return 1;
        }

        return 0;
    }

    @Nonnull
    @Override
    public String serialize() {
        StringBuilder content = new StringBuilder("<" + getElementName() + ">\n");
        content.append(serializeProperties());

        Collections.sort(shapes);

        for (Shape shape : shapes) {
            content.append(shape.serialize());
        }

        content.append("</").append(getElementName()).append(">\n");

        return content.toString();
    }

    @Nonnull
    protected String serializeProperties() {
        return "";
    }

    /** {@inheritDoc} */
    @Override
    public void deserialize(@Nonnull String content) {
        shapes.clear();
        links.clear();

        Document xml = XMLParser.parse(content);

        deserialize(xml.getFirstChild());
    }

}