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

package com.codenvy.modeling.configuration.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 * @author Dmitry Kuleshov
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class ConfigurationConstraintsValidator {
    public static final String SIMPLE_TEXT = "^[a-zA-Z0-9_]+$";

    public static Report validate(Object object) {
        Report report = Report.getEmptyReport();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        for (ConstraintViolation<?> violation : validator.validate(object)) {
            report.addError(violation.getMessage());
        }

        return report;
    }

}