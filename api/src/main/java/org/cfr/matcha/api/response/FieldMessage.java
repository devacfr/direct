/**
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

import javax.xml.bind.annotation.XmlType;

/**
 * An item describing the error.
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
    private String id;

    /**
     * Field msg.
     */
    private String msg;

    public FieldMessage() {
    }

    public FieldMessage(String id, String msg) {
        super();
        this.id = id;
        this.msg = msg;
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

    /**
     * Set the id field.
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Set the msg field.
     *
     * @param msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

}