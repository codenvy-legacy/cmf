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

import com.codenvy.modeling.configuration.editor.*;
import com.codenvy.modeling.configuration.impl.editor.*;
import com.codenvy.modeling.configuration.parser.editor.generated.EditorBaseListener;
import com.codenvy.modeling.configuration.parser.editor.generated.EditorParser;
import org.antlr.v4.runtime.misc.NotNull;

/**
 * @author Dmitry Kuleshov
 */
public class EditorConfigurationAdapterListener extends EditorBaseListener {
    private EditorConfigurationImpl editorConfiguration;
    private WorkspaceImpl workspace;
    private ToolbarImpl toolbar;
    private PanelImpl panel;
    private GroupImpl group;
    private SizeImpl size;
    private TextImpl text;
    private ItemImpl item;

    public EditorConfiguration getEditorConfiguration() {
        return editorConfiguration;
    }

    @Override
    public void enterEditor(@NotNull EditorParser.EditorContext ctx) {
        editorConfiguration = new EditorConfigurationImpl();
    }

    @Override
    public void enterToolbar(@NotNull EditorParser.ToolbarContext ctx) {
        toolbar = new ToolbarImpl();
    }

    @Override
    public void exitTbAlignment(@NotNull EditorParser.TbAlignmentContext ctx) {
        toolbar.setAlignment(Toolbar.Alignment.valueOf(ctx.SIDE().getText()));
    }

    @Override
    public void enterTbGroup(@NotNull EditorParser.TbGroupContext ctx) {
        group = new GroupImpl();
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
        size = new SizeImpl();
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
        item = new ItemImpl();
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
        text = new TextImpl();
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
        panel = new PanelImpl();
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
        workspace = new WorkspaceImpl();
    }

    @Override
    public void exitScrollability(@NotNull EditorParser.ScrollabilityContext ctx) {
        workspace.setScrollability(Workspace.Scrollability.valueOf(ctx.SCROLLABILITY().getText()));
    }

    @Override
    public void exitWorkspace(@NotNull EditorParser.WorkspaceContext ctx) {
        editorConfiguration.setWorkspace(workspace);
    }
}
