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

import com.google.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.codenvy.modeling.generator.SourceCodeBuilder.Access;
import static com.codenvy.modeling.generator.SourceCodeBuilder.Access.DEFAULT;
import static com.codenvy.modeling.generator.SourceCodeBuilder.Access.PRIVATE;
import static com.codenvy.modeling.generator.SourceCodeBuilder.Access.PROTECTED;
import static com.codenvy.modeling.generator.SourceCodeBuilder.Access.PUBLIC;
import static org.junit.Assert.assertEquals;

/**
 * Here we're testing {@link SourceCodeBuilderImpl}.
 *
 * @author Andrey Plotnikov
 */
public class SourceCodeBuilderImplTest {

    private SourceCodeBuilderImpl builder;

    @Before
    public void setUp() throws Exception {
        builder = new SourceCodeBuilderImpl();
    }

    @Test
    public void simpleJavaClassShouldBeCreated() throws Exception {
        String actualCode = builder.newClass("A").build();

        String expectedCode = "public class A {\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test
    public void simpleJavaClassWithPackageNameShouldBeCreated() throws Exception {
        String actualCode = builder.newClass("com.codenvy.generator.A").build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "public class A {\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasNotInitializedAndOneTriesToBuildCode() throws Exception {
        builder.build();
    }

    @Test
    public void simpleJavaInterfaceShouldBeCreated() throws Exception {
        String actualCode = builder.newClass("com.codenvy.generator.A").makeInterface().build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "public interface A {\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasNotInitializedAndOneTriesToMakeInterface() throws Exception {
        builder.makeInterface();
    }

    @Test
    public void simpleJavaEnumShouldBeCreated() throws Exception {
        String actualCode = builder.newClass("com.codenvy.generator.A").makeEnum().build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "public enum A {\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasNotInitializedAndOneTriesToMakeEnum() throws Exception {
        builder.makeEnum();
    }

    @Test
    public void simpleAbstractJavaClassShouldBeCreated() throws Exception {
        String actualCode = builder.newClass("com.codenvy.generator.A").withAbstractClassPrefix().build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "public abstract class A {\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasNotInitializedAndOneTriesToMakeAbstractClass() throws Exception {
        builder.withAbstractClassPrefix();
    }

    @Test
    public void simpleJavaClassWithAnnotationShouldBeCreated() throws Exception {
        String actualCode = builder.newClass("com.codenvy.generator.A").withClassAnnotation("@SuppressWarnings").build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "@SuppressWarnings\n" +
                              "public class A {\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasNotInitializedAndOneTriesToAddAnnotation() throws Exception {
        builder.withClassAnnotation("@SuppressWarnings");
    }

    @Test
    public void simpleJavaClassWithAccessLevelShouldBeCreated() throws Exception {
        for (Access level : Access.values()) {
            String actualCode = builder.newClass("com.codenvy.generator.A").withClassAccessLevel(level).build();

            String expectedCode = "package com.codenvy.generator;\n" +
                                  "\n" +
                                  level.getPrefix() + "class A {\n" +
                                  "\n" +
                                  "}\n";

            assertEquals(expectedCode, actualCode);
        }
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasNotInitializedAndOneTriesToAddAccessLevelToClass() throws Exception {
        builder.withClassAccessLevel(DEFAULT);
    }

    @Test
    public void simpleJavaClassWithImplementedInterfaceShouldBeCreated() throws Exception {
        String actualCode = builder.newClass("com.codenvy.generator.A").implementInterface(SourceCodeBuilder.class).build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "import com.codenvy.modeling.generator.SourceCodeBuilder;\n" +
                              "\n" +
                              "public class A implements SourceCodeBuilder {\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasNotInitializedAndOneTriesToImplementInterface() throws Exception {
        builder.implementInterface(SourceCodeBuilder.class);
    }

    @Test
    public void simpleJavaClassWithExtendedClassShouldBeCreated() throws Exception {
        String actualCode = builder.newClass("com.codenvy.generator.A").baseClass(SourceCodeBuilderImpl.class).build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "import com.codenvy.modeling.generator.SourceCodeBuilderImpl;\n" +
                              "\n" +
                              "public class A extends SourceCodeBuilderImpl {\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasNotInitializedAndOneTriesToAddBaseClass() throws Exception {
        builder.baseClass(SourceCodeBuilderImpl.class);
    }

    @Test
    public void simpleJavaClassWithImportedClassShouldBeCreated() throws Exception {
        String actualCode = builder.newClass("com.codenvy.generator.A").addImport(SourceCodeBuilderImpl.class).build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "import com.codenvy.modeling.generator.SourceCodeBuilderImpl;\n" +
                              "\n" +
                              "public class A {\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasNotInitializedAndOneTriesToAddImportedClass() throws Exception {
        builder.addImport(String.class);
    }

    @Test
    public void simpleJavaClassWithSimpleFieldShouldBeCreated() throws Exception {
        String actualCode = builder.newClass("com.codenvy.generator.A").addField("field1", Integer.class).build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "public class A {\n" +
                              "\n" +
                              "    private Integer field1;\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasNotInitializedAndOneTriesToAddField() throws Exception {
        builder.addField("field1", Integer.class);
    }

    @Test
    public void simpleJavaClassWithSimpleFieldShouldBeCreated2() throws Exception {
        String actualCode = builder.newClass("com.codenvy.generator.A").addField("field1", "int").build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "public class A {\n" +
                              "\n" +
                              "    private int field1;\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasNotInitializedAndOneTriesToAddField2() throws Exception {
        builder.addField("field1", "int");
    }

    @Test
    public void simpleJavaClassWithSimpleFieldWithAccessLevelShouldBeCreated() throws Exception {
        for (Access level : Access.values()) {
            String actualCode = builder.newClass("com.codenvy.generator.A")
                                       .addField("field1", Integer.class).withFieldAccessLevel(level)
                                       .build();

            String expectedCode = "package com.codenvy.generator;\n" +
                                  "\n" +
                                  "public class A {\n" +
                                  "\n" +
                                  "    " + level.getPrefix() + "Integer field1;\n" +
                                  "\n" +
                                  "}\n";

            assertEquals(expectedCode, actualCode);
        }
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasNotInitializedAndOneTriesToAddFieldAccessLevel() throws Exception {
        builder.withFieldAccessLevel(DEFAULT);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasInitializedButFieldWasNotInitializedAndOneTriesToAddFieldAccessLevel() throws Exception {
        builder.newClass("com.codenvy.generator.A").withFieldAccessLevel(DEFAULT);
    }

    @Test
    public void simpleJavaClassWithSimpleFieldWithFinalPrefixShouldBeCreated() throws Exception {
        String actualCode = builder.newClass("com.codenvy.generator.A").addField("field1", Integer.class).withFinalFieldPrefix().build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "public class A {\n" +
                              "\n" +
                              "    private final Integer field1;\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasNotInitializedAndOneTriesToMakeFieldFinal() throws Exception {
        builder.withFinalFieldPrefix();
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasInitializedButFieldWasNotInitializedAndOneTriesToMakeFieldFinal() throws Exception {
        builder.newClass("com.codenvy.generator.A").withFinalFieldPrefix();
    }

    @Test
    public void simpleJavaClassWithSimpleFieldWithStaticPrefixShouldBeCreated() throws Exception {
        String actualCode = builder.newClass("com.codenvy.generator.A").addField("field1", Integer.class).withStaticFieldPrefix().build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "public class A {\n" +
                              "\n" +
                              "    private static Integer field1;\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasNotInitializedAndOneTriesToMakeFieldStatic() throws Exception {
        builder.withStaticFieldPrefix();
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasInitializedButFieldWasNotInitializedAndOneTriesToMakeFieldStatic() throws Exception {
        builder.newClass("com.codenvy.generator.A").withStaticFieldPrefix();
    }

    @Test
    public void simpleJavaClassWithSimpleFieldWithAnnotationShouldBeCreated() throws Exception {
        String actualCode = builder.newClass("com.codenvy.generator.A")
                                   .addField("field1", Integer.class).withFieldAnnotation("@Inject")
                                   .build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "public class A {\n" +
                              "\n" +
                              "    @Inject\n" +
                              "    private Integer field1;\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasNotInitializedAndOneTriesToAddFieldAnnotation() throws Exception {
        builder.withFieldAnnotation("@Inject");
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasInitializedButFieldWasNotInitializedAndOneTriesToAddFieldAnnotation() throws Exception {
        builder.newClass("com.codenvy.generator.A").withFieldAnnotation("@Inject");
    }

    @Test
    public void simpleJavaClassWithSimpleMethodShouldBeCreated() throws Exception {
        String actualCode = builder.newClass("com.codenvy.generator.A").addMethod("getA").build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "public class A {\n" +
                              "\n" +
                              "    public void getA() {\n" +
                              "    }\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasNotInitializedAndOneTriesToAddMethod() throws Exception {
        builder.addMethod("getA");
    }

    @Test
    public void simpleJavaClassWithSimpleMethodWithReturnTypeShouldBeCreated() throws Exception {
        String actualCode = builder.newClass("com.codenvy.generator.A").addMethod("getA").withReturnType(Integer.class).build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "public class A {\n" +
                              "\n" +
                              "    public Integer getA() {\n" +
                              "    }\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasNotInitializedAndOneTriesToAddReturnType() throws Exception {
        builder.withReturnType(Integer.class);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasInitializedButMethodWasNotInitializedAndOneTriesToAddReturnType() throws Exception {
        builder.newClass("com.codenvy.generator.A").withReturnType(Integer.class);
    }

    @Test
    public void simpleJavaClassWithSimpleMethodWithReturnTypeShouldBeCreated2() throws Exception {
        String actualCode = builder.newClass("com.codenvy.generator.A").addMethod("getA").withReturnType("int").build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "public class A {\n" +
                              "\n" +
                              "    public int getA() {\n" +
                              "    }\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasNotInitializedAndOneTriesToAddReturnType2() throws Exception {
        builder.withReturnType("int");
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasInitializedButMethodWasNotInitializedAndOneTriesToAddReturnType2() throws Exception {
        builder.newClass("com.codenvy.generator.A").withReturnType("int");
    }

    @Test
    public void simpleJavaClassWithSimpleMethodWithMethodBodyShouldBeCreated() throws Exception {
        String actualCode = builder.newClass("com.codenvy.generator.A").addMethod("getA").withMethodBody("doSomething();").build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "public class A {\n" +
                              "\n" +
                              "    public void getA() {\n" +
                              "        doSomething();\n" +
                              "    }\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasNotInitializedAndOneTriesToAddMethodBody() throws Exception {
        builder.withMethodBody("doSomething();");
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasInitializedButMethodWasNotInitializedAndOneTriesToAddMethodBody() throws Exception {
        builder.newClass("com.codenvy.generator.A").withMethodBody("doSomething();");
    }

    @Test
    public void simpleJavaClassWithSimpleMethodWithAccessLevelShouldBeCreated() throws Exception {
        for (Access level : Access.values()) {
            String actualCode = builder.newClass("com.codenvy.generator.A").addMethod("getA").withMethodAccessLevel(level).build();

            String expectedCode = "package com.codenvy.generator;\n" +
                                  "\n" +
                                  "public class A {\n" +
                                  "\n" +
                                  "    " + level.getPrefix() + "void getA() {\n" +
                                  "    }\n" +
                                  "\n" +
                                  "}\n";

            assertEquals(expectedCode, actualCode);
        }
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasNotInitializedAndOneTriesToAddAccessLevel() throws Exception {
        builder.withMethodAccessLevel(DEFAULT);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasInitializedButMethodWasNotInitializedAndOneTriesToAddAccessLevel() throws Exception {
        builder.newClass("com.codenvy.generator.A").withMethodAccessLevel(DEFAULT);
    }

    @Test
    public void simpleJavaClassWithSimpleMethodWithStaticPrefixShouldBeCreated() throws Exception {
        String actualCode = builder.newClass("com.codenvy.generator.A").addMethod("getA").withStaticMethodPrefix().build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "public class A {\n" +
                              "\n" +
                              "    public static void getA() {\n" +
                              "    }\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasNotInitializedAndOneTriesToMakeMethodStatic() throws Exception {
        builder.withStaticMethodPrefix();
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasInitializedButMethodWasNotInitializedAndOneTriesToMakeMethodStatic() throws Exception {
        builder.newClass("com.codenvy.generator.A").withStaticMethodPrefix();
    }

    @Test
    public void simpleJavaClassWithSimpleMethodWithArgumentsShouldBeCreated() throws Exception {
        HashMap<String, String> arguments = new HashMap<>();
        arguments.put("Integer", "arg1");
        arguments.put("String", "arg2");

        String actualCode = builder.newClass("com.codenvy.generator.A").addMethod("getA").withMethodArguments(arguments).build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "public class A {\n" +
                              "\n" +
                              "    public void getA(String arg2, Integer arg1) {\n" +
                              "    }\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasNotInitializedAndOneTriesToAddArguments() throws Exception {
        builder.withMethodArguments(Collections.<String, String>emptyMap());
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasInitializedButMethodWasNotInitializedAndOneTriesToAddArguments() throws Exception {
        builder.newClass("com.codenvy.generator.A").withMethodArguments(Collections.<String, String>emptyMap());
    }

    @Test
    public void simpleJavaClassWithSimpleMethodWithAbstractPrefixShouldBeCreated() throws Exception {
        String actualCode = builder.newClass("com.codenvy.generator.A").addMethod("getA").withAbstractMethodPrefix().build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "public class A {\n" +
                              "\n" +
                              "    public abstract void getA();\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasNotInitializedAndOneTriesToMakeMethodAbstract() throws Exception {
        builder.withAbstractMethodPrefix();
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasInitializedButMethodWasNotInitializedAndOneTriesToMakeMethodAbstract() throws Exception {
        builder.newClass("com.codenvy.generator.A").withAbstractMethodPrefix();
    }

    @Test
    public void simpleJavaClassWithSimpleMethodWithAnnotationShouldBeCreated() throws Exception {
        String actualCode = builder.newClass("com.codenvy.generator.A").addMethod("getA").withMethodAnnotation("@Inject").build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "public class A {\n" +
                              "\n" +
                              "    @Inject\n" +
                              "    public void getA() {\n" +
                              "    }\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasNotInitializedAndOneTriesToAddMethodAnnotation() throws Exception {
        builder.withMethodAnnotation("@Inject");
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasInitializedButMethodWasNotInitializedAndOneTriesToAddMethodAnnotation()
            throws Exception {
        builder.newClass("com.codenvy.generator.A").withMethodAnnotation("@Inject");
    }

    @Test
    public void simpleJavaClassWithSimpleConstructorShouldBeCreated() throws Exception {
        String actualCode = builder.newClass("com.codenvy.generator.A").addConstructor().build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "public class A {\n" +
                              "\n" +
                              "    public A() {\n" +
                              "    }\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasNotInitializedAndOneTriesToAddConstructor() throws Exception {
        builder.addConstructor();
    }

    @Test
    public void simpleJavaClassWithSimpleConstructorWithConstructorBodyShouldBeCreated() throws Exception {
        String actualCode = builder.newClass("com.codenvy.generator.A").addConstructor().withConstructorBody("doSomething();").build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "public class A {\n" +
                              "\n" +
                              "    public A() {\n" +
                              "        doSomething();\n" +
                              "    }\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasNotInitializedAndOneTriesToAddConstructorBody() throws Exception {
        builder.withConstructorBody("doSomething();");
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasInitializedButMethodWasNotInitializedAndOneTriesToAddConstructorBody() throws Exception {
        builder.newClass("com.codenvy.generator.A").withConstructorBody("doSomething();");
    }

    @Test
    public void simpleJavaClassWithSimpleConstructorWithAccessLevelShouldBeCreated() throws Exception {
        for (Access level : Access.values()) {
            String actualCode = builder.newClass("com.codenvy.generator.A").addConstructor().withConstructorAccessLevel(level).build();

            String expectedCode = "package com.codenvy.generator;\n" +
                                  "\n" +
                                  "public class A {\n" +
                                  "\n" +
                                  "    " + level.getPrefix() + "A() {\n" +
                                  "    }\n" +
                                  "\n" +
                                  "}\n";

            assertEquals(expectedCode, actualCode);
        }
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasNotInitializedAndOneTriesToAddConstructorAccessLevel() throws Exception {
        builder.withConstructorAccessLevel(DEFAULT);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasInitializedButFieldWasNotInitializedAndOneTriesToAddConstructorAccessLevel()
            throws Exception {
        builder.newClass("com.codenvy.generator.A").withConstructorAccessLevel(DEFAULT);
    }

    @Test
    public void simpleJavaClassWithSimpleConstructorWithAnnotationShouldBeCreated() throws Exception {
        String actualCode = builder.newClass("com.codenvy.generator.A").addConstructor().withConstructorAnnotation("@Inject").build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "public class A {\n" +
                              "\n" +
                              "    @Inject\n" +
                              "    public A() {\n" +
                              "    }\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasNotInitializedAndOneTriesToAddConstructorAnnotation() throws Exception {
        builder.withConstructorAnnotation("@Inject");
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenClassWasInitializedButMethodWasNotInitializedAndOneTriesToAddConstructorAnnotation()
            throws Exception {
        builder.newClass("com.codenvy.generator.A").withConstructorAnnotation("@Inject");
    }

    @Test
    public void complexJavaClassShouldBeCreated() throws Exception {
        Map<String, String> arguments = new HashMap<>();
        arguments.put("field1", "String");

        String actualCode =
                builder.newClass("com.codenvy.generator.A").baseClass(SourceCodeBuilderImpl.class)
                       .withClassAnnotation("@SuppressWarnings")

                       .addImport(Inject.class)

                       .addField("field1", "String").withFinalFieldPrefix().withStaticFieldPrefix().withFieldAccessLevel(PUBLIC)
                       .addField("field2", String.class).withFieldAccessLevel(PROTECTED)
                       .addField("field3", String.class).withFieldAccessLevel(DEFAULT)
                       .addField("field4", Integer.class).withFieldAccessLevel(PRIVATE).withFieldAnnotation("@Inject")

                       .addConstructor().withConstructorAnnotation("@Inject")
                       .addConstructor("String field1").withConstructorAccessLevel(PRIVATE)

                       .addMethod("getField1").withReturnType(String.class).withMethodBody("return field1;").withStaticMethodPrefix()
                       .addMethod("setField2").withMethodArguments(arguments)
                       .withMethodBody("this.field2 = field2;")
                       .addMethod("getField4").withMethodAccessLevel(PRIVATE).withReturnType("int")
                       .withMethodBody("return field4;").withMethodAnnotation("@Inject")

                       .build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "import com.codenvy.modeling.generator.SourceCodeBuilderImpl;\n" +
                              "import com.google.inject.Inject;\n" +
                              "\n" +
                              "@SuppressWarnings\n" +
                              "public class A extends SourceCodeBuilderImpl {\n" +
                              "\n" +
                              "    public static final String field1;\n" +
                              "    protected String field2;\n" +
                              "    String field3;\n" +
                              "    @Inject\n" +
                              "    private Integer field4;\n" +
                              "\n" +
                              "    @Inject\n" +
                              "    public A() {\n" +
                              "    }\n" +
                              "\n" +
                              "    private A(String field1) {\n" +
                              "    }\n" +
                              "\n" +
                              "    public static String getField1() {\n" +
                              "        return field1;\n" +
                              "    }\n" +
                              "\n" +
                              "    public void setField2(field1 String) {\n" +
                              "        this.field2 = field2;\n" +
                              "    }\n" +
                              "\n" +
                              "    @Inject\n" +
                              "    private int getField4() {\n" +
                              "        return field4;\n" +
                              "    }\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test
    public void complexJavaAbstractClassShouldBeCreated() throws Exception {
        Map<String, String> argumentsForMethod2 = new HashMap<>();
        argumentsForMethod2.put("field2", "String");

        String actualCode = builder.newClass("com.codenvy.generator.A").withAbstractClassPrefix()

                                   .addField("field1", String.class).withFieldAccessLevel(PROTECTED)

                                   .addConstructor().withConstructorAccessLevel(PROTECTED)

                                   .addMethod("method1").withReturnType(String.class).withAbstractMethodPrefix()
                                   .withMethodAccessLevel(PUBLIC)
                                   .addMethod("method2").withMethodArguments(argumentsForMethod2).withAbstractMethodPrefix()
                                   .withMethodAccessLevel(PROTECTED)
                                   .addMethod("method3").withReturnType(String.class).withAbstractMethodPrefix()
                                   .withMethodAccessLevel(DEFAULT)
                                   .addMethod("method4").withReturnType(String.class).withAbstractMethodPrefix()
                                   .withMethodAccessLevel(PRIVATE)

                                   .build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "public abstract class A {\n" +
                              "\n" +
                              "    protected String field1;\n" +
                              "\n" +
                              "    protected A() {\n" +
                              "    }\n" +
                              "\n" +
                              "    public abstract String method1();\n" +
                              "\n" +
                              "    protected abstract void method2(field2 String);\n" +
                              "\n" +
                              "    abstract String method3();\n" +
                              "\n" +
                              "    private abstract String method4();\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

}