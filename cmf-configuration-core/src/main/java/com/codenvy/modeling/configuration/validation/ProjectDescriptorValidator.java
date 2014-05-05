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

import com.codenvy.modeling.configuration.ConfigurationFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author Dmitry Kuleshov
 */
public class ProjectDescriptorValidator {
    enum ErrorType {
        PARAMETER_ABSENCE("Mandatory parameter '%s' is absent."),
        EMPTY_VALUE("Parameter '%s' value is empty."),
        MALFORMED_VALUE("Parameter '%s' value is malformed."),
        NOT_AN_ERROR(null);

        @Nullable
        private String pattern;

        ErrorType(@Nullable String pattern) {
            this.pattern = pattern;
        }

        @Nullable
        public String getPattern() {
            return pattern;
        }
    }

    @Nonnull
    private Properties descriptorProperties;
    @Nonnull
    private Report     report;


    public ProjectDescriptorValidator(@Nonnull String path) {
        report = Report.getEmptyReport();
        descriptorProperties = loadDescriptor(path);
        validateParameters();
    }

    private Properties loadDescriptor(@Nonnull String path) {
        Properties descriptorProperties = new Properties();

        try (FileInputStream descriptorStream = new FileInputStream(path)) {
            descriptorProperties.load(descriptorStream);
        } catch (IOException e) {
            report.addError(e.getLocalizedMessage());
        }

        return descriptorProperties;
    }

    private void validateParameters() {
        for (ConfigurationFactory.PathParameter parameter : ConfigurationFactory.PathParameter.values()) {
            validate(parameter);
        }
    }

    private void validate(@Nonnull ConfigurationFactory.PathParameter parameter) {
        String parameterValue = descriptorProperties.getProperty(parameter.name().toLowerCase());
        ErrorType errorType = specifyErrorType(parameterValue, parameter);

        if (errorType != ErrorType.NOT_AN_ERROR && errorType.getPattern() != null) {
            report.addError(String.format(errorType.getPattern(), parameter.name().toLowerCase()));
        }
    }

    @Nonnull
    private ErrorType specifyErrorType(@Nonnull String parameterValue, @Nonnull ConfigurationFactory.PathParameter parameter) {
        ErrorType errorType = ErrorType.NOT_AN_ERROR;

        if (parameterValue == null && parameter.isMandatory()) {
            errorType = ErrorType.PARAMETER_ABSENCE;
        }

        if (parameterValue != null && parameterValue.isEmpty()) {
            errorType = ErrorType.EMPTY_VALUE;
        }

        if (parameterValue != null && !Files.isDirectory(Paths.get(parameterValue))) {
            errorType = ErrorType.MALFORMED_VALUE;
        }

        return errorType;
    }

    @Nonnull
    public Report getReport() {
        return report;
    }
}
