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

/**
 * The main presentation of a link diagram element. It provides an ability to connect shape elements.
 *
 * @author Andrey Plotnikov
 */
public interface Link extends Element {

    /** @return a source shape element */
    @Nonnull
    Shape getSource();

    /**
     * Change a source shape element.
     *
     * @param source
     *         a source element that need to be applied
     */
    void setSource(@Nonnull Shape source);

    /** @return a target shape element */
    @Nonnull
    Shape getTarget();

    /**
     * Change a target shape element.
     *
     * @param target
     *         a source element that need to be applied
     */
    void setTarget(@Nonnull Shape target);

}