package com.sfs.global.qa.api.framework.assertions;

import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.sfs.global.qa.api.framework.response.RestResponse;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.AbstractAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class ResponseAssert extends AbstractAssert<ResponseAssert, RestResponse> {
    private static final String INFO_MESSAGE_TEMPLATE = "Response value for key '%s' is '%s'";

    protected ResponseAssert(RestResponse response) {
        super(response, ResponseAssert.class);
    }
    

    public static ResponseAssert assertThat(RestResponse actual) {
        return new ResponseAssert(actual);
    }

    public ResponseAssert statusCodeIs(Integer statusCode) {
        int actualStatusCode = actual.getResponse().statusCode();
        if (!statusCode.equals(actualStatusCode)) {
            String msg = String.format("Status code is '%s', but '%s' is expected", actualStatusCode, statusCode);
            log.error(msg);
            failWithMessage(msg);
        }
        log.info("Status code is " + statusCode);
        return this;
    }

    public ResponseAssert jsonKeyExists(String key) {
        if (actual.getResponse().path(key) == null) {
            String msg = String.format("Key '%s' doesn't exist", key);
            log.error(msg);
            failWithMessage(msg);
        }
        log.info("Key '" + key + "' exists");
        return this;
    }

    public ResponseAssert jsonKeyEqualsTo(String key, String value) {
        isNotNull();
        String actualValue = actual.getResponse().path(key);
        if (!value.equals(actualValue)) {
            String msg = String.format("Response value for key '%s' is '%s', but '%s' is expected", key, actualValue, value);
            log.error(msg);
            failWithMessage(msg);
        }
        log.info(String.format(INFO_MESSAGE_TEMPLATE, key, actualValue));
        return this;
    }

    public ResponseAssert jsonKeyEqualsTo(String key, Integer value) {
        isNotNull();
        Integer actualValue = actual.getResponse().path(key);
        if (!value.equals(actualValue)) {
            String msg = String.format("Response value for key '%s' is '%s', but '%s' is expected", key, actualValue, value);
            log.error(msg);
            failWithMessage(msg);
        }
        log.info(String.format(INFO_MESSAGE_TEMPLATE, key, actualValue));
        return this;
    }

    public ResponseAssert jsonKeyContains(String key, String value) {
        isNotNull();
        String actualValue = actual.getResponse().path(key).toString();
        if (!actualValue.contains(value)) {
            String msg = String.format("Response value for key '%s' is '%s', but should contain '%s'", key, actualValue, value);
            log.error(msg);
            failWithMessage(msg);
        }
        log.info(String.format(INFO_MESSAGE_TEMPLATE, key, actualValue));
        return this;
    }

    public ResponseAssert isJsonFormat() {
        try {
            JsonParser.parseString(actual.getResponse().getBody().asString());
        } catch (JsonSyntaxException ex) {
            String msg = "Response body is NOT a Json";
            log.error(msg);
            failWithMessage(msg);
        }
        log.info("Response body is a Json");
        return this;
    }
}
