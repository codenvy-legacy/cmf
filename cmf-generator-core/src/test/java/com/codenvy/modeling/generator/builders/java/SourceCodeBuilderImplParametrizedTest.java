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

import com.codenvy.modeling.generator.builders.java.SourceCodeBuilder.Access;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Here we're testing {@link SourceCodeBuilderImpl} using parameters.
 *
 * @author Valeriy Svydenko
 */
@RunWith(Parameterized.class)
public class SourceCodeBuilderImplParametrizedTest {
    private SourceCodeBuilderImpl builder;

    @Parameter(0)
    public Access parameter;

    @Parameters
    public static Collection<Object[]> data() {

        List<Object[]> data = new ArrayList<>();

        for (Access a : Access.values()) {
            data.add(new Object[]{a});
        }
        return data;
    }

    @Before
    public void setUp() throws Exception {
        builder = new SourceCodeBuilderImpl();
    }

    @Test
    public void simpleJavaClassWithAccessLevelShouldBeCreated() throws Exception {
        String actualCode = builder.newClass("com.codenvy.generator.A").withClassAccessLevel(parameter).build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              parameter.getPrefix() + "class A {\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test
    public void simpleJavaClassWithSimpleFieldWithAccessLevelShouldBeCreated() throws Exception {
        String actualCode = builder.newClass("com.codenvy.generator.A")
                                   .addField("field1", Integer.class).withFieldAccessLevel(parameter)
                                   .build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "public class A {\n" +
                              "\n" +
                              "    " + parameter.getPrefix() + "Integer field1;\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test
    public void simpleJavaClassWithSimpleMethodWithAccessLevelShouldBeCreated() throws Exception {
        String actualCode = builder.newClass("com.codenvy.generator.A").addMethod("getA").withMethodAccessLevel(parameter).build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "public class A {\n" +
                              "\n" +
                              "    " + parameter.getPrefix() + "void getA() {\n" +
                              "    }\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }

    @Test
    public void simpleJavaClassWithSimpleConstructorWithAccessLevelShouldBeCreated() throws Exception {
        String actualCode = builder.newClass("com.codenvy.generator.A").addConstructor().withConstructorAccessLevel(parameter).build();

        String expectedCode = "package com.codenvy.generator;\n" +
                              "\n" +
                              "public class A {\n" +
                              "\n" +
                              "    " + parameter.getPrefix() + "A() {\n" +
                              "    }\n" +
                              "\n" +
                              "}\n";

        assertEquals(expectedCode, actualCode);
    }
}