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

package com.codenvy.modeling.configuration.validation.integrity;

import com.codenvy.modeling.configuration.validation.Report;


/**
 * @author Dmitry Kuleshov
 */
public class ConfigurationIntegrityValidator {

    public static Report validate(Object object) {
        Report report = Report.getEmptyReport();

        IntegrityCheckRegistry integrityCheckRegistry = new IntegrityCheckRegistry();
        IntegrityCheckRegistry.Initializer.initialize(integrityCheckRegistry, object);
        IntegrityCheckRegistry.Analyzer.analyze(integrityCheckRegistry, report);

        return report;
    }
}
