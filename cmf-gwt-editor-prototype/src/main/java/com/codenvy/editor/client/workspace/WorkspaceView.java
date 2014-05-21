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

import com.codenvy.editor.api.editor.workspace.AbstractWorkspaceView;
import com.codenvy.editor.client.elements.Shape1;
import com.codenvy.editor.client.elements.Shape2;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;

/**
 * @author Andrey Plotnikov
 */
@ImplementedBy(WorkspaceViewImpl.class)
public abstract class WorkspaceView extends AbstractWorkspaceView {

    public abstract void addShape1(int x, int y, @Nonnull Shape1 shape);

    public abstract void addShape2(int x, int y, @Nonnull Shape2 shape);

    public abstract void addLink(@Nonnull String sourceElementID, @Nonnull String targetElementID);

}