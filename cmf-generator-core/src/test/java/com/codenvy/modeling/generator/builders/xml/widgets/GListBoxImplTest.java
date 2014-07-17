/*
 * Copyright [2014] Codenvy, S.A.
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
package com.codenvy.modeling.generator.builders.xml.widgets;

import com.codenvy.modeling.generator.builders.xml.AbstractXmlBuilderTest;
import com.codenvy.modeling.generator.builders.xml.api.widgets.GListBox;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GListBoxImpl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Valeriy Svydenko
 */
public class GListBoxImplTest extends AbstractXmlBuilderTest {

    private GListBox builder;

    @Before
    public void setUp() throws Exception {
        builder = new GListBoxImpl();
    }

    @Test
    public void simpleListBoxShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").build();

        String expectedContent = "<g:ListBox/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenPrefixWasNotInitialized() throws Exception {
        builder.build();
    }

    @Test
    public void simpleListBoxWithEnableParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").setDisable().build();

        String expectedContent = "<g:ListBox enabled=\"false\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleListBoxWithFocusParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").setFocus().build();

        String expectedContent = "<g:ListBox focus=\"true\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleListBoxWithNameParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withName("name").build();

        String expectedContent = "<g:ListBox ui:field=\"name\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleListBoxWithStyleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withStyle("style1").withStyle("style2").build();

        String expectedContent = "<g:ListBox styleName=\"{style1} {style2}\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleListBoxWithAdditionalStyleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withAddStyle("style1").withAddStyle("style2").build();

        String expectedContent = "<g:ListBox addStyleNames=\"{style1} {style2}\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleListBoxWithHeightParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withHeight("10px").build();

        String expectedContent = "<g:ListBox height=\"10px\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleListBoxWithWidthParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withWidth("10px").build();

        String expectedContent = "<g:ListBox width=\"10px\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleListBoxWithDebugIdParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withDebugId("debugId").build();

        String expectedContent = "<g:ListBox debugId=\"debugId\"/>";

        assertEquals(expectedContent, actualContent);
    }
}