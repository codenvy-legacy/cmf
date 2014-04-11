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
package com.codenvy.editor.api.editor.elements;

import javax.annotation.Nonnull;

/**
 * @author Andrey Plotnikov
 */
public abstract class AbstractShape extends AbstractElement implements Shape {

    protected String title;

    protected AbstractShape(@Nonnull String name, @Nonnull String title) {
        super(name);
        this.title = title;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getTitle() {
        return title;
    }

    /** {@inheritDoc} */
    @Override
    public void setTitle(@Nonnull String title) {
        this.title = title;
    }

}