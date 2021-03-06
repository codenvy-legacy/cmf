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

package com.codenvy.modeling.generator.builders.xml.api.widgets.containers;

/**
 * The builder for FlowPanel GWT widget.
 * <p/>
 * The returned result must look like the following content:
 * <pre>
 * {@code
 * <g:FlowPanel addStyleNames="{style.emptyBorder}">
 *     <g:Button ui:field="btnRefresh" addStyleNames="{style.alignRight} {style.withoutPadding}"
 *               width="18px" height="16px" debugId="git-showHistory-refresh"/>
 *     <g:Button ui:field="btnProjectChanges" addStyleNames="{style.alignRight} {style.space} {style.withoutPadding}"
 *               width="18px" height="16px" debugId="git-showHistory-projectChanges"/>
 *     <g:Button ui:field="btnResourceChanges" addStyleNames="{style.alignRight} {style.space} {style.withoutPadding}"
 *               width="18px" height="16px" debugId="git-showHistory-resourceChanges"/>
 * </g:FlowPanel>
 * }
 * </pre>
 *
 * @author Andrey Plotnikov
 */
public interface GFlowPanel extends GContainer<GFlowPanel> {

    String FLOW_PANEL_OPEN_TAG_FORMAT  = "<%s:FlowPanel%s>";
    String FLOW_PANEL_CLOSE_TAG_FORMAT = "</%s:FlowPanel>";

}