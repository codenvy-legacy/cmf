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

/**
 * @author Dmitry Kuleshov
 */
public class CollectionFqn {
    private final Class<?> clazz;
    private final String   name;

    private CollectionFqn(Class<?> clazz, String name) {
        this.clazz = clazz;
        this.name = name;
    }

    public static CollectionFqn getInstance(Class<?> clazz, String name) {
        return new CollectionFqn(clazz, name);
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getName() {
        return name;
    }

    public String getAsString() {
        return clazz.getCanonicalName() + '.' + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CollectionFqn)) return false;

        CollectionFqn that = (CollectionFqn)o;

        if (clazz != null ? !clazz.equals(that.clazz) : that.clazz != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = clazz != null ? clazz.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
