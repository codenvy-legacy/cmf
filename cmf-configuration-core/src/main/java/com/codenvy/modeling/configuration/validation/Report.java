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
import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Dmitry Kuleshov
 */
public class Report {

    public static class Error {
        public enum Severity {
            HIGH, LOW
        }

        private String    message;
        private Exception exception;
        private Severity  severity;

        public Error(String message, Exception exception, Severity severity) {
            this.message = message;
            this.exception = exception;
            this.severity = severity;
        }

        public Error(String message, Severity severity) {
            this.message = message;
            this.severity = severity;
        }

        public Error(String message) {
            this.message = message;
            this.severity = Severity.LOW;
        }

        public Error(String message, Exception exception) {
            this.message = message;
            this.exception = exception;
            this.severity = Severity.LOW;
        }

        @Nonnull
        public String getMessage() {
            return message;
        }


        public void setMessage(@Nonnull String message) {
            this.message = message;
        }

        @Nullable
        public Exception getException() {
            return exception;
        }

        public void setException(@Nullable Exception exception) {
            this.exception = exception;
        }

        @Nonnull
        public Severity getSeverity() {
            return severity;
        }

        public void setSeverity(@Nonnull Severity severity) {
            this.severity = severity;
        }
    }

    private String type;

    private List<Error> errors = new LinkedList<>();

    public void setType(@Nonnull String type) {
        this.type = type;
    }

    @Nonnull
    public String getType() {
        return type;
    }

    public void addError(@Nonnull Error validationError) {
        errors.add(validationError);
    }

    public void addError(@Nonnull String message, @Nonnull Exception exception, @Nonnull Error.Severity severity) {
        errors.add(new Error(message, exception, severity));
    }

    /**
     * Will add error with null exception
     */
    public void addError(@Nonnull String message, @Nonnull Exception exception) {
        errors.add(new Error(message, exception));
    }

    /**
     * Will add error with default (LAW) severity
     */
    public void addError(@Nonnull String message, @Nonnull Error.Severity severity) {
        errors.add(new Error(message, severity));
    }

    /**
     * Will add error with null exception and default (LAW) severity
     */
    public void addError(@Nonnull String message) {
        errors.add(new Error(message));
    }

    @Nonnull
    public List<Error> getErrors() {
        return errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
