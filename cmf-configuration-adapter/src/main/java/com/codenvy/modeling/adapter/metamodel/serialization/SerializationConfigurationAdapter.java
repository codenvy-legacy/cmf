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

import com.codenvy.modeling.adapter.Adapter;
import com.codenvy.modeling.configuration.metamodel.serialization.SerializationConfiguration;
import com.codenvy.modeling.configuration.parser.metamodel.serialization.SerializationConfigurationAdapterListener;
import com.codenvy.modeling.configuration.parser.metamodel.serialization.SerializationLexer;
import com.codenvy.modeling.configuration.parser.metamodel.serialization.SerializationParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Dmitry Kuleshov
 */
public class SerializationConfigurationAdapter implements Adapter<SerializationConfiguration> {

    private InputStream inputStream;

    public SerializationConfigurationAdapter(@Nonnull InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Nonnull
    @Override
    public SerializationConfiguration getConfiguration() throws IOException {

        ANTLRInputStream antlrInputStream = new ANTLRInputStream(inputStream);
        SerializationLexer lexer = new SerializationLexer(antlrInputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        SerializationParser parser = new SerializationParser(tokenStream);
        SerializationConfigurationAdapterListener serializationConfigurationAdapterListener
                = new SerializationConfigurationAdapterListener();
        (new ParseTreeWalker()).walk(serializationConfigurationAdapterListener, parser.serialization());

        return serializationConfigurationAdapterListener.getSerializationConfiguration();
    }
}
