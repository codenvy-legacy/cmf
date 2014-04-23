/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codenvy.modeling.configuration.parser.metamodel.serialization;

import com.codenvy.modeling.configuration.impl.metamodel.serialization.ConnectionDesignationImpl;
import com.codenvy.modeling.configuration.impl.metamodel.serialization.ElementDesignationImpl;
import com.codenvy.modeling.configuration.impl.metamodel.serialization.ElementImpl;
import com.codenvy.modeling.configuration.impl.metamodel.serialization.SerializationConfigurationImpl;
import com.codenvy.modeling.configuration.metamodel.serialization.ConnectionDesignation;
import com.codenvy.modeling.configuration.metamodel.serialization.ElementDesignation;
import com.codenvy.modeling.configuration.metamodel.serialization.SerializationConfiguration;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Stack;

/**
 * @author Dmitry Kuleshov
 */
public class SerializationConfigurationAdapterListener extends SerializationBaseListener {

    private Stack<ElementImpl> elementStack = new Stack<>();

    private Stack<ElementDesignationImpl> elementDesignations = new Stack<>();

    private Stack<ConnectionDesignationImpl> connectionDesignations = new Stack<>();

    private SerializationConfigurationImpl serializationConfiguration = new SerializationConfigurationImpl();

    public SerializationConfiguration getSerializationConfiguration() {
        return serializationConfiguration;
    }

    @Override
    public void enterElement(@NotNull SerializationParser.ElementContext ctx) {
        elementStack.push(new ElementImpl());
    }

    @Override
    public void exitElementName(@NotNull SerializationParser.ElementNameContext ctx) {
        elementStack.peek().setName(ctx.TEXT().getText());
    }

    @Override
    public void exitElementTemplate(@NotNull SerializationParser.ElementTemplateContext ctx) {
        elementStack.peek().setTemplate(ctx.FILENAME().getText());
    }

    @Override
    public void enterElemDesignation(@NotNull SerializationParser.ElemDesignationContext ctx) {
        elementDesignations.push(new ElementDesignationImpl());
    }

    @Override
    public void exitElemRefProperty(@NotNull SerializationParser.ElemRefPropertyContext ctx) {
        elementDesignations.peek().setElementReferencePropertyName(ctx.TEXT().getText());
    }

    @Override
    public void exitElemRefTemplate(@NotNull SerializationParser.ElemRefTemplateContext ctx) {
        elementDesignations.peek().setElementReferenceTemplate(ctx.FILENAME().getText());
    }

    @Override
    public void exitElemDesignation(@NotNull SerializationParser.ElemDesignationContext ctx) {
        ElementDesignationImpl elementDesignation = elementDesignations.pop();
        if (elementDesignation.getElementReferencePropertyName() == null ||
                elementDesignation.getElementReferenceTemplate() == null) {
            elementDesignation.setType(ElementDesignation.Type.INSERTION);
        } else {
            elementDesignation.setType(ElementDesignation.Type.REFERENCE);
        }
        elementStack.peek().setElementDesignation(elementDesignation);
    }

    @Override
    public void enterConDesignation(@NotNull SerializationParser.ConDesignationContext ctx) {
        connectionDesignations.push(new ConnectionDesignationImpl());
    }

    @Override
    public void exitConRefProperty(@NotNull SerializationParser.ConRefPropertyContext ctx) {
        connectionDesignations.peek().setConnectionReferencePropertyName(ctx.TEXT().getText());
    }

    @Override
    public void exitConRefTemplate(@NotNull SerializationParser.ConRefTemplateContext ctx) {
        connectionDesignations.peek().setConnectionReferencePropertyName(ctx.FILENAME().getText());
    }

    @Override
    public void exitConDesignation(@NotNull SerializationParser.ConDesignationContext ctx) {
        ConnectionDesignationImpl connectionDesignation = connectionDesignations.pop();
        if (connectionDesignation.getConnectionReferencePropertyName() == null ||
                connectionDesignation.getConnectionReferenceTemplate() == null) {
            connectionDesignation.setType(ConnectionDesignation.Type.ORDERING);
        } else {
            connectionDesignation.setType(ConnectionDesignation.Type.REFERENCE);
        }
        elementStack.peek().setConnectionDesignation(connectionDesignation);
    }

    @Override
    public void exitElement(@NotNull SerializationParser.ElementContext ctx) {
        serializationConfiguration.addElement(elementStack.pop());
    }
}

