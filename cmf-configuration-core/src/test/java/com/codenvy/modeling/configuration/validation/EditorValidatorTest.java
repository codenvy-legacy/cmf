/*
 * Copyright [2014] Codenvy, S.A.
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

import com.codenvy.modeling.configuration.editor.EditorConfiguration;
import com.codenvy.modeling.configuration.editor.Group;
import com.codenvy.modeling.configuration.editor.Item;
import com.codenvy.modeling.configuration.editor.Panel;
import com.codenvy.modeling.configuration.editor.Size;
import com.codenvy.modeling.configuration.editor.Text;
import com.codenvy.modeling.configuration.editor.Toolbar;
import com.codenvy.modeling.configuration.editor.Workspace;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** @author Valeriy Svydenko */
public class EditorValidatorTest {
    public static final String SIMPLE_STRING = "simple_text";

    private Group               group;
    private Item                item;
    private Panel               panel;
    private Size                size;
    private Text                text;
    private Toolbar             toolbar;

    //tests Text
    @Test
    public void textValidationShouldResultInErrorIfAlignmentIsNull() {
        Text text = new Text();
        text.setAlignment(null);

        Report report = ConfigurationConstraintsValidator.validate(text);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    private void initializationText() {
        text = new Text();
        text.setAlignment(Text.Alignment.BOTTOM);
    }

    @Test
    public void textValidationShouldResultWithoutError() {
        initializationText();

        Report report = ConfigurationConstraintsValidator.validate(text);

        assertFalse(report.hasErrors());
    }

    //tests Item
    @Test
    public void itemValidationShouldResultInErrorIfItemSizeIsNegative() {
        initializationItem();

        item.setSize(-1);

        Report report = ConfigurationConstraintsValidator.validate(item);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void itemValidationShouldResultInErrorIfMarginIsNotValid() {
        initializationItem();

        item.setMargin("#not()valid");

        Report report = ConfigurationConstraintsValidator.validate(item);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());

        item.setMargin("");

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void itemValidationShouldResultInErrorIfAlignmentIsNull() {
        initializationItem();

        item.setAlignment(null);

        Report report = ConfigurationConstraintsValidator.validate(item);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void itemValidationShouldResultWithoutError() {
        initializationItem();

        Report report = ConfigurationConstraintsValidator.validate(item);

        assertFalse(report.hasErrors());
    }

    private void initializationItem() {
        initializationText();

        item = new Item();
        item.setText(text);
        item.setMargin(SIMPLE_STRING);
        item.setAlignment(Item.Alignment.BOTTOM);
        item.setSize(1);
    }

    //tests Group
    @Test
    public void groupValidationShouldResultWithoutError() {
        initializationGroup();

        Report report = ConfigurationConstraintsValidator.validate(group);

        assertFalse(report.hasErrors());
    }

    @Test
    public void groupValidationShouldResultInErrorIfMarginIsNotValid() {
        group = new Group();

        group.setMargin("");

        Report report = ConfigurationConstraintsValidator.validate(group);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    private void initializationGroup() {
        group = new Group();

        group.setMargin(SIMPLE_STRING);
    }

    //tests Size
    @Test
    public void sizeValidationShouldResultInErrorIfFieldsAreNegative() {
        size = new Size();

        size.setCompact(-1);
        size.setFull(-2);

        Report report = ConfigurationConstraintsValidator.validate(size);

        assertTrue(report.hasErrors());
        assertEquals(2, report.getErrors().size());
    }

    @Test
    public void sizeValidationShouldResultWithoutError() {
        initializationSize();

        Report report = ConfigurationConstraintsValidator.validate(size);

        assertFalse(report.hasErrors());
    }

    private void initializationSize() {
        size = new Size();

        size.setFull(2);
        size.setCompact(1);
    }

    //tests toolbar
    @Test
    public void toolbarValidationShouldResultWithoutError() {
        initializationToolbar();

        Report report = ConfigurationConstraintsValidator.validate(toolbar);

        assertFalse(report.hasErrors());
    }

    @Test
    public void toolbarValidationShouldResultInErrorIfAlignmentIsNull() {
        initializationToolbar();

        toolbar.setAlignment(null);

        Report report = ConfigurationConstraintsValidator.validate(toolbar);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    private void initializationToolbar() {
        toolbar = new Toolbar();

        initializationGroup();
        initializationItem();
        initializationSize();

        toolbar.setAlignment(Toolbar.Alignment.EAST);
        toolbar.setGroup(group);
        toolbar.setItem(item);
        toolbar.setSize(size);
    }

    //tests panel
    @Test
    public void panelValidationShouldResultInErrorIfAlignmentIsNull() {
        initializationPanel();

        panel.setAlignment(null);

        Report report = ConfigurationConstraintsValidator.validate(panel);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void panelValidationShouldResultInErrorIfDefaultSizeIsNegative() {
        initializationPanel();

        panel.setDefaultSize(-2);

        Report report = ConfigurationConstraintsValidator.validate(panel);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void panelValidationShouldResultInErrorIfParametersNotInitialized() {
        initializationPanel();

        panel.setAlignment(null);
        panel.setDefaultSize(-2);

        Report report = ConfigurationConstraintsValidator.validate(panel);

        assertTrue(report.hasErrors());
        assertEquals(2, report.getErrors().size());
    }

    @Test
    public void panelValidationShouldResultWithoutError() {
        initializationPanel();

        Report report = ConfigurationConstraintsValidator.validate(panel);

        assertFalse(report.hasErrors());
    }

    private void initializationPanel() {
        panel = new Panel();

        panel.setDefaultSize(2);
        panel.setAlignment(Panel.Alignment.EAST);
    }

    //tests EditorConfiguration
    @Test
    public void editorValidationShouldResultWithoutError() {
        EditorConfiguration editorConfiguration = new EditorConfiguration();

        initializationPanel();
        initializationToolbar();
        Workspace workspace = new Workspace();

        editorConfiguration.setPanel(panel);
        editorConfiguration.setToolbar(toolbar);
        editorConfiguration.setWorkspace(workspace);

        Report report = ConfigurationConstraintsValidator.validate(panel);

        assertFalse(report.hasErrors());
    }
}