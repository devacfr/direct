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

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

/**
 * Class ErrorResponse.
 *
 */
@XmlRootElement()
public class XmlFormResponse<T> extends DefaultResourceResponse implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private T data;

    /**
     *
     */
    private String message = null;

    /**
     * Field errors.
     */
    private Set<FieldMessage> fields;

    /**
     * Method addError.
     *
     * @param errorMessage
     */
    public void addFieldError(final FieldMessage errorMessage) {
        getErrors().add(errorMessage);
    }

    public void addFieldError(final String id, final String msg) {
        getErrors().add(new FieldMessage(id, msg));
    }

    /**
     * Method getErrors.
     *
     * @return java.util.List
     */
    public Set<FieldMessage> getErrors() {
        if (this.fields == null) {
            this.fields = new HashSet<FieldMessage>();
        }

        return this.fields;
    }

    /**
     * Method removeError.
     *
     * @param errorMessage
     */
    public void removeFieldError(final FieldMessage errorMessage) {
        getErrors().remove(errorMessage);
    }

    /**
     *
     * @return
     */
    public boolean isSuccess() {
        return this.fields == null && this.fields.isEmpty() && StringUtils.isNotEmpty(message);
    }

    /**
     *
     * @return
     */
    public T getData() {
        return data;
    }

    /**
     *
     * @param data
     */
    public void setData(final T data) {
        this.data = data;
    }

    /**
     *
     * @param message
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     *
     * @return
     */
    public String getMessage() {
        return message;
    }

}