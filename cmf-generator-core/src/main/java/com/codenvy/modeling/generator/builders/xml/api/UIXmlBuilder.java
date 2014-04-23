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

package com.codenvy.modeling.generator.builders.xml.api;

import com.codenvy.modeling.generator.builders.Builder;
import com.codenvy.modeling.generator.builders.xml.api.widgets.GWidget;

import javax.annotation.Nonnull;

/**
 * The builder of GWT ui xml. It provides an ability to generate ui xml from given configuration.
 * <p/>
 * The returned result must look like the following content:
 * <pre>
 * {@code
 * <ui:UiBinder xmlns:ui='urn:ui:com.google.gwt.uibinder'
 *              <!--more xmlns-->
 *              xmlns:g='urn:import:com.google.gwt.user.client.ui'>
 *
 *     <ui:with field="res" type="com.codenvy.editor.client.EditorResources"/>
 *     <!--more fields-->
 *
 *    <ui:style>
 *        .background {
 *            background: greenyellow;
 *        }
 *        <!--more styles-->
 *     </ui:style>
 *
 *     <!--some GWT widget-->
 *     <g:ScrollPanel addStyleDependentNames="{res.editorCSS.fullSize}">
 *         <g:FlowPanel ui:field="mainPanel" addStyleDependentNames="{res.editorCSS.fullSize}"/>
 *     </g:ScrollPanel>
 *
 * </ui:UiBinder>
 * }
 * </pre>
 *
 * @author Andrey Plotnikov
 */
public interface UIXmlBuilder extends Builder {

    /**
     * Add a new xmlns into 'UiBinder' tag.
     * <p/>
     * For example:
     * <pre>
     * {@code
     * <ui:UiBinder xmlns:ui='urn:ui:com.google.gwt.uibinder'
     *              <!--added xmlns-->>
     * </ui:UiBinder>
     * }
     * </pre>
     *
     * @param prefix
     *         prefix that this xmlns need to be identified
     * @param importPath
     *         path that need to be added after xmlns prefix
     * @return a instance of builder with given configuration
     */
    @Nonnull
    UIXmlBuilder withXmlns(@Nonnull String prefix, @Nonnull String importPath);

    /**
     * Add a new field into ui xml.
     * <p/>
     * For example:
     * <pre>
     * {@code
     * <ui:with field="res" type="com.codenvy.editor.client.EditorResources"/>
     * }
     * </pre>
     *
     * @param field
     *         field that need to be added
     * @return a instance of builder with given configuration
     */
    @Nonnull
    UIXmlBuilder withField(@Nonnull GField field);

    /**
     * Add a new style into ui xml.
     * <p/>
     * For example:
     * <pre>
     * {@code
     * <ui:style>
     *     <!--added style-->
     * </ui:style>
     * }
     * </pre>
     *
     * @param style
     *         style that need to be added
     * @return a instance of builder with given configuration
     */
    @Nonnull
    UIXmlBuilder withStyle(@Nonnull GStyle style);

    /**
     * Set a new GWT widget into ui xml. It is possible to add only one GWT widget because as usually it is enough for normal working.
     * The widget that was added early will be removed.
     * <p/>
     * For example:
     * <pre>
     * {@code
     * <g:Label ui:field="added widget"/>
     * }
     * </pre>
     *
     * @param widget
     *         widget that need to be added
     * @return a instance of builder with given configuration
     */
    @Nonnull
    UIXmlBuilder setWidget(@Nonnull GWidget<? extends GWidget> widget);

}