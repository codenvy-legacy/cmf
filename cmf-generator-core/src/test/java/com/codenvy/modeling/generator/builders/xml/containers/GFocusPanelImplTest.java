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
import com.codenvy.modeling.generator.builders.xml.api.widgets.GWidget;
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GFocusPanel;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GFocusPanelImpl;

import org.junit.Before;
import org.junit.Test;

import static com.codenvy.modeling.generator.builders.OffsetBuilderConstants.OFFSET;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author Andrey Plotnikov
 */
public class GFocusPanelImplTest extends AbstractXmlBuilderTest {

    private GFocusPanel builder;

    @Before
    public void setUp() throws Exception {
        builder = new GFocusPanelImpl();
    }

    @Test
    public void simpleSimpleLayoutPanelShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").build();

        String expectedContent = "<g:FocusPanel>\n" +
                                 "</g:FocusPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenPrefixWasNotInitialized() throws Exception {
        builder.build();
    }

    @Test
    public void simpleSimpleLayoutPanelWithTitleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withTitle("title").build();

        String expectedContent = "<g:FocusPanel title=\"title\">\n" +
                                 "</g:FocusPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSimpleLayoutPanelWithVisibleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").setInvisible().build();

        String expectedContent = "<g:FocusPanel visible=\"false\">\n" +
                                 "</g:FocusPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSimpleLayoutPanelWithNameParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withName("name").build();

        String expectedContent = "<g:FocusPanel ui:field=\"name\">\n" +
                                 "</g:FocusPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSimpleLayoutPanelWithStyleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withStyle("style1").withStyle("style2").build();

        String expectedContent = "<g:FocusPanel styleName=\"{style1} {style2}\">\n" +
                                 "</g:FocusPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSimpleLayoutPanelWithAdditionalStyleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withAddStyle("style1").withAddStyle("style2").build();

        String expectedContent = "<g:FocusPanel addStyleNames=\"{style1} {style2}\">\n" +
                                 "</g:FocusPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSimpleLayoutPanelWithHeightParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withHeight("10px").build();

        String expectedContent = "<g:FocusPanel height=\"10px\">\n" +
                                 "</g:FocusPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSimpleLayoutPanelWithWidthParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withWidth("10px").build();

        String expectedContent = "<g:FocusPanel width=\"10px\">\n" +
                                 "</g:FocusPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSimpleLayoutPanelWithDebugIdParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withDebugId("debugId").build();

        String expectedContent = "<g:FocusPanel debugId=\"debugId\">\n" +
                                 "</g:FocusPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSimpleLayoutPanelWithWidgetsShouldBeCreated() throws Exception {
        GWidget<GWidget> widget1 = createWidget(OFFSET + "widget 1");
        GWidget<GWidget> widget2 = createWidget(OFFSET + "widget 2");
        GWidget<GWidget> widget3 = createWidget(OFFSET + "widget 3");

        String actualContent = builder.withPrefix("g")
                                      .withWidget(widget1)
                                      .withWidget(widget2)
                                      .withWidget(widget3)
                                      .build();

        String expectedContent = "<g:FocusPanel>\n" +
                                 OFFSET + "widget 3\n" +
                                 "</g:FocusPanel>";

        assertEquals(expectedContent, actualContent);
        verify(widget1, never()).withOffset(eq(1));
        verify(widget2, never()).withOffset(eq(1));
        verify(widget3).withOffset(eq(1));
    }

    @Test
    public void complexSimpleLayoutPanelShouldBeCreated() throws Exception {
        GWidget<GWidget> widget1 = createWidget(OFFSET + "widget 1");
        GWidget<GWidget> widget2 = createWidget(OFFSET + "widget 2");

        String actualContent = builder.withPrefix("g")
                                      .withTitle("title")
                                      .withName("name")
                                      .withStyle("style1").withStyle("style2")
                                      .withAddStyle("style1").withAddStyle("style2")
                                      .withHeight("10px")
                                      .withWidth("10px")
                                      .withDebugId("debugId")

                                      .withWidget(widget1)
                                      .withWidget(widget2)

                                      .build();

        String expectedContent = "<g:FocusPanel title=\"title\" ui:field=\"name\" height=\"10px\" width=\"10px\" " +
                                 "debugId=\"debugId\" styleName=\"{style1} {style2}\" addStyleNames=\"{style1} {style2}\">\n" +
                                 OFFSET + "widget 2\n" +
                                 "</g:FocusPanel>";

        assertEquals(expectedContent, actualContent);

        verify(widget1, never()).withOffset(eq(1));
        verify(widget2).withOffset(eq(1));
    }

}