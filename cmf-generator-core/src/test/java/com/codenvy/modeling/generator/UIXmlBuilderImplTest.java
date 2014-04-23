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

package com.codenvy.modeling.generator;

import com.codenvy.modeling.generator.builders.xml.api.GField;
import com.codenvy.modeling.generator.builders.xml.api.GStyle;
import com.codenvy.modeling.generator.builders.xml.api.UIXmlBuilder;
import com.codenvy.modeling.generator.builders.xml.api.widgets.GWidget;
import com.codenvy.modeling.generator.builders.xml.impl.UIXmlBuilderImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Here we're testing {@link UIXmlBuilderImpl}.
 *
 * @author Andrey Plotnikov
 */
public class UIXmlBuilderImplTest {

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

    private GField createField(String content) {
        GField field = Mockito.mock(GField.class);
        when(field.build()).thenReturn(content);

        return field;
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

    @SuppressWarnings("unchecked")
    private GWidget<GWidget> createWidget(String content) {
        GWidget<GWidget> widget = mock(GWidget.class);
        when(widget.withOffset(anyInt())).thenReturn(widget);
        when(widget.build()).thenReturn(content);

        return widget;
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

    private GStyle createStyle(String content) {
        GStyle style = mock(GStyle.class);
        when(style.build()).thenReturn(content);

        return style;
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

}