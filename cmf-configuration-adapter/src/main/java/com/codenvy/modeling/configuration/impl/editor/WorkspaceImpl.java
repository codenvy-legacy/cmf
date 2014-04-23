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

package com.codenvy.modeling.configuration.impl.editor;

import com.codenvy.modeling.configuration.editor.Workspace;

import javax.annotation.Nonnull;

/**
 * @author Dmitry Kuleshov
 */
public class WorkspaceImpl implements Workspace {
    private Scrollability scrollability;

    @Nonnull
    @Override
    public Scrollability getScrollability() {
        return scrollability;
    }

    public void setScrollability(@Nonnull Scrollability scrollability) {
        this.scrollability = scrollability;
    }

    @Override
    public String toString() {
        return "\nWorkspaceImpl{" +
                "scrollability=" + scrollability +
                '}';
    }
}
