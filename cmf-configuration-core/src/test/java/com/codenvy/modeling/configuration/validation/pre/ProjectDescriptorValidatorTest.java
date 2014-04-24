package com.codenvy.modeling.configuration.validation.pre;

import com.codenvy.modeling.configuration.validation.Report;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Vladyslav Zhukovskii
 */
public class ProjectDescriptorValidatorTest {
    private static final String META_MODEL_DIR    = "metamodel_config";
    private static final String SERIALIZATION_DIR = "serialization_config";
    private static final String EDITOR_DIR        = "editor_config";
    private static final String STYLE_DIR         = "style_config";
    private static final String DESCRIPTOR_FILE   = "descriptor";

    private static final String PROPERTY_NOT_EXIST      = "Property '%s' doesn't exist.";
    private static final String DIRECTORY_NOT_EXIST     = "Directory '%s' doesn't exist.";
    private static final String PROPERTY_VALUE_IS_EMPTY = "Property '%s' value is empty.";

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
        props.put(ProjectDescriptorValidator.DescriptorProperty.metamodel.name(), metaModelDir.getPath());
        props.put(ProjectDescriptorValidator.DescriptorProperty.serialization.name(), serializationDir.getPath());
        props.put(ProjectDescriptorValidator.DescriptorProperty.editor.name(), editorDir.getPath());
        props.put(ProjectDescriptorValidator.DescriptorProperty.style.name(), styleDir.getPath());

        File descriptor = folder.newFile(DESCRIPTOR_FILE);

        props.store(new FileOutputStream(descriptor), "");

        Report report = new ProjectDescriptorValidator(descriptor.getPath()).getReport();

        assertEquals(report.hasErrors(), false);
        assertEquals(report.hasWarning(), false);

        folder.delete();
    }

    @Test
    public void testShouldReturnAllErrorsInReport() throws Exception {
        folder.create();

        File descriptor = folder.newFile(DESCRIPTOR_FILE);

        new Properties().store(new FileOutputStream(descriptor), "");

        Report report = new ProjectDescriptorValidator(descriptor.getPath()).getReport();

        assertEquals(report.hasErrors(), true);
        assertEquals(report.hasWarning(), false);
        assertEquals(report.getValidationErrors().size(), 1);
        assertTrue(report.getValidationErrors()
                         .contains(String.format(PROPERTY_NOT_EXIST, ProjectDescriptorValidator.DescriptorProperty.metamodel.name())));

        folder.delete();
    }

    @Test
    public void testShouldReturnErrorsWithValueEmptyMessage() throws Exception {
        folder.create();

        Properties props = new Properties();
        props.put(ProjectDescriptorValidator.DescriptorProperty.metamodel.name(), "");
        props.put(ProjectDescriptorValidator.DescriptorProperty.serialization.name(), "");
        props.put(ProjectDescriptorValidator.DescriptorProperty.editor.name(), "");
        props.put(ProjectDescriptorValidator.DescriptorProperty.style.name(), "");

        File descriptor = folder.newFile(DESCRIPTOR_FILE);

        props.store(new FileOutputStream(descriptor), "");

        Report report = new ProjectDescriptorValidator(descriptor.getPath()).getReport();

        assertEquals(report.hasErrors(), true);
        assertEquals(report.hasWarning(), false);
        assertEquals(report.getValidationErrors().size(), 4);
        assertTrue(report.getValidationErrors()
                         .remove(String.format(PROPERTY_VALUE_IS_EMPTY, ProjectDescriptorValidator.DescriptorProperty.metamodel.name())));
        assertTrue(report.getValidationErrors()
                         .remove(String.format(PROPERTY_VALUE_IS_EMPTY,
                                               ProjectDescriptorValidator.DescriptorProperty.serialization.name())));
        assertTrue(report.getValidationErrors()
                         .remove(String.format(PROPERTY_VALUE_IS_EMPTY, ProjectDescriptorValidator.DescriptorProperty.editor.name())));
        assertTrue(report.getValidationErrors()
                         .remove(String.format(PROPERTY_VALUE_IS_EMPTY, ProjectDescriptorValidator.DescriptorProperty.style.name())));
        assertEquals(report.hasErrors(), false);

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
        props.put(ProjectDescriptorValidator.DescriptorProperty.metamodel.name(), metaModelDir.getPath());
        props.put(ProjectDescriptorValidator.DescriptorProperty.serialization.name(), serializationDir.getPath());
        props.put(ProjectDescriptorValidator.DescriptorProperty.editor.name(), editorDir.getPath());
        props.put(ProjectDescriptorValidator.DescriptorProperty.style.name(), styleDir.getPath());

        File descriptor = folder.newFile(DESCRIPTOR_FILE);

        props.store(new FileOutputStream(descriptor), "");

        Report report = new ProjectDescriptorValidator(descriptor.getPath()).getReport();

        assertEquals(report.hasErrors(), true);
        assertEquals(report.hasWarning(), false);
        assertEquals(report.getValidationErrors().size(), 4);
        assertTrue(report.getValidationErrors().remove(String.format(DIRECTORY_NOT_EXIST, metaModelDir.getPath())));
        assertTrue(report.getValidationErrors().remove(String.format(DIRECTORY_NOT_EXIST, serializationDir.getPath())));
        assertTrue(report.getValidationErrors().remove(String.format(DIRECTORY_NOT_EXIST, editorDir.getPath())));
        assertTrue(report.getValidationErrors().remove(String.format(DIRECTORY_NOT_EXIST, styleDir.getPath())));
        assertEquals(report.hasErrors(), false);

        folder.delete();
    }
}
