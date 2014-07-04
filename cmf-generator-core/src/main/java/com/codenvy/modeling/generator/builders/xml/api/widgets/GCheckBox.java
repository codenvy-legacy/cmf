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

import javax.annotation.Nonnull;

/**
 * The builder for CheckBox GWT widget.
 * <p/>
 * The returned result must look like the following content:
 * <pre>
 * {@code
 * <g:CheckBox ui:field="autoAlignment" text="auto-alignment"/>
 * }
 * </pre>
 *
 * @author Andrey Plotnikov
 */
public interface GCheckBox extends GWidget<GCheckBox>, HasEnable<GCheckBox>, HasFocus<GCheckBox>, HasText<GCheckBox> {

    String CHECKBOX_FORMAT = "<%s:CheckBox%s/>";

    String FORM_VALUE_PARAM_NAME = "formValue";

    /**
     * Change formValue property to true value. Default value of formValue property is false.
     *
     * @param value
     *         value that need to be applied
     * @return a instance of builder with given configuration
     */
    @Nonnull
    GCheckBox withFormValue(boolean value);

}