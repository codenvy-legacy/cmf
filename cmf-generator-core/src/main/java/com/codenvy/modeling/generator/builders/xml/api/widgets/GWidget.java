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

package com.codenvy.modeling.generator.builders.xml.api.widgets;

import com.codenvy.modeling.generator.builders.Builder;

import javax.annotation.Nonnull;

/**
 * The builder for GWT widget. It contains all general parameters for average GWT widget.
 * <p/>
 * The returned result must look like the following content:
 * <pre>
 * {@code
 * <g:Button ui:field="btnDiffWithWorkTree" addStyleNames="{style.alignRight} {style.space} {style.withoutPadding}"
 *           width="18px" height="16px" debugId="git-showHistory-diffWithWorkTree"/>
 * }
 * </pre>
 *
 * @param <T>
 *         type of builder that implemented this interface
 * @author Andrey Plotnikov
 */
public interface GWidget<T> extends Builder {

    /**
     * Add name property for GWT widget.
     * <p/>
     * For example:
     * * <pre>
     * {@code
     * <g:Button ui:field="added name"/>
     * }
     * </pre>
     *
     * @param name
     *         name that need to be applied
     * @return a instance of builder with given configuration
     */
    @Nonnull
    T withName(@Nonnull String name);

    /**
     * Add title property for GWT widget.
     * <p/>
     * For example:
     * * <pre>
     * {@code
     * <g:Button title="added title"/>
     * }
     * </pre>
     *
     * @param title
     *         title that need to be applied
     * @return a instance of builder with given configuration
     */
    @Nonnull
    T withTitle(@Nonnull String title);

    /**
     * Add prefix for GWT widget.
     * <p/>
     * For example:
     * * <pre>
     * {@code
     * <addedPrefix:Button/>
     * }
     * </pre>
     *
     * @param prefix
     *         prefix that need to be appended
     * @return a instance of builder with given configuration
     */
    @Nonnull
    T withPrefix(@Nonnull String prefix);

    /**
     * Add style property for GWT widget.
     * <p/>
     * For example:
     * * <pre>
     * {@code
     * <g:Button styleName="{addedStyle1} {addedStyle2}"/>
     * }
     * </pre>
     *
     * @param style
     *         style that need to be applied
     * @return a instance of builder with given configuration
     */
    @Nonnull
    T withStyle(@Nonnull String style);

    /**
     * Add additional style property for GWT widget.
     * <p/>
     * For example:
     * * <pre>
     * {@code
     * <g:Button addStyleNames="{addedStyle1} {addedStyle2}"/>
     * }
     * </pre>
     *
     * @param style
     *         style that need to be applied
     * @return a instance of builder with given configuration
     */
    @Nonnull
    T withAddStyle(@Nonnull String style);

    /**
     * Add height property for GWT widget.
     * <p/>
     * For example:
     * * <pre>
     * {@code
     * <g:Button height="added height"/>
     * }
     * </pre>
     *
     * @param height
     *         height that need to be applied
     * @return a instance of builder with given configuration
     */
    @Nonnull
    T withHeight(@Nonnull String height);

    /**
     * Add width property for GWT widget.
     * <p/>
     * For example:
     * * <pre>
     * {@code
     * <g:Button width="added width"/>
     * }
     * </pre>
     *
     * @param width
     *         width that need to be applied
     * @return a instance of builder with given configuration
     */
    @Nonnull
    T withWidth(@Nonnull String width);

    /**
     * Add debugId property for GWT widget.
     * <p/>
     * For example:
     * * <pre>
     * {@code
     * <g:Button debugId="added debug id"/>
     * }
     * </pre>
     *
     * @param debugId
     *         debugId that need to be applied
     * @return a instance of builder with given configuration
     */
    @Nonnull
    T withDebugId(@Nonnull String debugId);

    /**
     * Change visible property to false value. Default value of visible property is true.
     *
     * @return a instance of builder with given configuration.
     */
    @Nonnull
    T setInvisible();

}