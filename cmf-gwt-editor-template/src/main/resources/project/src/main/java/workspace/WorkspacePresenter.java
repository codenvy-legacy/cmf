package current_package;

import main_package.client.State;

import com.codenvy.editor.api.editor.EditorState;
import com.codenvy.editor.api.editor.SelectionManager;
import com.codenvy.editor.api.editor.elements.Shape;
import com.codenvy.editor.api.editor.workspace.AbstractWorkspacePresenter;
import com.codenvy.editor.api.editor.workspace.AbstractWorkspaceView;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javax.annotation.Nonnull;

import_elements
static_import_elements
public class WorkspacePresenter extends AbstractWorkspacePresenter<State> {

    @Inject
    public WorkspacePresenter(WorkspaceView view, @Assisted EditorState<State> state, @Assisted SelectionManager selectionManager) {
        super(view, state, new main_element_name(), selectionManager);
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
    }

    /** {@inheritDoc} */
    @Override
    public void onMouseMoved(int x, int y) {

    }

    /** {@inheritDoc} */
    @Override
    public void onDiagramElementMoved(@Nonnull String elementId, int x, int y) {
        Shape shape = (Shape)elements.get(elementId);
        shape.setX(x);
        shape.setY(y);

        notifyDiagramChangeListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onDiagramElementClicked(@Nonnull String elementId) {
        String prevSelectedElement = selectedElement;
        selectedElement = elementId;

        Shape element = (Shape)elements.get(elementId);
        selectionManager.setElement(element);

        ((AbstractWorkspaceView)view).setZoomInButtonEnable(element.isContainer());

        Shape source;
        Shape parent;

        switch (getState()) {
create_graphic_connections        }
    }

    protected void showElements(@Nonnull Shape mainElement) {
        ((AbstractWorkspaceView)view).clearDiagram();
        elements.clear();

        nodeElement = mainElement;
        selectionManager.setElement(null);
        selectedElement = null;

        int x = 100;
        int y = 100;

        ((AbstractWorkspaceView)view).setZoomInButtonEnable(false);
        ((AbstractWorkspaceView)view).setZoomOutButtonEnable(nodeElement.getParent() != null);

        for (Shape shape : mainElement.getShapes()) {
create_graphical_elements
            shape.setX(x);
            shape.setY(y);

            elements.put(shape.getId(), shape);

            x += 100;
        }

        notifyMainElementChangeListeners();
    }

}