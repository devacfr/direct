package org.cfr.matcha.api.request;

public class Filter {

    private String property;

    private String value;

    public String getProperty() {
        return property;
    }

    public String getValue() {
        return value;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
