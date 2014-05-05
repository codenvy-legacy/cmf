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

import com.codenvy.modeling.configuration.metamodel.diagram.Component;
import com.codenvy.modeling.configuration.metamodel.diagram.Connection;
import com.codenvy.modeling.configuration.metamodel.diagram.DiagramConfiguration;
import com.codenvy.modeling.configuration.metamodel.diagram.Element;
import com.codenvy.modeling.configuration.metamodel.diagram.Pair;
import com.codenvy.modeling.configuration.metamodel.diagram.Property;

import org.antlr.v4.runtime.misc.NotNull;

import javax.annotation.Nonnull;
import java.util.Stack;

/**
 * @author Dmitry Kuleshov
 */
public class DiagramConfigurationAdapterListener extends DiagramBaseListener {

    private Stack<Element> elementStack = new Stack<>();

    private Stack<Property> propertyStack = new Stack<>();

    private Stack<Component> componentStack = new Stack<>();

    private Stack<Connection> connectionStack = new Stack<>();

    private Stack<Pair> pairStack = new Stack<>();

    private DiagramConfiguration diagramConfiguration = new DiagramConfiguration();

    @Nonnull
    public DiagramConfiguration getDiagramConfiguration() {
        return diagramConfiguration;
    }

    @Override
    public void enterElement(@NotNull DiagramParser.ElementContext ctx) {
        elementStack.push(new Element());
    }

    @Override
    public void exitElementName(@NotNull DiagramParser.ElementNameContext ctx) {
        elementStack.peek().setName(ctx.TEXT().getText());
    }

    @Override
    public void enterElementProperty(@NotNull DiagramParser.ElementPropertyContext ctx) {
        propertyStack.push(new Property());
    }

    @Override
    public void exitPropertyName(@NotNull DiagramParser.PropertyNameContext ctx) {
        propertyStack.peek().setName(ctx.TEXT().getText());
    }

    @Override
    public void exitPropertyType(@NotNull DiagramParser.PropertyTypeContext ctx) {
        propertyStack.peek().setType(Property.Type.valueOf(ctx.getText().toUpperCase()));
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
        componentStack.push(new Component());
    }

    @Override
    public void exitElementComponent(@NotNull DiagramParser.ElementComponentContext ctx) {
        componentStack.peek().setName(ctx.TEXT().getText());
        elementStack.peek().addComponent(componentStack.pop());
    }

    @Override
    public void exitElementRelation(@NotNull DiagramParser.ElementRelationContext ctx) {
        elementStack.peek().setRelation(Element.Relation.valueOf(ctx.RELATION().getText().toUpperCase()));
    }

    @Override
    public void exitElement(@NotNull DiagramParser.ElementContext ctx) {
        diagramConfiguration.addElement(elementStack.pop());
    }

    @Override
    public void enterConnection(@NotNull DiagramParser.ConnectionContext ctx) {
        connectionStack.push(new Connection());
    }

    @Override
    public void exitConnectionName(@NotNull DiagramParser.ConnectionNameContext ctx) {
        connectionStack.peek().setName(ctx.TEXT().getText());
    }

    @Override
    public void exitConnectionType(@NotNull DiagramParser.ConnectionTypeContext ctx) {
        connectionStack.peek().setType(Connection.Type.valueOf(ctx.getText().toUpperCase()));
    }

    @Override
    public void enterConnectionPair(@NotNull DiagramParser.ConnectionPairContext ctx) {
        pairStack.push(new Pair());
    }

    @Override
    public void exitConnectionStart(@NotNull DiagramParser.ConnectionStartContext ctx) {
        pairStack.peek().setStart(ctx.TEXT().getText());
    }

    @Override
    public void exitConnectionFinish(@NotNull DiagramParser.ConnectionFinishContext ctx) {
        pairStack.peek().setFinish(ctx.TEXT().getText());
    }

    @Override
    public void exitConnectionPair(@NotNull DiagramParser.ConnectionPairContext ctx) {
        connectionStack.peek().addPair(pairStack.pop());
    }

    @Override
    public void exitConnection(@NotNull DiagramParser.ConnectionContext ctx) {
        diagramConfiguration.addConnection(connectionStack.pop());
    }
}
