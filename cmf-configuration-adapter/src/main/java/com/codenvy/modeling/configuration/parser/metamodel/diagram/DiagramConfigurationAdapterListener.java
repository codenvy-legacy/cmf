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
import com.google.inject.Inject;

import org.antlr.v4.runtime.misc.NotNull;

import javax.annotation.Nonnull;
import java.util.Stack;

import static com.codenvy.modeling.configuration.metamodel.diagram.Element.Relation;
import static com.codenvy.modeling.configuration.metamodel.diagram.Property.Type;

/**
 * @author Dmitry Kuleshov
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class DiagramConfigurationAdapterListener extends DiagramBaseListener {

    private Element rootElement;

    private final Stack<Element>       elementStack;
    private final Stack<Property>      propertyStack;
    private final Stack<Component>     componentStack;
    private final Stack<Connection>    connectionStack;
    private final Stack<Pair>          pairStack;
    private final DiagramConfiguration diagramConfiguration;

    @Inject
    public DiagramConfigurationAdapterListener(DiagramConfiguration diagramConfiguration) {
        this.diagramConfiguration = diagramConfiguration;

        elementStack = new Stack<>();
        propertyStack = new Stack<>();
        componentStack = new Stack<>();
        connectionStack = new Stack<>();
        pairStack = new Stack<>();
    }

    @Nonnull
    public DiagramConfiguration getDiagramConfiguration() {
        return diagramConfiguration;
    }

    @Override
    public void enterRootElement(@NotNull DiagramParser.RootElementContext ctx) {
        rootElement = new Element();
    }

    @Override
    public void exitRootElement(@NotNull DiagramParser.RootElementContext ctx) {
        diagramConfiguration.setRootElement(rootElement);
        rootElement = null;
    }

    @Override
    public void enterElement(@NotNull DiagramParser.ElementContext ctx) {
        elementStack.push(new Element());
    }

    @Override
    public void exitElementName(@NotNull DiagramParser.ElementNameContext ctx) {
        Element element = rootElement == null ? elementStack.peek() : rootElement;
        element.setName(ctx.TEXT().getText());
    }

    @Override
    public void exitElementRelation(@NotNull DiagramParser.ElementRelationContext ctx) {
        Element element = rootElement == null ? elementStack.peek() : rootElement;
        element.setRelation(Relation.valueOf(ctx.RELATION().getText().toUpperCase()));
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
        propertyStack.peek().setType(Type.valueOf(ctx.PROPERTY_TYPE().getText()));
    }

    @Override
    public void exitPropertyValue(@NotNull DiagramParser.PropertyValueContext ctx) {
        propertyStack.peek().setValue(ctx.TEXT().getText());
    }

    @Override
    public void exitElementProperty(@NotNull DiagramParser.ElementPropertyContext ctx) {
        Element element = rootElement == null ? elementStack.peek() : rootElement;
        element.addProperty(propertyStack.pop());
    }

    @Override
    public void enterElementComponent(@NotNull DiagramParser.ElementComponentContext ctx) {
        componentStack.push(new Component());
    }

    @Override
    public void exitElementComponent(@NotNull DiagramParser.ElementComponentContext ctx) {
        componentStack.peek().setName(ctx.TEXT().getText());

        Element element = rootElement == null ? elementStack.peek() : rootElement;
        element.addComponent(componentStack.pop());
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
        connectionStack.peek().setType(Connection.Type.valueOf(ctx.CONNECTION_TYPE().getText()));
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