package com.sfs.global.qa.web.framework.data;

public enum WebElementAttributes {
    OUTER_HTML("outerHTML"),
    INNER_HTML("innerHTML"),
    VALUE("value"),
    CHECKED("checked");

    private String value;

    WebElementAttributes(String type) {
        this.value = type;
    }

    public String value() {
        return value;
    }
}
