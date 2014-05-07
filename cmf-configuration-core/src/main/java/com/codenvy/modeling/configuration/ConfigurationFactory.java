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

package com.codenvy.modeling.configuration;

import javax.annotation.Nonnull;
import java.io.IOException;

/**
 * @author Dmitry Kuleshov
 * @author Andrey Plotnikov
 */
public interface ConfigurationFactory {

    enum PathParameter {
        DIAGRAM {
            @Override
            public boolean isMandatory() {
                return true;
            }
        },
        SERIALIZATION {
            @Override
            public boolean isMandatory() {
                return false;
            }
        },
        EDITOR {
            @Override
            public boolean isMandatory() {
                return false;
            }
        },
        STYLE {
            @Override
            public boolean isMandatory() {
                return false;
            }
        };

        public abstract boolean isMandatory();
    }

    class ConfigurationPaths {

        private final String diagramConfigurationPath;
        private final String editorConfigurationPath;
        private final String serializationConfigurationPath;
        private final String styleConfigurationPath;

        public ConfigurationPaths(String diagramConfigurationPath,
                                  String editorConfigurationPath,
                                  String serializationConfigurationPath,
                                  String styleConfigurationPath) {
            this.diagramConfigurationPath = diagramConfigurationPath;
            this.editorConfigurationPath = editorConfigurationPath;
            this.serializationConfigurationPath = serializationConfigurationPath;
            this.styleConfigurationPath = styleConfigurationPath;
        }

        @Nonnull
        public String getDiagramConfigurationPath() {
            return diagramConfigurationPath;
        }

        @Nonnull
        public String getEditorConfigurationPath() {
            return editorConfigurationPath;
        }

        @Nonnull
        public String getSerializationConfigurationPath() {
            return serializationConfigurationPath;
        }

        @Nonnull
        public String getStyleConfigurationPath() {
            return styleConfigurationPath;
        }

    }

    @Nonnull
    Configuration getInstance(@Nonnull ConfigurationPaths configurationPaths) throws IOException;

}