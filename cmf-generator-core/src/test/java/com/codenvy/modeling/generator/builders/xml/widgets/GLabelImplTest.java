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
import com.codenvy.modeling.generator.builders.xml.api.widgets.GLabel;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GLabelImpl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Here we're testing {@link GLabelImpl}.
 *
 * @author Andrey Plotnikov
 */
public class GLabelImplTest extends AbstractXmlBuilderTest {

    private GLabel builder;

    @Before
    public void setUp() throws Exception {
        builder = new GLabelImpl();
    }

    @Test
    public void simpleLabelShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").build();

        String expectedContent = "<g:Label/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenPrefixWasNotInitialized() throws Exception {
        builder.build();
    }

    @Test
    public void simpleLabelWithTextPramShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withText("text").build();

        String expectedContent = "<g:Label text=\"text\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleLabelWithTitleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withTitle("title").build();

        String expectedContent = "<g:Label title=\"title\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleLabelWithVisibleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").setInvisible().build();

        String expectedContent = "<g:Label visible=\"false\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleLabelWithNameParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withName("name").build();

        String expectedContent = "<g:Label ui:field=\"name\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleLabelWithStyleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withStyle("style1").withStyle("style2").build();

        String expectedContent = "<g:Label styleName=\"{style1} {style2}\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleLabelWithAdditionalStyleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withAddStyle("style1").withAddStyle("style2").build();

        String expectedContent = "<g:Label addStyleNames=\"{style1} {style2}\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleLabelWithHeightParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withHeight("10px").build();

        String expectedContent = "<g:Label height=\"10px\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleLabelWithWidthParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withWidth("10px").build();

        String expectedContent = "<g:Label width=\"10px\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleLabelWithDebugIdParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withDebugId("debugId").build();

        String expectedContent = "<g:Label debugId=\"debugId\"/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleLabelWithOffsetShouldBeCreated() throws Exception {
        for (int i = 0; i < 5; i++) {
            String actualContent = builder.withPrefix("g").withOffset(i).build();

            String expectedContent = getOffset(i) + "<g:Label/>";

            assertEquals(expectedContent, actualContent);
        }
    }

    @Test
    public void complexLabelShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g")
                                      .withText("text")
                                      .withTitle("title")
                                      .withName("name")
                                      .withStyle("style1").withStyle("style2")
                                      .withAddStyle("style1").withAddStyle("style2")
                                      .withHeight("10px")
                                      .withWidth("10px")
                                      .withDebugId("debugId")
                                      .build();

        String expectedContent = "<g:Label text=\"text\" title=\"title\" ui:field=\"name\" height=\"10px\" width=\"10px\" " +
                                 "debugId=\"debugId\" styleName=\"{style1} {style2}\" addStyleNames=\"{style1} {style2}\"/>";

        assertEquals(expectedContent, actualContent);
    }

}