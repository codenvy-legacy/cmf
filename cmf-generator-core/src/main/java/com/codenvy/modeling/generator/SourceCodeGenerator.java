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
package com.codenvy.modeling.generator;

import com.codenvy.modeling.configuration.ConfigurationKeeper;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.Map;

import static com.codenvy.modeling.generator.GenerationController.Param;

/**
 * The main class that provides an ability to generate java source code from given params.
 *
 * @author Andrey Plotnikov
 */
public class SourceCodeGenerator {

    @Inject
    public SourceCodeGenerator() {

    }

    /**
     * Generates java source code of GWT editor.
     *
     * @param params
     *         params which are needed for code generation
     * @param configurationKeeper
     *         configuration keeper that contains full information about GWT editor
     */
    public void generate(@Nonnull Map<Param, String> params, ConfigurationKeeper configurationKeeper) {

    }

}