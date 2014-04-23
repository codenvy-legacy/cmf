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

import com.codenvy.modeling.adapter.metamodel.diagram.DiagramConfigurationAdapter;
import com.codenvy.modeling.adapter.metamodel.serialization.SerializationConfigurationAdapter;
import com.codenvy.modeling.configuration.ConfigurationFactory;
import com.codenvy.modeling.configuration.ConfigurationKeeper;
import com.codenvy.modeling.configuration.ConfigurationKeeperImpl;
import com.codenvy.modeling.configuration.validation.DiagramFileConsistencyValidatorImpl;
import com.codenvy.modeling.configuration.validation.SerializationFileConsistencyValidatorImpl;
import com.codenvy.modeling.configuration.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Dmitry Kuleshov
 */
public class ConfigurationFactoryImpl implements ConfigurationFactory {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationKeeperImpl.class);

    private Map<String, String> parameters = new HashMap<>();

    public ConfigurationFactoryImpl(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public ConfigurationKeeper getConfigurationKeeperInstance() {
        ConfigurationKeeperImpl configurationKeeperImpl = new ConfigurationKeeperImpl();
        try {
            setDiagramConfiguration(configurationKeeperImpl);
            setSerializationConfiguration(configurationKeeperImpl);

        } catch (IOException e) {
            LOG.error("Trying to create configuration keeper occurred an error: ", e);
        }

        return configurationKeeperImpl;
    }

    @Override
    public List<Validator> getValidatorsListInstance() {
        List<Validator> validators = new LinkedList<>();

        validators.add(new DiagramFileConsistencyValidatorImpl(parameters.get(PathParam.DIAGRAM)));
        validators.add(new SerializationFileConsistencyValidatorImpl(parameters.get(PathParam.SERIALIZATION)));

        return validators;
    }

    private void setSerializationConfiguration(ConfigurationKeeperImpl configurationKeeperImpl) throws IOException {
        configurationKeeperImpl.setSerializationConfiguration(
                (
                        new SerializationConfigurationAdapter(
                                new FileInputStream(
                                        new File(parameters.get(PathParam.SERIALIZATION))
                                )
                        )
                ).getConfiguration()
        );
    }

    private void setDiagramConfiguration(ConfigurationKeeperImpl configurationKeeperImpl) throws IOException {
        configurationKeeperImpl.setDiagramConfiguration(
                (
                        new DiagramConfigurationAdapter(
                                new FileInputStream(
                                        new File(parameters.get(PathParam.DIAGRAM))
                                )
                        )
                ).getConfiguration()
        );
    }


}
