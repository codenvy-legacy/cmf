package current_package;

import main_package.client.State;

import com.codenvy.editor.api.editor.EditorState;
import com.codenvy.editor.api.editor.SelectionManager;
import com.codenvy.editor.api.editor.elements.Element;
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
        super(view, state, new main_element_name(), selectionManager);

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
    }

    private void addShape(@Nonnull Shape shape, int x, int y) {
        elements.put(shape.getId(), shape);

        shape.setX(x);
        shape.setY(y);

        Shape parent = (Shape)elements.get(selectedElement);
        if (parent == null) {
            parent = mainElement;
        }
        parent.addShape(shape);

        notifyListeners();
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

        notifyListeners();
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
    }

    /** {@inheritDoc} */
    @Override
    public void deserialize(@Nonnull String content) {
        super.deserialize(content);

        int x = 100;
        int y = 100;

        for (Shape shape : mainElement.getShapes()) {
create_graphical_elements
            shape.setX(x);
            shape.setY(y);

            elements.put(shape.getId(), shape);

            x += 100;
        }
    }

}