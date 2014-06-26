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
import java.util.List;
import java.util.Set;

/**
 * The main presentation of shape diagram element. It can contain and manage inner diagram elements. Inner diagram elements mean that this
 * kind of elements provides an ability to have own diagram inside himself. This ability is optional.
 *
 * @author Andrey Plotnikov
 */
public interface Shape extends Element {

    /**
     * Add an inner shape into this one shape.
     *
     * @param shape
     *         shape that need to be added
     */
    void addShape(@Nonnull Shape shape);

    /**
     * Remove a inner shape from this one shape.
     *
     * @param shape
     *         shape that need to be removed
     */
    void removeShape(@Nonnull Shape shape);

    /** @return a list of inner shapes */
    @Nonnull
    List<Shape> getShapes();

    /** @return <code>true</code> if this shape has inner shapes, <code>false</code> it doesn't */
    boolean hasShapes();

    /**
     * Add an inner link into this one shape.
     *
     * @param link
     *         link that need to be added
     */
    void addLink(@Nonnull Link link);

    /**
     * Remove a inner link from this one shape.
     *
     * @param link
     *         link that need to be removed
     */
    void removeLink(@Nonnull Link link);

    /** @return a list of inner links */
    @Nonnull
    List<Link> getLinks();

    /** @return <code>true</code> if this shape has inner links, <code>false</code> it doesn't */
    boolean hasLinks();

    /** @return the x-position of shape in workspace */
    int getX();

    /**
     * Set the x-position of shape in workspace.
     *
     * @param x
     *         x-position
     */
    void setX(int x);

    /** @return the y-position of shape in workspace */
    int getY();

    /**
     * Set y-position of shape in workspace.
     *
     * @param y
     *         y-position
     */
    void setY(int y);

    /** @return the list of available inner components */
    Set<String> getComponents();

    /** @return <code>true</code> if this shape has an ability to have inner components, <code>false</code> it doesn't */
    boolean isContainer();

}