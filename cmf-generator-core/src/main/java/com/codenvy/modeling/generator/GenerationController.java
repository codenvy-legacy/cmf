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
import com.codenvy.modeling.generator.common.ProjectDescriptionReader;
import com.google.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Properties;

import static com.codenvy.modeling.configuration.ConfigurationFactory.ConfigurationPaths;

/**
 * The main class of generating GWT editor. It provides an ability to check configuration and generate GWT java code.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class GenerationController {
    private static final Logger LOG = LoggerFactory.getLogger(GenerationController.class);

    private final SourceCodeGenerator  sourceCodeGenerator;
    private final ConfigurationFactory configurationFactory;

    @Inject
    public GenerationController(ConfigurationFactory configurationFactory, SourceCodeGenerator sourceCodeGenerator) {
        this.configurationFactory = configurationFactory;
        this.sourceCodeGenerator = sourceCodeGenerator;
    }

    /**
     * Provides checking a given configuration and generates java code of GWT editor.
     *
     * @param baseDir
     *         the path for the project directory
     */
    public void generate(@Nonnull String baseDir) {
        ProjectDescriptionReader projectDescriptionReader = new ProjectDescriptionReader(baseDir);
        Properties properties = projectDescriptionReader.getProjectProperties();
        String sourcePath = properties.getProperty("baseDir");
        try {
            // TODO need to add configuration paths and insert it into configuration factory
            sourceCodeGenerator.generate(properties,
                                         configurationFactory.getInstance(new ConfigurationPaths(sourcePath + "/diagram/DiagramGrammar",
                                                                                                 sourcePath + "/editor/EditorGrammar",
                                                                                                 sourcePath +
                                                                                                 "/serialization/SerializationGrammar",
                                                                                                 "styleConfigurationPath")
                                                                         ));
        } catch (IOException e) {
            LOG.error("Some problem happened during code generating.", e);
        }
    }

    /** The list of params for code generation. */
    public enum Param {
        TARGET_PATH,
        MAIN_PACKAGE,
        EDITOR_NAME,
        MAVEN_ARTIFACT_ID,
        MAVEN_GROUP_ID,
        MAVEN_ARTIFACT_NAME
    }
}