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

package com.codenvy.modeling.configuration;

import com.codenvy.modeling.adapter.editor.EditorConfigurationAdapter;
import com.codenvy.modeling.configuration.editor.EditorConfiguration;
import com.codenvy.modeling.configuration.editor.Group;
import com.codenvy.modeling.configuration.editor.Item;
import com.codenvy.modeling.configuration.editor.Panel;
import com.codenvy.modeling.configuration.editor.Size;
import com.codenvy.modeling.configuration.editor.Text;
import com.codenvy.modeling.configuration.editor.Toolbar;
import com.codenvy.modeling.configuration.editor.Workspace;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Dmitry Kuleshov
 * @author Valeriy Svydenko
 */
public class EditorAdapterTest {

    private static final String EDITOR_GRAMMAR_TEST_I = "/EditorGrammarTest_I";

    private static Toolbar   toolbar;
    private static Panel     panel;
    private static Workspace workspace;
    private static Item      item;

    @BeforeClass
    public static void setUp() throws Exception {
        String configurationFile = EditorAdapterTest.class.getResource(EDITOR_GRAMMAR_TEST_I).getFile();
        EditorConfigurationAdapter editorConfigurationAdapter = new EditorConfigurationAdapter(configurationFile);
        EditorConfiguration configuration = editorConfigurationAdapter.getConfiguration();

        panel = configuration.getPanel();
        workspace = configuration.getWorkspace();
        toolbar = configuration.getToolbar();
        item = toolbar.getItem();
    }

    @Test
    public void valueOfAlignmentShouldBeDefined() {
        assertFalse(toolbar.getAlignment().name().isEmpty());
        assertNotNull(Toolbar.Alignment.valueOf(toolbar.getAlignment().name()));
    }

    @Test
    public void sizeShouldBeContainedTwoPositiveArguments() {
        Size size = toolbar.getSize();

        assertTrue(size.getCompact() > 0);
        assertTrue(size.getFull() > 0);
    }

    @Test
    public void groupShouldBeContainedMargin() {
        Group group = toolbar.getGroup();

        assertFalse(group.getMargin().isEmpty());
    }

    @Test
    public void itemMustHaveNames() {
        assertFalse(item.getAlignment().name().isEmpty());
    }

    @Test
    public void itemMustHaveMargin() {
        assertFalse(item.getMargin().isEmpty());
    }

    @Test
    public void itemMustHaveAlignment() {
        assertTrue(item.getAlignment().name().length() > 0);
        assertNotNull(Item.Alignment.valueOf(item.getAlignment().name()));
    }

    @Test
    public void itemMustHavePositiveValueOfSize() {
        assertTrue(item.getSize() > 0);
    }


    @Test
    public void itemMustHaveTextWithAlignmentParameter() {
        Item item = toolbar.getItem();
        Text text = item.getText();

        assertNotNull(Text.Alignment.valueOf(text.getAlignment().name()));
    }

    @Test
    public void panelMustHaveAlignment() {
        assertNotNull(Panel.Alignment.valueOf(panel.getAlignment().name()));
    }

    @Test
    public void panelMustHaveDefaultSizeParam() {
        assertTrue(panel.getDefaultSize() > 0);
    }

    @Test
    public void workspaceMustHaveScrollabilityParameter() {
        assertNotNull(Workspace.Scrollability.valueOf(workspace.getScrollability().name()));
    }
}