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
        ((AbstractWorkspaceView)view).setZoomInButtonEnable(false);

        switch (getState()) {
create_graphic_elements        }
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

    @Override
    protected void showElements(@Nonnull Shape element) {
        ((AbstractWorkspaceView)view).clearDiagram();

        nodeElement = element;
        Shape selectedElement = null;

        ((AbstractWorkspaceView)view).setZoomOutButtonEnable(nodeElement.getParent() != null);
        ((AbstractWorkspaceView)view).setAutoAlignmentParam(nodeElement.isAutoAligned());

        int defaultX = 100;
        int defaultY = 100;

        for (Shape shape : nodeElement.getShapes()) {
            int x = shape.getX();
            int y = shape.getY();

            if (x == Shape.UNDEFINED_POSITION || nodeElement.isAutoAligned()) {
                x = defaultX;
                defaultX += 100;
            }

            if (y == Shape.UNDEFINED_POSITION || nodeElement.isAutoAligned()) {
                y = defaultY;
            }

create_graphical_elements
            shape.setX(x);
            shape.setY(y);

            if (shape.getId().equals(this.selectedElement)) {
                selectedElement = shape;
            }

            elements.put(shape.getId(), shape);
        }

        selectionManager.setElement(selectedElement);
        ((AbstractWorkspaceView)view).setZoomInButtonEnable(selectedElement != null && selectedElement.isContainer());
        this.selectedElement = selectedElement != null ? selectedElement.getId() : null;

        for (Link link : mainElement.getLinks()) {
create_links        }

        notifyMainElementChangeListeners();
    }

}