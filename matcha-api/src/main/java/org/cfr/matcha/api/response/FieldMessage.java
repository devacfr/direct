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
package org.cfr.matcha.api.response;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlType;

import org.cfr.commons.util.Assert;

/**
 * An item describing the error.
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 *
 */
@XmlType(name = "field")
public class FieldMessage implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1866887314074897584L;

    /**
     * Field id.
     */
    private final String id;

    /**
     * Field msg.
     */
    private final String msg;

    public FieldMessage(@Nonnull final String id, @Nonnull final String msg) {
        super();
        this.id = Assert.hasText(id, "id parameter is required");
        this.msg = Assert.hasText(msg, "msg parameter is required");
    }

    /**
     * Get the id field.
     *
     * @return String
     */
    public String getId() {
        return this.id;
    }

    /**
     * Get the msg field.
     *
     * @return String
     */
    public String getMsg() {
        return this.msg;
    }

}