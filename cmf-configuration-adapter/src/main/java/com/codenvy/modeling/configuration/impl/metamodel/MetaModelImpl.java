/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codenvy.modeling.configuration.impl.metamodel;


import com.codenvy.modeling.configuration.metamodel.DynamicConstraint;
import com.codenvy.modeling.configuration.metamodel.MetaModel;
import com.codenvy.modeling.configuration.metamodel.StaticConstraint;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Dmitry Kuleshov
 */
public class MetaModelImpl implements MetaModel {

    private List<StaticConstraint> staticConstraints = new LinkedList<>();

    private List<DynamicConstraint> dynamicConstraints = new LinkedList<>();

    public void add(StaticConstraint staticConstraint) {
        staticConstraints.add(staticConstraint);
    }

    public void add(DynamicConstraint dynamicConstraint) {
        dynamicConstraints.add(dynamicConstraint);
    }

    @Override
    public List<StaticConstraint> getStaticConstraints() {
        return null;
    }

    @Override
    public List<DynamicConstraint> getDynamicConstraints() {
        return null;
    }

    @Override
    public String toString() {
        return "\nMetaModel{" +
                "staticConstraints=" + staticConstraints +
                ", dynamicConstraints=" + dynamicConstraints +
                '}';
    }
}
