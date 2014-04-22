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

import com.codenvy.modeling.adapter.AdapterFactory;
import com.codenvy.modeling.adapter.metamodel.MetaModelConfigurationAdapter;
import com.google.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static com.codenvy.modeling.generator.Generator.Param.SOURCE_PATH;

/**
 * The main class that provides an ability to generate java source code from given params.
 *
 * @author Andrey Plotnikov
 */
public class Generator {

    /** The list of params for code generation. */
    public enum Param {
        SOURCE_PATH, TARGET_PATH, MAIN_PACKAGE, EDITOR_NAME
    }

    private static final Logger LOG = LoggerFactory.getLogger(Generator.class);

    private final AdapterFactory adapterFactory;

    @Inject
    public Generator(AdapterFactory adapterFactory) {
        this.adapterFactory = adapterFactory;
    }

    /**
     * Generates java source code.
     *
     * @param params
     *         params which are needed for code generation
     */
    public void generate(@Nonnull Map<Param, String> params) {
        // TODO We must add checking of configuration

        String sourcePathText = params.get(SOURCE_PATH);
        Path sourcePath;

        if (sourcePathText != null) {
            sourcePath = Paths.get(sourcePathText);
        } else {
            LOG.error("The source path parameter is absent. You should add it and relaunch generation process.");
            return;
        }

        try {
            MetaModelConfigurationAdapter metaModelConfigurationAdapter =
                    adapterFactory.getMetaModelConfAdapter(Files.newInputStream(sourcePath));


        } catch (IOException e) {
            LOG.error("The resource on path " + sourcePath + " wasn't found", e);
        }
    }

}