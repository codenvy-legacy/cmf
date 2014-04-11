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

package com.codenvy.modeling.configuration.parser.metamodel;

import com.codenvy.modeling.configuration.impl.metamodel.*;
import com.codenvy.modeling.configuration.metamodel.ElementProperty;
import com.codenvy.modeling.configuration.metamodel.ElementRelation;
import com.codenvy.modeling.configuration.metamodel.MetaModel;
import com.codenvy.modeling.configuration.parser.metamodel.generated.MetaModelBaseListener;
import com.codenvy.modeling.configuration.parser.metamodel.generated.MetaModelParser;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Stack;

/**
 * @author Dmitry Kuleshov
 */
public class MetaModelConfigurationAdapterListener extends MetaModelBaseListener {

    private Stack<StaticConstraintImpl> staticConstraints = new Stack<>();

    private Stack<ElementStructureImpl> elementStructures = new Stack<>();

    private Stack<ElementRelationImpl> elementRelations = new Stack<>();

    private Stack<ElementPropertyImpl> elementProperties = new Stack<>();

    private MetaModelImpl metaModel = new MetaModelImpl();

    public MetaModel getMetaModel() {
        return metaModel;
    }

    @Override
    public void enterStaticConstraint(@NotNull MetaModelParser.StaticConstraintContext ctx) {
        staticConstraints.push(new StaticConstraintImpl());
    }

    @Override
    public void exitStaticConstraint(@NotNull MetaModelParser.StaticConstraintContext ctx) {
        metaModel.add(staticConstraints.pop());
    }

    @Override
    public void enterElementStructure(@NotNull MetaModelParser.ElementStructureContext ctx) {
        elementStructures.push(new ElementStructureImpl());
    }

    @Override
    public void exitElementStructure(@NotNull MetaModelParser.ElementStructureContext ctx) {
        staticConstraints.peek().add(elementStructures.pop());
    }

    @Override
    public void enterProperty(@NotNull MetaModelParser.PropertyContext ctx) {
        elementProperties.push(new ElementPropertyImpl());
    }

    @Override
    public void exitProperty(@NotNull MetaModelParser.PropertyContext ctx) {
        elementStructures.peek().add(elementProperties.pop());
    }

    @Override
    public void enterElementRelation(@NotNull MetaModelParser.ElementRelationContext ctx) {
        elementRelations.push(new ElementRelationImpl());
    }

    @Override
    public void exitElementRelation(@NotNull MetaModelParser.ElementRelationContext ctx) {
        staticConstraints.peek().add(elementRelations.pop());
    }

    @Override
    public void exitElementType(@NotNull MetaModelParser.ElementTypeContext ctx) {
        elementStructures.peek().setType(ctx.TEXT().getText());
    }

    @Override
    public void exitInnerElement(@NotNull MetaModelParser.InnerElementContext ctx) {
        elementStructures.peek().add(ctx.TEXT().getText());
    }

    @Override
    public void exitAlignment(@NotNull MetaModelParser.AlignmentContext ctx) {
        elementRelations.peek().setAlignment(ElementRelation.Alignment.valueOf(ctx.ALIGNMENT().getText().toUpperCase()));
    }

    @Override
    public void exitDirection(@NotNull MetaModelParser.DirectionContext ctx) {
        elementRelations.peek().setDirection(ElementRelation.Direction.valueOf(ctx.DIRECTION().getText().toUpperCase()));
    }

    @Override
    public void exitRelation(@NotNull MetaModelParser.RelationContext ctx) {
        elementRelations.peek().setFirst(ctx.TEXT(0).getText());
        elementRelations.peek().setLast(ctx.TEXT(1).getText());
    }

    @Override
    public void exitPropertyName(@NotNull MetaModelParser.PropertyNameContext ctx) {
        elementProperties.peek().setName(ctx.TEXT().getText());
    }

    @Override
    public void exitPropertyType(@NotNull MetaModelParser.PropertyTypeContext ctx) {
        elementProperties.peek().setType(ElementProperty.Type.valueOf(ctx.PROPERTY_TYPE().getText().toUpperCase()));
    }

    @Override
    public void exitDefaultValue(@NotNull MetaModelParser.DefaultValueContext ctx) {
        elementProperties.peek().setDefaultValue(ctx.TEXT().getText());
    }

    @Override
    public void exitVisibility(@NotNull MetaModelParser.VisibilityContext ctx) {
        elementProperties.peek().setVisibility(ElementProperty.Visibility.valueOf(ctx.VISIBILITY().getText().toUpperCase()));
    }
}
