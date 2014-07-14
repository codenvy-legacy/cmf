/*
 * Copyright 2014 Codenvy, S.A.
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
package com.codenvy.editor.api.editor.workspace;

import com.codenvy.editor.api.editor.EditorState;
import com.codenvy.editor.api.editor.HasState;
import com.codenvy.editor.api.editor.SelectionManager;
import com.codenvy.editor.api.editor.common.ContentFormatter;
import com.codenvy.editor.api.editor.elements.Element;
import com.codenvy.editor.api.editor.elements.Shape;
import com.codenvy.editor.api.mvp.AbstractPresenter;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.orange.links.client.menu.ContextMenu;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The abstract presentation of an editor workspace. It contains the implementation of general methods which might not be changed.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public abstract class AbstractWorkspacePresenter<T> extends AbstractPresenter implements AbstractWorkspaceView.ActionDelegate, HasState<T> {

    private EditorState<T> state;

    protected       String                          selectedElementId;
    protected final Shape                           mainElement;
    protected       SelectionManager                selectionManager;
    protected final Map<String, Element>            elements;
    private final   List<DiagramChangeListener>     diagramChangeListeners;
    private final   List<MainElementChangeListener> mainElementChangeListeners;
    protected       Shape                           nodeElement;
    private final   ContextMenu                     contextMenu;

    protected AbstractWorkspacePresenter(@Nonnull final AbstractWorkspaceView view,
                                         @Nonnull EditorState<T> state,
                                         @Nonnull Shape mainElement,
                                         @Nonnull SelectionManager selectionManager) {
        super(view);

        this.state = state;
        this.selectionManager = selectionManager;
        this.mainElement = mainElement;
        this.nodeElement = mainElement;
        this.elements = new HashMap<>();
        this.diagramChangeListeners = new ArrayList<>();
        this.mainElementChangeListeners = new ArrayList<>();

        this.selectionManager.setElement(mainElement);
        this.selectedElementId = mainElement.getId();

        this.elements.put(selectedElementId, mainElement);

        this.contextMenu = new ContextMenu();
        this.contextMenu.addItem("Delete", new Command() {
            @Override
            public void execute() {
                deleteSelectedElement();
                contextMenu.hide();
            }
        });
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public T getState() {
        return state.getState();
    }

    /** {@inheritDoc} */
    @Override
    public void setState(@Nonnull T state) {
        this.state.setState(state);
    }

    public void addDiagramChangeListener(@Nonnull DiagramChangeListener listener) {
        diagramChangeListeners.add(listener);
    }

    public void removeDiagramChangeListener(@Nonnull DiagramChangeListener listener) {
        diagramChangeListeners.remove(listener);
    }

    public void notifyDiagramChangeListeners() {
        for (DiagramChangeListener listener : diagramChangeListeners) {
            listener.onChanged();
        }
    }

    public void addMainElementChangeListener(@Nonnull MainElementChangeListener listener) {
        mainElementChangeListeners.add(listener);
    }

    public void removeMainElementChangeListener(@Nonnull MainElementChangeListener listener) {
        mainElementChangeListeners.remove(listener);
    }

    public void notifyMainElementChangeListeners() {
        for (MainElementChangeListener listener : mainElementChangeListeners) {
            listener.onMainElementChanged(nodeElement);
        }
    }

    @Nonnull
    public String serialize() {
        return ContentFormatter.formatXML(ContentFormatter.trimXML(mainElement.serialize()));
    }

    @Nonnull
    public String serializeInternalFormat() {
        return ContentFormatter.trimXML(mainElement.serializeInternalFormat());
    }

    public void deserialize(@Nonnull String content) {
        mainElement.deserialize(ContentFormatter.trimXML(content));
        showElements(mainElement);
    }

    public void deserializeInternalFormat(@Nonnull String content) {
        mainElement.deserializeInternalFormat(ContentFormatter.trimXML(content));
        showElements(mainElement);
    }

    /** {@inheritDoc} */
    @Override
    public void onRightMouseButtonClicked(int x, int y) {
        selectElement(null);
    }

    /** {@inheritDoc} */
    @Override
    public void onMouseMoved(int x, int y) {
        // TODO add implementation
    }

    /** {@inheritDoc} */
    @Override
    public void onDeleteButtonPressed() {
        deleteSelectedElement();
    }

    private void deleteSelectedElement() {
        Shape element = (Shape)elements.remove(selectedElementId);

        if (element != null) {
            nodeElement.removeShape(element);
            ((AbstractWorkspaceView)view).removeElement(selectedElementId);

            if (nodeElement.isAutoAligned()) {
                showElements(nodeElement);
            }

            notifyDiagramChangeListeners();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onDiagramElementRightClicked(@Nonnull String elementId, int x, int y) {
        selectElement(elementId);

        contextMenu.setPopupPosition(x, y);
        contextMenu.show();
    }

    /** {@inheritDoc} */
    @Override
    public void onMouseOutDiagramElement(@Nonnull String elementId) {
        ((AbstractWorkspaceView)view).unselectErrorElement(elementId);
    }

    /** {@inheritDoc} */
    @Override
    public void onZoomInButtonClicked() {
        Shape element = (Shape)elements.get(selectedElementId);

        if (element != null) {
            showElements(element);
            nodeElement = element;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onZoomOutButtonClicked() {
        Shape nodeElementParent = nodeElement.getParent();

        if (nodeElementParent != null) {
            showElements(nodeElementParent);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onDiagramElementMoved(@Nonnull String elementId, int x, int y) {
        Shape shape = (Shape)elements.get(elementId);
        shape.setX(x);
        shape.setY(y);

        if (nodeElement.isAutoAligned()) {
            showElements(nodeElement);
        }

        notifyDiagramChangeListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onAutoAlignmentParamChanged() {
        nodeElement.setAutoAlignmentParam(((AbstractWorkspaceView)view).isAutoAligned());
        showElements(nodeElement);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        showElements(mainElement);
    }

    protected void addShape(@Nonnull Shape shape, int x, int y) {
        String shapeId = shape.getId();

        elements.put(shapeId, shape);
        nodeElement.addShape(shape);

        shape.setX(x);
        shape.setY(y);

        selectElement(shapeId);

        if (nodeElement.isAutoAligned()) {
            showElements(nodeElement);
        }

        notifyDiagramChangeListeners();
    }

    protected void selectElement(@Nullable String elementId) {
        selectedElementId = elementId;
        Shape shape = (Shape)elements.get(elementId);

        selectionManager.setElement(shape);

        ((AbstractWorkspaceView)view).selectElement(elementId);
        ((AbstractWorkspaceView)view).setZoomInButtonEnable(shape != null && shape.isContainer());
    }

    protected abstract void showElements(@Nonnull Shape element);

    public interface DiagramChangeListener {

        void onChanged();

    }

    public interface MainElementChangeListener {

        void onMainElementChanged(@Nonnull Shape element);

    }

}