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

import com.codenvy.modeling.configuration.ConfigurationFactory;
import com.codenvy.modeling.configuration.ConfigurationFactoryCreator;
import com.google.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Map;

import static com.codenvy.modeling.generator.GenerationController.Param.DESCRIPTION_PATH;

/**
 * The main class of generating GWT editor. It provides an ability to check configuration and generate GWT java code.
 *
 * @author Andrey Plotnikov
 */
public class GenerationController {

    /** The list of params for code generation. */
    public enum Param {
        DESCRIPTION_PATH, SOURCE_PATH, TARGET_PATH, MAIN_PACKAGE, EDITOR_NAME
    }

    private static final Logger LOG = LoggerFactory.getLogger(GenerationController.class);

    private final ConfigurationFactoryCreator configurationFactoryCreator;
    private final SourceCodeGenerator         sourceCodeGenerator;

    @Inject
    public GenerationController(ConfigurationFactoryCreator configurationFactoryCreator, SourceCodeGenerator sourceCodeGenerator) {
        this.configurationFactoryCreator = configurationFactoryCreator;
        this.sourceCodeGenerator = sourceCodeGenerator;
    }

    /**
     * Provides checking a given configuration and generates java code of GWT editor.
     *
     * @param params
     *         parameters are given by user
     */
    public void generate(@Nonnull Map<Param, String> params) {
        ConfigurationFactory configurationFactory = configurationFactoryCreator.create(params.get(DESCRIPTION_PATH));

        try {
            sourceCodeGenerator.generate(params, configurationFactory.getConfigurationKeeperInstance());
        } catch (IOException e) {
            LOG.error("Some problem happened during code generating.", e);
        }
    }

}