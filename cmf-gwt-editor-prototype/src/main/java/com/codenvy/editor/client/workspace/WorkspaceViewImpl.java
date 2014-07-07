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
package com.codenvy.editor.client.workspace;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.codenvy.editor.api.editor.elements.Shape;
import com.codenvy.editor.api.editor.elements.widgets.shape.ShapeWidget;
import com.codenvy.editor.client.EditorResources;
import com.codenvy.editor.client.elements.Shape1;
import com.codenvy.editor.client.elements.Shape2;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.orange.links.client.DiagramController;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class WorkspaceViewImpl extends WorkspaceView {

    interface WorkspaceViewImplUiBinder extends UiBinder<Widget, WorkspaceViewImpl> {
    }

    @UiField
    FlowPanel  mainPanel;
    @UiField
    Button     zoomIn;
    @UiField
    Button     zoomOut;
    @UiField
    FocusPanel focusPanel;
    @UiField
    CheckBox   autoAlignment;

    private final DiagramController     controller;
    private final PickupDragController  dragController;
    private final Provider<ShapeWidget> widgetProvider;
    private final EditorResources       resources;
    private final Map<String, Widget>   elements;
    private       Widget                selectedElement;

    @Inject
    public WorkspaceViewImpl(WorkspaceViewImplUiBinder ourUiBinder, Provider<ShapeWidget> widgetProvider, EditorResources resources) {
        this.widgetProvider = widgetProvider;
        this.resources = resources;
        this.elements = new HashMap<>();

        widget = ourUiBinder.createAndBindUi(this);

        controller = new DiagramController(400, 400);
        mainPanel.add(controller.getView());

        dragController = new PickupDragController(controller.getView(), true);
        controller.registerDragController(dragController);

        bind();
    }

    private void bind() {
        focusPanel.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                selectElement((Widget)null);
                delegate.onLeftMouseButtonClicked(event.getRelativeX(mainPanel.getElement()), event.getRelativeY(mainPanel.getElement()));
            }
        });

        focusPanel.addDomHandler(new ContextMenuHandler() {
            @Override
            public void onContextMenu(ContextMenuEvent event) {
                selectElement((Widget)null);
                NativeEvent nativeEvent = event.getNativeEvent();
                delegate.onRightMouseButtonClicked(nativeEvent.getClientX(), nativeEvent.getClientY());
            }
        }, ContextMenuEvent.getType());

        focusPanel.addMouseMoveHandler(new MouseMoveHandler() {
            @Override
            public void onMouseMove(MouseMoveEvent event) {
                delegate.onMouseMoved(event.getRelativeX(mainPanel.getElement()), event.getRelativeY(mainPanel.getElement()));
            }
        });

        focusPanel.addDomHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_DELETE) {
                    delegate.onDeleteButtonPressed();
                }
            }
        }, KeyDownEvent.getType());
    }

    /** {@inheritDoc} */
    @Override
    public void clearDiagram() {
        controller.clearDiagram();
    }

    /** {@inheritDoc} */
    @Override
    public void removeElement(@Nonnull String elementId) {
        Widget widget = elements.get(elementId);
        if (widget != null) {
            controller.deleteWidget(widget);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void setZoomInButtonEnable(boolean enable) {
        zoomIn.setEnabled(enable);
    }

    /** {@inheritDoc} */
    @Override
    public void setZoomOutButtonEnable(boolean enable) {
        zoomOut.setEnabled(enable);
    }

    /** {@inheritDoc} */
    @Override
    public void setAutoAlignmentParam(boolean isAutoAligned) {
        autoAlignment.setValue(isAutoAligned);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isAutoAligned() {
        return autoAlignment.getValue();
    }

    /** {@inheritDoc} */
    @Override
    public void selectElement(@Nullable String elementId) {
        Widget widget = elements.get(elementId);
        selectElement(widget);
    }

    @UiHandler("zoomIn")
    public void onZoomInButtonClicked(ClickEvent event) {
        delegate.onZoomInButtonClicked();
    }

    @UiHandler("zoomOut")
    public void onZoomOutButtonClicked(ClickEvent event) {
        delegate.onZoomOutButtonClicked();
    }

    @UiHandler("autoAlignment")
    public void onAutoAlignmentParamChanged(ClickEvent event) {
        delegate.onAutoAlignmentParamChanged();
    }

    /** {@inheritDoc} */
    @Override
    public void addShape1(int x, int y, @Nonnull Shape1 shape) {
        addShape(x, y, shape, resources.shape1());
    }

    /** {@inheritDoc} */
    @Override
    public void addShape2(int x, int y, @Nonnull Shape2 shape) {
        addShape(x, y, shape, resources.shape2());
    }

    private void addShape(int x, int y, @Nonnull final Shape shape, @Nonnull final ImageResource resource) {
        final ShapeWidget elementWidget = widgetProvider.get();

        elementWidget.setTitle(shape.getTitle());
        elementWidget.setIcon(resource);

        elementWidget.addMouseDownHandler(new MouseDownHandler() {
            @Override
            public void onMouseDown(MouseDownEvent event) {
                selectElement(elementWidget);

                switch (event.getNativeButton()) {
                    case NativeEvent.BUTTON_RIGHT:
                        delegate.onDiagramElementRightClicked(shape.getId(), event.getClientX(), event.getClientY());
                        break;

                    case NativeEvent.BUTTON_LEFT:
                    default:
                        delegate.onDiagramElementClicked(shape.getId());
                }
            }
        });

        elementWidget.addMouseUpHandler(new MouseUpHandler() {
            @Override
            public void onMouseUp(MouseUpEvent event) {
                delegate.onDiagramElementMoved(shape.getId(),
                                               elementWidget.getAbsoluteLeft() - mainPanel.getAbsoluteLeft(),
                                               elementWidget.getAbsoluteTop() - mainPanel.getAbsoluteTop());
            }
        });

        controller.addWidget(elementWidget, x, y);
        dragController.makeDraggable(elementWidget);

        elements.put(shape.getId(), elementWidget);

        selectElement(elementWidget);
    }

    private void selectElement(@Nullable Widget element) {
        if (selectedElement != null) {
            selectedElement.removeStyleName(resources.editorCSS().selectedElement());
        }

        selectedElement = element;
        if (selectedElement != null) {
            selectedElement.addStyleName(resources.editorCSS().selectedElement());
        }
    }

    /** {@inheritDoc} */
    @Override
    public void addLink(@Nonnull String sourceElementID, @Nonnull String targetElementID) {
        Widget sourceWidget = elements.get(sourceElementID);
        Widget targetWidget = elements.get(targetElementID);

        controller.drawStraightArrowConnection(sourceWidget, targetWidget);
    }

}