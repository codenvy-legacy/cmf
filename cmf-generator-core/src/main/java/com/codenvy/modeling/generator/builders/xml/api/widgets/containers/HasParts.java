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

package com.codenvy.modeling.generator.builders.xml.api.widgets.containers;

import com.codenvy.modeling.generator.builders.xml.api.widgets.GWidget;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * Implemented by class that have a few parts.
 * * <p/>
 * The returned result must look like the following content:
 * <pre>
 * {@code
 * <g:DockLayoutPanel unit="PX" width="100%" addStyleNames="{style.spacing}">
 *     <g:west size="40.0">
 *         <!-- some widget -->
 *     </g:west>
 *     <g:north size="40.0">
 *         <!-- some widget -->
 *     </g:north>
 * </g:DockLayoutPanel>
 * }
 * </pre>
 *
 * @param <T>
 *         type of builder that implemented this interface
 * @author Andrey Plotnikov
 */
public interface HasParts<T> {

    String NORTH_PART_FORMAT  = "%s<%s:north size=\"%.2f\">\n" +
                                "%s\n" +
                                "%s</%s:north>\n";
    String SOUTH_PART_FORMAT  = "%s<%s:south size=\"%.2f\">\n" +
                                "%s\n" +
                                "%s</%s:south>\n";
    String EAST_PART_FORMAT   = "%s<%s:east size=\"%.2f\">\n" +
                                "%s\n" +
                                "%s</%s:east>\n";
    String WEST_PART_FORMAT   = "%s<%s:west size=\"%.2f\">\n" +
                                "%s\n" +
                                "%s</%s:west>\n";
    String CENTER_PART_FORMAT = "%s<%s:center>\n" +
                                "%s\n" +
                                "%s</%s:center>\n";

    /**
     * Add a widget in the north part.
     * <p/>
     * For example:
     * <pre>
     * {@code
     * <g:DockLayoutPanel>
     *     <g:north size="given size">
     *         <!-- added widget -->
     *     </g:north>
     * </g:DockLayoutPanel>
     * }
     * </pre>
     *
     * @param size
     *         size of part
     * @param widget
     *         widget that need to be inserted
     * @return a instance of builder with given configuration
     */
    @Nonnull
    T withNorth(@Nonnegative double size, @Nonnull GWidget<? extends GWidget> widget);

    /**
     * Add a widget in the south part.
     * <p/>
     * For example:
     * <pre>
     * {@code
     * <g:DockLayoutPanel>
     *     <g:south size="given size">
     *         <!-- added widget -->
     *     </g:south>
     * </g:DockLayoutPanel>
     * }
     * </pre>
     *
     * @param size
     *         size of part
     * @param widget
     *         widget that need to be inserted
     * @return a instance of builder with given configuration
     */
    @Nonnull
    T withSouth(@Nonnegative double size, @Nonnull GWidget<? extends GWidget> widget);

    /**
     * Add a widget in the west part.
     * <p/>
     * For example:
     * <pre>
     * {@code
     * <g:DockLayoutPanel>
     *     <g:west size="given size">
     *         <!-- added widget -->
     *     </g:west>
     * </g:DockLayoutPanel>
     * }
     * </pre>
     *
     * @param size
     *         size of part
     * @param widget
     *         widget that need to be inserted
     * @return a instance of builder with given configuration
     */
    @Nonnull
    T withWest(@Nonnegative double size, @Nonnull GWidget<? extends GWidget> widget);

    /**
     * Add a widget in the east part.
     * <p/>
     * For example:
     * <pre>
     * {@code
     * <g:DockLayoutPanel>
     *     <g:east size="given size">
     *         <!-- added widget -->
     *     </g:east>
     * </g:DockLayoutPanel>
     * }
     * </pre>
     *
     * @param size
     *         size of part
     * @param widget
     *         widget that need to be inserted
     * @return a instance of builder with given configuration
     */
    @Nonnull
    T withEast(@Nonnegative double size, @Nonnull GWidget<? extends GWidget> widget);

}