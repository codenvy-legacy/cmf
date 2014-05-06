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

package com.codenvy.modeling.generator.builders.java;

import joist.sourcegen.GClass;
import joist.sourcegen.GField;
import joist.sourcegen.GMethod;

import com.google.inject.Inject;

import javax.annotation.Nonnull;

/**
 * The implementation of {@link SourceCodeBuilder}. This implementation use 'Joist' library for generating java code.
 *
 * @author Andrey Plotnikov
 */
public class SourceCodeBuilderImpl implements SourceCodeBuilder {

    private GClass  generatingClass;
    private GField  generatingField;
    private GMethod generatingMethod;
    private GMethod generatingConstructor;

    @Inject
    public SourceCodeBuilderImpl() {
        // empty constructor
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder newClass(@Nonnull String name) {
        generatingClass = new GClass(name);
        return this;
    }

    /**
     * Check status of generating class.
     *
     * @throws IllegalStateException
     *         exception happens if class to be generated isn't initialized
     */
    private void checkClassStatus() throws IllegalStateException {
        if (generatingClass == null) {
            throw new IllegalStateException(
                    "The builder doesn't have any information about creating java class. " +
                    "You should execute newClass method and then this one."
            );
        }
    }

    /**
     * Convert our access level to access level of 'Joist'.
     *
     * @param access
     *         our access level that needs to be converted
     * @return 'Joist' access level
     */
    @Nonnull
    private joist.sourcegen.Access convertAccess(@Nonnull Access access) {
        switch (access) {
            case PRIVATE:
                return joist.sourcegen.Access.PRIVATE;

            case PROTECTED:
                return joist.sourcegen.Access.PROTECTED;

            case DEFAULT:
                return joist.sourcegen.Access.PACKAGE;

            case PUBLIC:
            default:
                return joist.sourcegen.Access.PUBLIC;
        }
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder makeInterface() throws IllegalStateException {
        checkClassStatus();

        generatingClass.setInterface();

        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder makeEnum() throws IllegalStateException {
        checkClassStatus();

        generatingClass.setEnum();

        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder withAbstractClassPrefix() throws IllegalStateException {
        checkClassStatus();

        generatingClass.setAbstract();

        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder withClassAccessLevel(@Nonnull Access accessLevel) throws IllegalStateException {
        checkClassStatus();

        generatingClass.setAccess(convertAccess(accessLevel));

        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder withClassAnnotation(@Nonnull String annotation) throws IllegalStateException {
        checkClassStatus();

        generatingClass.addAnnotation(annotation);

        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder implementInterface(@Nonnull Class implementedInterface) throws IllegalStateException {
        checkClassStatus();

        generatingClass.implementsInterface(implementedInterface);

        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder implementInterface(@Nonnull String implementedInterface) throws IllegalStateException {
        checkClassStatus();

        generatingClass.implementsInterface(implementedInterface);

        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder baseClass(@Nonnull Class baseClass) throws IllegalStateException {
        checkClassStatus();

        generatingClass.baseClass(baseClass);

        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder baseClass(@Nonnull String baseClass) throws IllegalStateException {
        checkClassStatus();

        generatingClass.baseClassName(baseClass);

        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder withEnumValue(String enumValue) throws IllegalStateException {
        checkClassStatus();

        generatingClass.addEnumValue(enumValue);

        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder addImport(@Nonnull Class importedClass) throws IllegalStateException {
        checkClassStatus();

        generatingClass.addImports(importedClass);

        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder addImport(@Nonnull String importedClass) throws IllegalStateException {
        checkClassStatus();

        generatingClass.addImports(importedClass);

        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder addField(@Nonnull String name, @Nonnull String type) throws IllegalStateException {
        checkClassStatus();

        generatingField = generatingClass.getField(name).type(type);

        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder addField(@Nonnull String name, @Nonnull Class type) throws IllegalStateException {
        checkClassStatus();

        generatingField = generatingClass.getField(name).type(type);

        return this;
    }

    /**
     * Check status of generating field.
     *
     * @throws IllegalStateException
     *         exception happens if field to be generated isn't initialized
     */
    private void checkFieldStatus() throws IllegalStateException {
        if (generatingField == null) {
            throw new IllegalStateException(
                    "The builder doesn't have any information about creating class field. " +
                    "You should execute addField method and then this one."
            );
        }
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder withFieldAccessLevel(@Nonnull Access accessLevel) throws IllegalStateException {
        checkClassStatus();
        checkFieldStatus();

        generatingField.setAccess(convertAccess(accessLevel));

        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder withStaticFieldPrefix() throws IllegalStateException {
        checkClassStatus();
        checkFieldStatus();

        generatingField.setStatic();

        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder withFinalFieldPrefix() throws IllegalStateException {
        checkClassStatus();
        checkFieldStatus();

        generatingField.setFinal();

        return this;
    }

    @Nonnull
    @Override
    public SourceCodeBuilder withFieldAnnotation(@Nonnull String annotation) throws IllegalStateException {
        checkClassStatus();
        checkFieldStatus();

        generatingField.addAnnotation(annotation);

        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder addMethod(@Nonnull String name) throws IllegalStateException {
        checkClassStatus();

        generatingMethod = generatingClass.getMethod(name);

        return this;
    }

    /**
     * Check status of generating method.
     *
     * @throws IllegalStateException
     *         exception happens if method to be generated isn't initialized
     */
    private void checkMethodStatus() throws IllegalStateException {
        if (generatingMethod == null) {
            throw new IllegalStateException(
                    "The builder doesn't have any information about creating class method. " +
                    "You should execute addMethod method and then this one."
            );
        }
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder withReturnType(@Nonnull String returnType) throws IllegalStateException {
        checkClassStatus();
        checkMethodStatus();

        generatingMethod.returnType(returnType);

        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder withReturnType(@Nonnull Class returnType) throws IllegalStateException {
        checkClassStatus();
        checkMethodStatus();

        generatingMethod.returnType(returnType);

        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder withMethodArguments(@Nonnull Argument... arguments) throws IllegalStateException {
        checkClassStatus();
        checkMethodStatus();

        for (Argument argument : arguments) {
            generatingMethod.argument(argument.getType(), argument.getName());
        }

        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder withMethodBody(@Nonnull String body) throws IllegalStateException {
        checkClassStatus();
        checkMethodStatus();

        generatingMethod.setBody(body);

        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder withMethodAccessLevel(@Nonnull Access accessLevel) throws IllegalStateException {
        checkClassStatus();
        checkMethodStatus();

        generatingMethod.setAccess(convertAccess(accessLevel));

        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder withAbstractMethodPrefix() throws IllegalStateException {
        checkClassStatus();
        checkMethodStatus();

        generatingMethod.setAbstract();

        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder withStaticMethodPrefix() throws IllegalStateException {
        checkClassStatus();
        checkMethodStatus();

        generatingMethod.setStatic();

        return this;
    }

    @Nonnull
    @Override
    public SourceCodeBuilder withMethodAnnotation(@Nonnull String annotation) throws IllegalStateException {
        checkClassStatus();
        checkMethodStatus();

        generatingMethod.addAnnotation(annotation);

        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder addConstructor(@Nonnull Argument... arguments) throws IllegalStateException {
        checkClassStatus();

        String[] typesAndNames = new String[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            typesAndNames[i] = arguments[i].toString();
        }

        generatingConstructor = generatingClass.getConstructor(typesAndNames);

        return this;
    }

    /**
     * Check status of generating constructor.
     *
     * @throws IllegalStateException
     *         exception happens if constructor to be generated isn't initialized
     */
    private void checkConstructorStatus() throws IllegalStateException {
        if (generatingConstructor == null) {
            throw new IllegalStateException(
                    "The builder doesn't have any information about creating class constructor. " +
                    "You should execute addConstructor method and then this one."
            );
        }
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder withConstructorBody(@Nonnull String body) throws IllegalStateException {
        checkClassStatus();
        checkConstructorStatus();

        generatingConstructor.setBody(body);

        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder withConstructorAccessLevel(@Nonnull Access accessLevel) throws IllegalStateException {
        checkClassStatus();
        checkConstructorStatus();

        generatingConstructor.setAccess(convertAccess(accessLevel));

        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public SourceCodeBuilder withConstructorAnnotation(@Nonnull String annotation) throws IllegalStateException {
        checkClassStatus();
        checkConstructorStatus();

        generatingConstructor.addAnnotation(annotation);

        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String build() throws IllegalStateException {
        checkClassStatus();

        String code = generatingClass.toCode();

        // Clean builder configuration
        generatingClass = null;
        generatingField = null;
        generatingMethod = null;
        generatingConstructor = null;

        return code;
    }

}