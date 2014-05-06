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
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GDockLayoutPanel;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GLabelImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GTextAreaImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GDockLayoutPanelImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GFlowPanelImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GScrollPanelImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GSimpleLayoutPanelImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GSplitLayoutPanelImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Here we're testing {@link GDockLayoutPanelImpl}, {@link GFlowPanelImpl}, {@link GScrollPanelImpl}, {@link GSimpleLayoutPanelImpl},
 * {@link GSplitLayoutPanelImpl} using parameters.
 *
 * @author Valeriy Svydenko
 */
@RunWith(Parameterized.class)
public class GPanelsParameterizedTest extends AbstractXmlBuilderTest {

    @Parameter(0)
    public int parameter;

    @Parameters
    public static Collection<Integer[]> data() {
        return Arrays.asList(new Integer[]{1},
                             new Integer[]{2},
                             new Integer[]{3},
                             new Integer[]{4});
    }

    @Test
    public void simpleDockLayoutPanelWithOffsetShouldBeCreated() throws Exception {
        GDockLayoutPanel builder = new GDockLayoutPanelImpl();

        String actualContent = builder.withPrefix("g").withOffset(parameter).build();

        String offset = getOffset(parameter);
        String expectedContent = offset + "<g:DockLayoutPanel>\n" +
                                 offset + "</g:DockLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void realDockLayoutPanelWithWidgetsShouldBeCreated() throws Exception {
        String actualContent = new GDockLayoutPanelImpl()
                .withPrefix("g").withOffset(parameter)
                .withNorth(20, new GDockLayoutPanelImpl().withPrefix("g").withWidget(new GLabelImpl().withPrefix("g")))
                .withSouth(20, new GDockLayoutPanelImpl().withPrefix("g").withWidget(new GLabelImpl().withPrefix("g")))
                .withEast(20, new GDockLayoutPanelImpl().withPrefix("g").withWidget(new GLabelImpl().withPrefix("g")))
                .withWest(20, new GDockLayoutPanelImpl().withPrefix("g").withWidget(new GLabelImpl().withPrefix("g")))
                .withWidget(new GDockLayoutPanelImpl().withPrefix("g").withWidget(new GLabelImpl().withPrefix("g")))
                .build();

        String expectedContent = getOffset(parameter) + "<g:DockLayoutPanel>\n" +

                                 getOffset(parameter + 1) + "<g:north size=\"20.00\">\n" +
                                 getOffset(parameter + 2) + "<g:DockLayoutPanel>\n" +
                                 getOffset(parameter + 3) + "<g:center>\n" +
                                 getOffset(parameter + 4) + "<g:Label/>\n" +
                                 getOffset(parameter + 3) + "</g:center>\n" +
                                 getOffset(parameter + 2) + "</g:DockLayoutPanel>\n" +
                                 getOffset(parameter + 1) + "</g:north>\n" +

                                 getOffset(parameter + 1) + "<g:south size=\"20.00\">\n" +
                                 getOffset(parameter + 2) + "<g:DockLayoutPanel>\n" +
                                 getOffset(parameter + 3) + "<g:center>\n" +
                                 getOffset(parameter + 4) + "<g:Label/>\n" +
                                 getOffset(parameter + 3) + "</g:center>\n" +
                                 getOffset(parameter + 2) + "</g:DockLayoutPanel>\n" +
                                 getOffset(parameter + 1) + "</g:south>\n" +

                                 getOffset(parameter + 1) + "<g:east size=\"20.00\">\n" +
                                 getOffset(parameter + 2) + "<g:DockLayoutPanel>\n" +
                                 getOffset(parameter + 3) + "<g:center>\n" +
                                 getOffset(parameter + 4) + "<g:Label/>\n" +
                                 getOffset(parameter + 3) + "</g:center>\n" +
                                 getOffset(parameter + 2) + "</g:DockLayoutPanel>\n" +
                                 getOffset(parameter + 1) + "</g:east>\n" +

                                 getOffset(parameter + 1) + "<g:west size=\"20.00\">\n" +
                                 getOffset(parameter + 2) + "<g:DockLayoutPanel>\n" +
                                 getOffset(parameter + 3) + "<g:center>\n" +
                                 getOffset(parameter + 4) + "<g:Label/>\n" +
                                 getOffset(parameter + 3) + "</g:center>\n" +
                                 getOffset(parameter + 2) + "</g:DockLayoutPanel>\n" +
                                 getOffset(parameter + 1) + "</g:west>\n" +

                                 getOffset(parameter + 1) + "<g:center>\n" +
                                 getOffset(parameter + 2) + "<g:DockLayoutPanel>\n" +
                                 getOffset(parameter + 3) + "<g:center>\n" +
                                 getOffset(parameter + 4) + "<g:Label/>\n" +
                                 getOffset(parameter + 3) + "</g:center>\n" +
                                 getOffset(parameter + 2) + "</g:DockLayoutPanel>\n" +
                                 getOffset(parameter + 1) + "</g:center>\n" +

                                 getOffset(parameter) + "</g:DockLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleFlowPanelWithOffsetShouldBeCreated() throws Exception {
        GFlowPanelImpl builder = new GFlowPanelImpl();

        String actualContent = builder.withPrefix("g").withOffset(parameter).build();

        String offset = getOffset(parameter);
        String expectedContent = offset + "<g:FlowPanel>\n" +
                                 offset + "</g:FlowPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void realFlowPanelWithWidgetsShouldBeCreated() throws Exception {
        String actualContent = new GFlowPanelImpl().withPrefix("g").withOffset(parameter)
                                                   .withWidget(new GLabelImpl().withPrefix("g").withText("text").withHeight("10px"))
                                                   .withWidget(new GTextAreaImpl().withPrefix("g").withTitle("title").withName("name"))
                                                   .withWidget(new GFlowPanelImpl().withPrefix("g")
                                                                                   .withWidget(new GLabelImpl().withPrefix("g"))
                                                              )
                                                   .build();

        String expectedContent = getOffset(parameter) + "<g:FlowPanel>\n" +
                                 getOffset(parameter + 1) + "<g:Label text=\"text\" height=\"10px\"/>\n" +
                                 getOffset(parameter + 1) + "<g:TextArea title=\"title\" ui:field=\"name\"/>\n" +
                                 getOffset(parameter + 1) + "<g:FlowPanel>\n" +
                                 getOffset(parameter + 2) + "<g:Label/>\n" +
                                 getOffset(parameter + 1) + "</g:FlowPanel>\n" +
                                 getOffset(parameter) + "</g:FlowPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleScrollPanelWithOffsetShouldBeCreated() throws Exception {
        GScrollPanelImpl builder = new GScrollPanelImpl();

        String actualContent = builder.withPrefix("g").withOffset(parameter).build();

        String offset = getOffset(parameter);
        String expectedContent = offset + "<g:ScrollPanel>\n" +
                                 offset + "</g:ScrollPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void realScrollPanelWithWidgetsShouldBeCreated() throws Exception {
        String actualContent = new GScrollPanelImpl()
                .withPrefix("g").withOffset(parameter)
                .withWidget(new GLabelImpl().withPrefix("g").withText("text").withHeight("10px"))
                .withWidget(new GTextAreaImpl().withPrefix("g").withTitle("title").withName("name"))
                .withWidget(new GScrollPanelImpl().withPrefix("g")
                                                  .withWidget(new GLabelImpl().withPrefix("g"))
                           )
                .build();

        String expectedContent = getOffset(parameter) + "<g:ScrollPanel>\n" +
                                 getOffset(parameter + 1) + "<g:Label text=\"text\" height=\"10px\"/>\n" +
                                 getOffset(parameter + 1) + "<g:TextArea title=\"title\" ui:field=\"name\"/>\n" +
                                 getOffset(parameter + 1) + "<g:ScrollPanel>\n" +
                                 getOffset(parameter + 2) + "<g:Label/>\n" +
                                 getOffset(parameter + 1) + "</g:ScrollPanel>\n" +
                                 getOffset(parameter) + "</g:ScrollPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSimpleLayoutPanelWithOffsetShouldBeCreated() throws Exception {
        GSimpleLayoutPanelImpl builder = new GSimpleLayoutPanelImpl();

        String actualContent = builder.withPrefix("g").withOffset(parameter).build();

        String offset = getOffset(parameter);
        String expectedContent = offset + "<g:SimpleLayoutPanel>\n" +
                                 offset + "</g:SimpleLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void realSimpleLayoutPanelWithWidgetsShouldBeCreated() throws Exception {
        String actualContent = new GSimpleLayoutPanelImpl()
                .withPrefix("g").withOffset(parameter)
                .withWidget(new GSimpleLayoutPanelImpl().withPrefix("g").withWidget(new GLabelImpl().withPrefix("g")))
                .build();

        String expectedContent = getOffset(parameter) + "<g:SimpleLayoutPanel>\n" +
                                 getOffset(parameter + 1) + "<g:SimpleLayoutPanel>\n" +
                                 getOffset(parameter + 2) + "<g:Label/>\n" +
                                 getOffset(parameter + 1) + "</g:SimpleLayoutPanel>\n" +
                                 getOffset(parameter) + "</g:SimpleLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void realSplitLayoutPanelWithWidgetsShouldBeCreated() throws Exception {
        String actualContent = new GSplitLayoutPanelImpl()
                .withPrefix("g").withOffset(parameter)
                .withNorth(20, new GSplitLayoutPanelImpl().withPrefix("g").withWidget(new GLabelImpl().withPrefix("g")))
                .withSouth(20, new GSplitLayoutPanelImpl().withPrefix("g").withWidget(new GLabelImpl().withPrefix("g")))
                .withEast(20, new GSplitLayoutPanelImpl().withPrefix("g").withWidget(new GLabelImpl().withPrefix("g")))
                .withWest(20, new GSplitLayoutPanelImpl().withPrefix("g").withWidget(new GLabelImpl().withPrefix("g")))
                .withWidget(new GSplitLayoutPanelImpl().withPrefix("g").withWidget(new GLabelImpl().withPrefix("g")))
                .build();

        String expectedContent = getOffset(parameter) + "<g:SplitLayoutPanel>\n" +

                                 getOffset(parameter + 1) + "<g:north size=\"20.00\">\n" +
                                 getOffset(parameter + 2) + "<g:SplitLayoutPanel>\n" +
                                 getOffset(parameter + 3) + "<g:center>\n" +
                                 getOffset(parameter + 4) + "<g:Label/>\n" +
                                 getOffset(parameter + 3) + "</g:center>\n" +
                                 getOffset(parameter + 2) + "</g:SplitLayoutPanel>\n" +
                                 getOffset(parameter + 1) + "</g:north>\n" +

                                 getOffset(parameter + 1) + "<g:south size=\"20.00\">\n" +
                                 getOffset(parameter + 2) + "<g:SplitLayoutPanel>\n" +
                                 getOffset(parameter + 3) + "<g:center>\n" +
                                 getOffset(parameter + 4) + "<g:Label/>\n" +
                                 getOffset(parameter + 3) + "</g:center>\n" +
                                 getOffset(parameter + 2) + "</g:SplitLayoutPanel>\n" +
                                 getOffset(parameter + 1) + "</g:south>\n" +

                                 getOffset(parameter + 1) + "<g:east size=\"20.00\">\n" +
                                 getOffset(parameter + 2) + "<g:SplitLayoutPanel>\n" +
                                 getOffset(parameter + 3) + "<g:center>\n" +
                                 getOffset(parameter + 4) + "<g:Label/>\n" +
                                 getOffset(parameter + 3) + "</g:center>\n" +
                                 getOffset(parameter + 2) + "</g:SplitLayoutPanel>\n" +
                                 getOffset(parameter + 1) + "</g:east>\n" +

                                 getOffset(parameter + 1) + "<g:west size=\"20.00\">\n" +
                                 getOffset(parameter + 2) + "<g:SplitLayoutPanel>\n" +
                                 getOffset(parameter + 3) + "<g:center>\n" +
                                 getOffset(parameter + 4) + "<g:Label/>\n" +
                                 getOffset(parameter + 3) + "</g:center>\n" +
                                 getOffset(parameter + 2) + "</g:SplitLayoutPanel>\n" +
                                 getOffset(parameter + 1) + "</g:west>\n" +

                                 getOffset(parameter + 1) + "<g:center>\n" +
                                 getOffset(parameter + 2) + "<g:SplitLayoutPanel>\n" +
                                 getOffset(parameter + 3) + "<g:center>\n" +
                                 getOffset(parameter + 4) + "<g:Label/>\n" +
                                 getOffset(parameter + 3) + "</g:center>\n" +
                                 getOffset(parameter + 2) + "</g:SplitLayoutPanel>\n" +
                                 getOffset(parameter + 1) + "</g:center>\n" +

                                 getOffset(parameter) + "</g:SplitLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleSplitLayoutPanelWithOffsetShouldBeCreated() throws Exception {
        GSplitLayoutPanelImpl builder = new GSplitLayoutPanelImpl();

        String actualContent = builder.withPrefix("g").withOffset(parameter).build();

        String offset = getOffset(parameter);
        String expectedContent = offset + "<g:SplitLayoutPanel>\n" +
                                 offset + "</g:SplitLayoutPanel>";

        assertEquals(expectedContent, actualContent);
    }
}