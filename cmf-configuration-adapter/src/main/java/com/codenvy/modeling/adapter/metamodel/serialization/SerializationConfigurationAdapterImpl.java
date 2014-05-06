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

package com.codenvy.modeling.adapter.metamodel.serialization;

import com.codenvy.modeling.configuration.SerializationConfigurationAdapter;
import com.codenvy.modeling.configuration.metamodel.serialization.SerializationConfiguration;
import com.codenvy.modeling.configuration.parser.metamodel.serialization.SerializationConfigurationAdapterListener;
import com.codenvy.modeling.configuration.parser.metamodel.serialization.SerializationLexer;
import com.codenvy.modeling.configuration.parser.metamodel.serialization.SerializationParser;
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
public class SerializationConfigurationAdapterImpl implements SerializationConfigurationAdapter {

    private final InputStream                                         inputStream;
    private final Provider<SerializationConfigurationAdapterListener> serializationConfigurationAdapterListenerProvider;

    @Inject
    public SerializationConfigurationAdapterImpl(
            Provider<SerializationConfigurationAdapterListener> serializationConfigurationAdapterListenerProvider,
            @Assisted String configurationPath) throws IOException {
        this.serializationConfigurationAdapterListenerProvider = serializationConfigurationAdapterListenerProvider;
        this.inputStream = Files.newInputStream(Paths.get(configurationPath));
    }

    @Nonnull
    @Override
    public SerializationConfiguration getConfiguration() throws IOException {

        ANTLRInputStream antlrInputStream = new ANTLRInputStream(inputStream);
        SerializationLexer lexer = new SerializationLexer(antlrInputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        SerializationParser parser = new SerializationParser(tokenStream);
        SerializationConfigurationAdapterListener serializationConfigurationAdapterListener
                = serializationConfigurationAdapterListenerProvider.get();
        (new ParseTreeWalker()).walk(serializationConfigurationAdapterListener, parser.serialization());

        return serializationConfigurationAdapterListener.getSerializationConfiguration();
    }
}
