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

package com.codenvy.modeling.generator;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * The builder of source code. It provides an ability to generate code. One can create java classes with different methods and different
 * logic. The result is the text format of source code.
 *
 * @author Andrey Plotnikov
 */
public interface SourceCodeBuilder {

    /** The list of available access level for java code */
    enum Access {
        PRIVATE("private "), PROTECTED("protected "), PUBLIC("public "), DEFAULT("");

        private String prefix;

        Access(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return prefix;
        }

    }

    /**
     * Create a new java class with a given name. This java class will have a public access level and it isn't abstract.
     *
     * @param name
     *         the name of java class. Also the name can contain a package name. If the name includes a package name the package name will
     *         add to code automatically.
     * @return an instance of source code builder with a given configuration
     */
    @Nonnull
    SourceCodeBuilder newClass(@Nonnull String name);

    /**
     * Make a generating class as interface. It means that will be generated a java interface.
     *
     * @return an instance of source code builder with a given configuration
     * @throws IllegalStateException
     *         exception happens in case a new java resource had been not initialized before this method was executed
     */
    @Nonnull
    SourceCodeBuilder makeInterface() throws IllegalStateException;

    /**
     * Make a generating class as enum. It means that will be generated a java enum.
     *
     * @return an instance of source code builder with a given configuration
     * @throws IllegalStateException
     *         exception happens in case a new java resource had been not initialized before this method was executed
     */
    @Nonnull
    SourceCodeBuilder makeEnum() throws IllegalStateException;

    /**
     * Make a generating class as abstract. It means that will be generated an abstract java class.
     *
     * @return an instance of source code builder with a given configuration
     * @throws IllegalStateException
     *         exception happens in case a new java resource had been not initialized before this method was executed
     */
    @Nonnull
    SourceCodeBuilder withAbstractClassPrefix() throws IllegalStateException;

    /**
     * Add an access level for a generating class.
     *
     * @param accessLevel
     *         access level that must be applied
     * @return an instance of source code builder with a given configuration
     * @throws IllegalStateException
     *         exception happens in case a new java resource had been not initialized before this method was executed
     */
    @Nonnull
    SourceCodeBuilder withClassAccessLevel(@Nonnull Access accessLevel) throws IllegalStateException;

    /**
     * Add an annotation to a generating class.
     *
     * @param annotation
     *         annotation that need to be added
     * @return an instance of source code builder with a given configuration
     * @throws IllegalStateException
     *         exception happens in case a new java resource had been not initialized before this method was executed
     */
    @Nonnull
    SourceCodeBuilder withClassAnnotation(@Nonnull String annotation) throws IllegalStateException;

    /**
     * Add an interface which a generating java class must implement.
     *
     * @param implementedInterface
     *         the interface that a generating java class must implement
     * @return an instance of source code builder with a given configuration
     * @throws IllegalStateException
     *         exception happens in case a new java resource had been not initialized before this method was executed
     */
    @Nonnull
    SourceCodeBuilder implementInterface(@Nonnull Class implementedInterface) throws IllegalStateException;

    /**
     * Add a base class which a generating java class must extend.
     *
     * @param baseClass
     *         the class that the generating java class must extend
     * @return an instance of source code builder with a given configuration
     * @throws IllegalStateException
     *         exception happens in case a new java resource had been not initialized before this method was executed
     */
    @Nonnull
    SourceCodeBuilder baseClass(@Nonnull Class baseClass) throws IllegalStateException;

    /**
     * Add a import of a given class to a generating class.
     *
     * @param importedClass
     *         class that need be imported
     * @return an instance of source code builder with a given configuration
     * @throws IllegalStateException
     *         exception happens in case a new java resource had been not initialized before this method was executed
     */
    @Nonnull
    SourceCodeBuilder addImport(@Nonnull Class importedClass) throws IllegalStateException;

    /**
     * Add a field to a generating java class with given parameters: name, type. This field will have a private access level.
     *
     * @param name
     *         field name
     * @param type
     *         field type
     * @return an instance of source code builder with a given configuration
     * @throws IllegalStateException
     *         exception happens in case a new java resource had been not initialized before this method was executed
     */
    @Nonnull
    SourceCodeBuilder addField(@Nonnull String name, @Nonnull String type) throws IllegalStateException;

    /**
     * Add a field to a generating java class with given parameters: name, type. This field will have a private access level.
     *
     * @param name
     *         field name
     * @param type
     *         field type
     * @return an instance of source code builder with a given configuration
     * @throws IllegalStateException
     *         exception happens in case a new java resource had been not initialized before this method was executed
     */
    @Nonnull
    SourceCodeBuilder addField(@Nonnull String name, @Nonnull Class type) throws IllegalStateException;

    /**
     * Add an access level for the generating field.
     *
     * @param accessLevel
     *         access level that must be applied
     * @return an instance of source code builder with a given configuration
     * @throws IllegalStateException
     *         exception happens in case a new class field had been not initialized before this method was executed
     *         also in case a new java resource had been not initialized before this method was executed
     */
    @Nonnull
    SourceCodeBuilder withFieldAccessLevel(@Nonnull Access accessLevel) throws IllegalStateException;

    /**
     * Make a generating field as static. It means that will be generated a static field in java class.
     *
     * @return an instance of source code builder with a given configuration
     * @throws IllegalStateException
     *         exception happens in case a new class field had been not initialized before this method was executed
     *         also in case a new java resource had been not initialized before this method was executed
     */
    @Nonnull
    SourceCodeBuilder withStaticFieldPrefix() throws IllegalStateException;

    /**
     * Make a generating field as final. It means that will be generated a final field in java class.
     *
     * @return an instance of source code builder with a given configuration
     * @throws IllegalStateException
     *         exception happens in case a new class field had been not initialized before this method was executed
     *         also in case a new java resource had been not initialized before this method was executed
     */
    @Nonnull
    SourceCodeBuilder withFinalFieldPrefix() throws IllegalStateException;

    /**
     * Add an annotation to a generating field.
     *
     * @param annotation
     *         annotation that need to be added
     * @return an instance of source code builder with a given configuration
     * @throws IllegalStateException
     *         exception happens in case a new class field had been not initialized before this method was executed
     *         also in case a new java resource had been not initialized before this method was executed
     */
    @Nonnull
    SourceCodeBuilder withFieldAnnotation(@Nonnull String annotation) throws IllegalStateException;

    /**
     * Add a method to a generating java class. The method will have the following parameters: return type is void, no input arguments,
     * empty body, public access level.
     *
     * @param name
     *         method name
     * @return an instance of source code builder with a given configuration
     * @throws IllegalStateException
     *         exception happens in case a new java resource had been not initialized before this method was executed
     */
    @Nonnull
    SourceCodeBuilder addMethod(@Nonnull String name) throws IllegalStateException;

    /**
     * Add a return type to a generating method.
     *
     * @param returnType
     *         return type of method
     * @return an instance of source code builder with a given configuration
     * @throws IllegalStateException
     *         exception happens in case a new class method had been not initialized before this method was executed
     *         also in case a new java resource had been not initialized before this method was executed
     */
    @Nonnull
    SourceCodeBuilder withReturnType(@Nonnull String returnType) throws IllegalStateException;

    /**
     * Add a return type to a generating method.
     *
     * @param returnType
     *         return type of method that must be added
     * @return an instance of source code builder with a given configuration
     * @throws IllegalStateException
     *         exception happens in case a new class method had been not initialized before this method was executed
     *         also in case a new java resource had been not initialized before this method was executed
     */
    @Nonnull
    SourceCodeBuilder withReturnType(@Nonnull Class returnType) throws IllegalStateException;

    /**
     * Add inputted arguments to a generating method.
     *
     * @param arguments
     *         inputted arguments which must be added
     * @return an instance of source code builder with a given configuration
     * @throws IllegalStateException
     *         exception happens in case a new class method had been not initialized before this method was executed
     *         also in case a new java resource had been not initialized before this method was executed
     */
    @Nonnull
    SourceCodeBuilder withMethodArguments(@Nonnull Map<String, String> arguments) throws IllegalStateException;

    /**
     * Add a method body to a generating method.
     *
     * @param body
     *         body of method that must be added
     * @return an instance of source code builder with a given configuration
     * @throws IllegalStateException
     *         exception happens in case a new class method had been not initialized before this method was executed
     *         also in case a new java resource had been not initialized before this method was executed
     */
    @Nonnull
    SourceCodeBuilder withMethodBody(@Nonnull String body) throws IllegalStateException;

    /**
     * Add an access level for the generating method.
     *
     * @param accessLevel
     *         access level that must be applied
     * @return an instance of source code builder with a given configuration
     * @throws IllegalStateException
     *         exception happens in case a new class method had been not initialized before this method was executed
     *         also in case a new java resource had been not initialized before this method was executed
     */
    @Nonnull
    SourceCodeBuilder withMethodAccessLevel(@Nonnull Access accessLevel) throws IllegalStateException;

    /**
     * Make a generating method as abstract. It means that will be generated an abstract method in java class.
     *
     * @return an instance of source code builder with a given configuration
     * @throws IllegalStateException
     *         exception happens in case a new class method had been not initialized before this method was executed
     *         also in case a new java resource had been not initialized before this method was executed
     */
    @Nonnull
    SourceCodeBuilder withAbstractMethodPrefix() throws IllegalStateException;

    /**
     * Make a generating method as static. It means that will be generated a static method in java class.
     *
     * @return an instance of source code builder with a given configuration
     * @throws IllegalStateException
     *         exception happens in case a new class method had been not initialized before this method was executed
     *         also in case a new java resource had been not initialized before this method was executed
     */
    @Nonnull
    SourceCodeBuilder withStaticMethodPrefix() throws IllegalStateException;

    /**
     * Add an annotation to a generating method.
     *
     * @param annotation
     *         annotation that need to be added
     * @return an instance of source code builder with a given configuration
     * @throws IllegalStateException
     *         exception happens in case a new class method had been not initialized before this method was executed
     *         also in case a new java resource had been not initialized before this method was executed
     */
    @Nonnull
    SourceCodeBuilder withMethodAnnotation(@Nonnull String annotation) throws IllegalStateException;

    /**
     * Add a constructor to a generating java class. The constructor will have the following parameters: public access level, no input
     * arguments, empty body.
     *
     * @return an instance of source code builder with a given configuration
     * @throws IllegalStateException
     *         exception happens in case a new java resource had been not initialized before this method was executed
     */
    @Nonnull
    SourceCodeBuilder addConstructor(String... typeAndNames) throws IllegalStateException;

    /**
     * Add a constructor body to a generating constructor.
     *
     * @param body
     *         body of constructor that must be added
     * @return an instance of source code builder with a given configuration
     * @throws IllegalStateException
     *         exception happens in case a new class constructor had been not initialized before this method was executed
     *         also in case a new java resource had been not initialized before this method was executed
     */
    @Nonnull
    SourceCodeBuilder withConstructorBody(@Nonnull String body) throws IllegalStateException;

    /**
     * Add an access level for a generating constructor.
     *
     * @param accessLevel
     *         access level that must be applied
     * @return an instance of source code builder with a given configuration
     * @throws IllegalStateException
     *         exception happens in case a new class constructor had been not initialized before this method was executed
     *         also in case a new java resource had been not initialized before this method was executed
     */
    @Nonnull
    SourceCodeBuilder withConstructorAccessLevel(@Nonnull Access accessLevel) throws IllegalStateException;

    /**
     * Add an annotation to a generating constructor.
     *
     * @param annotation
     *         annotation that need to be added
     * @return an instance of source code builder with a given configuration
     * @throws IllegalStateException
     *         exception happens in case a new class constructor had been not initialized before this method was executed
     *         also in case a new java resource had been not initialized before this method was executed
     */
    @Nonnull
    SourceCodeBuilder withConstructorAnnotation(@Nonnull String annotation) throws IllegalStateException;

    /**
     * Build java source code from given configuration.
     *
     * @return the text format of java class
     * @throws IllegalStateException
     *         exception happens in case a new java resource had been not initialized before this method was executed
     */
    @Nonnull
    String build() throws IllegalStateException;

}