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

import com.codenvy.modeling.configuration.metamodel.diagram.Connection;
import com.codenvy.modeling.configuration.metamodel.diagram.Element;
import com.codenvy.modeling.generator.builders.ResourceNameConstants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.codenvy.modeling.generator.GenerationController.Param.MAVEN_ARTIFACT_ID;
import static com.codenvy.modeling.generator.GenerationController.Param.MAVEN_ARTIFACT_NAME;
import static com.codenvy.modeling.generator.GenerationController.Param.MAVEN_GROUP_ID;
import static com.codenvy.modeling.generator.GenerationController.Param.TARGET_PATH;
import static com.codenvy.modeling.generator.builders.PathConstants.MAIN_SOURCE_PATH;
import static com.codenvy.modeling.generator.builders.PathConstants.WEBAPP_FOLDER;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anySet;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
@RunWith(MockitoJUnitRunner.class)
public class SourceCodeGeneratorTest extends AbstractBuilderTest {
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        generateSources();
    }

    @Test
    public void editorHtmlShouldBeCreated() throws IOException {
        String targetPath = properties.getProperty(TARGET_PATH.name());

        assertContent("/webapp/EditorHtml", targetPath, MAIN_SOURCE_PATH, WEBAPP_FOLDER,
                      ResourceNameConstants.MAIN_HTML_FILE_FULL_NAME);
    }

    @Test
    public void webXmlShouldBeCreated() throws IOException {
        String targetPath = properties.getProperty(TARGET_PATH.name());

        assertContent("/webapp/webXml", targetPath, MAIN_SOURCE_PATH, WEBAPP_FOLDER, "/WEB-INF/web.xml");
    }

    @Test
    public void pomShouldBeModified() throws Exception {
        Path pomPath = Paths.get(temporaryFolder.getRoot().getAbsolutePath() + "/testProject", ResourceNameConstants.POM_FILE_FULL_NAME);

        String pomContent = new String(Files.readAllBytes(pomPath));

        assertTrue(pomContent.contains(properties.getProperty(MAVEN_ARTIFACT_ID.name())));
        assertTrue(pomContent.contains(properties.getProperty(MAVEN_GROUP_ID.name())));
        assertTrue(pomContent.contains(properties.getProperty(MAVEN_ARTIFACT_NAME.name())));
    }

    @Test
    public void mainHTMLFileShouldBeDefined() throws IOException {
        String editorName = properties.getProperty(GenerationController.Param.EDITOR_NAME.name());

        Path mainHtmlFilePath =
                Paths.get(temporaryFolder.getRoot().getAbsolutePath() + "/testProject", MAIN_SOURCE_PATH, WEBAPP_FOLDER,
                          ResourceNameConstants.MAIN_HTML_FILE_FULL_NAME);

        String content = new String(Files.readAllBytes(mainHtmlFilePath));

        assertTrue(content.contains(editorName));
    }

    @Test
    public void workspacePresenterShouldBeGenerated() throws Exception {
        verify(workspacePresenterBuilder).path(eq(targetPath));
        verify(workspacePresenterBuilder).connections(eq(connections));
        verify(workspacePresenterBuilder).elements(eq(elements));
        verify(workspacePresenterBuilder).mainPackage(MY_PACKAGE);
        verify(workspacePresenterBuilder).build();
    }

    @Test
    public void workspaceViewShouldBeGenerated() throws Exception {
        verify(workspaceViewBuilder).path(eq(targetPath));
        verify(workspaceViewBuilder).connections(eq(connections));
        verify(workspaceViewBuilder).elements(eq(elements));
        verify(workspaceViewBuilder).mainPackage(eq(MY_PACKAGE));
        verify(workspaceViewBuilder).build();
    }

    @Test
    public void workspaceViewImplShouldBeGenerated() throws Exception {
        verify(workspaceViewImplBuilder).path(eq(targetPath));
        verify(workspaceViewImplBuilder).connections(eq(connections));
        verify(workspaceViewImplBuilder).elements(eq(elements));
        verify(workspaceViewImplBuilder).mainPackage(eq(MY_PACKAGE));
        verify(workspaceViewImplBuilder).needRemoveTemplateParentFolder(eq(true));
        verify(workspaceViewImplBuilder).build();
    }

    @Test
    public void toolbarPresenterShouldBeGenerated() throws Exception {
        verify(toolbarPresenterBuilder).path(eq(targetPath));
        verify(toolbarPresenterBuilder).connections(eq(connections));
        verify(toolbarPresenterBuilder).elements(eq(elements));
        verify(toolbarPresenterBuilder).mainPackage(eq(MY_PACKAGE));
        verify(toolbarPresenterBuilder).build();
    }

    @Test
    public void toolbarViewShouldBeGenerated() throws Exception {
        verify(toolbarViewBuilder).path(eq(targetPath));
        verify(toolbarViewBuilder).connections(eq(connections));
        verify(toolbarViewBuilder).elements(eq(elements));
        verify(toolbarViewBuilder).mainPackage(eq(MY_PACKAGE));
        verify(toolbarViewBuilder).build();
    }

    @Test
    public void toolbarViewImplShouldBeGenerated() throws Exception {
        verify(toolbarViewImplBuilder).path(eq(targetPath));
        verify(toolbarViewImplBuilder).connections(eq(connections));
        verify(toolbarViewImplBuilder).elements(eq(elements));
        verify(toolbarViewImplBuilder).mainPackage(eq(MY_PACKAGE));
        verify(toolbarViewImplBuilder).needRemoveTemplateParentFolder(eq(true));
        verify(toolbarViewImplBuilder).build();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void propertiesPanelPresenterShouldBeGenerated() throws Exception {
        verify(propertiesPanelPresenterBuilderProvider, atLeast(2)).get();

        verify(propertiesPanelPresenterBuilder).needRemoveTemplate(eq(false));
        verify(propertiesPanelPresenterBuilder).needRemoveTemplate(eq(true));
        verify(propertiesPanelPresenterBuilder, times(2)).path(eq(targetPath));
        verify(propertiesPanelPresenterBuilder, times(2)).properties(anySet());
        verify(propertiesPanelPresenterBuilder, times(2)).element((Element)anyObject());
        verify(propertiesPanelPresenterBuilder, times(2)).mainPackage(eq(MY_PACKAGE));
        verify(propertiesPanelPresenterBuilder, times(2)).build();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void propertiesPanelViewShouldBeGenerated() throws Exception {
        verify(propertiesPanelViewBuilderProvider, times(2)).get();

        verify(propertiesPanelViewBuilder).needRemoveTemplate(eq(false));
        verify(propertiesPanelViewBuilder).needRemoveTemplate(eq(true));
        verify(propertiesPanelViewBuilder, times(2)).path(eq(targetPath));
        verify(propertiesPanelViewBuilder, times(2)).properties(anySet());
        verify(propertiesPanelViewBuilder, times(2)).element((Element)anyObject());
        verify(propertiesPanelViewBuilder, times(2)).mainPackage(eq(MY_PACKAGE));
        verify(propertiesPanelViewBuilder, times(2)).build();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void propertiesPanelViewImplShouldBeGenerated() throws Exception {
        verify(propertiesPanelViewImplBuilderProvider, atLeast(2)).get();

        verify(propertiesPanelViewImplBuilder).needRemoveTemplate(eq(false));
        verify(propertiesPanelViewImplBuilder).needRemoveTemplate(eq(true));
        verify(propertiesPanelViewImplBuilder, times(2)).path(eq(targetPath));
        verify(propertiesPanelViewImplBuilder, times(2)).properties(anySet());
        verify(propertiesPanelViewImplBuilder, times(2)).element((Element)anyObject());
        verify(propertiesPanelViewImplBuilder, times(2)).mainPackage(eq(MY_PACKAGE));
        verify(propertiesPanelViewImplBuilder, times(2)).build();
    }

    @Test
    public void elementShouldBeGenerated() throws Exception {
        verify(elementBuilderProvider, times(3)).get();

        verify(elementBuilder, times(2)).needRemoveTemplate(eq(false));
        verify(elementBuilder).needRemoveTemplate(eq(true));
        verify(elementBuilder, times(3)).path(eq(targetPath));
        verify(elementBuilder, times(3)).currentElement((Element)anyObject());
        verify(elementBuilder, times(3)).elements(eq(elements));
        verify(elementBuilder, times(3)).connections(eq(connections));
        verify(elementBuilder, times(3)).mainPackage(eq(MY_PACKAGE));
        verify(elementBuilder, times(3)).build();
    }

    @Test
    public void connectionShouldBeGenerated() throws Exception {
        verify(connectionBuilderProvider).get();

        verify(connectionBuilder).needRemoveTemplate(eq(true));
        verify(connectionBuilder).needRemoveTemplate(eq(true));
        verify(connectionBuilder).path(eq(targetPath));
        verify(connectionBuilder).needRemoveTemplateParentFolder(anyBoolean());
        verify(connectionBuilder).connection((Connection)anyObject());
        verify(connectionBuilder).mainPackage(eq(MY_PACKAGE));
        verify(connectionBuilder).build();
    }

    @Test
    public void editorFactoryShouldBeGenerated() throws Exception {
        verify(editorFactoryBuilder).path(eq(targetPath));
        verify(editorFactoryBuilder).mainPackage(eq(MY_PACKAGE));
        verify(editorFactoryBuilder).build();
    }

    @Test
    public void ginModuleShouldBeGenerated() throws Exception {
        verify(ginModuleBuilder).path(eq(targetPath));
        verify(ginModuleBuilder).mainPackage(eq(MY_PACKAGE));
        verify(ginModuleBuilder).build();
    }

    @Test
    public void injectorShouldBeGenerated() throws Exception {
        verify(injectorBuilder).path(eq(targetPath));
        verify(injectorBuilder).mainPackage(eq(MY_PACKAGE));
        verify(injectorBuilder).needRemoveTemplateParentFolder(eq(true));
        verify(injectorBuilder).build();
    }

    @Test
    public void resourcesShouldBeGenerated() throws Exception {
        verify(resourcesBuilder).properties(eq(properties));
        verify(resourcesBuilder).build();
    }

    @Test
    public void editorEntryPointShouldBeGenerated() throws Exception {
        verify(editorEntryPointBuilder).path(eq(targetPath));
        verify(editorEntryPointBuilder).properties(eq(properties));
        verify(editorFactoryBuilder).build();
    }

    @Test
    public void editorPresenterShouldBeGenerated() throws Exception {
        verify(editorPresenterBuilder).path(eq(targetPath));
        verify(editorPresenterBuilder).properties(eq(properties));
        verify(editorPresenterBuilder).elements(eq(elements));
        verify(editorPresenterBuilder).needRemoveTemplateParentFolder(eq(true));
        verify(editorPresenterBuilder).build();
    }

    @Test
    public void editorResourceShouldBeGenerated() throws Exception {
        verify(editorResourcesBuilder).path(eq(targetPath));
        verify(editorResourcesBuilder).properties(eq(properties));
        verify(editorResourcesBuilder).build();
    }

    @Test
    public void stateBuilderShouldBeGenerated() throws Exception {
        verify(stateBuilder).path(eq(targetPath));
        verify(stateBuilder).properties(eq(properties));
        verify(stateBuilder).elements(eq(elements));
        verify(stateBuilder).connections(eq(connections));
        verify(stateBuilder).build();
    }
}