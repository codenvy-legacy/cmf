package com.codenvy.modeling.configuration.validation;

import com.codenvy.modeling.configuration.ConfigurationFactory;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.rules.RuleChain;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import static com.codenvy.modeling.configuration.ConfigurationFactory.PathParameter;
import static com.codenvy.modeling.configuration.ConfigurationFactory.PathParameter.DIAGRAM;
import static com.codenvy.modeling.configuration.ConfigurationFactory.PathParameter.EDITOR;
import static com.codenvy.modeling.configuration.ConfigurationFactory.PathParameter.SERIALIZATION;
import static com.codenvy.modeling.configuration.ConfigurationFactory.PathParameter.STYLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Vladyslav Zhukovskii
 * @author Dmitry Kuleshov
 */
public class ProjectDescriptorValidatorTest {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectDescriptorValidator.class);

    private static final String          DESCRIPTOR_FILE     = "descriptor";
    public static final  PathParameter[] ALL_PATH_PARAMETERS = new PathParameter[]{
            DIAGRAM,
            SERIALIZATION,
            EDITOR,
            STYLE
    };

    public TemporaryFolder     rootFolder        = new TemporaryFolder();
    public TemporaryDescriptor descriptorFile    = new TemporaryDescriptor();
    public PropertiesBuilder   propertiesBuilder = new PropertiesBuilder();

    @Rule
    public TestRule chain = RuleChain
            .outerRule(rootFolder)
            .around(descriptorFile)
            .around(propertiesBuilder);


    @Test
    public void shouldReturnEmptyReportAfterDiagramMandatoryPropertyIsSet() throws Exception {
        checkAdditionOfArbitraryNumberOfCorrectProperties(DIAGRAM);
    }

    @Test
    public void shouldReturnEmptyReportAfterAllPropertiesAreSetCorrectly() throws Exception {
        checkAdditionOfArbitraryNumberOfCorrectProperties(ALL_PATH_PARAMETERS);
    }

    @Test
    public void shouldReturnErrorReportAfterNotAllMandatoryPropertiesAreSet() throws Exception {
        Properties properties = propertiesBuilder.build();
        descriptorFile.storeProperties(properties);

        Report report = new ProjectDescriptorValidator(descriptorFile.getPath()).getReport();

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void shouldReturnErrorReportAfterEmptyDiagramPropertyValueIsSet() throws Exception {
        checkAddingEmptyProperties(DIAGRAM);
    }

    @Test
    public void shouldReturnErrorReportAfterEmptySerializationPropertyValueIsSet() throws Exception {
        checkAddingEmptyProperties(SERIALIZATION);
    }

    @Test
    public void shouldReturnErrorReportAfterEmptyEditorPropertyValueIsSet() throws Exception {
        checkAddingEmptyProperties(EDITOR);
    }

    @Test
    public void shouldReturnErrorReportAfterEmptyStylePropertyValueIsSet() throws Exception {
        checkAddingEmptyProperties(STYLE);
    }

    @Test
    public void shouldReturnThreeErrorsAfterEmptyPropertyValuesAreSet() throws Exception {
        checkAddingEmptyProperties(DIAGRAM, SERIALIZATION, EDITOR, STYLE);
    }

    private void checkAddingEmptyProperties(ConfigurationFactory.PathParameter... parameters) throws IOException {
        checkAdditionOfArbitraryNumberOfIncorrectProperties("", parameters);
    }

    @Test
    public void shouldReturnErrorReportAfterMalformedDiagramPropertyValueIsSet() throws Exception {
        checkAddingMalformedProperties(DIAGRAM);
    }

    @Test
    public void shouldReturnErrorReportAfterMalformedSerializationPropertyValueIsSet() throws Exception {
        checkAddingMalformedProperties(SERIALIZATION);
    }

    @Test
    public void shouldReturnErrorReportAfterMalformedEditorPropertyValueIsSet() throws Exception {
        checkAddingMalformedProperties(EDITOR);
    }

    @Test
    public void shouldReturnErrorReportAfterMalformedStylePropertyValueIsSet() throws Exception {
        checkAddingMalformedProperties(STYLE);
    }

    @Test
    public void shouldReturnThreeErrorsAfterMalformedPropertyValuesAreSet() throws Exception {
        checkAddingMalformedProperties(DIAGRAM, SERIALIZATION, EDITOR, STYLE);
    }

    private void checkAddingMalformedProperties(ConfigurationFactory.PathParameter... parameters) throws IOException {
        checkAdditionOfArbitraryNumberOfIncorrectProperties(String.valueOf(System.currentTimeMillis()), parameters);
    }

    private void checkAdditionOfArbitraryNumberOfIncorrectProperties(String value, ConfigurationFactory.PathParameter... parameters)
            throws IOException {
        // first let's add mandatory parameters
        // TODO probably should be reorganized after more mandatory properties are introduced
        addMandatoryProperties();

        for (ConfigurationFactory.PathParameter parameter : parameters) {
            propertiesBuilder.add(parameter, value);
        }

        descriptorFile.storeProperties(propertiesBuilder.build());
        Report report = new ProjectDescriptorValidator(descriptorFile.getPath()).getReport();

        assertTrue(report.hasErrors());
        assertEquals(parameters.length, report.getErrors().size());
    }


    private void checkAdditionOfArbitraryNumberOfCorrectProperties(ConfigurationFactory.PathParameter... parameters) throws Exception {
        // first let's add mandatory parameters
        // TODO probably should be reorganized after more mandatory properties are introduced
        addMandatoryProperties();

        for (ConfigurationFactory.PathParameter parameter : parameters) {
            propertiesBuilder.add(parameter, rootFolder.newFolder().getPath());
        }

        descriptorFile.storeProperties(propertiesBuilder.build());

        Report report = new ProjectDescriptorValidator(descriptorFile.getPath()).getReport();

        assertFalse(report.hasErrors());
        assertTrue(report.getErrors().isEmpty());
    }

    private void addMandatoryProperties() throws IOException {
        for (PathParameter parameter : PathParameter.values()) {
            if (parameter.isMandatory()) {
                propertiesBuilder.add(parameter, rootFolder.newFolder().getPath());
            }
        }
    }

    private class TemporaryDescriptor extends ExternalResource {

        private FileWriter fileWriter;
        private String     path;

        @Override
        protected void before() throws Throwable {
            File file = rootFolder.newFile(DESCRIPTOR_FILE);

            path = file.getPath();
            fileWriter = new FileWriter(file);
        }

        @Override
        protected void after() {
            path = null;

            try {
                fileWriter.close();
            } catch (IOException e) {
                LOG.error("An error while trying to close project descriptor file writer.", e);
            }
        }

        protected void storeProperties(Properties properties) throws IOException {
            properties.store(fileWriter, "");
        }

        protected String getPath() {
            return path;
        }
    }

    private class PropertiesBuilder extends ExternalResource {
        private Properties properties;

        public PropertiesBuilder add(ConfigurationFactory.PathParameter pathParameter, String value) {
            properties.setProperty(pathParameter.name().toLowerCase(), value);
            return this;
        }

        public Properties build() {
            return properties;
        }

        @Override
        protected void before() throws Throwable {
            if (properties == null) {
                properties = new Properties();
            }
        }

        @Override
        protected void after() {
            properties.clear();
        }
    }

}
