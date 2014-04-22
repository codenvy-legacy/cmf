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

import com.codenvy.modeling.generator.builders.xml.api.GStyle;
import com.codenvy.modeling.generator.builders.xml.impl.GStyleImpl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Here we're testing {@link GStyle}.
 *
 * @author Andrey Plotnikov
 */
public class GStyleImplTest {

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

        String expectedContent = "    <ui:style>\n" +
                                 "        .name{\n" +
                                 "            float: right;\n" +
                                 "        }\n" +
                                 "    </ui:style>";

        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void complexContentShouldBeGenerated() throws Exception {
        String actualContent = builder
                .withStyle("name", "float: right;    \n   margin: 6px;    margin-right: 5px;")
                .withStyle("name2", "  float: right;    \n   margin: 6px;    margin-right: 5px;")
                .withStyle("name3", "    float: right;\n    margin: 6px;       margin-right: 5px;")
                .build();

        String expectedContent = "    <ui:style>\n" +
                                 "        .name3{\n" +
                                 "            float: right;\n" +
                                 "            margin: 6px;\n" +
                                 "            margin-right: 5px;\n" +
                                 "        }\n" +
                                 "        .name{\n" +
                                 "            float: right;\n" +
                                 "            margin: 6px;\n" +
                                 "            margin-right: 5px;\n" +
                                 "        }\n" +
                                 "        .name2{\n" +
                                 "            float: right;\n" +
                                 "            margin: 6px;\n" +
                                 "            margin-right: 5px;\n" +
                                 "        }\n" +
                                 "    </ui:style>";

        assertEquals(expectedContent, actualContent);
    }

}