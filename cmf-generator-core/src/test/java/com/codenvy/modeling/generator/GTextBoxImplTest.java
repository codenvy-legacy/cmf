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

import com.codenvy.modeling.generator.builders.xml.api.widgets.GTextBox;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GTextBoxImpl;

import org.junit.Before;
import org.junit.Test;

import static com.codenvy.modeling.generator.builders.xml.impl.widgets.AbstractGWidget.OFFSET;
import static org.junit.Assert.assertEquals;

/**
 * Here we're testing {@link GTextBoxImpl}.
 *
 * @author Andrey Plotnikov
 */
public class GTextBoxImplTest {

    private GTextBox builder;

    @Before
    public void setUp() throws Exception {
        builder = new GTextBoxImpl();
    }

    @Test
    public void simpleTextBoxShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").build();

        String expectedContent = "<g:TextBox/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenPrefixWasNotInitialized() throws Exception {
        builder.build();
    }

    @Test
    public void simpleTextBoxWithTextPramShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withText("text").build();

        String expectedContent = "<g:TextBox text=\"text\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleTextBoxWithTitleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withTitle("title").build();

        String expectedContent = "<g:TextBox title=\"title\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleTextBoxWithEnableParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").setDisable().build();

        String expectedContent = "<g:TextBox enabled=\"false\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleTextBoxWithVisibleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").setInvisible().build();

        String expectedContent = "<g:TextBox visible=\"false\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleTextBoxWithFocusParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").setFocus().build();

        String expectedContent = "<g:TextBox focus=\"true\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleTextBoxWithNameParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withName("name").build();

        String expectedContent = "<g:TextBox ui:field=\"name\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleTextBoxWithStyleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withStyle("style1").withStyle("style2").build();

        String expectedContent = "<g:TextBox styleName=\"{style1} {style2}\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleTextBoxWithAdditionalStyleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withAddStyle("style1").withAddStyle("style2").build();

        String expectedContent = "<g:TextBox addStyleNames=\"{style1} {style2}\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleTextBoxWithHeightParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withHeight("10px").build();

        String expectedContent = "<g:TextBox height=\"10px\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleTextBoxWithWidthParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withWidth("10px").build();

        String expectedContent = "<g:TextBox width=\"10px\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleTextBoxWithDebugIdParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withDebugId("debugId").build();

        String expectedContent = "<g:TextBox debugId=\"debugId\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleTextBoxWithReadOnlyParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").setReadOnly().build();

        String expectedContent = "<g:TextBox readOnly=\"true\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleTextBoxWithOffsetShouldBeCreated() throws Exception {
        for (int i = 0; i < 5; i++) {
            String actualContent = builder.withPrefix("g").withOffset(i).build();

            String expectedContent = getOffset(i) + "<g:TextBox/>";

            assertEquals(expectedContent, actualContent);
        }
    }

    private String getOffset(int offset) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < offset; i++) {
            result.append(OFFSET);
        }

        return result.toString();
    }

    @Test
    public void complexTextBoxShouldBeCreated() throws Exception {
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
                                      .setReadOnly()
                                      .build();

        String expectedContent = "<g:TextBox title=\"title\" ui:field=\"name\" height=\"10px\" width=\"10px\" debugId=\"debugId\" " +
                                 "styleName=\"{style1} {style2}\" addStyleNames=\"{style1} {style2}\" text=\"text\" focus=\"true\" " +
                                 "readOnly=\"true\"/>";

        assertEquals(expectedContent, actualContent);
    }

}