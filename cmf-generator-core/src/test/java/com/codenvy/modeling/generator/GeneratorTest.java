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

package com.codenvy.modeling.generator;

import com.codenvy.modeling.adapter.AdapterFactory;
import com.codenvy.modeling.adapter.metamodel.diagram.DiagramConfigurationAdapter;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Here we're testing {@link Generator}.
 *
 * @author Andrey Plotnikov
 */
@RunWith(MockitoJUnitRunner.class)
public class GeneratorTest {

    @Mock
    private DiagramConfigurationAdapter diagramConfigurationAdapter;
    @Mock
    private AdapterFactory              adapterFactory;
    @InjectMocks
    private Generator                   generator;

    private Map<Generator.Param, String> params;

    @Before
    public void setUp() throws Exception {
        params = new HashMap<>();

        when(adapterFactory.getMetaModelConfAdapter((InputStream)anyObject())).thenReturn(diagramConfigurationAdapter);
    }

    @Test
    public void shouldNotGenerateCodeWhenSourcePathIsAbsent() throws Exception {
        generator.generate(params);

        verify(adapterFactory, never()).getMetaModelConfAdapter((InputStream)anyObject());
    }

    @Test
    @Ignore
    public void shouldGenerateCodeWhenAllParamsAreGiven() throws Exception {
        params.put(Generator.Param.SOURCE_PATH, "/cmf-generator-core/src/test/resources/MetaModel");

        generator.generate(params);

        verify(adapterFactory).getMetaModelConfAdapter((InputStream)anyObject());
    }

}