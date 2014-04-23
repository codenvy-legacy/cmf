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

package com.codenvy.modeling.configuration.validation.post;

import com.codenvy.modeling.configuration.ConfigurationKeeper;
import com.codenvy.modeling.configuration.validation.Report;

import javax.annotation.Nonnull;

/**
 * @author Dmitry Kuleshov
 */
public class SerializationConfigurationValidator extends AbstractConfigurationValidator {

    public SerializationConfigurationValidator(@Nonnull ConfigurationKeeper configurationKeeper) {
        super(configurationKeeper);
    }

    @Override
    public Report getReport() {
        return null;
    }
}
