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

package com.codenvy.modeling;

import com.codenvy.modeling.configuration.ConfigurationFactory;
import com.codenvy.modeling.configuration.ConfigurationKeeper;
import com.codenvy.modeling.configuration.ConfigurationKeeperFactory;
import com.codenvy.modeling.configuration.ValidatorFactory;
import com.codenvy.modeling.configuration.validation.Validator;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.codenvy.modeling.configuration.ConfigurationFactory.PathParam.DIAGRAM;
import static com.codenvy.modeling.configuration.ConfigurationFactory.PathParam.EDITOR;
import static com.codenvy.modeling.configuration.ConfigurationFactory.PathParam.SERIALIZATION;
import static com.codenvy.modeling.configuration.ConfigurationFactory.PathParam.STYLE;

/**
 * @author Dmitry Kuleshov
 * @author Andrey Plotnikov
 */
public class ConfigurationFactoryImpl implements ConfigurationFactory {

    private final Map<PathParam, String>     parameters;
    private final ConfigurationKeeperFactory factory;
    private final ValidatorFactory           validatorFactory;

    @Inject
    public ConfigurationFactoryImpl(@Assisted String configurationPath,
                                    ConfigurationKeeperFactory keeperFactory,
                                    ValidatorFactory validatorFactory) {
        // TODO add an ability to get path parameters from main path
        this.parameters = new HashMap<>();
        this.factory = keeperFactory;
        this.validatorFactory = validatorFactory;
    }

    @Nonnull
    @Override
    public ConfigurationKeeper getConfigurationKeeperInstance() throws IOException {
        return factory.createConfKeeper(parameters.get(DIAGRAM),
                                        parameters.get(EDITOR),
                                        parameters.get(SERIALIZATION),
                                        parameters.get(STYLE));
    }

    @Nonnull
    @Override
    public List<Validator> getValidatorsListInstance() {
        List<Validator> validators = new LinkedList<>();

        validators.add(validatorFactory.createDiagramValidator(parameters.get(DIAGRAM)));
        validators.add(validatorFactory.createSerializationValidator(parameters.get(SERIALIZATION)));
        validators.add(validatorFactory.createEditorValidator(parameters.get(EDITOR)));
        // TODO add style validator

        return validators;
    }

}
