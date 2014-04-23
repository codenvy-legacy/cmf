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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Dmitry Kuleshov
 */
public abstract class ValidationManager {

    private static final Logger LOG = LoggerFactory.getLogger(ValidationManager.class);

    private List<Validator> validators = new LinkedList<>();

    public ValidationManager (@Nonnull  List<Validator> validators){
        for (Validator validator : validators){
            validateValidator(validator);
        }
    }

    private void validateValidator(@Nonnull Validator validator) {
        if (validator.getType().equals(getType())) {
            validators.add(validator);
        } else {
            LOG.error("Expected " + getType() + " validator");
        }
    }

    @Nonnull
    public List<Report> getValidationReports() {
        List<Report> reports = new LinkedList<>();
        for (Validator validator : validators) {
            reports.add(validator.getReport());
        }

        return reports;
    }

    protected abstract Validator.Type getType();

}

