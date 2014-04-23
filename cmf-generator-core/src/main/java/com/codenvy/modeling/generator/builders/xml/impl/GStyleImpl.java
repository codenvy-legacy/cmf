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

package com.codenvy.modeling.generator.builders.xml.impl;

import com.codenvy.modeling.generator.builders.xml.api.GStyle;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

import static com.codenvy.modeling.generator.builders.xml.api.UIXmlBuilder.OFFSET;
import static java.util.Map.Entry;

/**
 * The implementation of {@link GStyle}.
 *
 * @author Andrey Plotnikov
 */
public class GStyleImpl implements GStyle {

    private static final String STYLES_FORMAT = OFFSET + "<ui:style>\n" +
                                                "%s" +
                                                OFFSET + "</ui:style>";
    private static final String STYLE_FORMAT  = OFFSET + OFFSET + ".%s{\n" +
                                                "%s" +
                                                OFFSET + OFFSET + "}\n";

    private Map<String, String> styles;

    @Inject
    public GStyleImpl() {
        clean();
    }

    /** Clean builder configuration */
    private void clean() {
        styles = new HashMap<>();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public GStyle withStyle(@Nonnull String name, @Nonnull String style) {
        styles.put(name, style);
        return this;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String build() throws IllegalStateException {
        if (styles.isEmpty()) {
            return "";
        }

        StringBuilder styles = new StringBuilder();
        for (Entry<String, String> style : this.styles.entrySet()) {
            styles.append(String.format(STYLE_FORMAT, style.getKey(), format(style.getValue())));
        }

        clean();

        return String.format(String.format(STYLES_FORMAT, styles));
    }

    /**
     * Format style content to more readable format.
     *
     * @param content
     *         content that need to be formatted
     * @return style content in more readable format
     */
    @Nonnull
    private String format(@Nonnull String content) {
        StringBuilder result = new StringBuilder();

        String[] styles = content.split(";");
        for (String style : styles) {
            String trimContent = style.trim();
            result.append(OFFSET).append(OFFSET).append(OFFSET).append(trimContent).append(';').append('\n');
        }

        return result.toString();
    }

}