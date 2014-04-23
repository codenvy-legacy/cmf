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

import com.codenvy.modeling.configuration.editor.Text;

import javax.annotation.Nonnull;

/**
 * @author Dmitry Kuleshov
 */
public class TextImpl implements Text {
    private Alignment alignment;

    @Nonnull
    @Override
    public Alignment getAlignment() {
        return alignment;
    }

    public void setAlignment(@Nonnull Alignment alignment) {
        this.alignment = alignment;
    }

    @Override
    public String toString() {
        return "\nTextImpl{" +
                "alignment=" + alignment +
                '}';
    }
}
