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

package com.codenvy.modeling.generator.builders;

import javax.annotation.Nonnull;

/**
 * The general presentation of builders. It contains main method for any builder.
 *
 * @author Andrey Plotnikov
 */
public interface Builder {
    /**
     * Build content from given configuration.
     *
     * @return the text format of content
     * @throws IllegalStateException
     *         exception happens in case some configuration had been not initialized before this method was executed
     */
    @Nonnull
    String build() throws IllegalStateException;
}