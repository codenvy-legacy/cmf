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

package com.codenvy.modeling.generator.builders.xml.containers;

import com.codenvy.modeling.generator.builders.xml.AbstractXmlBuilderTest;
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GSimpleLayoutPanel;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GSimpleLayoutPanelImpl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Here we're testing {@link GSimpleLayoutPanelImpl}.
 *
 * @author Andrey Plotnikov
 */
public class GSimpleLayoutPanelImplTest extends AbstractXmlBuilderTest {

    private GSimpleLayoutPanel builder;

    @Before
    public void setUp() throws Exception {
        builder = new GSimpleLayoutPanelImpl();
    }

    @Test
    public void simpleSimpleLayoutPanelShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").build();

        String expectedContent = "<g:SimpleLayoutPanel>\n" +
                                 "</g:SimpleLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenPrefixWasNotInitialized() throws Exception {
        builder.build();
    }

    @Test
    public void simpleSimpleLayoutPanelWithTitleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withTitle("title").build();

        String expectedContent = "<g:SimpleLayoutPanel title=\"title\">\n" +
                                 "</g:SimpleLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSimpleLayoutPanelWithVisibleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").setInvisible().build();

        String expectedContent = "<g:SimpleLayoutPanel visible=\"false\">\n" +
                                 "</g:SimpleLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSimpleLayoutPanelWithNameParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withName("name").build();

        String expectedContent = "<g:SimpleLayoutPanel ui:field=\"name\">\n" +
                                 "</g:SimpleLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSimpleLayoutPanelWithStyleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withStyle("style1").withStyle("style2").build();

        String expectedContent = "<g:SimpleLayoutPanel styleName=\"{style1} {style2}\">\n" +
                                 "</g:SimpleLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSimpleLayoutPanelWithAdditionalStyleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withAddStyle("style1").withAddStyle("style2").build();

        String expectedContent = "<g:SimpleLayoutPanel addStyleNames=\"{style1} {style2}\">\n" +
                                 "</g:SimpleLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSimpleLayoutPanelWithHeightParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withHeight("10px").build();

        String expectedContent = "<g:SimpleLayoutPanel height=\"10px\">\n" +
                                 "</g:SimpleLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSimpleLayoutPanelWithWidthParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withWidth("10px").build();

        String expectedContent = "<g:SimpleLayoutPanel width=\"10px\">\n" +
                                 "</g:SimpleLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSimpleLayoutPanelWithDebugIdParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withDebugId("debugId").build();

        String expectedContent = "<g:SimpleLayoutPanel debugId=\"debugId\">\n" +
                                 "</g:SimpleLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSimpleLayoutPanelWithOffsetShouldBeCreated() throws Exception {
        for (int i = 0; i < 5; i++) {
            String actualContent = builder.withPrefix("g").withOffset(i).build();

            String offset = getOffset(i);
            String expectedContent = offset + "<g:SimpleLayoutPanel>\n" +
                                     offset + "</g:SimpleLayoutPanel>";

            assertEquals(expectedContent, actualContent);
        }
    }

    @Test
    public void simpleSimpleLayoutPanelWithWidgetsShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g")
                                      .withWidget(createWidget("    widget 1"))
                                      .withWidget(createWidget("    widget 2"))
                                      .withWidget(createWidget("    widget 3"))
                                      .build();

        String expectedContent = "<g:SimpleLayoutPanel>\n" +
                                 "    widget 3\n" +
                                 "</g:SimpleLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void complexSimpleLayoutPanelShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g")
                                      .withTitle("title")
                                      .withName("name")
                                      .withStyle("style1").withStyle("style2")
                                      .withAddStyle("style1").withAddStyle("style2")
                                      .withHeight("10px")
                                      .withWidth("10px")
                                      .withDebugId("debugId")

                                      .withWidget(createWidget("    widget 1"))
                                      .withWidget(createWidget("    widget 2"))

                                      .build();

        String expectedContent = "<g:SimpleLayoutPanel title=\"title\" ui:field=\"name\" height=\"10px\" width=\"10px\" " +
                                 "debugId=\"debugId\" styleName=\"{style1} {style2}\" addStyleNames=\"{style1} {style2}\">\n" +
                                 "    widget 2\n" +
                                 "</g:SimpleLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

}