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

package com.codenvy.modeling.generator.builders.xml;

import com.codenvy.modeling.generator.builders.xml.api.GField;
import com.codenvy.modeling.generator.builders.xml.api.GStyle;
import com.codenvy.modeling.generator.builders.xml.api.UIXmlBuilder;
import com.codenvy.modeling.generator.builders.xml.api.widgets.GWidget;
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GDockLayoutPanel;
import com.codenvy.modeling.generator.builders.xml.impl.GFieldImpl;
import com.codenvy.modeling.generator.builders.xml.impl.GStyleImpl;
import com.codenvy.modeling.generator.builders.xml.impl.UIXmlBuilderImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GLabelImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GTextBoxImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GDockLayoutPanelImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GFlowPanelImpl;

import org.junit.Before;
import org.junit.Test;

import static com.codenvy.modeling.generator.builders.xml.api.UIXmlBuilder.OFFSET;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * Here we're testing {@link UIXmlBuilderImpl}.
 *
 * @author Andrey Plotnikov
 */
public class UIXmlBuilderImplTest extends AbstractXmlBuilderTest {

    private UIXmlBuilder builder;

    @Before
    public void setUp() throws Exception {
        builder = new UIXmlBuilderImpl();
    }

    @Test
    public void simpleXmlShouldBeCreated() throws Exception {
        String actualXML = builder.build();

        String expectedXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                             "<!DOCTYPE ui:UiBinder SYSTEM \"http://dl.google.com/gwt/DTD/xhtml.ent\">\n" +
                             "<ui:UiBinder \n" +
                             "             xmlns:ui='urn:ui:com.google.gwt.uibinder'>\n" +
                             "</ui:UiBinder>";

        assertEquals(expectedXML, actualXML);
    }

    @Test
    public void xmlWithFewXmlnsShouldBeCreated() throws Exception {
        String actualXML = builder.withXmlns("p", "urn:import:com.google.gwt.user.client.ui")
                                  .withXmlns("p1", "urn:import:com.google.gwt.user.cellview.client")
                                  .build();

        String expectedXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                             "<!DOCTYPE ui:UiBinder SYSTEM \"http://dl.google.com/gwt/DTD/xhtml.ent\">\n" +
                             "<ui:UiBinder \n" +
                             "             xmlns:ui='urn:ui:com.google.gwt.uibinder'\n" +
                             "             xmlns:p='urn:import:com.google.gwt.user.client.ui'\n" +
                             "             xmlns:p1='urn:import:com.google.gwt.user.cellview.client'>\n" +
                             "</ui:UiBinder>";

        assertEquals(expectedXML, actualXML);
    }

    @Test
    public void xmlWithFewFieldsShouldBeCreated() throws Exception {
        String content1 = "field 1";
        String content2 = "field 2";

        GField field1 = createField(content1);
        GField field2 = createField(content2);

        String actualXML = builder.withField(field1)
                                  .withField(field2)
                                  .build();

        String expectedXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                             "<!DOCTYPE ui:UiBinder SYSTEM \"http://dl.google.com/gwt/DTD/xhtml.ent\">\n" +
                             "<ui:UiBinder \n" +
                             "             xmlns:ui='urn:ui:com.google.gwt.uibinder'>\n" +
                             content1 + '\n' +
                             content2 + '\n' +
                             "</ui:UiBinder>";

        assertEquals(expectedXML, actualXML);

        verify(field1).build();
        verify(field2).build();
    }

    @Test
    public void xmlWithWidgetShouldBeCreate() throws Exception {
        String content = "widget";
        GWidget<GWidget> widget = createWidget(content);

        String actualXML = builder.setWidget(widget).build();

        String expectedXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                             "<!DOCTYPE ui:UiBinder SYSTEM \"http://dl.google.com/gwt/DTD/xhtml.ent\">\n" +
                             "<ui:UiBinder \n" +
                             "             xmlns:ui='urn:ui:com.google.gwt.uibinder'>\n" +
                             content + '\n' +
                             "</ui:UiBinder>";

        assertEquals(expectedXML, actualXML);

        verify(widget).build();
    }

    @Test
    public void xmlWithStyleShouldBeCreate() throws Exception {
        String content = "style";
        GStyle style = createStyle(content);

        String actualXML = builder.withStyle(style).build();

        String expectedXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                             "<!DOCTYPE ui:UiBinder SYSTEM \"http://dl.google.com/gwt/DTD/xhtml.ent\">\n" +
                             "<ui:UiBinder \n" +
                             "             xmlns:ui='urn:ui:com.google.gwt.uibinder'>\n" +
                             content + '\n' +
                             "</ui:UiBinder>";

        assertEquals(expectedXML, actualXML);

        verify(style).build();
    }

    @Test
    public void complexXMLShouldBeCreated() throws Exception {
        String fieldContent = "field";
        String styleContent = "style";
        String widgetContent = "widget";

        GField field = createField(fieldContent);
        GStyle style = createStyle(styleContent);
        GWidget<GWidget> widget = createWidget(widgetContent);

        String actualXML = builder.withField(field)
                                  .withStyle(style)
                                  .setWidget(widget)
                                  .build();

        String expectedXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                             "<!DOCTYPE ui:UiBinder SYSTEM \"http://dl.google.com/gwt/DTD/xhtml.ent\">\n" +
                             "<ui:UiBinder \n" +
                             "             xmlns:ui='urn:ui:com.google.gwt.uibinder'>\n" +
                             fieldContent + '\n' +
                             styleContent + '\n' +
                             widgetContent + '\n' +
                             "</ui:UiBinder>";

        assertEquals(expectedXML, actualXML);

        verify(field).build();
        verify(style).build();
        verify(widget).build();
    }

    @Test
    public void realXMLShouldBeCreated() throws Exception {
        String threeOffsets = getOffset(3);
        String fourOffsets = getOffset(4);

        GStyle style = new GStyleImpl().withStyle("name", "float: right;   \n   margin: 6px;    margin-right: 5px;")
                                       .withStyle("name2", "  float: right;   \n   margin: 6px;    margin-right: 5px;")
                                       .withStyle("name3", "  float: right;\n   margin: 6px;       margin-right: 5px;");

        GDockLayoutPanel widget = new GDockLayoutPanelImpl()
                .withPrefix("g")
                .withNorth(10, new GLabelImpl().withPrefix("g").withText("text"))
                .withSouth(10, new GTextBoxImpl().withPrefix("g").withText("text"))
                .withWidget(new GFlowPanelImpl().withPrefix("g")
                                                .withWidget(new GLabelImpl().withPrefix("g").withText("text"))
                           );

        String actualXML = builder.withField(new GFieldImpl().withName("name1").withType(String.class))
                                  .withField(new GFieldImpl().withName("name2").withType(String.class))
                                  .withStyle(style)
                                  .setWidget(widget)
                                  .build();

        String expectedXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                             "<!DOCTYPE ui:UiBinder SYSTEM \"http://dl.google.com/gwt/DTD/xhtml.ent\">\n" +
                             "<ui:UiBinder \n" +
                             "             xmlns:ui='urn:ui:com.google.gwt.uibinder'>\n" +
                             OFFSET + "<ui:with field='name1' type='java.lang.String'/>\n" +
                             OFFSET + "<ui:with field='name2' type='java.lang.String'/>\n" +
                             OFFSET + "<ui:style>\n" +
                             TWO_OFFSETS + ".name3{\n" +
                             threeOffsets + "float: right;\n" +
                             threeOffsets + "margin: 6px;\n" +
                             threeOffsets + "margin-right: 5px;\n" +
                             TWO_OFFSETS + "}\n" +
                             TWO_OFFSETS + ".name{\n" +
                             threeOffsets + "float: right;\n" +
                             threeOffsets + "margin: 6px;\n" +
                             threeOffsets + "margin-right: 5px;\n" +
                             TWO_OFFSETS + "}\n" +
                             TWO_OFFSETS + ".name2{\n" +
                             threeOffsets + "float: right;\n" +
                             threeOffsets + "margin: 6px;\n" +
                             threeOffsets + "margin-right: 5px;\n" +
                             TWO_OFFSETS + "}\n" +
                             OFFSET + "</ui:style>\n" +
                             OFFSET + "<g:DockLayoutPanel>\n" +
                             TWO_OFFSETS + "<g:north size=\"10.00\">\n" +
                             threeOffsets + "<g:Label text=\"text\"/>\n" +
                             TWO_OFFSETS + "</g:north>\n" +
                             TWO_OFFSETS + "<g:south size=\"10.00\">\n" +
                             threeOffsets + "<g:TextBox text=\"text\"/>\n" +
                             TWO_OFFSETS + "</g:south>\n" +
                             TWO_OFFSETS + "<g:center>\n" +
                             threeOffsets + "<g:FlowPanel>\n" +
                             fourOffsets + "<g:Label text=\"text\"/>\n" +
                             threeOffsets + "</g:FlowPanel>\n" +
                             TWO_OFFSETS + "</g:center>\n" +
                             OFFSET + "</g:DockLayoutPanel>\n" +
                             "</ui:UiBinder>";

        assertEquals(expectedXML, actualXML);
    }

}