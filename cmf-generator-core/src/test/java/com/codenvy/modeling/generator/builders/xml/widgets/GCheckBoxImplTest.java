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

import com.codenvy.modeling.generator.builders.xml.api.widgets.GCheckBox;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GCheckBoxImpl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Andrey Plotnikov
 */
public class GCheckBoxImplTest {

    private GCheckBox builder;

    @Before
    public void setUp() throws Exception {
        builder = new GCheckBoxImpl();
    }

    @Test
    public void simpleCheckBoxShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").build();

        String expectedContent = "<g:CheckBox/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenPrefixWasNotInitialized() throws Exception {
        builder.build();
    }

    @Test
    public void simpleCheckBoxWithTextPramShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withText("text").build();

        String expectedContent = "<g:CheckBox text=\"text\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleCheckBoxWithTitleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withTitle("title").build();

        String expectedContent = "<g:CheckBox title=\"title\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleCheckBoxWithEnableParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").setDisable().build();

        String expectedContent = "<g:CheckBox enabled=\"false\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleCheckBoxWithVisibleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").setInvisible().build();

        String expectedContent = "<g:CheckBox visible=\"false\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleCheckBoxWithFocusParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").setFocus().build();

        String expectedContent = "<g:CheckBox focus=\"true\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleCheckBoxWithNameParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withName("name").build();

        String expectedContent = "<g:CheckBox ui:field=\"name\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleCheckBoxWithStyleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withStyle("style1").withStyle("style2").build();

        String expectedContent = "<g:CheckBox styleName=\"{style1} {style2}\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleCheckBoxWithAdditionalStyleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withAddStyle("style1").withAddStyle("style2").build();

        String expectedContent = "<g:CheckBox addStyleNames=\"{style1} {style2}\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleCheckBoxWithHeightParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withHeight("10px").build();

        String expectedContent = "<g:CheckBox height=\"10px\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleCheckBoxWithWidthParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withWidth("10px").build();

        String expectedContent = "<g:CheckBox width=\"10px\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleCheckBoxWithDebugIdParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withDebugId("debugId").build();

        String expectedContent = "<g:CheckBox debugId=\"debugId\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleCheckBoxWithFormValueParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withFormValue(true).build();

        String expectedContent = "<g:CheckBox formValue=\"true\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void complexCheckBoxShouldBeCreated() throws Exception {
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
                                      .withFormValue(true)
                                      .build();

        String expectedContent = "<g:CheckBox text=\"text\" title=\"title\" focus=\"true\" ui:field=\"name\" height=\"10px\" " +
                                 "width=\"10px\" debugId=\"debugId\" formValue=\"true\" styleName=\"{style1} {style2}\" " +
                                 "addStyleNames=\"{style1} {style2}\"/>";

        assertEquals(expectedContent, actualContent);
    }

}