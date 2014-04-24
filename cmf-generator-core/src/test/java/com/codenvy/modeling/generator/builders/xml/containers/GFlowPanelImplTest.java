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
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GFlowPanel;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GLabelImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GTextAreaImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GFlowPanelImpl;

import org.junit.Before;
import org.junit.Test;

import static com.codenvy.modeling.generator.builders.xml.api.UIXmlBuilder.OFFSET;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

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
        GWidget<GWidget> widget1 = createWidget(OFFSET + "widget 1");
        GWidget<GWidget> widget2 = createWidget(OFFSET + "widget 2");
        GWidget<GWidget> widget3 = createWidget(OFFSET + "widget 3");

        String actualContent = builder.withPrefix("g")
                                      .withWidget(widget1)
                                      .withWidget(widget2)
                                      .withWidget(widget3)
                                      .build();

        String expectedContent = "<g:FlowPanel>\n" +
                                 OFFSET + "widget 1\n" +
                                 OFFSET + "widget 2\n" +
                                 OFFSET + "widget 3\n" +
                                 "</g:FlowPanel>";

        assertEquals(expectedContent, actualContent);

        verify(widget1).withOffset(eq(1));
        verify(widget2).withOffset(eq(1));
        verify(widget3).withOffset(eq(1));
    }

    @Test
    public void realFlowPanelWithWidgetsShouldBeCreated() throws Exception {
        for (int i = 0; i < 5; i++) {
            String actualContent = new GFlowPanelImpl().withPrefix("g").withOffset(i)
                                                       .withWidget(new GLabelImpl().withPrefix("g").withText("text").withHeight("10px"))
                                                       .withWidget(new GTextAreaImpl().withPrefix("g").withTitle("title").withName("name"))
                                                       .withWidget(new GFlowPanelImpl().withPrefix("g")
                                                                                       .withWidget(new GLabelImpl().withPrefix("g"))
                                                                  )
                                                       .build();

            String expectedContent = getOffset(i) + "<g:FlowPanel>\n" +
                                     getOffset(i + 1) + "<g:Label text=\"text\" height=\"10px\"/>\n" +
                                     getOffset(i + 1) + "<g:TextArea title=\"title\" ui:field=\"name\"/>\n" +
                                     getOffset(i + 1) + "<g:FlowPanel>\n" +
                                     getOffset(i + 2) + "<g:Label/>\n" +
                                     getOffset(i + 1) + "</g:FlowPanel>\n" +
                                     getOffset(i) + "</g:FlowPanel>";

            assertEquals(expectedContent, actualContent);
        }
    }

    @Test
    public void complexFlowPanelShouldBeCreated() throws Exception {
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

        String expectedContent = "<g:FlowPanel title=\"title\" ui:field=\"name\" height=\"10px\" width=\"10px\" debugId=\"debugId\" " +
                                 "styleName=\"{style1} {style2}\" addStyleNames=\"{style1} {style2}\">\n" +
                                 "    widget 1\n" +
                                 "    widget 2\n" +
                                 "</g:FlowPanel>";

        assertEquals(expectedContent, actualContent);

        verify(widget1).withOffset(eq(1));
        verify(widget2).withOffset(eq(1));
    }

}