package com.codenvy.modeling.configuration.metamodel;

import com.codenvy.modeling.configuration.DescriptorConfig;
import com.codenvy.modeling.configuration.InvalidDescriptorException;

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
public class DescriptorConfigTest {

    private static final String META_MODEL_DIR    = "metamodel_config";
    private static final String SERIALIZATION_DIR = "serialization_config";
    private static final String EDITOR_DIR        = "editor_config";
    private static final String STYLE_DIR         = "style_config";
    private static final String DESCRIPTOR_FILE   = "descriptor";

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testGettingConfiguration() throws Exception {
        folder.create();

        File metaModelDir = folder.newFolder(META_MODEL_DIR);
        File serializationDir = folder.newFolder(SERIALIZATION_DIR);
        File editorDir = folder.newFolder(EDITOR_DIR);
        File styleDir = folder.newFolder(STYLE_DIR);

        new File(metaModelDir, "metamodel.g4").createNewFile();

        Properties props = new Properties();
        props.put(DescriptorConfig.Property.META_MODEL.value(), metaModelDir.getPath());
        props.put(DescriptorConfig.Property.SERIALIZATION.value(), serializationDir.getPath());
        props.put(DescriptorConfig.Property.EDITOR.value(), editorDir.getPath());
        props.put(DescriptorConfig.Property.STYLE.value(), styleDir.getPath());

        File descriptor = folder.newFile(DESCRIPTOR_FILE);

        props.store(new FileOutputStream(descriptor), "");

        DescriptorConfig config = new DescriptorConfig(descriptor);
        assertEquals(config.getMetaModelConfigDir(), metaModelDir.getPath());
        assertEquals(config.getSerializationConfigDir(), serializationDir.getPath());
        assertEquals(config.getEditorConfigDir(), editorDir.getPath());
        assertEquals(config.getStyleConfigDir(), styleDir.getPath());

        folder.delete();
    }

    @Test
    public void testShouldThrowExceptionOnNotExistDescriptor() throws Exception {
        folder.create();

        File invalidDescriptorConfiguration = folder.newFolder(DESCRIPTOR_FILE);
        try {
            new DescriptorConfig(invalidDescriptorConfiguration);
        } catch (InvalidDescriptorException e) {
            assertEquals(e.getMessage(), "Configuration descriptor file doesn't exist.");
        }
    }

    @Test
    public void testShouldThrowExceptionOnEmptyDescriptorConfiguration() throws Exception {
        folder.create();

        File descriptor = folder.newFile(DESCRIPTOR_FILE);

        new Properties().store(new FileOutputStream(descriptor), "");

        try {
            new DescriptorConfig(descriptor);
        } catch (InvalidDescriptorException e) {
            assertEquals(e.getMessage(), "Configuration descriptor is empty.");
        }

        folder.delete();
    }

    @Test
    public void testShouldThrowExceptionOnEmptyMetaModelProperty() throws Exception {
        folder.create();

        File serializationDir = folder.newFolder(SERIALIZATION_DIR);
        File editorDir = folder.newFolder(EDITOR_DIR);
        File styleDir = folder.newFolder(STYLE_DIR);

        Properties props = new Properties();
        props.put(DescriptorConfig.Property.SERIALIZATION.value(), serializationDir.getPath());
        props.put(DescriptorConfig.Property.EDITOR.value(), editorDir.getPath());
        props.put(DescriptorConfig.Property.STYLE.value(), styleDir.getPath());

        File descriptor = folder.newFile(DESCRIPTOR_FILE);

        props.store(new FileOutputStream(descriptor), "");

        try {
            new DescriptorConfig(descriptor);
        } catch (InvalidDescriptorException e) {
            assertEquals(e.getMessage(), "Property '" + DescriptorConfig.Property.META_MODEL.value() + "' doesn't exist.");
        }

        folder.delete();
    }

    @Test
    public void testShouldThrowExceptionOnEmptyMetaModelPropertyValue() throws Exception {
        folder.create();

        File serializationDir = folder.newFolder(SERIALIZATION_DIR);
        File editorDir = folder.newFolder(EDITOR_DIR);
        File styleDir = folder.newFolder(STYLE_DIR);

        Properties props = new Properties();
        props.put(DescriptorConfig.Property.META_MODEL.value(), "");
        props.put(DescriptorConfig.Property.SERIALIZATION.value(), serializationDir.getPath());
        props.put(DescriptorConfig.Property.EDITOR.value(), editorDir.getPath());
        props.put(DescriptorConfig.Property.STYLE.value(), styleDir.getPath());

        File descriptor = folder.newFile(DESCRIPTOR_FILE);

        props.store(new FileOutputStream(descriptor), "");

        try {
            new DescriptorConfig(descriptor);
        } catch (InvalidDescriptorException e) {
            assertEquals(e.getMessage(), "Property '" + DescriptorConfig.Property.META_MODEL.value() + "' value is empty.");
        }

        folder.delete();
    }

    @Test
    public void testShouldThrowExceptionOnEmptySerializationPropertyValue() throws Exception {
        folder.create();

        File metaModelDir = folder.newFolder(META_MODEL_DIR);
        File editorDir = folder.newFolder(EDITOR_DIR);
        File styleDir = folder.newFolder(STYLE_DIR);

        new File(metaModelDir, "metamodel.g4").createNewFile();

        Properties props = new Properties();
        props.put(DescriptorConfig.Property.META_MODEL.value(), metaModelDir.getPath());
        props.put(DescriptorConfig.Property.SERIALIZATION.value(), "");
        props.put(DescriptorConfig.Property.EDITOR.value(), editorDir.getPath());
        props.put(DescriptorConfig.Property.STYLE.value(), styleDir.getPath());

        File descriptor = folder.newFile(DESCRIPTOR_FILE);

        props.store(new FileOutputStream(descriptor), "");

        try {
            new DescriptorConfig(descriptor);
        } catch (InvalidDescriptorException e) {
            assertEquals(e.getMessage(), "Property '" + DescriptorConfig.Property.SERIALIZATION.value() + "' value is empty.");
        }

        folder.delete();
    }

    @Test
    public void testShouldThrowExceptionOnEmptyEditorPropertyValue() throws Exception {
        folder.create();

        File metaModelDir = folder.newFolder(META_MODEL_DIR);
        File serializationDir = folder.newFolder(SERIALIZATION_DIR);
        File styleDir = folder.newFolder(STYLE_DIR);

        new File(metaModelDir, "metamodel.g4").createNewFile();

        Properties props = new Properties();
        props.put(DescriptorConfig.Property.META_MODEL.value(), metaModelDir.getPath());
        props.put(DescriptorConfig.Property.SERIALIZATION.value(), serializationDir.getPath());
        props.put(DescriptorConfig.Property.EDITOR.value(), "");
        props.put(DescriptorConfig.Property.STYLE.value(), styleDir.getPath());

        File descriptor = folder.newFile(DESCRIPTOR_FILE);

        props.store(new FileOutputStream(descriptor), "");

        try {
            new DescriptorConfig(descriptor);
        } catch (InvalidDescriptorException e) {
            assertEquals(e.getMessage(), "Property '" + DescriptorConfig.Property.EDITOR.value() + "' value is empty.");
        }

        folder.delete();
    }

    @Test
    public void testShouldThrowExceptionOnEmptyStylePropertyValue() throws Exception {
        folder.create();

        File metaModelDir = folder.newFolder(META_MODEL_DIR);
        File serializationDir = folder.newFolder(SERIALIZATION_DIR);
        File editorDir = folder.newFolder(EDITOR_DIR);

        new File(metaModelDir, "metamodel.g4").createNewFile();

        Properties props = new Properties();
        props.put(DescriptorConfig.Property.META_MODEL.value(), metaModelDir.getPath());
        props.put(DescriptorConfig.Property.SERIALIZATION.value(), serializationDir.getPath());
        props.put(DescriptorConfig.Property.EDITOR.value(), editorDir.getPath());
        props.put(DescriptorConfig.Property.STYLE.value(), "");

        File descriptor = folder.newFile(DESCRIPTOR_FILE);

        props.store(new FileOutputStream(descriptor), "");

        try {
            new DescriptorConfig(descriptor);
        } catch (InvalidDescriptorException e) {
            assertEquals(e.getMessage(), "Property '" + DescriptorConfig.Property.STYLE.value() + "' value is empty.");
        }

        folder.delete();
    }

    @Test
    public void testShouldThrowExceptionOnNonValidMetaModelDirectory() throws Exception {
        folder.create();

        File metaModelDir = folder.newFile(META_MODEL_DIR);
        File serializationDir = folder.newFolder(SERIALIZATION_DIR);
        File editorDir = folder.newFolder(EDITOR_DIR);
        File styleDir = folder.newFolder(STYLE_DIR);

        Properties props = new Properties();
        props.put(DescriptorConfig.Property.META_MODEL.value(), metaModelDir.getPath());
        props.put(DescriptorConfig.Property.SERIALIZATION.value(), serializationDir.getPath());
        props.put(DescriptorConfig.Property.EDITOR.value(), editorDir.getPath());
        props.put(DescriptorConfig.Property.STYLE.value(), styleDir.getPath());

        File descriptor = folder.newFile(DESCRIPTOR_FILE);

        props.store(new FileOutputStream(descriptor), "");

        try {
            new DescriptorConfig(descriptor);
        } catch (InvalidDescriptorException e) {
            assertEquals(e.getMessage(), "Path '" + metaModelDir.getPath() + "' is not a directory.");
        }

        folder.delete();
    }

    @Test
    public void testShouldThrowExceptionOnNonValidSerializationDirectory() throws Exception {
        folder.create();

        File metaModelDir = folder.newFolder(META_MODEL_DIR);
        File serializationDir = folder.newFile(SERIALIZATION_DIR);
        File editorDir = folder.newFolder(EDITOR_DIR);
        File styleDir = folder.newFolder(STYLE_DIR);

        new File(metaModelDir, "metamodel.g4").createNewFile();

        Properties props = new Properties();
        props.put(DescriptorConfig.Property.META_MODEL.value(), metaModelDir.getPath());
        props.put(DescriptorConfig.Property.SERIALIZATION.value(), serializationDir.getPath());
        props.put(DescriptorConfig.Property.EDITOR.value(), editorDir.getPath());
        props.put(DescriptorConfig.Property.STYLE.value(), styleDir.getPath());

        File descriptor = folder.newFile(DESCRIPTOR_FILE);

        props.store(new FileOutputStream(descriptor), "");

        try {
            new DescriptorConfig(descriptor);
        } catch (InvalidDescriptorException e) {
            assertEquals(e.getMessage(), "Path '" + serializationDir.getPath() + "' is not a directory.");
        }

        folder.delete();
    }

    @Test
    public void testShouldThrowExceptionOnNonValidEditorDirectory() throws Exception {
        folder.create();

        File metaModelDir = folder.newFolder(META_MODEL_DIR);
        File serializationDir = folder.newFolder(SERIALIZATION_DIR);
        File editorDir = folder.newFile(EDITOR_DIR);
        File styleDir = folder.newFolder(STYLE_DIR);

        new File(metaModelDir, "metamodel.g4").createNewFile();

        Properties props = new Properties();
        props.put(DescriptorConfig.Property.META_MODEL.value(), metaModelDir.getPath());
        props.put(DescriptorConfig.Property.SERIALIZATION.value(), serializationDir.getPath());
        props.put(DescriptorConfig.Property.EDITOR.value(), editorDir.getPath());
        props.put(DescriptorConfig.Property.STYLE.value(), styleDir.getPath());

        File descriptor = folder.newFile(DESCRIPTOR_FILE);

        props.store(new FileOutputStream(descriptor), "");

        try {
            new DescriptorConfig(descriptor);
        } catch (InvalidDescriptorException e) {
            assertEquals(e.getMessage(), "Path '" + editorDir.getPath() + "' is not a directory.");
        }

        folder.delete();
    }

    @Test
    public void testShouldThrowExceptionOnNonValidStyleDirectory() throws Exception {
        folder.create();

        File metaModelDir = folder.newFolder(META_MODEL_DIR);
        File serializationDir = folder.newFolder(SERIALIZATION_DIR);
        File editorDir = folder.newFolder(EDITOR_DIR);
        File styleDir = folder.newFile(STYLE_DIR);

        new File(metaModelDir, "metamodel.g4").createNewFile();

        Properties props = new Properties();
        props.put(DescriptorConfig.Property.META_MODEL.value(), metaModelDir.getPath());
        props.put(DescriptorConfig.Property.SERIALIZATION.value(), serializationDir.getPath());
        props.put(DescriptorConfig.Property.EDITOR.value(), editorDir.getPath());
        props.put(DescriptorConfig.Property.STYLE.value(), styleDir.getPath());

        File descriptor = folder.newFile(DESCRIPTOR_FILE);

        props.store(new FileOutputStream(descriptor), "");

        try {
            new DescriptorConfig(descriptor);
        } catch (InvalidDescriptorException e) {
            assertEquals(e.getMessage(), "Path '" + styleDir.getPath() + "' is not a directory.");
        }

        folder.delete();
    }

    @Test
    public void testShouldThrowExceptionOnNonExistMetaModelConfiguration() throws Exception {
        folder.create();

        File metaModelDir = folder.newFolder(META_MODEL_DIR);
        File serializationDir = folder.newFolder(SERIALIZATION_DIR);
        File editorDir = folder.newFolder(EDITOR_DIR);
        File styleDir = folder.newFile(STYLE_DIR);

        Properties props = new Properties();
        props.put(DescriptorConfig.Property.META_MODEL.value(), metaModelDir.getPath());
        props.put(DescriptorConfig.Property.SERIALIZATION.value(), serializationDir.getPath());
        props.put(DescriptorConfig.Property.EDITOR.value(), editorDir.getPath());
        props.put(DescriptorConfig.Property.STYLE.value(), styleDir.getPath());

        File descriptor = folder.newFile(DESCRIPTOR_FILE);

        props.store(new FileOutputStream(descriptor), "");

        try {
            new DescriptorConfig(descriptor);
        } catch (InvalidDescriptorException e) {
            assertEquals(e.getMessage(), "Directory '" + metaModelDir.getPath() + "' doesn't contains meta model configuration.");
        }

        folder.delete();
    }

}
