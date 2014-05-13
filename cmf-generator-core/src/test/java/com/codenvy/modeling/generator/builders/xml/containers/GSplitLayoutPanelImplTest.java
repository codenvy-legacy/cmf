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
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GSplitLayoutPanel;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GSplitLayoutPanelImpl;

import org.junit.Before;
import org.junit.Test;

import static com.codenvy.modeling.generator.builders.xml.api.UIXmlBuilder.OFFSET;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Here we're testing {@link GSplitLayoutPanelImpl}.
 *
 * @author Andrey Plotnikov
 */
public class GSplitLayoutPanelImplTest extends AbstractXmlBuilderTest {

    private GSplitLayoutPanel builder;

    @Before
    public void setUp() throws Exception {
        builder = new GSplitLayoutPanelImpl();
    }

    @Test
    public void simpleSplitLayoutPanelShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").build();

        String expectedContent = "<g:SplitLayoutPanel>\n" +
                                 "</g:SplitLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test(expected = IllegalStateException.class)
    public void exceptionShouldBeThrownWhenPrefixWasNotInitialized() throws Exception {
        builder.build();
    }

    @Test
    public void simpleSplitLayoutPanelWithTitleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withTitle("title").build();

        String expectedContent = "<g:SplitLayoutPanel title=\"title\">\n" +
                                 "</g:SplitLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSplitLayoutPanelWithVisibleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").setInvisible().build();

        String expectedContent = "<g:SplitLayoutPanel visible=\"false\">\n" +
                                 "</g:SplitLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSplitLayoutPanelWithNameParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withName("name").build();

        String expectedContent = "<g:SplitLayoutPanel ui:field=\"name\">\n" +
                                 "</g:SplitLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSplitLayoutPanelWithStyleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withStyle("style1").withStyle("style2").build();

        String expectedContent = "<g:SplitLayoutPanel styleName=\"{style1} {style2}\">\n" +
                                 "</g:SplitLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSplitLayoutPanelWithAdditionalStyleParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withAddStyle("style1").withAddStyle("style2").build();

        String expectedContent = "<g:SplitLayoutPanel addStyleNames=\"{style1} {style2}\">\n" +
                                 "</g:SplitLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSplitLayoutPanelWithHeightParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withHeight("10px").build();

        String expectedContent = "<g:SplitLayoutPanel height=\"10px\">\n" +
                                 "</g:SplitLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSplitLayoutPanelWithWidthParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withWidth("10px").build();

        String expectedContent = "<g:SplitLayoutPanel width=\"10px\">\n" +
                                 "</g:SplitLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSplitLayoutPanelWithDebugIdParamShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withDebugId("debugId").build();

        String expectedContent = "<g:SplitLayoutPanel debugId=\"debugId\">\n" +
                                 "</g:SplitLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSplitLayoutPanelWithOffsetShouldBeCreated() throws Exception {
        for (int i = 0; i < 5; i++) {
            String actualContent = builder.withPrefix("g").withOffset(i).build();

            String offset = getOffset(i);
            String expectedContent = offset + "<g:SplitLayoutPanel>\n" +
                                     offset + "</g:SplitLayoutPanel>";

            assertEquals(expectedContent, actualContent);
        }
    }

    @Test
    public void simpleSplitLayoutPanelWithCenterPartShouldBeCreated() throws Exception {
        String actualContent = builder.withPrefix("g").withWidget(createWidget(TWO_OFFSETS + "widget1")).build();

        String expectedContent = "<g:SplitLayoutPanel>\n" +
                                 OFFSET + "<g:center>\n" +
                                 TWO_OFFSETS + "widget1\n" +
                                 OFFSET + "</g:center>\n" +
                                 "</g:SplitLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSplitLayoutPanelWithNorthPartShouldBeCreated() throws Exception {
        double size = 10.;
        String actualContent = builder.withPrefix("g").withNorth(size, createWidget(TWO_OFFSETS + "widget 1")).build();

        String expectedContent = String.format("<g:SplitLayoutPanel>%n" +
                                               OFFSET + "<g:north size=\"%.2f\">%n" +
                                               TWO_OFFSETS + "widget 1%n" +
                                               OFFSET + "</g:north>%n" +
                                               "</g:SplitLayoutPanel>",

                                               size
                                              );

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSplitLayoutPanelWithSouthPartShouldBeCreated() throws Exception {
        double size = 10.;
        String actualContent = builder.withPrefix("g").withSouth(size, createWidget(TWO_OFFSETS + "widget 1")).build();

        String expectedContent = String.format("<g:SplitLayoutPanel>%n" +
                                               OFFSET + "<g:south size=\"%.2f\">%n" +
                                               TWO_OFFSETS + "widget 1%n" +
                                               OFFSET + "</g:south>%n" +
                                               "</g:SplitLayoutPanel>",

                                               size
                                              );

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSplitLayoutPanelWithEastPartShouldBeCreated() throws Exception {
        double size = 10.;
        String actualContent = builder.withPrefix("g").withEast(size, createWidget(TWO_OFFSETS + "widget 1")).build();

        String expectedContent = String.format("<g:SplitLayoutPanel>%n" +
                                               OFFSET + "<g:east size=\"%.2f\">%n" +
                                               TWO_OFFSETS + "widget 1%n" +
                                               OFFSET + "</g:east>%n" +
                                               "</g:SplitLayoutPanel>",

                                               size
                                              );

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSplitLayoutPanelWithWestPartShouldBeCreated() throws Exception {
        double size = 10.;
        String actualContent = builder.withPrefix("g").withWest(size, createWidget(TWO_OFFSETS + "widget 1")).build();

        String expectedContent = String.format("<g:SplitLayoutPanel>%n" +
                                               OFFSET + "<g:west size=\"%.2f\">%n" +
                                               TWO_OFFSETS + "widget 1%n" +
                                               OFFSET + "</g:west>%n" +
                                               "</g:SplitLayoutPanel>",

                                               size
                                              );

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void complexSplitLayoutPanelShouldBeCreated() throws Exception {
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

        String expectedContent = String.format("<g:SplitLayoutPanel title=\"title\" ui:field=\"name\" height=\"10px\" width=\"10px\" " +
                                               "debugId=\"debugId\" styleName=\"{style1} {style2}\" addStyleNames=\"{style1} {style2}\">%n" +
                                               OFFSET + "<g:north size=\"%.2f\">%n" +
                                               TWO_OFFSETS + "widget 1%n" +
                                               OFFSET + "</g:north>%n" +
                                               OFFSET + "<g:west size=\"%.2f\">%n" +
                                               TWO_OFFSETS + "widget 2%n" +
                                               OFFSET + "</g:west>%n" +
                                               OFFSET + "<g:west size=\"%.2f\">%n" +
                                               TWO_OFFSETS + "widget 4%n" +
                                               OFFSET + "</g:west>%n" +
                                               OFFSET + "<g:center>%n" +
                                               TWO_OFFSETS + "widget 3%n" +
                                               OFFSET + "</g:center>%n" +
                                               "</g:SplitLayoutPanel>",

                                               size, size, size
                                              );

        assertEquals(expectedContent, actualContent);

        verify(widget1).withOffset(eq(2));
        verify(widget2).withOffset(eq(2));
        verify(widget3).withOffset(eq(2));
    }

}