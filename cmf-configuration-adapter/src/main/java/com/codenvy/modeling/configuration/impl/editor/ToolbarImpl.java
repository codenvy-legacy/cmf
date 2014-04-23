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

import com.codenvy.modeling.configuration.editor.Group;
import com.codenvy.modeling.configuration.editor.Item;
import com.codenvy.modeling.configuration.editor.Size;
import com.codenvy.modeling.configuration.editor.Toolbar;

import javax.annotation.Nonnull;

/**
 * @author Dmitry Kuleshov
 */
public class ToolbarImpl implements Toolbar {
    private Alignment alignment;
    private Size size;
    private Group group;
    private Item item;

    @Nonnull
    @Override
    public Alignment getAlignment() {
        return alignment;
    }

    @Nonnull
    @Override
    public Size getSize() {
        return size;
    }

    @Nonnull
    @Override
    public Group getGroup() {
        return group;
    }

    @Nonnull
    @Override
    public Item getItem() {
        return item;
    }

    public void setAlignment(@Nonnull Alignment alignment) {
        this.alignment = alignment;
    }

    public void setSize(@Nonnull Size size) {
        this.size = size;
    }

    public void setGroup(@Nonnull Group group) {
        this.group = group;
    }

    public void setItem(@Nonnull Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "\nToolbarImpl{" +
                "alignment=" + alignment +
                ", size=" + size +
                ", group=" + group +
                ", item=" + item +
                '}';
    }
}
