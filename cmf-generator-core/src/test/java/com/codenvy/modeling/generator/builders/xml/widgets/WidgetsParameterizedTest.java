/*
 * Copyright [2014] Codenvy, S.A.
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
package com.codenvy.modeling.generator.builders.xml.widgets;

import com.codenvy.modeling.generator.builders.xml.AbstractXmlBuilderTest;
import com.codenvy.modeling.generator.builders.xml.api.widgets.GCheckBox;
import com.codenvy.modeling.generator.builders.xml.api.widgets.containers.GFocusPanel;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GButtonImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GCheckBoxImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GHtmlImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GLabelImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GListBoxImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GPushButtonImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GTextAreaImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.GTextBoxImpl;
import com.codenvy.modeling.generator.builders.xml.impl.widgets.containers.GFocusPanelImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Here we're testing {@link GPushButtonImpl}, {@link GButtonImpl}, {@link GHtmlImpl}, {@link GLabelImpl}, {@link GTextAreaImpl},
 * {@link GTextBoxImpl}, {@link GListBoxImpl} using parameters.
 *
 * @author Valeriy Svydenko
 */
@RunWith(Parameterized.class)
public class WidgetsParameterizedTest extends AbstractXmlBuilderTest {
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
    public void simplePushButtonWithOffsetShouldBeCreated() throws Exception {
        GPushButtonImpl builder = new GPushButtonImpl();

        String actualContent = builder.withPrefix("g").withOffset(parameter).build();

        String expectedContent = getOffset(parameter) + "<g:PushButton/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleButtonWithOffsetShouldBeCreated() throws Exception {
        GButtonImpl builder = new GButtonImpl();

        String actualContent = builder.withPrefix("g").withOffset(parameter).build();

        String expectedContent = getOffset(parameter) + "<g:Button/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleHTMLWithOffsetShouldBeCreated() throws Exception {
        GHtmlImpl builder = new GHtmlImpl();

        String actualContent = builder.withPrefix("g").withOffset(parameter).build();

        String expectedContent = getOffset(parameter) + "<g:HTML/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleLabelWithOffsetShouldBeCreated() throws Exception {
        GLabelImpl builder = new GLabelImpl();

        String actualContent = builder.withPrefix("g").withOffset(parameter).build();

        String expectedContent = getOffset(parameter) + "<g:Label/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleTextAreaWithOffsetShouldBeCreated() throws Exception {
        GTextAreaImpl builder = new GTextAreaImpl();

        String actualContent = builder.withPrefix("g").withOffset(parameter).build();

        String expectedContent = getOffset(parameter) + "<g:TextArea/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleTextBoxWithOffsetShouldBeCreated() throws Exception {
        GTextBoxImpl builder = new GTextBoxImpl();

        String actualContent = builder.withPrefix("g").withOffset(parameter).build();

        String expectedContent = getOffset(parameter) + "<g:TextBox/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleListBoxWithOffsetShouldBeCreated() throws Exception {
        GListBoxImpl builder = new GListBoxImpl();

        String actualContent = builder.withPrefix("g").withOffset(parameter).build();

        String expectedContent = getOffset(parameter) + "<g:ListBox/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleCheckBoxWithOffsetShouldBeCreated() throws Exception {
        GCheckBox builder = new GCheckBoxImpl();

        String actualContent = builder.withPrefix("g").withOffset(parameter).build();

        String expectedContent = getOffset(parameter) + "<g:CheckBox/>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleFocusPanelWithOffsetShouldBeCreated() throws Exception {
        GFocusPanel builder = new GFocusPanelImpl();

        String actualContent = builder.withPrefix("g").withOffset(parameter).build();

        String expectedContent = getOffset(parameter) + "<g:FocusPanel>\n" + getOffset(parameter) + "</g:FocusPanel>";

        assertEquals(expectedContent, actualContent);
    }
}