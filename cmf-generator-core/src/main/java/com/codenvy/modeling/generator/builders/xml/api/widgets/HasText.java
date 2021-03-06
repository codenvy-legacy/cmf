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

package com.codenvy.modeling.generator.builders.xml.api.widgets;

import javax.annotation.Nonnull;

/**
 * Implemented by class that supports text property.
 *
 * @param <T>
 *         type of builder that implemented this interface
 * @author Andrey Plotnikov
 */
public interface HasText<T> {

    String TEXT_PARAM_NAME = "text";

    /**
     * Change content of text property.
     *
     * @param text
     *         content that need to be applied
     * @return a instance of builder with given configuration.
     */
    @Nonnull
    T withText(@Nonnull String text);

}