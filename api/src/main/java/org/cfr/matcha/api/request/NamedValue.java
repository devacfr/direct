/**
 * Copyright 2014 devacfr<christophefriederich@mac.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cfr.matcha.api.request;

import javax.annotation.Nonnull;

import org.cfr.commons.util.Assert;

/**
 * POJO Class used mainly to populate Ext.ComboxBox
 * @author devacfr<christophefriederich@mac.com>
 *
 */
public class NamedValue {

    /**
     *
     */
    private final String value;

    /**
     *
     */
    private final String name;

    /**
     *
     * @param value
     * @param name
     */
    public NamedValue(@Nonnull final String name, @Nonnull final String value) {
        super();
        this.name = Assert.notNull(name);
        this.value = Assert.notNull(value);
    }

    public @Nonnull String getName() {
        return name;
    }

    public @Nonnull String getValue() {
        return value;
    }

}
