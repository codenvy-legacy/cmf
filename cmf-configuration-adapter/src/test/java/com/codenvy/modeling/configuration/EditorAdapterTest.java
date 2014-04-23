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

package com.codenvy.modeling.configuration;

import com.codenvy.modeling.adapter.editor.EditorConfigurationAdapter;
import org.junit.Test;

import java.io.InputStream;

/**
 * @author Dmitry Kuleshov
 */
public class EditorAdapterTest {

    public static final String EDITOR_GRAMMAR_TEST_I = "/EditorGrammarTest_I";

    @Test
    public void testExampleField() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream(EDITOR_GRAMMAR_TEST_I);
        EditorConfigurationAdapter editorConfigurationAdapter = new EditorConfigurationAdapter(inputStream);

        System.out.println("===============================================================================" + "\n\n");
        System.out.println(editorConfigurationAdapter.getConfiguration().toString());
        System.out.println("\n\n" + "===============================================================================");
    }
}