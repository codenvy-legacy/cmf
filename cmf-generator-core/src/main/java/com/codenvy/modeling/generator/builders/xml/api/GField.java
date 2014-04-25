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
 * The builder for a field into GWT ui xml. It provides an ability to generate ui xml field from given configuration.
 * <p/>
 * The returned result must look like the following content:
 * <pre>
 * {@code
 * <ui:with field="res" type="com.codenvy.editor.client.EditorResources"/>
 * }
 * </pre>
 *
 * @author Andrey Plotnikov
 */
public interface GField extends Builder {

    String FIELD_FORMAT = OFFSET + "<ui:with field='%s' type='%s'/>";

    /**
     * Add name of field.
     * <p/>
     * For example:
     * <pre>
     * {@code
     * <ui:with field="added name"/>
     * }
     * </pre>
     *
     * @param name
     *         name that need to be added
     * @return a instance of builder with given configuration
     */
    @Nonnull
    GField withName(@Nonnull String name);

    /**
     * Add type of field.
     * <p/>
     * For example:
     * <pre>
     * {@code
     * <ui:with field="field" type="added type"/>
     * }
     * </pre>
     *
     * @param type
     *         type that need to be added
     * @return a instance of builder with given configuration
     */
    @Nonnull
    GField withType(@Nonnull Class type);

}