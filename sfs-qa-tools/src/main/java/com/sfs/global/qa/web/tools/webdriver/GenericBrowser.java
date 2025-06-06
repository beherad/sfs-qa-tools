package com.sfs.global.qa.web.tools.webdriver;

public interface GenericBrowser {

    int getImplicitlyWaitTimeout();
    int getPageLoadTimeout();
    int getElementLoadTimeout();
    void setDefaultImplicitWaitTimeout();
}
