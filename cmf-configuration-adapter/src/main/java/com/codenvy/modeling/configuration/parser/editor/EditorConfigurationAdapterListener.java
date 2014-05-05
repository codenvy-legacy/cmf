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

package com.codenvy.modeling.configuration.parser.editor;

import com.codenvy.modeling.configuration.editor.EditorConfiguration;
import com.codenvy.modeling.configuration.editor.Group;
import com.codenvy.modeling.configuration.editor.Item;
import com.codenvy.modeling.configuration.editor.Panel;
import com.codenvy.modeling.configuration.editor.Size;
import com.codenvy.modeling.configuration.editor.Text;
import com.codenvy.modeling.configuration.editor.Toolbar;
import com.codenvy.modeling.configuration.editor.Workspace;

import org.antlr.v4.runtime.misc.NotNull;

import javax.annotation.Nonnull;

/**
 * @author Dmitry Kuleshov
 */
public class EditorConfigurationAdapterListener extends EditorBaseListener {
    private EditorConfiguration editorConfiguration;
    private Workspace           workspace;
    private Toolbar             toolbar;
    private Panel               panel;
    private Group               group;
    private Size                size;
    private Text                text;
    private Item                item;

    @Nonnull
    public EditorConfiguration getEditorConfiguration() {
        return editorConfiguration;
    }

    @Override
    public void enterEditor(@NotNull EditorParser.EditorContext ctx) {
        editorConfiguration = new EditorConfiguration();
    }

    @Override
    public void enterToolbar(@NotNull EditorParser.ToolbarContext ctx) {
        toolbar = new Toolbar();
    }

    @Override
    public void exitTbAlignment(@NotNull EditorParser.TbAlignmentContext ctx) {
        toolbar.setAlignment(Toolbar.Alignment.valueOf(ctx.SIDE().getText()));
    }

    @Override
    public void enterTbGroup(@NotNull EditorParser.TbGroupContext ctx) {
        group = new Group();
    }

    @Override
    public void exitGMargin(@NotNull EditorParser.GMarginContext ctx) {
        group.setMargin(ctx.TEXT().getText());
    }

    @Override
    public void exitTbGroup(@NotNull EditorParser.TbGroupContext ctx) {
        toolbar.setGroup(group);
    }

    @Override
    public void enterTbSize(@NotNull EditorParser.TbSizeContext ctx) {
        size = new Size();
    }

    @Override
    public void exitCompact(@NotNull EditorParser.CompactContext ctx) {
        size.setCompact(Integer.valueOf(ctx.INTEGER().getText()));
    }

    @Override
    public void exitFull(@NotNull EditorParser.FullContext ctx) {
        size.setFull(Integer.valueOf(ctx.INTEGER().getText()));
    }

    @Override
    public void exitTbSize(@NotNull EditorParser.TbSizeContext ctx) {
        toolbar.setSize(size);
    }

    @Override
    public void enterTbItem(@NotNull EditorParser.TbItemContext ctx) {
        item = new Item();
    }

    @Override
    public void exitISize(@NotNull EditorParser.ISizeContext ctx) {
        item.setSize(Integer.valueOf(ctx.INTEGER().getText()));
    }

    @Override
    public void exitIMargin(@NotNull EditorParser.IMarginContext ctx) {
        item.setMargin(ctx.TEXT().getText());
    }

    @Override
    public void exitIAlignment(@NotNull EditorParser.IAlignmentContext ctx) {
        item.setAlignment(Item.Alignment.valueOf(ctx.ALIGNMENT().getText()));
    }

    @Override
    public void enterIText(@NotNull EditorParser.ITextContext ctx) {
        text = new Text();
    }

    @Override
    public void exitITextAlignment(@NotNull EditorParser.ITextAlignmentContext ctx) {
        text.setAlignment(Text.Alignment.valueOf(ctx.ALIGNMENT().getText()));
    }

    @Override
    public void exitIText(@NotNull EditorParser.ITextContext ctx) {
        item.setText(text);
    }

    @Override
    public void exitTbItem(@NotNull EditorParser.TbItemContext ctx) {
        toolbar.setItem(item);
    }

    @Override
    public void exitToolbar(@NotNull EditorParser.ToolbarContext ctx) {
        editorConfiguration.setToolbar(toolbar);
    }

    @Override
    public void enterPPanel(@NotNull EditorParser.PPanelContext ctx) {
        panel = new Panel();
    }

    @Override
    public void exitPpAlignment(@NotNull EditorParser.PpAlignmentContext ctx) {
        panel.setAlignment(Panel.Alignment.valueOf(ctx.SIDE().getText()));
    }

    @Override
    public void exitPpDefaultSize(@NotNull EditorParser.PpDefaultSizeContext ctx) {
        panel.setDefaultSize(Integer.valueOf(ctx.INTEGER().getText()));
    }

    @Override
    public void exitPPanel(@NotNull EditorParser.PPanelContext ctx) {
        editorConfiguration.setPanel(panel);
    }

    @Override
    public void enterWorkspace(@NotNull EditorParser.WorkspaceContext ctx) {
        workspace = new Workspace();
    }

    @Override
    public void exitScrollable(@NotNull EditorParser.ScrollableContext ctx) {
        workspace.setScrollable(Boolean.valueOf(ctx.SCROLLABLE().getText().toLowerCase()));
    }

    @Override
    public void exitWorkspace(@NotNull EditorParser.WorkspaceContext ctx) {
        editorConfiguration.setWorkspace(workspace);
    }
}

