package current_package;

import com.codenvy.editor.api.editor.AbstractEditor;
import com.codenvy.editor.api.editor.EditorState;
import com.codenvy.editor.api.editor.EditorView;
import com.codenvy.editor.api.editor.SelectionManager;
import com.codenvy.editor.api.editor.propertiespanel.PropertiesPanelManager;
import com.codenvy.editor.api.editor.propertiespanel.empty.EmptyPropertiesPanelPresenter;
import_elements
import main_package.client.inject.EditorFactory;
import_properties_panel_elements
import com.google.inject.Inject;

import javax.annotation.Nonnull;

public class editor_name extends AbstractEditor implements EditorView.ActionDelegate {

    @Inject
    public editor_name ( EditorView view,
                        EditorFactory editorFactory,
                        SelectionManager selectionManager,
                        EmptyPropertiesPanelPresenter emptyPropertiesPanelPresenter,
constructor_arguments) {
        super(view);

        EditorState<State> state = new EditorState<>(State.CREATING_NOTHING);

        this.workspace = editorFactory.createWorkspace(state, selectionManager);
        this.toolbar = editorFactory.createToolbar(state);

        PropertiesPanelManager propertiesPanelManager = editorFactory.createPropertiesPanelManager(view.getPropertiesPanel());
constructor_body

        propertiesPanelManager.register(null, emptyPropertiesPanelPresenter);
        emptyPropertiesPanelPresenter.addListener(this);

        selectionManager.addListener(propertiesPanelManager);
        workspace.addListener(this);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String serialize() {
        return workspace.serialize();
    }

    /** {@inheritDoc} */
    @Override
    public void deserialize(@Nonnull String content) {
        workspace.deserialize(content);
    }

}