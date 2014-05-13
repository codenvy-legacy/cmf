/*
 * Copyright [2014] Codenvy, S.A.
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
package com.codenvy.modeling.generator.common;

import com.codenvy.modeling.configuration.ConfigurationFactory;
import com.codenvy.modeling.generator.GenerationController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * The main class that provides an ability to read project description.
 *
 * @author Valeriy Svydenko
 * @author Andrey Plotnikov
 */
public class ProjectDescriptionReader {
    private static final Logger LOG                 = LoggerFactory.getLogger(ProjectDescriptionReader.class);
    private static final String PROJECT_DESCRIPTION = "ProjectDescription";

    private final String projectDescriptionPath;

    public ProjectDescriptionReader(@Nonnull String path) {
        projectDescriptionPath = path;
    }

    /**
     * Get properties from project description.
     *
     * @return a properties
     */
    @Nonnull
    public Properties getProjectProperties() {
        Properties properties = new Properties();

        try (InputStream inputStream = Files.newInputStream(Paths.get(projectDescriptionPath, PROJECT_DESCRIPTION))) {
            properties.load(inputStream);
        } catch (IOException e) {
            LOG.error("Some problem happened during project description reading.", e);
        }

        makeAbsolutePaths(properties);

        return properties;
    }

    private void makeAbsolutePaths(@Nonnull Properties properties) {
        String diagramPathKey = ConfigurationFactory.PathParameter.DIAGRAM.name();
        String editorPathKey = ConfigurationFactory.PathParameter.EDITOR.name();
        String serializationPathKey = ConfigurationFactory.PathParameter.SERIALIZATION.name();
        String stylePathKey = ConfigurationFactory.PathParameter.STYLE.name();

        String templatePath = GenerationController.Param.TEMPLATE_PATH.name();
        String targetPath = GenerationController.Param.TARGET_PATH.name();

        properties.setProperty(diagramPathKey, projectDescriptionPath + properties.getProperty(diagramPathKey));
        properties.setProperty(editorPathKey, projectDescriptionPath + properties.getProperty(editorPathKey));
        properties.setProperty(serializationPathKey, projectDescriptionPath + properties.getProperty(serializationPathKey));
        properties.setProperty(stylePathKey, projectDescriptionPath + properties.getProperty(stylePathKey));

        properties.setProperty(templatePath, projectDescriptionPath + properties.getProperty(templatePath));
        properties.setProperty(targetPath, projectDescriptionPath + properties.getProperty(targetPath));
    }
}