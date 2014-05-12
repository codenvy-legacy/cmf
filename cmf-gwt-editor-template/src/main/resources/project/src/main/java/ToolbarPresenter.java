package current_package;

import main_package.client.State;

import com.codenvy.editor.api.editor.EditorState;
import com.codenvy.editor.api.editor.toolbar.AbstractToolbarPresenter;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

static_import_elements

public class ToolbarPresenter extends AbstractToolbarPresenter<State> implements ActionDelegate {

    @Inject
    public ToolbarPresenter(ToolbarView view, @Assisted EditorState<State> editorState) {
        super(view, editorState);
    }

    change_editor_states
}