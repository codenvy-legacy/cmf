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

package com.codenvy.modeling.generator.builders.xml.api;

import com.codenvy.modeling.generator.builders.Builder;

import javax.annotation.Nonnull;

import static com.codenvy.modeling.generator.builders.xml.api.UIXmlBuilder.OFFSET;

/**
 * The builder for a style into GWT ui xml. It provides an ability to generate ui xml style from given configuration.
 * <p/>
 * The returned result must look like the following content:
 * <pre>
 * {@code
 * <ui:style>
 *     .style1 {
 *         <!--some styles-->
 *     }
 *     .style2 {
 *         <!--some styles-->
 *     }
 * </ui:style>
 * }
 * </pre>
 *
 * @author Andrey Plotnikov
 */
public interface GStyle extends Builder {

    String STYLE_OPEN_TAG  = "<ui:style>";
    String STYLE_CLOSE_TAG = "</ui:style>";
    String STYLE_FORMAT    = OFFSET + OFFSET + ".%s{%n" +
                             "%s" +
                             OFFSET + OFFSET + "}%n";

    /**
     * Add a new style.
     * <p/>
     * For example:
     * <pre>
     * {@code
     * <ui:style>
     *     .addedStyleName {
     *         <!--added styles-->
     *     }
     * </ui:style>
     * }
     * </pre>
     *
     * @param name
     *         style name that need to be added
     * @param style
     *         set of style that need to be mapped to a given name
     * @return a instance of builder with given configuration
     */
    @Nonnull
    GStyle withStyle(@Nonnull String name, @Nonnull String style);

}