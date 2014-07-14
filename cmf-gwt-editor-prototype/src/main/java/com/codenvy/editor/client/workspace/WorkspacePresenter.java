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
import com.codenvy.editor.api.editor.elements.Link;
import com.codenvy.editor.api.editor.elements.Shape;
import com.codenvy.editor.api.editor.workspace.AbstractWorkspacePresenter;
import com.codenvy.editor.api.editor.workspace.AbstractWorkspaceView;
import com.codenvy.editor.client.State;
import com.codenvy.editor.client.elements.Link1;
import com.codenvy.editor.client.elements.MainElement;
import com.codenvy.editor.client.elements.Shape1;
import com.codenvy.editor.client.elements.Shape2;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javax.annotation.Nonnull;

import static com.codenvy.editor.client.State.CREATING_LINK1_TARGET;
import static com.codenvy.editor.client.State.CREATING_NOTING;

/**
 * @author Andrey Plotnikov
 */
public class WorkspacePresenter extends AbstractWorkspacePresenter<State> {

    @Inject
    public WorkspacePresenter(WorkspaceView view, @Assisted EditorState<State> state, @Assisted SelectionManager selectionManager) {
        super(view, state, new MainElement(), selectionManager);
    }

    /** {@inheritDoc} */
    @Override
    public void onLeftMouseButtonClicked(int x, int y) {
        selectElement(null);

        switch (getState()) {
            case CREATING_SHAPE1:
                Shape1 shape1 = new Shape1();

                ((WorkspaceView)view).addShape1(x, y, shape1);
                addShape(shape1, x, y);

                setState(CREATING_NOTING);
                break;

            case CREATING_SHAPE2:
                Shape2 shape2 = new Shape2();

                ((WorkspaceView)view).addShape2(x, y, shape2);
                addShape(shape2, x, y);

                setState(CREATING_NOTING);
                break;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onDiagramElementClicked(@Nonnull String elementId) {
        String prevSelectedElement = selectedElementId;
        selectElement(elementId);

        Shape selectedElement = (Shape)elements.get(prevSelectedElement);
        Shape element = (Shape)elements.get(elementId);

        ((AbstractWorkspaceView)view).unselectErrorElement(elementId);

        Shape parent;

        switch (getState()) {
            case CREATING_LINK1_SOURCE:
                setState(CREATING_LINK1_TARGET);
                break;

            case CREATING_LINK1_TARGET:
                if (selectedElement.canCreateConnection("Link1", element.getElementName())) {
                    ((WorkspaceView)view).addLink(prevSelectedElement, selectedElementId);

                    Link1 link = new Link1(selectedElement.getId(), element.getId());
                    elements.put(element.getId(), element);

                    parent = selectedElement.getParent();
                    if (parent != null) {
                        parent.addLink(link);
                    }

                    notifyDiagramChangeListeners();
                }

                setState(CREATING_NOTING);
                selectElement(elementId);

                break;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onMouseOverDiagramElement(@Nonnull String elementId) {
        Element element = elements.get(elementId);
        if (element == null) {
            return;
        }

        switch (getState()) {
            case CREATING_LINK1_TARGET:
                Shape selectedElement = (Shape)elements.get(selectedElementId);
                if (!selectedElement.canCreateConnection("Link1", element.getElementName())) {
                    ((AbstractWorkspaceView)view).selectErrorElement(elementId);
                }
                break;
            default:
        }
    }

    @Override
    protected void showElements(@Nonnull Shape element) {
        ((AbstractWorkspaceView)view).clearDiagram();

        nodeElement = element;
        String selectedElement = null;

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

            if (shape instanceof Shape1) {
                ((WorkspaceView)view).addShape1(x, y, (Shape1)shape);
            } else if (shape instanceof Shape2) {
                ((WorkspaceView)view).addShape2(x, y, (Shape2)shape);
            }

            shape.setX(x);
            shape.setY(y);

            if (shape.getId().equals(selectedElementId)) {
                selectedElement = shape.getId();
            }

            elements.put(shape.getId(), shape);
        }

        selectElement(selectedElement);

        for (Link link : mainElement.getLinks()) {
            if (link instanceof Link1) {
                ((WorkspaceView)view).addLink(link.getSource(), link.getTarget());
            }
        }

        notifyMainElementChangeListeners();
    }

}