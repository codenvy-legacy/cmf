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
package com.codenvy.editor.client.elements.shape1;

import com.codenvy.editor.api.editor.elements.AbstractShape;

import javax.annotation.Nonnull;

/**
 * @author Andrey Plotnikov
 */
public class Shape1 extends AbstractShape {

    private String property1;

    public Shape1() {
        super("Shape1");

        property1 = "property1";
    }

    public String getProperty1() {
        return property1;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    @Nonnull
    @Override
    protected String serializeProperties() {
        StringBuilder properties = new StringBuilder();

        properties.append('<').append("property1").append(">\n")
                  .append(property1).append('\n')
                  .append("</").append("property1").append(">\n");

        return properties.toString();
    }

}