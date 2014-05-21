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
package com.codenvy.editor.api.editor.common;

import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTestWithMockito;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Here we're testing {@link ContentFormatter}.
 *
 * @author Valeriy Svydenko
 */
@GwtModule("com.codenvy.editor.api.EditorCore")
public class ContentFormatterTest extends GwtTestWithMockito {
    private String inputData;
    private String expectedResult;

    private void prepareOnlyOneNode() {
        inputData = "<sequence  name1=\"value1\" name2=\"value2\"></sequence>";

        expectedResult = "<sequence name1=\"value1\" name2=\"value2\">\n</sequence>";
    }

    @Test
    public void testFormatXmlIfDocumentHasOnlyOneNode() {
        prepareOnlyOneNode();
        assertEquals(expectedResult, ContentFormatter.formatXML(inputData));
    }

    private void prepareInheritedNodes() {
        inputData = "<sequence name1=\"value1\" name2=\"value2\"><endpoint name1=\"value1\" " +
                    "name2=\"value2\"/></sequence>";

        expectedResult = "<sequence name1=\"value1\" name2=\"value2\">\n" +
                         "    <endpoint name1=\"value1\" name2=\"value2\"></endpoint>\n" +
                         "</sequence>";
    }

    @Test
    public void testFormatXmlIfDocumentHasInheritedNodes() {
        prepareInheritedNodes();
        assertEquals(expectedResult, ContentFormatter.formatXML(inputData));
    }

    private void prepareInheritedNodes2() {
        inputData = "<sequence name1=\"value1\" name2=\"value2\"><log level=\"SIMPLE\" category=\"INFO\" " +
                    "separator=\"\" description=\"Logger\"/><property name=\"property_name\"><super name=\"name1\"/><A laa=\"laa\"><B " +
                    "up=\"iop\">df</B><C>ddd</C></A></property></sequence>";

        expectedResult = "<sequence name1=\"value1\" name2=\"value2\">\n" +
                         "    <log level=\"SIMPLE\" category=\"INFO\" separator=\"\" description=\"Logger\"></log>\n" +
                         "    <property name=\"property_name\">\n" +
                         "        <super name=\"name1\"></super>\n" +
                         "        <A laa=\"laa\">\n" +
                         "            <B up=\"iop\">df</B>\n" +
                         "            <C>ddd</C>\n" +
                         "        </A>\n" +
                         "    </property>\n" +
                         "</sequence>";
    }

    @Test
    public void testFormatXmlIfDocumentHasInheritedNodes2() {
        prepareInheritedNodes2();
        assertEquals(expectedResult, ContentFormatter.formatXML(inputData));
    }

    private void prepareIfTwoNodes() {
        inputData =
                "<root><sequence name1=\"value1\" name=\"value\" name2=\"NewESB\"></sequence><sequence name12=\"value1\" name22=\"value\"" +
                " " +
                "name32=\"NewESB\"></sequence></root>";

        expectedResult = "<root>\n" +
                         "    <sequence name1=\"value1\" name=\"value\" name2=\"NewESB\"></sequence>\n" +
                         "    <sequence name12=\"value1\" name22=\"value\" name32=\"NewESB\"></sequence>\n" +
                         "</root>";
    }

    @Test
    public void testIfXmlHaveTwoNodes() {
        prepareIfTwoNodes();
        assertEquals(expectedResult, ContentFormatter.formatXML(inputData));
    }
}