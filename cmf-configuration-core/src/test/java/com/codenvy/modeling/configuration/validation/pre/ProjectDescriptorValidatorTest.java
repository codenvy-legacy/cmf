package com.codenvy.modeling.configuration.validation.pre;

import com.codenvy.modeling.configuration.ConfigurationFactory;
import com.codenvy.modeling.configuration.validation.Report;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

/**
 * @author Vladyslav Zhukovskii
 */
public class ProjectDescriptorValidatorTest {
    private static final String META_MODEL_DIR    = "metamodel_config";
    private static final String SERIALIZATION_DIR = "serialization_config";
    private static final String EDITOR_DIR        = "editor_config";
    private static final String STYLE_DIR         = "style_config";
    private static final String DESCRIPTOR_FILE   = "descriptor";

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testGettingEmptyReport() throws Exception {
        folder.create();

        File metaModelDir = folder.newFolder(META_MODEL_DIR);
        File serializationDir = folder.newFolder(SERIALIZATION_DIR);
        File editorDir = folder.newFolder(EDITOR_DIR);
        File styleDir = folder.newFolder(STYLE_DIR);

        Properties props = new Properties();
        props.put(ConfigurationFactory.PathParameter.DIAGRAM.name().toLowerCase(), metaModelDir.getPath());
        props.put(ConfigurationFactory.PathParameter.SERIALIZATION.name().toLowerCase(), serializationDir.getPath());
        props.put(ConfigurationFactory.PathParameter.EDITOR.name().toLowerCase(), editorDir.getPath());
        props.put(ConfigurationFactory.PathParameter.STYLE.name().toLowerCase(), styleDir.getPath());

        File descriptor = folder.newFile(DESCRIPTOR_FILE);

        props.store(new FileOutputStream(descriptor), "");

        Report report = new ProjectDescriptorValidator(descriptor.getPath()).getReport();

        assertEquals(report.hasErrors(), false);

        folder.delete();
    }

    @Test
    public void testShouldReturnAllErrorsInReport() throws Exception {
        folder.create();

        File descriptor = folder.newFile(DESCRIPTOR_FILE);

        new Properties().store(new FileOutputStream(descriptor), "");

        Report report = new ProjectDescriptorValidator(descriptor.getPath()).getReport();

        assertEquals(report.hasErrors(), true);
        assertEquals(report.getErrors().size(), 1);

        folder.delete();
    }

    @Test
    public void testShouldReturnErrorsWithValueEmptyMessage() throws Exception {
        folder.create();

        Properties props = new Properties();
        props.put(ConfigurationFactory.PathParameter.DIAGRAM.name().toLowerCase(), "");
        props.put(ConfigurationFactory.PathParameter.SERIALIZATION.name().toLowerCase(), "");
        props.put(ConfigurationFactory.PathParameter.EDITOR.name().toLowerCase(), "");
        props.put(ConfigurationFactory.PathParameter.STYLE.name().toLowerCase(), "");

        File descriptor = folder.newFile(DESCRIPTOR_FILE);

        props.store(new FileOutputStream(descriptor), "");

        Report report = new ProjectDescriptorValidator(descriptor.getPath()).getReport();

        assertEquals(report.hasErrors(), true);
        assertEquals(report.getErrors().size(), 4);

        folder.delete();
    }

    @Test
    public void testShouldReturnErrorsWithInvalidDirectoryMessage() throws Exception {
        folder.create();

        File metaModelDir = folder.newFile(META_MODEL_DIR);
        File serializationDir = folder.newFile(SERIALIZATION_DIR);
        File editorDir = folder.newFile(EDITOR_DIR);
        File styleDir = folder.newFile(STYLE_DIR);

        Properties props = new Properties();
        props.put(ConfigurationFactory.PathParameter.DIAGRAM.name().toLowerCase(), metaModelDir.getPath());
        props.put(ConfigurationFactory.PathParameter.SERIALIZATION.name().toLowerCase(), serializationDir.getPath());
        props.put(ConfigurationFactory.PathParameter.EDITOR.name().toLowerCase(), editorDir.getPath());
        props.put(ConfigurationFactory.PathParameter.STYLE.name().toLowerCase(), styleDir.getPath());

        File descriptor = folder.newFile(DESCRIPTOR_FILE);

        props.store(new FileOutputStream(descriptor), "");

        Report report = new ProjectDescriptorValidator(descriptor.getPath()).getReport();

        assertEquals(report.hasErrors(), true);
        assertEquals(report.getErrors().size(), 4);

        folder.delete();
    }
}
