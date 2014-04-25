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

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * @author Dmitry Kuleshov
 * @author Valeriy Svydenko
 */
public class EditorAdapterTest {

    private static final String EDITOR_GRAMMAR_TEST_I     = "/EditorGrammarTest_I";
    private static final String EDITOR_CONFIGURATION_IMPL = "EditorConfigurationImpl";
    private static final String TOOLBAR_IMPL              = "ToolbarImpl";
    private static final String SIZE_IMPL                 = "SizeImpl";
    private static final String GROUP_IMPL                = "GroupImpl";
    private static final String ITEM_IMPL                 = "ItemImpl";
    private static final String TEXT_IMPL                 = "TextImpl";
    private static final String PANEL_IMPL                = "PanelImpl";
    private static final String WORKSPACE_IMPL            = "WorkspaceImpl";

    private String      editorConfiguration;
    private String[]    configurationElements;
    private Set<String> tbAlignment;
    private Set<String> alignment;
    private Set<String> scrollability;

    @Before
    public void setUp() throws Exception {
        tbAlignment = new HashSet<>(Arrays.asList("SOUTH", "NORTH", "WEST", "EAST"));
        alignment = new HashSet<>(Arrays.asList("TOP", "BOTTOM", "LEFT", "RIGHT", "CENTER"));
        scrollability = new HashSet<>(Arrays.asList("YES", "NO"));

        String configurationFile = getClass().getResource(EDITOR_GRAMMAR_TEST_I).getFile();
        EditorConfigurationAdapter editorConfigurationAdapter = new EditorConfigurationAdapter(configurationFile);
        editorConfiguration = editorConfigurationAdapter.getConfiguration().toString();
        configurationElements = editorConfiguration.split("\n");

    }

    @Test
    public void editorConfigurationShouldBeContainedEditorConfigurationImplString() {
        assertTrue(editorConfiguration.contains(EDITOR_CONFIGURATION_IMPL));
    }

    @Test
    public void editorConfigurationShouldBeContainedToolbarImplString() {
        assertTrue(editorConfiguration.contains(TOOLBAR_IMPL));
    }

    @Test
    public void toolbarShouldBeContainedAlignmentParameter() {
        String toolbarDescription = getDescriptionOfConfigurationElement(TOOLBAR_IMPL);

        assertTrue(toolbarDescription.contains("alignment"));
    }

    @Test
    public void valueOfAlignmentShouldBeDefined() {
        String toolbarDescription = getDescriptionOfConfigurationElement(TOOLBAR_IMPL);

        int indexOfAlign = toolbarDescription.indexOf("alignment");
        toolbarDescription = toolbarDescription.substring(indexOfAlign);
        indexOfAlign = toolbarDescription.indexOf('=');

        assertTrue(tbAlignment.contains(toolbarDescription.substring(indexOfAlign + 1, toolbarDescription.indexOf(','))));
    }

    @Test
    public void editorConfigurationShouldBeContainedSizeImplString() {
        assertTrue(editorConfiguration.contains(SIZE_IMPL));
    }

    @Test
    public void sizeShouldBeContainedTwoArguments() {
        String sizeDescription = getDescriptionOfConfigurationElement(SIZE_IMPL);
        int indexOfCompact = sizeDescription.indexOf("compact");
        int indexOfFull = sizeDescription.indexOf("full");

        String compactValue = sizeDescription.substring(indexOfCompact);
        compactValue = compactValue.substring(compactValue.indexOf('=') + 1);
        compactValue = compactValue.substring(0, compactValue.indexOf(','));

        String fullValue = sizeDescription.substring(indexOfFull);
        fullValue = fullValue.substring(fullValue.indexOf('=') + 1);
        fullValue = fullValue.substring(0, fullValue.indexOf('}'));

        assertTrue(indexOfCompact > -1);
        assertTrue(indexOfFull > -1);

        assertTrue(Integer.parseInt(compactValue) > 0);
        assertTrue(Integer.parseInt(fullValue) > 0);
    }

    @Test
    public void editorConfigurationShouldBeContainedGroupImplString() {
        assertTrue(editorConfiguration.contains(GROUP_IMPL));
    }

    @Test
    public void groupShouldBeContainedMargin() {
        String groupDescription = getDescriptionOfConfigurationElement(GROUP_IMPL);

        assertTrue(groupDescription.contains("margin"));
    }

    @Test
    public void editorConfigurationShouldBeContainedItemImplString() {
        assertTrue(editorConfiguration.contains(ITEM_IMPL));
    }

    @Test
    public void itemShouldBeContainedSizeAndMarginParameters() {
        String itemDescription = getDescriptionOfConfigurationElement(ITEM_IMPL);
        int indexOfSize = itemDescription.indexOf("size");

        String valueOfSize = itemDescription.substring(indexOfSize);
        valueOfSize = valueOfSize.substring(valueOfSize.indexOf('=') + 1);
        valueOfSize = valueOfSize.substring(0, valueOfSize.indexOf(','));

        assertTrue(itemDescription.contains("size"));
        assertTrue(itemDescription.contains("margin"));
        assertTrue(Integer.parseInt(valueOfSize) > 0);
    }

    @Test
    public void editorConfigurationShouldBeContainedTextImplString() {
        assertTrue(editorConfiguration.contains(TEXT_IMPL));
    }

    @Test
    public void textImplShouldBeContainedAlignment() {
        String textDescription = getDescriptionOfConfigurationElement(TEXT_IMPL);
        int indexOfAlignment = textDescription.indexOf("alignment");

        String valueOfAlignment = textDescription.substring(indexOfAlignment);
        valueOfAlignment = valueOfAlignment.substring(valueOfAlignment.indexOf("=") + 1);
        valueOfAlignment = valueOfAlignment.substring(0, valueOfAlignment.indexOf('}'));

        assertTrue(alignment.contains(valueOfAlignment));

    }

    @Test
    public void editorConfigurationShouldBeContainedPanelImplString() {
        assertTrue(editorConfiguration.contains(PANEL_IMPL));
    }

    @Test
    public void panelShouldBeContainedAlignmentAndDefaultSizeParam() {
        String panelDescription = getDescriptionOfConfigurationElement(PANEL_IMPL);
        int indexOfAlignment = panelDescription.indexOf("alignment");
        int indexOfSize = panelDescription.indexOf("defaultSize");

        String valueOfAlignment = panelDescription.substring(indexOfAlignment);
        valueOfAlignment = valueOfAlignment.substring(valueOfAlignment.indexOf('=') + 1);
        valueOfAlignment = valueOfAlignment.substring(0, valueOfAlignment.indexOf(','));

        String valueOfSize = panelDescription.substring(indexOfSize);
        valueOfSize = valueOfSize.substring(valueOfSize.indexOf('=') + 1);
        valueOfSize = valueOfSize.substring(0, valueOfSize.indexOf('}'));

        assertTrue(indexOfAlignment > -1);
        assertTrue(indexOfSize > -1);

        assertTrue(new Integer(valueOfSize) > 0);
        assertTrue(tbAlignment.contains(valueOfAlignment));

    }

    @Test
    public void editorConfigurationShouldBeContainedWorkspaceImplString() {
        assertTrue(editorConfiguration.contains(WORKSPACE_IMPL));
    }

    @Test
    public void workspaceShouldBeContainedScrollabilityParameter() {
        String workspaceDescription = getDescriptionOfConfigurationElement(WORKSPACE_IMPL);
        int indexOfWorkspace = workspaceDescription.indexOf("scrollability");


        String valueOfScrollability = workspaceDescription.substring(indexOfWorkspace);
        valueOfScrollability = valueOfScrollability.substring(valueOfScrollability.indexOf('=') + 1);
        valueOfScrollability = valueOfScrollability.substring(0, valueOfScrollability.indexOf("}"));

        assertTrue(indexOfWorkspace > -1);
        assertTrue(scrollability.contains(valueOfScrollability));

    }

    private String getDescriptionOfConfigurationElement(String element) {
        for (String configurationElement : configurationElements) {
            if (configurationElement.startsWith(element)) {
                return configurationElement;
            }
        }
        return "";
    }
}