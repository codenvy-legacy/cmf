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
package com.codenvy.editor.client.elements;

import com.google.gwt.xml.client.Node;

import javax.annotation.Nonnull;
import java.util.Arrays;

/**
 * @author Andrey Plotnikov
 */
public class Shape1 extends MainElement {

    private String property1;

    public Shape1() {
        super("Shape1", Arrays.asList("property1"), Arrays.asList("property1", "x", "y", "uuid", "autoAlign"));

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
        return "<property1>\n" + property1 + "\n</property1>\n";
    }

    @Override
    public void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        switch (nodeName) {
            case "property1":
                property1 = nodeValue;
                break;
            case "x":
                setX(Integer.valueOf(nodeValue));
                break;
            case "y":
                setY(Integer.valueOf(nodeValue));
                break;
            case "uuid":
                id = nodeValue;
                break;
            case "autoAlign":
                setAutoAlignmentParam(Boolean.valueOf(nodeValue));
                break;
        }
    }

}