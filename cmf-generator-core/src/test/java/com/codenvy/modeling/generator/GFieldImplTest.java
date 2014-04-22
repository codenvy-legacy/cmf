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

import com.codenvy.modeling.generator.builders.xml.api.GField;
import com.codenvy.modeling.generator.builders.xml.impl.GFieldImpl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Here we're testing {@link GFieldImpl}.
 *
 * @author Andrey Plotnikov
 */
public class GFieldImplTest {

    private GField builder;

    @Before
    public void setUp() throws Exception {
        builder = new GFieldImpl();
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenNameParamWasNotConfigured() throws Exception {
        builder.build();
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenNameParamWasNotConfigured2() throws Exception {
        builder.withType(String.class).build();
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenTypeParamWasNotConfigured() throws Exception {
        builder.withName("name").build();
    }

    @Test
    public void fieldShouldBeCreated() throws Exception {
        String actualContent = builder.withName("name").withType(String.class).build();

        String expectedContent = "    <ui:with field='name' type='java.lang.String'/>";

        assertEquals(expectedContent, actualContent);
    }

}