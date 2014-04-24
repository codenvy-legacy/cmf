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

package com.codenvy.modeling.configuration.validation.pre;

import com.codenvy.modeling.configuration.validation.Report;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Dmitry Kuleshov
 */
public class ProjectDescriptorValidator extends AbstractPersistenceValidator {

    private String path;
    private Report report;

    private static final String PROPERTY_NOT_EXIST      = "Property '%s' doesn't exist.";
    private static final String DIRECTORY_NOT_EXIST     = "Directory '%s' doesn't exist.";
    private static final String PROPERTY_VALUE_IS_EMPTY = "Property '%s' value is empty.";

    enum DescriptorProperty {
        metamodel, serialization, editor, style
    }

    public ProjectDescriptorValidator(String path) {
        this.path = path;
        this.report = new Report();
    }

    @Nonnull
    @Override
    public Report getReport() {
        try (FileInputStream descriptorStream = new FileInputStream(path)) {
            Properties descriptor = new Properties();
            descriptor.load(descriptorStream);

            validate(descriptor, DescriptorProperty.metamodel, true);
            validate(descriptor, DescriptorProperty.serialization, false);
            validate(descriptor, DescriptorProperty.editor, false);
            validate(descriptor, DescriptorProperty.style, false);
        } catch (IOException e) {
            report.addValidationError(e.getLocalizedMessage());
        }

        return report;
    }

    private void validate(Properties properties, DescriptorProperty property, boolean mandatory) {
        if (isPropertyExists(properties, property.name(), mandatory) &&
            !isPropertyValueEmpty(properties, property.name())) {
            checkDirectoryExistence(properties, property.name());
        }
    }

    private boolean isPropertyExists(Properties properties, String property, boolean mandatory) {
        if (!properties.containsKey(property)) {
            if (mandatory) {
                report.addValidationError(String.format(PROPERTY_NOT_EXIST, property));
            }
            return false;
        }

        return true;
    }

    private boolean isPropertyValueEmpty(Properties properties, String property) {
        if (properties.getProperty(property) == null || properties.getProperty(property).trim().isEmpty()) {
            report.addValidationError(String.format(PROPERTY_VALUE_IS_EMPTY, property));
            return true;
        }

        return false;
    }

    private void checkDirectoryExistence(Properties properties, String property) {
        final String directoryPath = properties.getProperty(property);
        final File ioFile = new File(directoryPath);
        if (!ioFile.exists() || !ioFile.isDirectory()) {
            report.addValidationError(String.format(DIRECTORY_NOT_EXIST, ioFile.getPath()));
        }
    }
}
