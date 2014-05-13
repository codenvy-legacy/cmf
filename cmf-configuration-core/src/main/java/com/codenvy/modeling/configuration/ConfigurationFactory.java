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
import java.util.Properties;

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
        private final String diagram;
        private final String editor;
        private final String serialization;
        private final String style;

        public ConfigurationPaths(@Nonnull Properties properties) {
            this.diagram = properties.getProperty(ConfigurationFactory.PathParameter.DIAGRAM.name());
            this.editor = properties.getProperty(ConfigurationFactory.PathParameter.EDITOR.name());
            this.serialization = properties.getProperty(ConfigurationFactory.PathParameter.SERIALIZATION.name());
            this.style = properties.getProperty(ConfigurationFactory.PathParameter.STYLE.name());
        }
        @Nonnull
        public String getDiagram() {
            return diagram;
        }

        @Nonnull
        public String getEditor() {
            return editor;
        }

        @Nonnull
        public String getSerialization() {
            return serialization;
        }

        @Nonnull
        public String getStyle() {
            return style;
        }

    }

    @Nonnull
    Configuration getInstance(@Nonnull ConfigurationPaths configurationPaths) throws IOException;

}