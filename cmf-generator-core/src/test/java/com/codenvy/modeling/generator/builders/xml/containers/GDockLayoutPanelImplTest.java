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
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GDockLayoutPanel;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GDockLayoutPanelImpl;

import org.junit.Before;
import org.junit.Test;

import static com.codenvy.modeling.generator.builders.xml.api.UIXmlBuilder.OFFSET;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Here we're testing {@link GDockLayoutPanelImpl}.
 *
 * @author Andrey Plotnikov
 */
public class GDockLayoutPanelImplTest extends AbstractXmlBuilderTest {

    private GDockLayoutPanel builder;

    @Before
    public void setUp() throws Exception {
        builder = new GDockLayoutPanelImpl();
    }

    @Test
    public void simpleDockLayoutPanelShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").build();

        String expectedContent = "<g:DockLayoutPanel>\n" +
                                 "</g:DockLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenPrefixWasNotInitialized() throws Exception {
        builder.build();
    }

    @Test
    public void simpleDockLayoutPanelWithTitleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withTitle("title").build();

        String expectedContent = "<g:DockLayoutPanel title=\"title\">\n" +
                                 "</g:DockLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleDockLayoutPanelWithVisibleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").setInvisible().build();

        String expectedContent = "<g:DockLayoutPanel visible=\"false\">\n" +
                                 "</g:DockLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleDockLayoutPanelWithNameParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withName("name").build();

        String expectedContent = "<g:DockLayoutPanel ui:field=\"name\">\n" +
                                 "</g:DockLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleDockLayoutPanelWithStyleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withStyle("style1").withStyle("style2").build();

        String expectedContent = "<g:DockLayoutPanel styleName=\"{style1} {style2}\">\n" +
                                 "</g:DockLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleDockLayoutPanelWithAdditionalStyleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withAddStyle("style1").withAddStyle("style2").build();

        String expectedContent = "<g:DockLayoutPanel addStyleNames=\"{style1} {style2}\">\n" +
                                 "</g:DockLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleDockLayoutPanelWithHeightParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withHeight("10px").build();

        String expectedContent = "<g:DockLayoutPanel height=\"10px\">\n" +
                                 "</g:DockLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleDockLayoutPanelWithWidthParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withWidth("10px").build();

        String expectedContent = "<g:DockLayoutPanel width=\"10px\">\n" +
                                 "</g:DockLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleDockLayoutPanelWithDebugIdParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withDebugId("debugId").build();

        String expectedContent = "<g:DockLayoutPanel debugId=\"debugId\">\n" +
                                 "</g:DockLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleDockLayoutPanelWithCenterPartShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withWidget(createWidget(TWO_OFFSETS + "widget1")).build();

        String expectedContent = "<g:DockLayoutPanel>\n" +
                                 "    <g:center>\n" +
                                 TWO_OFFSETS + "widget1\n" +
                                 "    </g:center>\n" +
                                 "</g:DockLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleDockLayoutPanelWithNorthPartShouldBeCreated() throws Exception {
        double size = 10.;
        String actualContent = builder.withPrefix("g").withNorth(size, createWidget(TWO_OFFSETS + "widget 1")).build();

        String expectedContent = String.format("<g:DockLayoutPanel>%n" +
                                               OFFSET + "<g:north size=\"%.2f\">%n" +
                                               TWO_OFFSETS + "widget 1%n" +
                                               OFFSET + "</g:north>%n" +
                                               "</g:DockLayoutPanel>",

                                               size
                                              );

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleDockLayoutPanelWithSouthPartShouldBeCreated() throws Exception {
        double size = 10.;
        String actualContent = builder.withPrefix("g").withSouth(size, createWidget(TWO_OFFSETS + "widget 1")).build();

        String expectedContent = String.format("<g:DockLayoutPanel>%n" +
                                               OFFSET + "<g:south size=\"%.2f\">%n" +
                                               TWO_OFFSETS + "widget 1%n" +
                                               OFFSET + "</g:south>%n" +
                                               "</g:DockLayoutPanel>",

                                               size
                                              );

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleDockLayoutPanelWithEastPartShouldBeCreated() throws Exception {
        double size = 10.;
        String actualContent = builder.withPrefix("g").withEast(size, createWidget(TWO_OFFSETS + "widget 1")).build();

        String expectedContent = String.format("<g:DockLayoutPanel>%n" +
                                               OFFSET + "<g:east size=\"%.2f\">%n" +
                                               TWO_OFFSETS + "widget 1%n" +
                                               OFFSET + "</g:east>%n" +
                                               "</g:DockLayoutPanel>",

                                               size
                                              );

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleDockLayoutPanelWithWestPartShouldBeCreated() throws Exception {
        double size = 10.;
        String actualContent = builder.withPrefix("g").withWest(size, createWidget(TWO_OFFSETS + "widget 1")).build();

        String expectedContent = String.format("<g:DockLayoutPanel>%n" +
                                               OFFSET + "<g:west size=\"%.2f\">%n" +
                                               TWO_OFFSETS + "widget 1%n" +
                                               OFFSET + "</g:west>%n" +
                                               "</g:DockLayoutPanel>",

                                               size
                                              );

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void complexDockLayoutPanelShouldBeCreated() throws Exception {
        GWidget<GWidget> widget1 = createWidget(TWO_OFFSETS + "widget 1");
        GWidget<GWidget> widget2 = createWidget(TWO_OFFSETS + "widget 2");
        GWidget<GWidget> widget3 = createWidget(TWO_OFFSETS + "widget 3");
        GWidget<GWidget> widget4 = createWidget(TWO_OFFSETS + "widget 4");

        double size = 10.;

        String actualContent = builder.withPrefix("g")
                                      .withTitle("title")
                                      .withName("name")
                                      .withStyle("style1").withStyle("style2")
                                      .withAddStyle("style1").withAddStyle("style2")
                                      .withHeight("10px")
                                      .withWidth("10px")
                                      .withDebugId("debugId")

                                      .withNorth(size, widget1)
                                      .withWest(size, widget2)
                                      .withWest(size, widget4)
                                      .withWidget(widget3)

                                      .build();

        String expectedContent = String.format("<g:DockLayoutPanel title=\"title\" ui:field=\"name\" height=\"10px\" width=\"10px\" " +
                                               "debugId=\"debugId\" styleName=\"{style1} {style2}\" addStyleNames=\"{style1} {style2}\">%n" +
                                               OFFSET + "<g:north size=\"%.2f\">%n" +
                                               TWO_OFFSETS + "widget 1%n" +
                                               OFFSET + "</g:north>\n" +
                                               OFFSET + "<g:west size=\"%.2f\">%n" +
                                               TWO_OFFSETS + "widget 2%n" +
                                               OFFSET + "</g:west>\n" +
                                               OFFSET + "<g:west size=\"%.2f\">%n" +
                                               TWO_OFFSETS + "widget 4%n" +
                                               OFFSET + "</g:west>%n" +
                                               OFFSET + "<g:center>%n" +
                                               TWO_OFFSETS + "widget 3%n" +
                                               OFFSET + "</g:center>%n" +
                                               "</g:DockLayoutPanel>",

                                               size, size, size
                                              );

        assertEquals(expectedContent, actualContent);

        verify(widget1).withOffset(eq(2));
        verify(widget2).withOffset(eq(2));
        verify(widget3).withOffset(eq(2));
    }

}