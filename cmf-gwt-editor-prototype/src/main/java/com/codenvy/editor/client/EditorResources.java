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
package com.codenvy.editor.client;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

/**
 * @author Andrey Plotnikov
 */
public interface EditorResources extends ClientBundle {

    interface EditorCSS extends CssResource {

        String fullSize();

    }

    @Source("editor.css")
    EditorCSS editorCSS();

    @Source("random_picture_2.png")
    ImageResource shape1();

    @Source("random_picture_1.png")
    ImageResource shape2();

}