package current_package;

import main_package.client.State;

import com.codenvy.editor.api.editor.EditorState;
import com.codenvy.editor.api.editor.SelectionManager;
import com.codenvy.editor.api.editor.elements.Element;
import com.codenvy.editor.api.editor.elements.MainElement;
import com.codenvy.editor.api.editor.elements.Shape;
import com.codenvy.editor.api.editor.workspace.AbstractWorkspacePresenter;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javax.annotation.Nonnull;

import_elements
static_import_elements
public class WorkspacePresenter extends AbstractWorkspacePresenter<State> {

    @Inject
    public WorkspacePresenter(WorkspaceView view, @Assisted EditorState<State> state, @Assisted SelectionManager selectionManager) {
        super(view, state, new MainElement(), selectionManager);

        selectedElement = mainElement.getId();
        elements.put(selectedElement, mainElement);
    }

    /** {@inheritDoc} */
    @Override
    public void onRightMouseButtonClicked(int x, int y) {

    }

    /** {@inheritDoc} */
    @Override
    public void onLeftMouseButtonClicked(int x, int y) {
        selectionManager.setElement(null);

        switch (getState()) {
create_graphic_elements        }
        
        notifyListeners();
    }

    private void addElement(Element element) {
        elements.put(element.getId(), element);

        Shape parent = (Shape)elements.get(selectedElement);
        parent.addElement(element);
    }

    /** {@inheritDoc} */
    @Override
    public void onMouseMoved(int x, int y) {

    }

    /** {@inheritDoc} */
    @Override
    public void onDiagramElementClicked(@Nonnull String elementId) {
        String prevSelectedElement = selectedElement;
        selectedElement = elementId;

        Element element = elements.get(elementId);
        selectionManager.setElement(element);

        Element source;
        Shape parent;

        switch (getState()) {
create_graphic_connections        }

        notifyListeners();
    }

}