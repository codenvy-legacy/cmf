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

import com.codenvy.editor.api.mvp.AbstractView;

import javax.annotation.Nonnull;

/**
 * The abstract presentation of an editor workspace view.
 *
 * @author Andrey Plotnikov
 */
public abstract class AbstractWorkspaceView extends AbstractView<AbstractWorkspaceView.ActionDelegate> {

    /** Required for delegating functions in the view. */
    public interface ActionDelegate extends AbstractView.ActionDelegate {
        /**
         * Performs some actions in response to a user's doing right mouse click.
         *
         * @param x
         *         the mouse x-position
         * @param y
         *         the mouse y-position
         */
        void onRightMouseButtonClicked(int x, int y);

        /**
         * Performs some actions in response to a user's doing left mouse click.
         *
         * @param x
         *         the mouse x-position
         * @param y
         *         the mouse y-position
         */
        void onLeftMouseButtonClicked(int x, int y);

        /**
         * Performs some actions in response to a user's moving mouse.
         *
         * @param x
         *         the mouse x-position
         * @param y
         *         the mouse y-position
         */
        void onMouseMoved(int x, int y);

        /**
         * Performs some actions in response to a user's clicking on diagram element.
         *
         * @param elementId
         *         the identifier of clicked diagram element
         */
        void onDiagramElementClicked(@Nonnull String elementId);

        /**
         * Performs some actions in response to a user's moving on diagram element.
         *
         * @param elementId
         *         the identifier of moved diagram element
         */
        void onDiagramElementMoved(@Nonnull String elementId, int x, int y);

        /** Performs any actions appropriate in response to the user having pressed the Zoom In button. */
        void onZoomInButtonClicked();

        /** Performs any actions appropriate in response to the user having pressed the Zoom Out button. */
        void onZoomOutButtonClicked();

    }

    /** Clear diagram content. */
    public abstract void clearDiagram();

    /**
     * Change the enable state of the Zoom In button.
     *
     * @param enable
     *         <code>true</code> to enable the button, <code>false</code> to disable it
     */
    public abstract void setZoomInButtonEnable(boolean enable);

    /**
     * Change the enable state of the Zoom Out button.
     *
     * @param enable
     *         <code>true</code> to enable the button, <code>false</code> to disable it
     */
    public abstract void setZoomOutButtonEnable(boolean enable);

}