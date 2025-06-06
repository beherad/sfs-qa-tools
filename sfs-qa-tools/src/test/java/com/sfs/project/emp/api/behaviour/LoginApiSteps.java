package com.sfs.project.emp.api.behaviour;


import com.sfs.global.qa.api.framework.request.RestRequestSpecification;

import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginApiSteps extends RestRequestSpecification {

    public LoginApiSteps() {
        super();
    }

    public String getdata(String api) {
    	return testCaseReport().baseUri(api).contentType(ContentType.JSON).get("products").printBody();
    }
}
