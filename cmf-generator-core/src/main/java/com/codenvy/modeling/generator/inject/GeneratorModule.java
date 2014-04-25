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

package com.codenvy.modeling.generator.inject;

import com.codenvy.modeling.ConfigurationFactoryImpl;
import com.codenvy.modeling.configuration.ConfigurationAdapterFactory;
import com.codenvy.modeling.configuration.ConfigurationFactory;
import com.codenvy.modeling.configuration.ConfigurationFactoryCreator;
import com.codenvy.modeling.configuration.ConfigurationKeeper;
import com.codenvy.modeling.configuration.ConfigurationKeeperFactory;
import com.codenvy.modeling.configuration.ConfigurationKeeperImpl;
import com.codenvy.modeling.configuration.ValidatorFactory;
import com.codenvy.modeling.configuration.validation.DiagramFileConsistencyValidatorImpl;
import com.codenvy.modeling.configuration.validation.EditorFileConsistencyValidatorImpl;
import com.codenvy.modeling.configuration.validation.SerializationFileConsistencyValidatorImpl;
import com.codenvy.modeling.configuration.validation.pre.DiagramFileConsistencyValidator;
import com.codenvy.modeling.configuration.validation.pre.EditorFileConsistencyValidator;
import com.codenvy.modeling.configuration.validation.pre.SerializationFileConsistencyValidator;
import com.codenvy.modeling.generator.builders.java.SourceCodeBuilder;
import com.codenvy.modeling.generator.builders.java.SourceCodeBuilderImpl;
import com.codenvy.modeling.generator.builders.xml.api.UIXmlBuilder;
import com.codenvy.modeling.generator.builders.xml.api.widgets.GButton;
import com.codenvy.modeling.generator.builders.xml.api.widgets.GHtml;
import com.codenvy.modeling.generator.builders.xml.api.widgets.GLabel;
import com.codenvy.modeling.generator.builders.xml.api.widgets.GTextArea;
import com.codenvy.modeling.generator.builders.xml.api.widgets.GTextBox;
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GDockLayoutPanel;
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GFlowPanel;
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GScrollPanel;
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GSimpleLayoutPanel;
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GSplitLayoutPanel;
import com.codenvy.modeling.generator.builders.xml.impl.UIXmlBuilderImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GButtonImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GHtmlImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GLabelImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GTextAreaImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GTextBoxImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GDockLayoutPanelImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GFlowPanelImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GScrollPanelImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GSimpleLayoutPanelImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GSplitLayoutPanelImpl;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * The module that contains information about configuration of generator.
 *
 * @author Andrey Plotnikov
 */
public class GeneratorModule extends AbstractModule {

    /** {@inheritDoc} */
    @Override
    protected void configure() {
        // Configuration of configuration factory
        install(new FactoryModuleBuilder().build(ConfigurationFactoryCreator.class));
        bind(ConfigurationFactory.class).to(ConfigurationFactoryImpl.class);

        // Configuration of configuration keeper
        install(new FactoryModuleBuilder().build(ConfigurationKeeperFactory.class));
        bind(ConfigurationKeeper.class).to(ConfigurationKeeperImpl.class);
        install(new FactoryModuleBuilder().build(ConfigurationAdapterFactory.class));

        // Validator configuration
        install(new FactoryModuleBuilder().build(ValidatorFactory.class));

        bind(EditorFileConsistencyValidator.class).to(EditorFileConsistencyValidatorImpl.class);
        bind(SerializationFileConsistencyValidator.class).to(SerializationFileConsistencyValidatorImpl.class);
        bind(DiagramFileConsistencyValidator.class).to(DiagramFileConsistencyValidatorImpl.class);
        // TODO add style validator

        // Java code builder configuration
        bind(SourceCodeBuilder.class).to(SourceCodeBuilderImpl.class);

        // UI XML builder configuration
        bind(UIXmlBuilder.class).to(UIXmlBuilderImpl.class);

        bind(GButton.class).to(GButtonImpl.class);
        bind(GHtml.class).to(GHtmlImpl.class);
        bind(GLabel.class).to(GLabelImpl.class);
        bind(GTextArea.class).to(GTextAreaImpl.class);
        bind(GTextBox.class).to(GTextBoxImpl.class);

        bind(GScrollPanel.class).to(GScrollPanelImpl.class);
        bind(GFlowPanel.class).to(GFlowPanelImpl.class);
        bind(GSimpleLayoutPanel.class).to(GSimpleLayoutPanelImpl.class);
        bind(GDockLayoutPanel.class).to(GDockLayoutPanelImpl.class);
        bind(GSplitLayoutPanel.class).to(GSplitLayoutPanelImpl.class);
    }

}