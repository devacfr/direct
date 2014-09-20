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
package org.cfr.matcha.direct.handler.processor.form;

import java.util.Map;

import javax.annotation.Nonnull;

import org.apache.commons.fileupload.FileItem;
import org.cfr.commons.util.Assert;
import org.cfr.matcha.api.form.Form;
import org.cfr.matcha.direct.handler.processor.StandardRequestData;

/**
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public class FormPostRequestData extends StandardRequestData {

    /**
     *
     */
    public static final String ACTION_ELEMENT = "extAction";

    /**
     *
     */
    public static final String METHOD_ELEMENT = "extMethod";

    /**
     *
     */
    public static final String TID_ELEMENT = "extTID";

    /**
     *
     */
    public static final String TYPE_ELEMENT = "extType";

    /**
     *
     */
    public static final String UPLOAD_ELEMENT = "extUpload";

    /**
     *
     */
    // Make transient so that it is not serialised by our json processor
    transient private boolean isUpload;

    /**
     *
     */
    transient private Form formParameters;

    /**
     *
     */
    transient private Map<String, FileItem> fileFields;

    /**
     *
     * @param type
     * @param action
     * @param method
     * @param tid
     * @param isUpload
     * @param parameters
     * @param fileFields
     */
    public FormPostRequestData(@Nonnull final String type, @Nonnull final String action, @Nonnull final String method,
                               @Nonnull final Long tid, @Nonnull final boolean isUpload, @Nonnull final Form parameters,
                               @Nonnull final Map<String, FileItem> fileFields) {
        super(type, action, method, tid);

        Assert.notNull(parameters);
        Assert.notNull(parameters.getFirst(ACTION_ELEMENT));
        Assert.notNull(parameters.getFirst(METHOD_ELEMENT));
        Assert.notNull(parameters.getFirst(TYPE_ELEMENT));
        Assert.notNull(parameters.getFirst(TID_ELEMENT));
        Assert.notNull(parameters.getFirst(UPLOAD_ELEMENT));

        this.formParameters = new Form(parameters);
        this.isUpload = isUpload;
        this.fileFields = fileFields;
    }

    /**
     *
     * @return
     */
    public boolean isUpload() {
        return this.isUpload;
    }

    /**
     *
     * @return
     */
    public Form getFormParameters() {
        return this.formParameters;
    }

    /**
     *
     * @return
     */
    public Map<String, FileItem> getFileFields() {
        return this.fileFields;
    }
}