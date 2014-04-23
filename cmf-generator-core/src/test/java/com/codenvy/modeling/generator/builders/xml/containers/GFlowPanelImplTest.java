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
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GFlowPanel;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GFlowPanelImpl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Here we're testing {@link GFlowPanelImpl}.
 *
 * @author Andrey Plotnikov
 */
public class GFlowPanelImplTest extends AbstractXmlBuilderTest {

    private GFlowPanel builder;

    @Before
    public void setUp() throws Exception {
        builder = new GFlowPanelImpl();
    }

    @Test
    public void simpleFlowPanelShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").build();

        String expectedContent = "<g:FlowPanel>\n" +
                                 "</g:FlowPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenPrefixWasNotInitialized() throws Exception {
        builder.build();
    }

    @Test
    public void simpleFlowPanelWithTitleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withTitle("title").build();

        String expectedContent = "<g:FlowPanel title=\"title\">\n" +
                                 "</g:FlowPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleFlowPanelWithVisibleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").setInvisible().build();

        String expectedContent = "<g:FlowPanel visible=\"false\">\n" +
                                 "</g:FlowPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleFlowPanelWithNameParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withName("name").build();

        String expectedContent = "<g:FlowPanel ui:field=\"name\">\n" +
                                 "</g:FlowPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleFlowPanelWithStyleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withStyle("style1").withStyle("style2").build();

        String expectedContent = "<g:FlowPanel styleName=\"{style1} {style2}\">\n" +
                                 "</g:FlowPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleFlowPanelWithAdditionalStyleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withAddStyle("style1").withAddStyle("style2").build();

        String expectedContent = "<g:FlowPanel addStyleNames=\"{style1} {style2}\">\n" +
                                 "</g:FlowPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleFlowPanelWithHeightParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withHeight("10px").build();

        String expectedContent = "<g:FlowPanel height=\"10px\">\n" +
                                 "</g:FlowPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleFlowPanelWithWidthParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withWidth("10px").build();

        String expectedContent = "<g:FlowPanel width=\"10px\">\n" +
                                 "</g:FlowPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleFlowPanelWithDebugIdParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withDebugId("debugId").build();

        String expectedContent = "<g:FlowPanel debugId=\"debugId\">\n" +
                                 "</g:FlowPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleFlowPanelWithOffsetShouldBeCreated() throws Exception {
        for (int i = 0; i < 5; i++) {
            String actualContent = builder.withPrefix("g").withOffset(i).build();

            String offset = getOffset(i);
            String expectedContent = offset + "<g:FlowPanel>\n" +
                                     offset + "</g:FlowPanel>";

            assertEquals(expectedContent, actualContent);
        }
    }

    @Test
    public void simpleFlowPanelWithWidgetsShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g")
                                      .withWidget(createWidget("    widget 1"))
                                      .withWidget(createWidget("    widget 2"))
                                      .withWidget(createWidget("    widget 3"))
                                      .build();

        String expectedContent = "<g:FlowPanel>\n" +
                                 "    widget 1\n" +
                                 "    widget 2\n" +
                                 "    widget 3\n" +
                                 "</g:FlowPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void complexFlowPanelShouldBeCreated() throws Exception {
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

        String expectedContent = "<g:FlowPanel title=\"title\" ui:field=\"name\" height=\"10px\" width=\"10px\" debugId=\"debugId\" " +
                                 "styleName=\"{style1} {style2}\" addStyleNames=\"{style1} {style2}\">\n" +
                                 "    widget 1\n" +
                                 "    widget 2\n" +
                                 "</g:FlowPanel>";

        assertEquals(expectedContent, actualContent);
    }

}