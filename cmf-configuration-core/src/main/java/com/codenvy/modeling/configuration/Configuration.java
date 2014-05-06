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

import com.codenvy.modeling.configuration.editor.EditorConfiguration;
import com.codenvy.modeling.configuration.metamodel.diagram.DiagramConfiguration;
import com.codenvy.modeling.configuration.metamodel.serialization.SerializationConfiguration;
import com.codenvy.modeling.configuration.style.StyleConfiguration;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * @author Dmitry Kuleshov
 * @author Andrey Plotnikov
 */
public class Configuration {

    @NotNull
    @Valid
    private DiagramConfiguration       diagramConfiguration;
    @NotNull
    @Valid
    private SerializationConfiguration serializationConfiguration;
    @NotNull
    @Valid
    private EditorConfiguration        editorConfiguration;
    @NotNull
    @Valid
    private StyleConfiguration         styleConfiguration;

    public Configuration() {

    }

    @Inject
    public Configuration(ConfigurationAdapterFactory configurationAdapterFactory,
                         @Assisted String diagramConfigurationPath,
                         @Assisted String editorConfigurationPath,
                         @Assisted String serializationConfigurationPath,
                         @Assisted String styleConfigurationPath) throws IOException {
        this.diagramConfiguration = configurationAdapterFactory.createDiagramConfAdapter(diagramConfigurationPath).getConfiguration();
        this.serializationConfiguration =
                configurationAdapterFactory.createSerializationConfAdapter(serializationConfigurationPath).getConfiguration();
        this.editorConfiguration = configurationAdapterFactory.createEditorConfAdapter(editorConfigurationPath).getConfiguration();
        this.styleConfiguration = configurationAdapterFactory.createStyleConfAdapter(styleConfigurationPath).getConfiguration();
    }

    public DiagramConfiguration getDiagramConfiguration() {
        return diagramConfiguration;
    }

    public SerializationConfiguration getSerializationConfiguration() {
        return serializationConfiguration;
    }

    public EditorConfiguration getEditorConfiguration() {
        return editorConfiguration;
    }

    public StyleConfiguration getStyleConfiguration() {
        return styleConfiguration;
    }

}