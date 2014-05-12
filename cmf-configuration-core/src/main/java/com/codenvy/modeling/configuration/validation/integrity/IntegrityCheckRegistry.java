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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Dmitry Kuleshov
 */
public class IntegrityCheckRegistry {
    private static final Logger LOG = LoggerFactory.getLogger(IntegrityCheckRegistry.class);

    private Map<CollectionFqn, IntegrityCheck> contentsChecks = new HashMap<>();

    public void addCollection(CollectionFqn collectionFqn, Collection collection, MustContain.Match match) {
        getIntegrityCheck(collectionFqn).setCollection(collection);
        getIntegrityCheck(collectionFqn).setMatch(match);
    }

    public void addValue(CollectionFqn collectionFqn, String value) {
        getIntegrityCheck(collectionFqn).addItem(value);
    }

    private IntegrityCheck getIntegrityCheck(CollectionFqn collectionFqn) {
        IntegrityCheck integrityCheck = contentsChecks.get(collectionFqn);

        if (integrityCheck == null) {
            integrityCheck = new IntegrityCheck();
            contentsChecks.put(collectionFqn, integrityCheck);
        }
        return integrityCheck;
    }

    public static class Initializer {

        public static void initialize(IntegrityCheckRegistry registry, Object object) {
            try {
                for (Field field : object.getClass().getDeclaredFields()) {
                    processMustBeContainedAnnotation(registry, object, field);
                    processMustContainAnnotation(registry, object, field);
                    processValidAnnotation(registry, object, field);
                }
            } catch (IllegalAccessException e) {
                LOG.error("Error trying to perform integrity check of: " + object.getClass().getName(), e);
            }
        }

        private static void processValidAnnotation(IntegrityCheckRegistry registry, Object object, Field field)
                throws IllegalAccessException {

            if (field.getAnnotation(Valid.class) != null) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }

                Object o = field.get(object);
                if (o instanceof Collection) {
                    for (Object collectionElement : (Collection)o) {
                        Initializer.initialize(registry, collectionElement);
                    }
                }
            }
        }

        private static void processMustBeContainedAnnotation(IntegrityCheckRegistry registry, Object object, Field field)
                throws IllegalAccessException {

            MustBeContained annotation = field.getAnnotation(MustBeContained.class);
            if (annotation != null) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }

                if (!field.getType().equals(String.class)) {
                    throw new IllegalStateException("Wrong type is annotated for integrity validation");
                }

                registry.addValue(CollectionFqn.getInstance(annotation.clazz(), annotation.name()), (String)field.get(object));
            }
        }

        private static void processMustContainAnnotation(IntegrityCheckRegistry registry, Object object, Field field)
                throws IllegalAccessException {

            MustContain annotation = field.getAnnotation(MustContain.class);
            if (annotation != null) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }

                if (!field.getType().isAssignableFrom(Set.class)) {
                    throw new IllegalStateException("Wrong type is annotated for integrity validation");
                }

                registry.addCollection(CollectionFqn.getInstance(object.getClass(), field.getName()),
                                       (Collection)field.get(object),
                                       annotation.match());
            }
        }
    }

    public static class Analyzer {
        public static void analyze(IntegrityCheckRegistry registry, Report report) {

            for (Map.Entry<CollectionFqn, IntegrityCheck> entry : registry.contentsChecks.entrySet()){
                Collection<String> collection = entry.getValue().getCollection();
                Set<String> items = entry.getValue().getItems();
                MustContain.Match match = entry.getValue().getMatch();

                //TODO make more accurate analysis
                if (MustContain.Match.STRICT == match) {
                    if (!collection.equals(items)){
                        report.addError("Integrity check failure: " + entry.getKey().getAsString());
                    }
                } else
                {
                    if (!collection.containsAll(items)){
                        report.addError("Integrity check failure: " + entry.getKey().getAsString());
                    }
                }
            }
        }
    }
}
