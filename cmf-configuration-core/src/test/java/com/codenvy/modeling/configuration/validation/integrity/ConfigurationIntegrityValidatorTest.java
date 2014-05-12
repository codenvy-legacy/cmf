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

package com.codenvy.modeling.configuration.validation.integrity;


import com.codenvy.modeling.configuration.validation.Report;

import org.junit.Test;

import javax.validation.Valid;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Dmitry Kuleshov
 */
public class ConfigurationIntegrityValidatorTest {

    @Test
    public void testOne() throws Exception {
        Item itemOne = new Item();
        itemOne.setField("1");

        Item itemTwo = new Item();
        itemTwo.setField("2");

        Aggregation aggregation = new Aggregation();

        aggregation.addItem(itemOne);
        aggregation.addItem(itemTwo);

        aggregation.addString("1");
        aggregation.addString("2");

        Report report = ConfigurationIntegrityValidator.validate(aggregation);

        assertTrue(report.getErrors().isEmpty());
        assertFalse(report.hasErrors());
    }

    @Test
    public void testTwo() throws Exception {
        Item itemOne = new Item();
        itemOne.setField("1");

        Item itemTwo = new Item();
        itemTwo.setField("2");

        Aggregation aggregation = new Aggregation();

        aggregation.addItem(itemOne);
        aggregation.addItem(itemTwo);

        aggregation.addString("2");

        Report report = ConfigurationIntegrityValidator.validate(aggregation);

        assertEquals(1, report.getErrors().size());
        assertTrue(report.hasErrors());
    }


    public class Item {

        @MustBeContained(clazz = Aggregation.class, name = "strings")
        private String field;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }
    }

    public class Aggregation {

        @MustContain
        private Set<String> strings = new LinkedHashSet<>();
        @Valid
        private Set<Item>   items   = new LinkedHashSet<>();

        public void addString(String string) {
            strings.add(string);
        }

        public void addItem(Item item) {
            items.add(item);
        }

        public Set<String> getStrings() {
            return strings;
        }

        public void setStrings(Set<String> strings) {
            this.strings = strings;
        }
    }

}
