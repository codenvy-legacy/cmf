package current_package;

import com.codenvy.editor.api.editor.EditorState;
import com.codenvy.editor.api.editor.SelectionManager;
import com.codenvy.editor.api.editor.propertiespanel.PropertiesPanelManager;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import main_package.client.State;
import main_package.client.toolbar.ToolbarPresenter;
import main_package.client.workspace.WorkspacePresenter;

import javax.annotation.Nonnull;

public interface EditorFactory {

    ToolbarPresenter createToolbar(@Nonnull EditorState<State> editorState);

    WorkspacePresenter createWorkspace(@Nonnull EditorState<State> editorState, @Nonnull SelectionManager selectionManager);

    PropertiesPanelManager createPropertiesPanelManager(@Nonnull AcceptsOneWidget container);

}