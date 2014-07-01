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

import com.google.gwt.xml.client.Node;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The main presentation of diagram elements. All diagram elements will implement this class. It contains general information for all
 * diagram elements.
 *
 * @author Andrey Plotnikov
 */
public interface Element {

    /**
     * @return an unique element identifier. All instances of elements will have an unique identifier. This means that anyone can find
     * needed diagram element.
     */
    @Nonnull
    String getId();

    /** @return a title of a shape diagram element. This title will show in GWT widget. */
    @Nonnull
    String getTitle();

    /**
     * Change title of a shape diagram element to a new one.
     *
     * @param title
     *         title that need to be applied
     */
    void setTitle(@Nonnull String title);

    /** @return parent of this graphical element */
    @Nullable
    Shape getParent();

    /**
     * Change a parent of the graphical element.
     *
     * @param parent
     *         parent that needs to be applied
     */
    void setParent(@Nullable Shape parent);

    String getElementName();

    /**
     * Serialize diagram element with all inner elements.
     *
     * @return serialized view of diagram element
     */
    @Nonnull
    String serialize();

    /**
     * Serialize diagram element with all inner elements and all properties(include internal properties).
     *
     * @return serialized view of diagram element
     */
    @Nonnull
    String serializeInternalFormat();

    /**
     * Deserialize diagram element with all inner elements.
     *
     * @param node
     *         XML node that need to be deserialized
     */
    void deserialize(@Nonnull Node node);

    /**
     * Deserialize diagram element with all inner elements and all properties(include internal properties).
     *
     * @param node
     *         XML node that need to be deserialized
     */
    void deserializeInternalFormat(@Nonnull Node node);

    /**
     * Apply property from XML node to the diagram element.
     *
     * @param node
     *         XML node that need to be analyzed
     */
    void applyProperty(@Nonnull Node node);

}