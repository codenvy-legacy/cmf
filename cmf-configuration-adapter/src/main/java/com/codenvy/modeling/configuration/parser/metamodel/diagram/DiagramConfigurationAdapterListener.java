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

package com.codenvy.modeling.configuration.parser.metamodel.diagram;

import com.codenvy.modeling.configuration.impl.metamodel.diagram.*;
import com.codenvy.modeling.configuration.metamodel.diagram.Connection;
import com.codenvy.modeling.configuration.metamodel.diagram.DiagramConfiguration;
import com.codenvy.modeling.configuration.metamodel.diagram.Property;
import com.codenvy.modeling.configuration.parser.metamodel.generated.DiagramBaseListener;
import com.codenvy.modeling.configuration.parser.metamodel.generated.DiagramParser;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Stack;

/**
 * @author Dmitry Kuleshov
 */
public class DiagramConfigurationAdapterListener extends DiagramBaseListener {

    private Stack<ElementImpl> elementStack = new Stack<>();

    private Stack<PropertyImpl> propertyStack = new Stack<>();

    private Stack<ComponentImpl> componentStack = new Stack<>();

    private Stack<ConnectionImpl> connectionStack = new Stack<>();

    private DiagramConfigurationImpl diagramConfiguration = new DiagramConfigurationImpl();

    public DiagramConfiguration getDiagramConfiguration() {
        return diagramConfiguration;
    }

    @Override
    public void enterElement(@NotNull DiagramParser.ElementContext ctx) {
        elementStack.push(new ElementImpl());
    }

    @Override
    public void exitElementName(@NotNull DiagramParser.ElementNameContext ctx) {
        elementStack.peek().setName(ctx.TEXT().getText());
    }

    @Override
    public void enterElementProperty(@NotNull DiagramParser.ElementPropertyContext ctx) {
        propertyStack.push(new PropertyImpl());
    }

    @Override
    public void exitPropertyName(@NotNull DiagramParser.PropertyNameContext ctx) {
        propertyStack.peek().setName(ctx.TEXT().getText());
    }

    @Override
    public void exitPropertyType(@NotNull DiagramParser.PropertyTypeContext ctx) {
        propertyStack.peek().setType(Property.Type.valueOf(ctx.TEXT().getText().toUpperCase()));
    }

    @Override
    public void exitPropertyValue(@NotNull DiagramParser.PropertyValueContext ctx) {
        propertyStack.peek().setValue(ctx.TEXT().getText());
    }

    @Override
    public void exitElementProperty(@NotNull DiagramParser.ElementPropertyContext ctx) {
        elementStack.peek().addProperty(propertyStack.pop());
    }

    @Override
    public void enterElementComponent(@NotNull DiagramParser.ElementComponentContext ctx) {
        componentStack.push(new ComponentImpl());
    }

    @Override
    public void exitElementComponent(@NotNull DiagramParser.ElementComponentContext ctx) {
        componentStack.peek().setName(ctx.TEXT().getText());
        elementStack.peek().addComponent(componentStack.pop());
    }

    @Override
    public void enterElementConnection(@NotNull DiagramParser.ElementConnectionContext ctx) {
        connectionStack.push(new ConnectionImpl());
    }

    @Override
    public void exitConnectionName(@NotNull DiagramParser.ConnectionNameContext ctx) {
        connectionStack.peek().setName(ctx.TEXT().getText());
    }

    @Override
    public void exitConnectionDestination(@NotNull DiagramParser.ConnectionDestinationContext ctx) {
        connectionStack.peek().setDestination(ctx.TEXT().getText());
    }

    @Override
    public void exitConnectionType(@NotNull DiagramParser.ConnectionTypeContext ctx) {
        connectionStack.peek().setType(Connection.Type.valueOf(ctx.getText().toUpperCase()));
    }

    @Override
    public void exitConnectionRelation(@NotNull DiagramParser.ConnectionRelationContext ctx) {
        connectionStack.peek().setRelation(Connection.Relation.valueOf(ctx.getText().toUpperCase()));
    }

    @Override
    public void exitElementConnection(@NotNull DiagramParser.ElementConnectionContext ctx) {
        elementStack.peek().addConnection(connectionStack.pop());
    }

    @Override
    public void exitElement(@NotNull DiagramParser.ElementContext ctx) {
        diagramConfiguration.addElement(elementStack.pop());
    }
}
