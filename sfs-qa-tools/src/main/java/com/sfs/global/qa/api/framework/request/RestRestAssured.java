package com.sfs.global.qa.api.framework.request;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;

public class RestRestAssured {

    private RestRestAssured() {
    }

    public static void useRelaxedHTTPSValidation(String protocol) {
        RestAssured.useRelaxedHTTPSValidation(protocol);
    }

    public static void baseUri(String baseUri) {
        RestAssured.baseURI = baseUri;
    }

    public static void reset() {
        RestAssured.reset();
    }

    public static RestAssuredConfig config() {
        return RestAssured.config();
    }

    public static RestRequestSpecification given() {
        return new RestRequestSpecification();
    }

}
