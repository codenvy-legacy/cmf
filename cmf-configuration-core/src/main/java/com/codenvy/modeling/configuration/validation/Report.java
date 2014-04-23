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

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Dmitry Kuleshov
 */
public class Report {

    private String type;

    private List<String> validationErrors = new LinkedList<>();

    private List<String> validationWarnings = new LinkedList<>();

    public String getType() {
        return type;
    }

    @Nonnull
    public List<String> getValidationErrors() {
        return validationErrors;
    }

    @Nonnull
    public List<String> getValidationWarnings() {
        return validationWarnings;
    }

    public void setType(@Nonnull String type) {
        this.type = type;
    }

    public void addValidationError(@Nonnull String validationError) {
        validationErrors.add(validationError);
    }

    public void addValidationWarning(@Nonnull String validationWarning) {
        validationWarnings.add(validationWarning);
    }

    public boolean hasErrors() {
        return !validationErrors.isEmpty();
    }

    public boolean hasWarning() {
        return !validationWarnings.isEmpty();
    }
}
