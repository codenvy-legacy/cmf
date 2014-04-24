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

package com.codenvy.modeling.generator.builders.xml.impl.widgets.containers;

import com.codenvy.modeling.generator.builders.xml.api.widgets.GWidget;
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GContainer;
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.HasParts;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.AbstractGWidget;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The abstract presentation of GWT dock container builder. It contains general fields and method which need for all GWT dock container
 * builders. The main idea of this class is to simplify creating an implementation of GWT dock container builders.
 *
 * @author Andrey Plotnikov
 */
public abstract class AbstractGPartContainer<T> extends AbstractGWidget<T> implements GContainer<T>, HasParts<T> {

    /** Enum that detects in which kind of place the widget must be added */
    private enum Parts {
        NORTH, SOUTH, EAST, WEST
    }

    /** Aggregate information about container's part */
    private class Part {

        private final double                     size;
        private final GWidget<? extends GWidget> widget;

        private Part(@Nonnegative double size, @Nonnull GWidget<? extends GWidget> widget) {
            this.size = size;
            this.widget = widget;
        }

        @Nonnegative
        public double getSize() {
            return size;
        }

        @Nonnull
        public GWidget<? extends GWidget> getWidget() {
            return widget;
        }

    }

    @Nonnull
    private       Map<Parts, Part>           parts;
    @Nullable
    private       GWidget<? extends GWidget> widget;
    @Nonnull
    private final String                     closeTagFormat;

    protected AbstractGPartContainer(@Nonnull String openTagFormat, @Nonnull String closeTagFormat) {
        super(openTagFormat);

        this.closeTagFormat = closeTagFormat;
    }

    /** {@inheritDoc} */
    @Override
    protected void clean() {
        super.clean();

        parts = new LinkedHashMap<>();
        widget = null;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public T withNorth(@Nonnegative double size, @Nonnull GWidget<? extends GWidget> widget) {
        addPart(Parts.NORTH, size, widget);
        return builder;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public T withSouth(@Nonnegative double size, @Nonnull GWidget<? extends GWidget> widget) {
        addPart(Parts.SOUTH, size, widget);
        return builder;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public T withWest(@Nonnegative double size, @Nonnull GWidget<? extends GWidget> widget) {
        addPart(Parts.WEST, size, widget);
        return builder;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public T withEast(@Nonnegative double size, @Nonnull GWidget<? extends GWidget> widget) {
        addPart(Parts.EAST, size, widget);
        return builder;
    }

    /**
     * Add part to builder configuration.
     *
     * @param part
     *         part in which widget need to be added
     * @param size
     *         part size
     * @param widget
     *         widget that need to be added into part
     */
    private void addPart(@Nonnull Parts part, @Nonnegative double size, @Nonnull GWidget<? extends GWidget> widget) {
        parts.put(part, new Part(size, widget));
    }

    @Nonnull
    @Override
    public T withWidget(@Nonnull GWidget<? extends GWidget> widget) {
        this.widget = widget;
        return builder;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String build() throws IllegalStateException {
        StringBuilder result = new StringBuilder();

        result.append(super.build()).append('\n');

        for (Map.Entry<Parts, Part> part : parts.entrySet()) {
            Part partDescription = part.getValue();

            addPart(result, getFormat(part.getKey()), partDescription.getSize(), partDescription.getWidget());
        }

        if (widget != null) {
            String textOffset = getOffset(offset + 1);
            result.append(String.format(CENTER_PART_FORMAT, textOffset, prefix, widget.withOffset(offset + 2).build(), textOffset, prefix));
        }

        result.append(getOffset(offset)).append(String.format(closeTagFormat, prefix));

        return result.toString();
    }

    /**
     * Detect format for a given part.
     *
     * @param part
     *         part which needs a format
     * @return format for part
     */
    @Nonnull
    private String getFormat(@Nonnull Parts part) {
        switch (part) {
            case EAST:
                return EAST_PART_FORMAT;
            case WEST:
                return WEST_PART_FORMAT;
            case NORTH:
                return NORTH_PART_FORMAT;
            case SOUTH:
            default:
                return SOUTH_PART_FORMAT;
        }
    }

    /**
     * Add part to dock container in XML format.
     *
     * @param str
     *         {@link StringBuilder} that need to contain a given part
     * @param format
     *         part format
     * @param size
     *         part size
     * @param widget
     *         widget that need to be added into part
     */
    private void addPart(@Nonnull StringBuilder str,
                         @Nonnull String format,
                         @Nonnegative double size,
                         @Nullable GWidget<? extends GWidget> widget) {
        if (widget != null) {
            String textOffset = getOffset(offset + 1);
            str.append(String.format(format, textOffset, prefix, size, widget.withOffset(offset + 2).build(), textOffset, prefix));
        }
    }

}