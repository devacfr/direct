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

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Maps;

/**
 * Class Form Response specific for JSON response.
 *
 */
public class JSONFormResponse extends DefaultResourceResponse implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Object data;

    /**
     * message indicating a global error.
     */
    private String message = null;

    /**
     * Field errors.
     */
    private Map<String, String> errors;

    /**
     * Adds error message for a specific field of form.
     * 
     * @param errorMessage
     *            error message of a field
     */
    public void addFieldError(@Nonnull final FieldMessage errorMessage) {
        getErrors().put(errorMessage.getId(), errorMessage.getMsg());
    }

    /**
     * Adds error message for a specific field of form.
     * 
     * @param name
     *            the field name of form in error
     * @param msg
     *            the error message
     */
    public void addFieldError(@Nonnull final String name, @Nonnull final String msg) {
        getErrors().put(name, msg);
    }

    /**
     * Gets all fields in error.
     *
     * @return Returns {@link Map} containing all fields in error.
     */
    public Map<String, String> getErrors() {
        if (this.errors == null) {
            this.errors = Maps.newHashMap();
        }

        return this.errors;
    }

    public void removeFieldError(@Nonnull final FieldMessage errorMessage) {
        getErrors().remove(errorMessage.getId());
    }

    /**
     *
     * @return
     */
    public boolean isSuccess() {
        return this.errors == null && this.errors.isEmpty() && StringUtils.isNotEmpty(message);
    }

    /**
     *
     * @return
     */
    @Nullable
    public Object getData() {
        return data;
    }

    /**
     *
     * @param data
     */
    public void setData(@Nullable final Object data) {
        this.data = data;
    }

    /**
     *
     * @param message
     */
    public void setMessage(@Nullable final String message) {
        this.message = message;
    }

    /**
     *
     * @return
     */
    @Nullable
    public String getMessage() {
        return message;
    }

}