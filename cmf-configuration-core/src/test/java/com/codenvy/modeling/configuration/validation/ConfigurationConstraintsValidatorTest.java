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

package com.codenvy.modeling.configuration.validation;

import com.codenvy.modeling.configuration.editor.Group;
import com.codenvy.modeling.configuration.editor.Item;
import com.codenvy.modeling.configuration.editor.Text;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Dmitry Kuleshov
 */
public class ConfigurationConstraintsValidatorTest {
    @Test
    public void simpleConstraintViolationShouldResultInError() {
        Group group = new Group();
        //breaking constraint
        group.setMargin("");

        Report report = ConfigurationConstraintsValidator.validate(group);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }

    @Test
    public void innerFieldClassConstraintViolationShouldResultInError() {
        Text text = new Text();
        Item item = new Item();

        //breaking constraint
        text.setAlignment(null);

        item.setMargin("Margin");
        item.setAlignment(Item.Alignment.BOTTOM);
        item.setSize(0);
        item.setText(text);

        Report report = ConfigurationConstraintsValidator.validate(item);

        assertTrue(report.hasErrors());
        assertEquals(1, report.getErrors().size());
    }
}