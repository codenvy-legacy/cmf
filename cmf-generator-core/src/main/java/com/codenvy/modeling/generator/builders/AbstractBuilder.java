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

package com.codenvy.modeling.generator.builders;

import com.codenvy.modeling.configuration.metamodel.diagram.Component;
import com.codenvy.modeling.configuration.metamodel.diagram.Element;
import com.codenvy.modeling.configuration.metamodel.diagram.Property;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Andrey Plotnikov
 */
public abstract class AbstractBuilder<T extends AbstractBuilder> {

    protected String              path;
    protected T                   builder;
    protected boolean             needRemoveTemplate;
    protected boolean             needRemoveTemplateParentFolder;
    protected Map<String, String> replaceElements;
    protected Path                source;
    protected Path                target;

    protected AbstractBuilder() {
        needRemoveTemplate = true;
        needRemoveTemplateParentFolder = false;
        replaceElements = new LinkedHashMap<>();
    }

    @Nonnull
    public T path(@Nonnull String path) {
        this.path = path;
        return builder;
    }

    @Nonnull
    public T needRemoveTemplate(boolean needRemoveTemplate) {
        this.needRemoveTemplate = needRemoveTemplate;
        return builder;
    }

    @Nonnull
    public T needRemoveTemplateParentFolder(boolean needRemoveTemplateParentFolder) {
        this.needRemoveTemplateParentFolder = needRemoveTemplateParentFolder;
        return builder;
    }

    @Nonnull
    protected String changeFirstSymbolToLowCase(@Nonnull String name) {
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    @Nonnull
    protected String convertPathToPackageName(@Nonnull String packageName) {
        return packageName.replace('.', '/');
    }

    protected void createFile(@Nonnull Path sourcePath, @Nonnull Path targetPath, @Nonnull Map<String, String> replaceArguments)
            throws IOException {
        String content = ContentReplacer.replace(new String(Files.readAllBytes(sourcePath)), replaceArguments);

        Files.createDirectories(targetPath.getParent());

        Files.write(targetPath, content.getBytes());
    }

    @Nonnull
    protected Class convertPropertyTypeToJavaClass(@Nonnull Property property) {
        switch (property.getType()) {
            case INTEGER:
                return Integer.class;

            case FLOAT:
                return Double.class;

            case BOOLEAN:
                return Boolean.class;

            case STRING:
            default:
                return String.class;
        }
    }

    @Nullable
    protected Element findRootElement(@Nonnull Set<Element> elements) {
        Set<String> subElements = new HashSet<>();

        for (Element element : elements) {
            for (Component component : element.getComponents()) {
                subElements.add(component.getName());
            }
        }

        for (Element element : elements) {
            if (!subElements.contains(element.getName())) {
                return element;
            }
        }

        return null;
    }

    public void build() throws IOException {
        createFile(source, target, replaceElements);

        removeTemplate(source);
        removeTemplateParentFolder(source.getParent());
    }

    private void removeTemplate(@Nonnull Path templatePath) throws IOException {
        if (needRemoveTemplate) {
            Files.delete(templatePath);
        }
    }

    private void removeTemplateParentFolder(@Nonnull Path parentPath) throws IOException {
        if (needRemoveTemplateParentFolder) {
            Files.delete(parentPath);
        }
    }

}