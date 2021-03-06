/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codenvy.modeling.adapter.metamodel.diagram;

import com.codenvy.modeling.configuration.DiagramConfigurationAdapter;
import com.codenvy.modeling.configuration.ParseConfigurationException;
import com.codenvy.modeling.configuration.metamodel.diagram.DiagramConfiguration;
import com.codenvy.modeling.configuration.parser.metamodel.diagram.DiagramConfigurationAdapterListener;
import com.codenvy.modeling.configuration.parser.metamodel.diagram.DiagramLexer;
import com.codenvy.modeling.configuration.parser.metamodel.diagram.DiagramParser;
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
 * @author Valeriy Svydenko
 */
public class DiagramConfigurationAdapterImpl implements DiagramConfigurationAdapter {

    private final InputStream                                   inputStream;
    private final Provider<DiagramConfigurationAdapterListener> diagramConfigurationAdapterListenerProvider;

    @Inject
    public DiagramConfigurationAdapterImpl(Provider<DiagramConfigurationAdapterListener> diagramConfigurationAdapterListenerProvider,
                                           @Assisted String configurationPath) throws IOException {
        this.diagramConfigurationAdapterListenerProvider = diagramConfigurationAdapterListenerProvider;
        this.inputStream = Files.newInputStream(Paths.get(configurationPath));
    }

    @Nonnull
    @Override
    public DiagramConfiguration getConfiguration() throws IOException, ParseConfigurationException {
        ANTLRInputStream antlrInputStream = new ANTLRInputStream(inputStream);
        DiagramLexer lexer = new DiagramLexer(antlrInputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        DiagramParser parser = new DiagramParser(tokenStream);
        DiagramConfigurationAdapterListener diagramConfigurationAdapterListener
                = diagramConfigurationAdapterListenerProvider.get();
        (new ParseTreeWalker()).walk(diagramConfigurationAdapterListener, parser.diagram());

        return diagramConfigurationAdapterListener.getDiagramConfiguration();
    }
}
