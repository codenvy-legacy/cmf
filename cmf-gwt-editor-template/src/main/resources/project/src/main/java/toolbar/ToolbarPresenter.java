package current_package;

import main_package.client.State;

import com.codenvy.editor.api.editor.EditorState;
import com.codenvy.editor.api.editor.elements.Shape;
import com.codenvy.editor.api.editor.toolbar.AbstractToolbarPresenter;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javax.annotation.Nonnull;

static_import_elements
public class ToolbarPresenter extends AbstractToolbarPresenter<State> implements ToolbarView.ActionDelegate {

    @Inject
    public ToolbarPresenter(ToolbarView view, @Assisted EditorState<State> editorState) {
        super(view, editorState);
    }

    /** {@inheritDoc} */
    @Override
    public void onMainElementChanged(@Nonnull Shape element) {
        ((ToolbarView)view).showButtons(element.getComponents());
    }

change_editor_states}