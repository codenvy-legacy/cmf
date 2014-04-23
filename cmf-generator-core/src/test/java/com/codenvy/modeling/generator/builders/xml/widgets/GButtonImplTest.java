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

package com.codenvy.modeling.generator.builders.xml.widgets;

import com.codenvy.modeling.generator.builders.xml.AbstractXmlBuilderTest;
import com.codenvy.modeling.generator.builders.xml.api.widgets.GButton;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GButtonImpl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Here we're testing {@link GButtonImpl}.
 *
 * @author Andrey Plotnikov
 */
public class GButtonImplTest extends AbstractXmlBuilderTest {

    private GButton builder;

    @Before
    public void setUp() throws Exception {
        builder = new GButtonImpl();
    }

    @Test
    public void simpleButtonShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").build();

        String expectedContent = "<g:Button/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenPrefixWasNotInitialized() throws Exception {
        builder.build();
    }

    @Test
    public void simpleButtonWithTextPramShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withText("text").build();

        String expectedContent = "<g:Button text=\"text\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleButtonWithTitleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withTitle("title").build();

        String expectedContent = "<g:Button title=\"title\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleButtonWithEnableParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").setDisable().build();

        String expectedContent = "<g:Button enabled=\"false\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleButtonWithVisibleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").setInvisible().build();

        String expectedContent = "<g:Button visible=\"false\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleButtonWithFocusParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").setFocus().build();

        String expectedContent = "<g:Button focus=\"true\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleButtonWithNameParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withName("name").build();

        String expectedContent = "<g:Button ui:field=\"name\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleButtonWithStyleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withStyle("style1").withStyle("style2").build();

        String expectedContent = "<g:Button styleName=\"{style1} {style2}\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleButtonWithAdditionalStyleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withAddStyle("style1").withAddStyle("style2").build();

        String expectedContent = "<g:Button addStyleNames=\"{style1} {style2}\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleButtonWithHeightParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withHeight("10px").build();

        String expectedContent = "<g:Button height=\"10px\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleButtonWithWidthParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withWidth("10px").build();

        String expectedContent = "<g:Button width=\"10px\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleButtonWithDebugIdParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withDebugId("debugId").build();

        String expectedContent = "<g:Button debugId=\"debugId\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleButtonWithOffsetShouldBeCreated() throws Exception {
        for (int i = 0; i < 5; i++) {
            String actualContent = builder.withPrefix("g").withOffset(i).build();

            String expectedContent = getOffset(i) + "<g:Button/>";

            assertEquals(expectedContent, actualContent);
        }
    }

    @Test
    public void complexButtonShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g")
                                      .withText("text")
                                      .withTitle("title")
                                      .setFocus()
                                      .withName("name")
                                      .withStyle("style1").withStyle("style2")
                                      .withAddStyle("style1").withAddStyle("style2")
                                      .withHeight("10px")
                                      .withWidth("10px")
                                      .withDebugId("debugId")
                                      .build();

        String expectedContent = "<g:Button text=\"text\" title=\"title\" focus=\"true\" ui:field=\"name\" height=\"10px\" " +
                                 "width=\"10px\" debugId=\"debugId\" styleName=\"{style1} {style2}\" addStyleNames=\"{style1} {style2}\"/>";

        assertEquals(expectedContent, actualContent);
    }

}