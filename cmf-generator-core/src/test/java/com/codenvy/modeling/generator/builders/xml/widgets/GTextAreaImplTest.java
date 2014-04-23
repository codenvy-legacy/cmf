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
import com.codenvy.modeling.generator.builders.xml.api.widgets.GTextArea;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GTextAreaImpl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Here we're testing {@link GTextAreaImpl}.
 *
 * @author Andrey Plotnikov
 */
public class GTextAreaImplTest extends AbstractXmlBuilderTest {

    private GTextArea builder;

    @Before
    public void setUp() throws Exception {
        builder = new GTextAreaImpl();
    }

    @Test
    public void simpleTextAreaShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").build();

        String expectedContent = "<g:TextArea/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenPrefixWasNotInitialized() throws Exception {
        builder.build();
    }

    @Test
    public void simpleTextAreaWithTextPramShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withText("text").build();

        String expectedContent = "<g:TextArea text=\"text\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleTextAreaWithTitleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withTitle("title").build();

        String expectedContent = "<g:TextArea title=\"title\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleTextAreaWithEnableParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").setDisable().build();

        String expectedContent = "<g:TextArea enabled=\"false\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleTextAreaWithVisibleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").setInvisible().build();

        String expectedContent = "<g:TextArea visible=\"false\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleTextAreaWithFocusParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").setFocus().build();

        String expectedContent = "<g:TextArea focus=\"true\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleTextAreaWithNameParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withName("name").build();

        String expectedContent = "<g:TextArea ui:field=\"name\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleTextAreaWithStyleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withStyle("style1").withStyle("style2").build();

        String expectedContent = "<g:TextArea styleName=\"{style1} {style2}\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleTextAreaWithAdditionalStyleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withAddStyle("style1").withAddStyle("style2").build();

        String expectedContent = "<g:TextArea addStyleNames=\"{style1} {style2}\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleTextAreaWithHeightParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withHeight("10px").build();

        String expectedContent = "<g:TextArea height=\"10px\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleTextAreaWithWidthParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withWidth("10px").build();

        String expectedContent = "<g:TextArea width=\"10px\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleTextAreaWithDebugIdParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withDebugId("debugId").build();

        String expectedContent = "<g:TextArea debugId=\"debugId\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleTextAreaWithReadOnlyParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").setReadOnly().build();

        String expectedContent = "<g:TextArea readOnly=\"true\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleTextAreaWithOffsetShouldBeCreated() throws Exception {
        for (int i = 0; i < 5; i++) {
            String actualContent = builder.withPrefix("g").withOffset(i).build();

            String expectedContent = getOffset(i) + "<g:TextArea/>";

            assertEquals(expectedContent, actualContent);
        }
    }

    @Test
    public void complexTextAreaShouldBeCreated() throws Exception {
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

        String expectedContent = "<g:TextArea text=\"text\" title=\"title\" focus=\"true\" ui:field=\"name\" height=\"10px\" " +
                                 "width=\"10px\" debugId=\"debugId\" readOnly=\"true\" styleName=\"{style1} {style2}\" " +
                                 "addStyleNames=\"{style1} {style2}\"/>";

        assertEquals(expectedContent, actualContent);
    }

}