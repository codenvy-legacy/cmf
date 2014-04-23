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
import com.codenvy.modeling.generator.builders.xml.api.widgets.GWidget;

import org.mockito.Mockito;

import static com.codenvy.modeling.generator.builders.xml.api.UIXmlBuilder.OFFSET;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The abstract test for UI XML builders. It contains general methods for all UI XML builders.
 *
 * @author Andrey Plotnikov
 */
public abstract class AbstractXmlBuilderTest {

    protected String getOffset(int offset) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < offset; i++) {
            result.append(OFFSET);
        }

        return result.toString();
    }

    @SuppressWarnings("unchecked")
    protected GWidget<GWidget> createWidget(String content) {
        GWidget<GWidget> widget = mock(GWidget.class);
        when(widget.withOffset(anyInt())).thenReturn(widget);
        when(widget.build()).thenReturn(content);

        return widget;
    }

    protected GStyle createStyle(String content) {
        GStyle style = mock(GStyle.class);
        when(style.build()).thenReturn(content);

        return style;
    }

    protected GField createField(String content) {
        GField field = Mockito.mock(GField.class);
        when(field.build()).thenReturn(content);

        return field;
    }

}