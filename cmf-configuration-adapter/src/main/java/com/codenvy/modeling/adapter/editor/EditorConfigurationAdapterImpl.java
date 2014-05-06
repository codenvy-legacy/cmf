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

package com.codenvy.modeling.adapter.editor;

import com.codenvy.modeling.configuration.EditorConfigurationAdapter;
import com.codenvy.modeling.configuration.editor.EditorConfiguration;
import com.codenvy.modeling.configuration.parser.editor.EditorConfigurationAdapterListener;
import com.codenvy.modeling.configuration.parser.editor.EditorLexer;
import com.codenvy.modeling.configuration.parser.editor.EditorParser;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Dmitry Kuleshov
 * @author Andrey Plotnikov
 */
public class EditorConfigurationAdapterImpl implements EditorConfigurationAdapter {

    private final InputStream                                  inputStream;
    private final Provider<EditorConfigurationAdapterListener> editorConfigurationAdapterListenerProvider;

    @Inject
    public EditorConfigurationAdapterImpl(Provider<EditorConfigurationAdapterListener> editorConfigurationAdapterListenerProvider,
                                          @Assisted String configurationPath) throws IOException {
        this.editorConfigurationAdapterListenerProvider = editorConfigurationAdapterListenerProvider;
        this.inputStream = Files.newInputStream(Paths.get(configurationPath));
    }

    @Nonnull
    @Override
    public EditorConfiguration getConfiguration() throws IOException {
        ANTLRInputStream antlrInputStream = new ANTLRInputStream(inputStream);
        EditorLexer lexer = new EditorLexer(antlrInputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        EditorParser parser = new EditorParser(tokenStream);
        EditorConfigurationAdapterListener editorConfigurationAdapterListener
                = editorConfigurationAdapterListenerProvider.get();
        (new ParseTreeWalker()).walk(editorConfigurationAdapterListener, parser.editor());

        return editorConfigurationAdapterListener.getEditorConfiguration();
    }
}
