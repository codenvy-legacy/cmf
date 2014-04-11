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

package com.codenvy.modeling.adapter.metamodel;

import com.codenvy.modeling.adapter.Adapter;
import com.codenvy.modeling.configuration.metamodel.MetaModel;
import com.codenvy.modeling.configuration.parser.metamodel.MetaModelConfigurationAdapterListener;
import com.codenvy.modeling.configuration.parser.metamodel.generated.MetaModelLexer;
import com.codenvy.modeling.configuration.parser.metamodel.generated.MetaModelParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Dmitry Kuleshov
 */
public class MetaModelConfigurationAdapter implements Adapter<MetaModel> {

    private InputStream inputStream;

    public MetaModelConfigurationAdapter(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public MetaModel getConfiguration() throws IOException {

        ANTLRInputStream antlrInputStream = new ANTLRInputStream(inputStream);
        MetaModelLexer lexer = new MetaModelLexer(antlrInputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        MetaModelParser parser = new MetaModelParser(tokenStream);
        MetaModelConfigurationAdapterListener metaModelConfigurationAdapterListener = new MetaModelConfigurationAdapterListener();
        (new ParseTreeWalker()).walk(metaModelConfigurationAdapterListener, parser.metaModel());

        return metaModelConfigurationAdapterListener.getMetaModel();
    }
}
