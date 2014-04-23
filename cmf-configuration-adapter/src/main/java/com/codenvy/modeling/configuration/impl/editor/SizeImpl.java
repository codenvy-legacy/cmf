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

import com.codenvy.modeling.configuration.editor.Size;

import javax.annotation.Nonnegative;

/**
 * @author Dmitry Kuleshov
 */
public class SizeImpl implements Size {
    private int compact;
    private int full;

    @Nonnegative
    @Override
    public int getCompact() {
        return compact;
    }

    @Nonnegative
    @Override
    public int getFull() {
        return full;
    }

    public void setCompact(@Nonnegative int compact) {
        this.compact = compact;
    }

    public void setFull(@Nonnegative int full) {
        this.full = full;
    }

    @Override
    public String toString() {
        return "\nSizeImpl{" +
                "compact=" + compact +
                ", full=" + full +
                '}';
    }
}
