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
import java.util.List;

/**
 * The abstract implementation of {@link Link}. It contains the implementation of general methods which might not be changed.
 *
 * @author Andrey Plotnikov
 */
public abstract class AbstractLink extends AbstractElement implements Link {

    private Shape source;
    private Shape target;

    protected AbstractLink(@Nonnull Shape source, @Nonnull Shape target, @Nonnull String elementName, @Nonnull List<String> properties) {
        super(elementName, properties);

        this.source = source;
        this.target = target;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public Shape getSource() {
        return source;
    }

    /** {@inheritDoc} */
    @Override
    public void setSource(@Nonnull Shape source) {
        this.source = source;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public Shape getTarget() {
        return target;
    }

    /** {@inheritDoc} */
    @Override
    public void setTarget(@Nonnull Shape target) {
        this.target = target;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String serialize() {
        // TODO It is not implemented for now.
        return "";
    }

}