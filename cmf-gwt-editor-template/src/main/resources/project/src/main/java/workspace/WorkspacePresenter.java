package current_package;

import main_package.client.State;

import com.codenvy.editor.api.editor.EditorState;
import com.codenvy.editor.api.editor.SelectionManager;
import com.codenvy.editor.api.editor.elements.Link;
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
    public void onLeftMouseButtonClicked(int x, int y) {
        selectionManager.setElement(null);

        switch (getState()) {
create_graphic_elements        }
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

        ((AbstractWorkspaceView)view).setZoomInButtonEnable(false);
        ((AbstractWorkspaceView)view).setZoomOutButtonEnable(nodeElement.getParent() != null);

        int defaultX = 100;
        int defaultY = 100;

        for (Shape shape : mainElement.getShapes()) {
            int x = shape.getX();
            int y = shape.getY();

            if (x == Shape.UNDEFINED_POSITION) {
                x = defaultX;
                defaultX += 100;
            }

            if (y == Shape.UNDEFINED_POSITION) {
                y = defaultY;
            }

create_graphical_elements
            shape.setX(x);
            shape.setY(y);

            elements.put(shape.getId(), shape);
        }

        for (Link link : mainElement.getLinks()) {
create_links        }

        notifyMainElementChangeListeners();
    }

}