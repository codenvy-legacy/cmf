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

import com.codenvy.modeling.generator.builders.xml.api.GStyle;
import com.codenvy.modeling.generator.builders.xml.impl.GStyleImpl;

import org.junit.Before;
import org.junit.Test;

import static com.codenvy.modeling.generator.builders.xml.api.UIXmlBuilder.OFFSET;
import static org.junit.Assert.assertEquals;

/**
 * Here we're testing {@link GStyle}.
 *
 * @author Andrey Plotnikov
 */
public class GStyleImplTest extends AbstractXmlBuilderTest {

    private GStyle builder;

    @Before
    public void setUp() throws Exception {
        builder = new GStyleImpl();
    }

    @Test
    public void emptyContentShouldBeGenerated() throws Exception {
        String actualContent = builder.build();

        String expectedContent = "";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void simpleContentShouldBeGenerated() throws Exception {
        String actualContent = builder.withStyle("name", "float: right;").build();

        String expectedContent = OFFSET + "<ui:style>\n" +
                                 OFFSET + OFFSET + ".name{\n" +
                                 OFFSET + OFFSET + OFFSET + "float: right;\n" +
                                 OFFSET + OFFSET + "}\n" +
                                 OFFSET + "</ui:style>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void complexContentShouldBeGenerated() throws Exception {
        String actualContent = builder
                .withStyle("name", "float: right;    \n   margin: 6px;    margin-right: 5px;")
                .withStyle("name2", "  float: right;    \n   margin: 6px;    margin-right: 5px;")
                .withStyle("name3", "    float: right;\n    margin: 6px;       margin-right: 5px;")
                .withStyle("name4", "   width: 100%; height: 100%;")
                .build();

        String expectedContent = OFFSET + "<ui:style>\n" +
                                 OFFSET + OFFSET + ".name{\n" +
                                 OFFSET + OFFSET + OFFSET + "float: right;\n" +
                                 OFFSET + OFFSET + OFFSET + "margin: 6px;\n" +
                                 OFFSET + OFFSET + OFFSET + "margin-right: 5px;\n" +
                                 OFFSET + OFFSET + "}\n" +
                                 OFFSET + OFFSET + ".name2{\n" +
                                 OFFSET + OFFSET + OFFSET + "float: right;\n" +
                                 OFFSET + OFFSET + OFFSET + "margin: 6px;\n" +
                                 OFFSET + OFFSET + OFFSET + "margin-right: 5px;\n" +
                                 OFFSET + OFFSET + "}\n" +
                                 OFFSET + OFFSET + ".name3{\n" +
                                 OFFSET + OFFSET + OFFSET + "float: right;\n" +
                                 OFFSET + OFFSET + OFFSET + "margin: 6px;\n" +
                                 OFFSET + OFFSET + OFFSET + "margin-right: 5px;\n" +
                                 OFFSET + OFFSET + "}\n" +
                                 OFFSET + OFFSET + ".name4{\n" +
                                 OFFSET + OFFSET + OFFSET + "width: 100%;\n" +
                                 OFFSET + OFFSET + OFFSET + "height: 100%;\n" +
                                 OFFSET + OFFSET + "}\n" +
                                 OFFSET + "</ui:style>";

        assertEquals(expectedContent, actualContent);
    }

}