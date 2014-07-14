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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The abstract implementation of {@link Shape}. It contains the implementation of general methods which might not be changed.
 *
 * @author Andrey Plotnikov
 */
public abstract class AbstractShape extends AbstractElement implements Shape, Comparable<AbstractShape> {

    private final List<AbstractShape> shapes;
    private final List<Link>          links;

    protected final Set<String>               components;
    protected final Map<String, List<String>> targetElements;

    private int     x;
    private boolean isAutoAligned;
    private int     y;

    protected AbstractShape(@Nonnull String elementName, @Nonnull List<String> properties, @Nonnull List<String> internalProperties) {
        super(elementName, properties, internalProperties);

        this.shapes = new ArrayList<>();
        this.links = new ArrayList<>();
        this.components = new HashSet<>();
        this.targetElements = new HashMap<>();

        this.x = UNDEFINED_POSITION;
        this.y = UNDEFINED_POSITION;

        this.isAutoAligned = false;
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
        Collections.sort(shapes);

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

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String serializeInternalFormat() {
        StringBuilder content = new StringBuilder("<" + getElementName() + ">\n");
        content.append(serializeInternalProperties());

        Collections.sort(shapes);

        for (Shape shape : shapes) {
            content.append(shape.serializeInternalFormat());
        }

        for (Link link : links) {
            content.append(link.serializeInternalFormat());
        }

        content.append("</").append(getElementName()).append(">\n");

        return content.toString();
    }

    @Nonnull
    protected String serializeProperties() {
        return "";
    }

    @Nonnull
    protected String serializeInternalProperties() {
        return serializeProperties() +
               "<x>\n" + getX() + "\n</x>\n" +
               "<y>\n" + getY() + "\n</y>\n" +
               "<uuid>\n" + id + "\n</uuid>\n" +
               "<autoAlign>\n" + isAutoAligned + "\n</autoAlign>\n";
    }

    /** {@inheritDoc} */
    @Override
    public void deserialize(@Nonnull String content) {
        shapes.clear();
        links.clear();

        Document xml = XMLParser.parse(content);

        deserialize(xml.getFirstChild());
    }

    /** {@inheritDoc} */
    @Override
    public void deserializeInternalFormat(@Nonnull String content) {
        shapes.clear();
        links.clear();

        Document xml = XMLParser.parse(content);

        deserializeInternalFormat(xml.getFirstChild());
    }

    /** {@inheritDoc} */
    @Override
    public boolean isContainer() {
        return !components.isEmpty();
    }

    /** {@inheritDoc} */
    @Override
    public Set<String> getComponents() {
        return components;
    }

    /** {@inheritDoc} */
    @Override
    public void setAutoAlignmentParam(boolean isAutoAligned) {
        this.isAutoAligned = isAutoAligned;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isAutoAligned() {
        return isAutoAligned;
    }

    /** {@inheritDoc} */
    @Override
    public boolean canCreateConnection(@Nonnull String connection, @Nonnull String targetElement) {
        List<String> targetElements = this.targetElements.get(connection);
        return targetElements.contains(targetElement);
    }

}