package org.cfr.direct.handler.processor.form;

import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.cfr.commons.util.Assert;

import com.softwarementors.extjs.djn.router.processor.standard.StandardRequestData;

public class FormPostRequestData extends StandardRequestData {

    public static final String ACTION_ELEMENT = "extAction";

    public static final String METHOD_ELEMENT = "extMethod";

    public static final String TID_ELEMENT = "extTID";

    public static final String TYPE_ELEMENT = "extType";

    public static final String UPLOAD_ELEMENT = "extUpload";

    // Make transient so that it is not serialized by our json processor
    transient private boolean isUpload;

    transient private Form formParameters;

    transient private Map<String, FileItem> fileFields;

    public FormPostRequestData(String type, String action, String method, Long tid, boolean isUpload, Form parameters,
            Map<String, FileItem> fileFields) {
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

    public boolean isUpload() {
        return this.isUpload;
    }

    public Form getFormParameters() {
        return this.formParameters;
    }

    public Map<String, FileItem> getFileFields() {
        return this.fileFields;
    }
}