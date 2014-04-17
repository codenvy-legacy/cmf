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
package com.codenvy.editor.api.mvp;

import com.google.gwt.user.client.ui.IsWidget;

import javax.annotation.Nonnull;

/**
 * The general presentation of view.
 *
 * @param <T>
 *         type of action delegate
 * @author Andrey Plotnikov
 */
public interface View<T> extends IsWidget {
    /**
     * Set a delegate that receives events from view to presenter.
     *
     * @param delegate
     *         delgate that need to be applied
     */
    void setDelegate(@Nonnull T delegate);
}