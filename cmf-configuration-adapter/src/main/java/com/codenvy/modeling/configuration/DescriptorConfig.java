package com.codenvy.modeling.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Properties;

/**
 * Define configuration description, that store
 * placement of meta model, serialization, editor
 * and style directory configuration.
 * Perform validation on input properties file.
 *
 * Input properties file should be in format like
 * <code>key=value</code>
 *
 * Key in this case is one of four allowed names:
 * <ul>
 *     <li>metamodel</li>
 *     <li>serialization</li>
 *     <li>editor</li>
 *     <li>style</li>
 * </ul>
 *
 * and value in this case describe directory where
 * configuration files are placed.
 *
 * @author Vladyslav Zhukovskii
 */
public class DescriptorConfig {

    /** Pre-defined name for properties in property file. */
    public enum Property {
        META_MODEL("metamodel"),
        SERIALIZATION("serialization"),
        EDITOR("editor"),
        STYLE("style");

        private final String value;

        Property(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    private final Properties properties = new Properties();

    private static final String META_MODEL_FILE_EXT = ".g4";

    /** Filter for antlr meta model configuration files. */
    private FilenameFilter META_MODEL_EXTENSION_FILTER = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(META_MODEL_FILE_EXT);
        }
    };

    public DescriptorConfig(File descriptorConfigFile) throws InvalidDescriptorException {
        try {
            properties.load(new FileInputStream(descriptorConfigFile));
        } catch (FileNotFoundException e) {
            throw new InvalidDescriptorException("Configuration descriptor file doesn't exist.");
        } catch (IOException e) {
            throw new InvalidDescriptorException(e.getMessage(), e);
        }

        validateDescriptorConfiguration();
    }

    /** Perform validation of input configuration. */
    private void validateDescriptorConfiguration() throws InvalidDescriptorException {
        if (properties.size() == 0) {
            throw new InvalidDescriptorException("Configuration descriptor is empty.");
        }

        for (Property property : Property.values()) {
            if (property == Property.META_MODEL && !properties.containsKey(property.value())) {
                throw new InvalidDescriptorException("Property '" + property.value() + "' doesn't exist.");
            }

            if (properties.containsKey(property.value())) {
                if (properties.getProperty(property.value()).trim().isEmpty()) {
                    throw new InvalidDescriptorException("Property '" + property.value() + "' value is empty.");
                } else {
                    File folder = new File(properties.getProperty(property.value()));
                    if (!folder.isDirectory()) {
                        throw new InvalidDescriptorException("Path '" + folder.getPath() + "' is not a directory.");
                    }

                    if (property == Property.META_MODEL) {
                        if (folder.listFiles(META_MODEL_EXTENSION_FILTER).length == 0) {
                            throw new InvalidDescriptorException(
                                    "Directory '" + folder.getPath() + "' doesn't contains meta model configuration.");
                        }
                    }
                }
            }
        }
    }

    /** Return path to meta model directory where configuration placed. */
    public String getMetaModelConfigDir() {
        return properties.getProperty(Property.META_MODEL.value());
    }

    /** Return path to serialization directory where placed configuration for serialization. */
    public String getSerializationConfigDir() {
        return properties.getProperty(Property.SERIALIZATION.value());
    }

    /** Return path to editor directory where placed editor configuration. */
    public String getEditorConfigDir() {
        return properties.getProperty(Property.EDITOR.value());
    }

    /** Return path to style directory where placed style configuration. */
    public String getStyleConfigDir() {
        return properties.getProperty(Property.STYLE.value());
    }
}
