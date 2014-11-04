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
import javax.annotation.Nullable;

/**
 *
 * @author devacfr<christophefriederich@mac.com>
 *
 */
public class Filter {

    /**
     *
     */
    private @Nonnull final String property;

    /**
     *
     */

    private @Nullable final String value;

    /**
     *
     * @param property
     * @param value
     */
    public Filter(@Nonnull final String property, @Nullable final String value) {
        this.property = property;
        this.value = value;
    }

    /**
     *
     * @return
     */
    public @Nonnull String getProperty() {
        return property;
    }

    public @Nullable String getValue() {
        return value;
    }

}
