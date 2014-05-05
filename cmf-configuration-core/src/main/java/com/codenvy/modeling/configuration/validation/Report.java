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

    private List<Error> errors = new LinkedList<>();

    private Report() {
    }

    public static Report getEmptyReport() {
        return new Report();
    }

    public void addError(@Nonnull Error validationError) {
        errors.add(validationError);
    }

    /**
     * Will add error with null exception
     */
    public void addError(@Nonnull String message, @Nonnull Exception exception) {
        errors.add(new Error(message, exception));
    }

    /**
     * Will add error with null exception
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

    public static class Error {
        @Nonnull
        private String    message;
        @Nullable
        private Exception exception;

        public Error(@Nonnull String message, @Nullable Exception exception) {
            this.message = message;
            this.exception = exception;
        }

        public Error(@Nonnull String message) {
            this(message, null);
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
    }
}
