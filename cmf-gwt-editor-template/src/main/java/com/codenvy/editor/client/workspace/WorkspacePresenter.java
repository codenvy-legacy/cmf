/*
 * Copyright [2014] Codenvy, S.A.
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
package com.codenvy.editor.client.workspace;

import com.codenvy.editor.api.editor.EditorState;
import com.codenvy.editor.api.editor.SelectionManager;
import com.codenvy.editor.api.editor.elements.Element;
import com.codenvy.editor.api.editor.workspace.AbstractWorkspacePresenter;
import com.codenvy.editor.client.State;
import com.codenvy.editor.client.elements.shape1.Shape1;
import com.codenvy.editor.client.elements.shape2.Shape2;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

import static com.codenvy.editor.client.State.CREATING_LINK1_TARGET;
import static com.codenvy.editor.client.State.CREATING_NOTING;

/**
 * @author Andrey Plotnikov
 */
public class WorkspacePresenter extends AbstractWorkspacePresenter<State> {

    private Map<String, Element> elements;
    private String               selectedElement;
    private int                  shape1Id;
    private int                  shape2Id;
    private int                  link1Id;
    private SelectionManager     selectionManager;

    @Inject
    public WorkspacePresenter(WorkspaceView view, @Assisted EditorState<State> state, @Assisted SelectionManager selectionManager) {
        super(view, state);

        this.selectionManager = selectionManager;
        this.elements = new HashMap<>();

        this.shape1Id = 0;
        this.shape2Id = 0;
        this.link1Id = 0;
    }

    /** {@inheritDoc} */
    @Override
    public void onRightMouseButtonClicked(int x, int y) {
        // TODO add implementation
        Window.alert("onRightMouseButtonClicked");
    }

    /** {@inheritDoc} */
    @Override
    public void onLeftMouseButtonClicked(int x, int y) {
        selectionManager.setElement(null);

        switch (state.getState()) {
            case CREATING_SHAPE1:
                Shape1 shape1 = new Shape1(Shape1.class.getSimpleName() + ' ' + shape1Id, Shape1.class.getSimpleName() + ' ' + shape1Id);

                ((WorkspaceView)view).addShape1(x, y, shape1);
                addElement(shape1);

                state.setState(CREATING_NOTING);

                shape1Id++;
                break;

            case CREATING_SHAPE2:
                Shape2 shape2 = new Shape2(Shape2.class.getSimpleName() + ' ' + shape2Id, Shape2.class.getSimpleName() + ' ' + shape2Id);

                ((WorkspaceView)view).addShape2(x, y, shape2);
                addElement(shape2);

                state.setState(CREATING_NOTING);

                shape2Id++;
                break;
        }
    }

    private void addElement(Element element) {
        elements.put(element.getId(), element);
    }

    /** {@inheritDoc} */
    @Override
    public void onMouseMoved(int x, int y) {
        // TODO add implementation
//        Window.alert("onMouseMoved");
    }

    /** {@inheritDoc} */
    @Override
    public void onDiagramElementClicked(@Nonnull String elementId) {
        String prevSelectedElement = selectedElement;
        selectedElement = elementId;

        Element element = elements.get(elementId);
        selectionManager.setElement(element);

        switch (state.getState()) {
            case CREATING_LINK1_SOURCE:
                state.setState(CREATING_LINK1_TARGET);
                break;

            case CREATING_LINK1_TARGET:
                ((WorkspaceView)view).addLink(prevSelectedElement, selectedElement);

                state.setState(CREATING_NOTING);
                break;
        }
    }

}