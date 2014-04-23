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
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GScrollPanel;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GScrollPanelImpl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Here we're testing {@link GScrollPanelImpl}.
 *
 * @author Andrey Plotnikov
 */
public class GScrollPanelImplTest extends AbstractXmlBuilderTest {

    private GScrollPanel builder;

    @Before
    public void setUp() throws Exception {
        builder = new GScrollPanelImpl();
    }

    @Test
    public void simpleScrollPanelShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").build();

        String expectedContent = "<g:ScrollPanel>\n" +
                                 "</g:ScrollPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenPrefixWasNotInitialized() throws Exception {
        builder.build();
    }

    @Test
    public void simpleScrollPanelWithTitleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withTitle("title").build();

        String expectedContent = "<g:ScrollPanel title=\"title\">\n" +
                                 "</g:ScrollPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleScrollPanelWithVisibleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").setInvisible().build();

        String expectedContent = "<g:ScrollPanel visible=\"false\">\n" +
                                 "</g:ScrollPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleScrollPanelWithNameParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withName("name").build();

        String expectedContent = "<g:ScrollPanel ui:field=\"name\">\n" +
                                 "</g:ScrollPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleScrollPanelWithStyleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withStyle("style1").withStyle("style2").build();

        String expectedContent = "<g:ScrollPanel styleName=\"{style1} {style2}\">\n" +
                                 "</g:ScrollPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleScrollPanelWithAdditionalStyleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withAddStyle("style1").withAddStyle("style2").build();

        String expectedContent = "<g:ScrollPanel addStyleNames=\"{style1} {style2}\">\n" +
                                 "</g:ScrollPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleScrollPanelWithHeightParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withHeight("10px").build();

        String expectedContent = "<g:ScrollPanel height=\"10px\">\n" +
                                 "</g:ScrollPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleScrollPanelWithWidthParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withWidth("10px").build();

        String expectedContent = "<g:ScrollPanel width=\"10px\">\n" +
                                 "</g:ScrollPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleScrollPanelWithDebugIdParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withDebugId("debugId").build();

        String expectedContent = "<g:ScrollPanel debugId=\"debugId\">\n" +
                                 "</g:ScrollPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleScrollPanelWithOffsetShouldBeCreated() throws Exception {
        for (int i = 0; i < 5; i++) {
            String actualContent = builder.withPrefix("g").withOffset(i).build();

            String offset = getOffset(i);
            String expectedContent = offset + "<g:ScrollPanel>\n" +
                                     offset + "</g:ScrollPanel>";

            assertEquals(expectedContent, actualContent);
        }
    }

    @Test
    public void simpleScrollPanelWithWidgetsShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g")
                                      .withWidget(createWidget("    widget 1"))
                                      .withWidget(createWidget("    widget 2"))
                                      .withWidget(createWidget("    widget 3"))
                                      .build();

        String expectedContent = "<g:ScrollPanel>\n" +
                                 "    widget 1\n" +
                                 "    widget 2\n" +
                                 "    widget 3\n" +
                                 "</g:ScrollPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleScrollPanelWithAlwaysShowScrollBarsParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").alwaysShowScrollBars().build();

        String expectedContent = "<g:ScrollPanel alwaysShowScrollBars=\"true\">\n" +
                                 "</g:ScrollPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void complexScrollPanelShouldBeCreated() throws Exception {
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

        String expectedContent = "<g:ScrollPanel title=\"title\" ui:field=\"name\" height=\"10px\" width=\"10px\" debugId=\"debugId\" " +
                                 "styleName=\"{style1} {style2}\" addStyleNames=\"{style1} {style2}\">\n" +
                                 "    widget 1\n" +
                                 "    widget 2\n" +
                                 "</g:ScrollPanel>";

        assertEquals(expectedContent, actualContent);
    }

}