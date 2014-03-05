package org.cfr.matcha.api.response;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class ErrorResponse.
 *
 */
@XmlRootElement()
public class FormResponse extends DefaultResourceResponse implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private boolean success = true;

    private Object data;

    private String message;

    /**
     * Field errors.
     */
    private Set<FieldMessage> fields;

    /**
     * Method addError.
     *
     * @param errorMessage
     */
    public void addFieldError(FieldMessage errorMessage) {
        getErrors().add(errorMessage);
    }

    public void addFieldError(String id, String msg) {
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
    public void removeFieldError(FieldMessage errorMessage) {
        getErrors().remove(errorMessage);
    }

    /**
     * 
     * @return
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * 
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * 
     * @return
     */
    public Object getData() {
        return data;
    }

    /**
     * 
     * @param data
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 
     * @param message
     */
    public void setMessage(String message) {
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