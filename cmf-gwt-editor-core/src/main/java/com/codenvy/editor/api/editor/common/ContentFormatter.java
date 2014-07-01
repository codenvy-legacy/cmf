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

import com.codenvy.ide.collections.Array;
import com.codenvy.ide.util.StringUtils;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

import javax.validation.constraints.NotNull;

/**
 * The utility class that provides an ability to format text to readable string XML format.
 *
 * @author Valeriy Svydenko
 */
public class ContentFormatter {

    private static final String TAB = "    ";

    /**
     * Format XML to the more readable state.
     *
     * @param inputText
     *         node that need to be formatted
     * @return node in xml format with readable state
     */
    @NotNull
    public static String formatXML(@NotNull String inputText) {
        return parseXMLNode(XMLParser.parse(inputText).getDocumentElement(), 0);
    }

    @NotNull
    public static String trimXML(@NotNull String xmlContent) {
        StringBuilder trimXML = new StringBuilder();

        Array<String> rows = StringUtils.split(xmlContent, "\n");
        for (String row : rows.asIterable()) {
            trimXML.append(row.trim());
        }

        return trimXML.toString();
    }

    /**
     * Parse XML node to text format.
     *
     * @param node
     *         node that need to be formatted
     * @param depth
     *         depth of node in xml
     * @return node in text format
     */
    @NotNull
    private static String parseXMLNode(@NotNull Node node, int depth) {
        StringBuilder outputXml = new StringBuilder();

        if (depth > 0) {
            outputXml.append('\n').append(createTab(depth));
        }

        outputXml.append('<').append(node.getNodeName()).append(transformAttributes(node.getAttributes())).append('>');

        outputXml.append(transformChildrenNodes(node, depth));

        if (!hasTextChildNode(node)) {
            outputXml.append('\n').append(createTab(depth));
        }

        outputXml.append("</").append(node.getNodeName()).append('>');

        return outputXml.toString();
    }

    /**
     * Create tab for a tag with given depth.
     *
     * @param depth
     *         how many tabs need to be added
     * @return tab for a tag
     */
    @NotNull
    private static String createTab(int depth) {
        StringBuilder tabs = new StringBuilder();

        for (int i = 0; i < depth; i++) {
            tabs.append(TAB);
        }

        return tabs.toString();
    }

    /**
     * Transform attributes of xml node to string format.
     *
     * @param attributes
     *         attributes that need to be formatted
     * @return node's attributes in text format
     */
    @NotNull
    private static String transformAttributes(@NotNull NamedNodeMap attributes) {
        StringBuilder text = new StringBuilder();

        for (int i = 0; i < attributes.getLength(); i++) {
            Node item = attributes.item(i);
            text.append(' ').append(item.getNodeName()).append("=\"").append(item.getNodeValue()).append('"');
        }

        return text.toString();
    }

    /**
     * Transform children nodes of xml node to text format.
     *
     * @param node
     *         node that contains children which need to be formatted
     * @param depth
     *         depth of parent node in xml
     * @return children nodes in text format
     */
    @NotNull
    private static String transformChildrenNodes(@NotNull Node node, int depth) {
        StringBuilder text = new StringBuilder();
        NodeList nodeList = node.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);

            if (isTextNode(currentNode)) {
                text.append(currentNode.toString());
            } else if (currentNode.hasChildNodes()) {
                text.append(parseXMLNode(currentNode, depth + 1));
            } else {
                text.append('\n').append(createTab(depth + 1)).append(currentNode);
            }
        }

        return text.toString();
    }

    /**
     * Returns whether a node is the text node.
     *
     * @param node
     *         node that need to be checked
     * @return <code>true</code> if a node is the text node, and <code>false</code> otherwise
     */
    private static boolean isTextNode(@NotNull Node node) {
        return node.getNodeName().equals("#text");
    }

    /**
     * Returns whether a node has a text child node.
     *
     * @param node
     *         node that need to be checked
     * @return <code>true</code> if a node has a text child node, and <code>false</code> otherwise
     */
    private static boolean hasTextChildNode(@NotNull Node node) {
        if (node.hasChildNodes()) {
            Node child = node.getChildNodes().item(0);
            return isTextNode(child);
        }

        return false;
    }
}