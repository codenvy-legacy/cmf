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
import com.codenvy.modeling.configuration.validation.constraints.ConfigurationConstraintsValidator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** @author Valeriy Svydenko */
public class EditorValidatorTest {
    public static final String SIMPLE_STRING = "simple_text";

    public TextInitializer    textInitializer    = new TextInitializer();
    public GroupInitializer   groupInitializer   = new GroupInitializer();
    public SizeInitializer    sizeInitializer    = new SizeInitializer();
    public ToolbarInitializer toolbarInitializer = new ToolbarInitializer();
    public ItemInitializer    itemInitializer    = new ItemInitializer();

    @Rule
    public PanelInitializer panelInitializer = new PanelInitializer();

    @Rule
    public TestRule chainText    = RuleChain
            .outerRule(textInitializer)
            .around(itemInitializer);
    @Rule
    public TestRule chainToolbar = RuleChain
            .outerRule(groupInitializer)
            .around(textInitializer)
            .around(itemInitializer)
            .around(sizeInitializer)
            .around(toolbarInitializer);

    //tests Text
    @Test
    public void textValidationShouldResultInErrorIfAlignmentIsNull() {
        textInitializer.getText().setAlignment(null);

        Report report = ConfigurationConstraintsValidator.validate(textInitializer.getText());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void textValidationShouldResultWithoutError() {
        Report report = ConfigurationConstraintsValidator.validate(textInitializer.getText());

        assertFalse(report.hasErrors());
    }

    //tests Item
    @Test
    public void itemValidationShouldResultInErrorIfItemSizeIsNegative() {
        itemInitializer.getItem().setSize(-1);

        Report report = ConfigurationConstraintsValidator.validate(itemInitializer.getItem());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void itemValidationShouldResultInErrorIfMarginIsNotValid() {
        itemInitializer.getItem().setMargin("#not()valid");

        Report report = ConfigurationConstraintsValidator.validate(itemInitializer.getItem());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());

        itemInitializer.getItem().setMargin("");

        report = ConfigurationConstraintsValidator.validate(itemInitializer.getItem());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void itemValidationShouldResultInErrorIfAlignmentIsNull() {
        itemInitializer.getItem().setAlignment(null);

        Report report = ConfigurationConstraintsValidator.validate(itemInitializer.getItem());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void itemValidationShouldResultWithoutError() {
        Report report = ConfigurationConstraintsValidator.validate(itemInitializer.getItem());

        assertFalse(report.hasErrors());
    }

    //tests Group
    @Test
    public void groupValidationShouldResultWithoutError() {
        Report report = ConfigurationConstraintsValidator.validate(groupInitializer.getGroup());

        assertFalse(report.hasErrors());
    }

    @Test
    public void groupValidationShouldResultInErrorIfMarginIsNotValid() {
        groupInitializer.getGroup().setMargin("");

        Report report = ConfigurationConstraintsValidator.validate(groupInitializer.getGroup());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    //tests Size
    @Test
    public void sizeValidationShouldResultInErrorIfFieldsAreNegative() {
        sizeInitializer.getSize().setCompact(-1);
        sizeInitializer.getSize().setFull(-2);

        Report report = ConfigurationConstraintsValidator.validate(sizeInitializer.getSize());

        assertTrue(report.hasErrors());
        assertEquals(2, report.getErrors().size());
    }

    @Test
    public void sizeValidationShouldResultWithoutError() {
        Report report = ConfigurationConstraintsValidator.validate(sizeInitializer.getSize());

        assertFalse(report.hasErrors());
    }

    //tests toolbar
    @Test
    public void toolbarValidationShouldResultWithoutError() {
        Report report = ConfigurationConstraintsValidator.validate(toolbarInitializer.getToolbar());

        assertFalse(report.hasErrors());
    }

    @Test
    public void toolbarValidationShouldResultInErrorIfAlignmentIsNull() {
        toolbarInitializer.getToolbar().setAlignment(null);

        Report report = ConfigurationConstraintsValidator.validate(toolbarInitializer.getToolbar());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    //tests panel
    @Test
    public void panelValidationShouldResultInErrorIfAlignmentIsNull() {
        panelInitializer.getPanel().setAlignment(null);

        Report report = ConfigurationConstraintsValidator.validate(panelInitializer.getPanel());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void panelValidationShouldResultInErrorIfDefaultSizeIsNegative() {
        panelInitializer.getPanel().setDefaultSize(-2);

        Report report = ConfigurationConstraintsValidator.validate(panelInitializer.getPanel());

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void panelValidationShouldResultInErrorIfParametersNotInitialized() {
        panelInitializer.getPanel().setAlignment(null);
        panelInitializer.getPanel().setDefaultSize(-2);

        Report report = ConfigurationConstraintsValidator.validate(panelInitializer.getPanel());

        assertTrue(report.hasErrors());
        assertEquals(2, report.getErrors().size());
    }

    @Test
    public void panelValidationShouldResultWithoutError() {
        Report report = ConfigurationConstraintsValidator.validate(panelInitializer);

        assertFalse(report.hasErrors());
    }

    //tests EditorConfiguration
    @Test
    public void editorValidationShouldResultWithoutError() {
        EditorConfiguration editorConfiguration = new EditorConfiguration();

        Workspace workspace = new Workspace();

        editorConfiguration.setPanel(panelInitializer.getPanel());
        editorConfiguration.setToolbar(toolbarInitializer.getToolbar());
        editorConfiguration.setWorkspace(workspace);

        Report report = ConfigurationConstraintsValidator.validate(panelInitializer.getPanel());

        assertFalse(report.hasErrors());
    }

    private class TextInitializer extends ExternalResource {
        private Text text;

        @Override
        protected void before() throws Throwable {
            text = new Text();
            text.setAlignment(Text.Alignment.BOTTOM);
        }

        protected Text getText() {
            return text;
        }
    }

    private class ItemInitializer extends ExternalResource {
        private Item item;

        @Override
        protected void before() throws Throwable {
            item = new Item();
            item.setText(textInitializer.getText());
            item.setMargin(SIMPLE_STRING);
            item.setAlignment(Item.Alignment.BOTTOM);
            item.setSize(1);
        }

        protected Item getItem() {
            return item;
        }
    }

    private class GroupInitializer extends ExternalResource {
        private Group group;

        @Override
        protected void before() throws Throwable {
            group = new Group();

            group.setMargin(SIMPLE_STRING);
        }

        protected Group getGroup() {
            return group;
        }
    }

    private class SizeInitializer extends ExternalResource {
        private Size size;

        @Override
        protected void before() throws Throwable {
            size = new Size();

            size.setFull(2);
            size.setCompact(1);
        }

        protected Size getSize() {
            return size;
        }
    }

    private class ToolbarInitializer extends ExternalResource {
        private Toolbar toolbar;

        @Override
        protected void before() throws Throwable {
            toolbar = new Toolbar();

            toolbar.setAlignment(Toolbar.Alignment.EAST);
            toolbar.setGroup(groupInitializer.getGroup());
            toolbar.setItem(itemInitializer.getItem());
            toolbar.setSize(sizeInitializer.getSize());
        }

        protected Toolbar getToolbar() {
            return toolbar;
        }
    }

    private class PanelInitializer extends ExternalResource {
        private Panel panel;

        @Override
        protected void before() throws Throwable {
            panel = new Panel();

            panel.setDefaultSize(2);
            panel.setAlignment(Panel.Alignment.EAST);
        }

        protected Panel getPanel() {
            return panel;
        }
    }
}