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

import com.codenvy.modeling.generator.GenerationController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * The main class that provides an ability to read project description.
 *
 * @author Valeriy Svydenko
 */
public class ProjectDescriptionReader {
    private static final Logger LOG                 = LoggerFactory.getLogger(ProjectDescriptionReader.class);
    private static final String PROJECT_DESCRIPTION = "ProjectDescription";

    private String projectDescriptionPath;

    public ProjectDescriptionReader(String path) {
        projectDescriptionPath = path;
    }

    /**
     * Get properties from project description.
     *
     * @return a properties
     */
    public Properties getProjectProperties() {
        Properties prop = new Properties();

        try {
            InputStream inputStream = new FileInputStream(projectDescriptionPath + "/" + PROJECT_DESCRIPTION);
            prop.load(inputStream);
        } catch (Exception e) {
            LOG.error("Some problem happened during project description reading.", e);
        }
        prop.setProperty(GenerationController.Param.BASE_DIR.name(), projectDescriptionPath);

        return prop;
    }
}