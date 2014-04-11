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
package com.codenvy.editor.api.editor.elements;

import javax.annotation.Nonnull;

/**
 * @author Andrey Plotnikov
 */
public class UUID {

    private static final char[] CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    @Nonnull
    public static String get() {
        char[] uuid = new char[36];

        uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';

        for (int i = 0; i < uuid.length; i++) {
            if (uuid[i] == 0) {
                int r = (int)(Math.random() * 16);
                uuid[i] = CHARS[(i == 19) ? (r & 0x3) | 0x8 : r & 0xf];
            }
        }

        return new String(uuid);
    }

}