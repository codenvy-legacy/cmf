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
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GScrollPanel;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GLabelImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GTextAreaImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GScrollPanelImpl;

import org.junit.Before;
import org.junit.Test;

import static com.codenvy.modeling.generator.builders.xml.api.UIXmlBuilder.OFFSET;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

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
        GWidget<GWidget> widget1 = createWidget(OFFSET + "widget 1");
        GWidget<GWidget> widget2 = createWidget(OFFSET + "widget 2");
        GWidget<GWidget> widget3 = createWidget(OFFSET + "widget 3");

        String actualContent = builder.withPrefix("g")
                                      .withWidget(widget1)
                                      .withWidget(widget2)
                                      .withWidget(widget3)
                                      .build();

        String expectedContent = "<g:ScrollPanel>\n" +
                                 OFFSET + "widget 1\n" +
                                 OFFSET + "widget 2\n" +
                                 OFFSET + "widget 3\n" +
                                 "</g:ScrollPanel>";

        assertEquals(expectedContent, actualContent);

        verify(widget1).withOffset(eq(1));
        verify(widget2).withOffset(eq(1));
        verify(widget3).withOffset(eq(1));
    }

    @Test
    public void realScrollPanelWithWidgetsShouldBeCreated() throws Exception {
        for (int i = 0; i < 5; i++) {
            String actualContent = new GScrollPanelImpl()
                    .withPrefix("g").withOffset(i)
                    .withWidget(new GLabelImpl().withPrefix("g").withText("text").withHeight("10px"))
                    .withWidget(new GTextAreaImpl().withPrefix("g").withTitle("title").withName("name"))
                    .withWidget(new GScrollPanelImpl().withPrefix("g")
                                                      .withWidget(new GLabelImpl().withPrefix("g"))
                               )
                    .build();

            String expectedContent = getOffset(i) + "<g:ScrollPanel>\n" +
                                     getOffset(i + 1) + "<g:Label text=\"text\" height=\"10px\"/>\n" +
                                     getOffset(i + 1) + "<g:TextArea title=\"title\" ui:field=\"name\"/>\n" +
                                     getOffset(i + 1) + "<g:ScrollPanel>\n" +
                                     getOffset(i + 2) + "<g:Label/>\n" +
                                     getOffset(i + 1) + "</g:ScrollPanel>\n" +
                                     getOffset(i) + "</g:ScrollPanel>";

            assertEquals(expectedContent, actualContent);
        }
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

        String expectedContent = "<g:ScrollPanel title=\"title\" ui:field=\"name\" height=\"10px\" width=\"10px\" debugId=\"debugId\" " +
                                 "styleName=\"{style1} {style2}\" addStyleNames=\"{style1} {style2}\">\n" +
                                 OFFSET + "widget 1\n" +
                                 OFFSET + "widget 2\n" +
                                 "</g:ScrollPanel>";

        assertEquals(expectedContent, actualContent);

        verify(widget1).withOffset(eq(1));
        verify(widget2).withOffset(eq(1));
    }

}