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

import com.codenvy.modeling.adapter.AdapterFactory;
import com.codenvy.modeling.generator.builders.java.SourceCodeBuilder;
import com.codenvy.modeling.generator.builders.java.SourceCodeBuilderImpl;
import com.codenvy.modeling.generator.builders.xml.api.UIXmlBuilder;
import com.codenvy.modeling.generator.builders.xml.impl.UIXmlBuilderImpl;
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
        install(new FactoryModuleBuilder().build(AdapterFactory.class));

        bind(SourceCodeBuilder.class).to(SourceCodeBuilderImpl.class);

        bind(UIXmlBuilder.class).to(UIXmlBuilderImpl.class);
    }

}