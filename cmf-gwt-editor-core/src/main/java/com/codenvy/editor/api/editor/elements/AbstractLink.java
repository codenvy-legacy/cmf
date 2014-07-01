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

import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * The abstract implementation of {@link Link}. It contains the implementation of general methods which might not be changed.
 *
 * @author Andrey Plotnikov
 */
public abstract class AbstractLink extends AbstractElement implements Link {

    private String source;
    private String target;

    protected AbstractLink(@Nullable String source,
                           @Nullable String target,
                           @Nonnull String elementName,
                           @Nonnull List<String> properties,
                           @Nonnull List<String> internalProperties) {
        super(elementName, properties, internalProperties);

        this.source = source;
        this.target = target;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getSource() {
        return source;
    }

    /** {@inheritDoc} */
    @Override
    public void setSource(@Nonnull String source) {
        this.source = source;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getTarget() {
        return target;
    }

    /** {@inheritDoc} */
    @Override
    public void setTarget(@Nonnull String target) {
        this.target = target;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String serialize() {
        // TODO It is not implemented for now.
        return "";
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String serializeInternalFormat() {
        return '<' + getElementName() + ">\n" +
               "<source>\n" + source + "</source>\n" +
               "<target>\n" + target + "</target>\n" +
               "<uuid>\n" + id + "\n</uuid>\n" +
               "</" + getElementName() + ">\n";
    }

    /** {@inheritDoc} */
    @Override
    public void deserialize(@Nonnull Node node) {
        // TODO It is not implemented for now.
    }

    /** {@inheritDoc} */
    @Override
    public void deserializeInternalFormat(@Nonnull Node node) {
        NodeList childNodes = node.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            applyProperty(item);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        switch (nodeName) {
            case "source":
                source = nodeValue;
                break;
            case "target":
                target = nodeValue;
                break;
            case "uuid":
                id = nodeValue;
                break;
        }
    }

}