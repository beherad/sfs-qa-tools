package com.sfs.global.qa.api.framework.request;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Method;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.output.WriterOutputStream;

import com.sfs.global.qa.api.framework.response.RestResponse;
import com.sfs.global.qa.core.test.TestContext;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Map;

public class RestRequestSpecification extends TestContext {

    private RequestSpecification requestSpecification;
    private StringWriter requestWriter;
    private StringWriter responseWriter;

    public RestRequestSpecification() {
        this.requestSpecification = RestAssured.given();
    }

    public RestRequestSpecification testCaseReport() {
        requestWriter = new StringWriter();
        responseWriter = new StringWriter();

        PrintStream requestPrintStream = new PrintStream(new WriterOutputStream(requestWriter), true);
        PrintStream responsePrintStream = new PrintStream(new WriterOutputStream(responseWriter), true);
        requestSpecification = requestSpecification
                .filter(new RequestLoggingFilter(requestPrintStream))
                .filter(new ResponseLoggingFilter(responsePrintStream))
                .when();
        return this;
    }

    public RestRequestSpecification spec(RestRequestSpecification spec) {
        this.requestSpecification = this.requestSpecification.spec(spec.requestSpecification);
        this.responseWriter = spec.responseWriter;
        this.requestWriter = spec.requestWriter;
        return this;
    }

    public RestRequestSpecification baseUri(String baseUri) {
        requestSpecification.baseUri(baseUri);
        return this;
    }

    public RestRequestSpecification contentType(ContentType contentType) {
        requestSpecification.contentType(contentType);
        return this;
    }

    public RestRequestSpecification contentType(String contentType) {
        requestSpecification.contentType(contentType);
        return this;
    }

    public RestRequestSpecification body(String body) {
        requestSpecification.body(body);
        return this;
    }

    public RestRequestSpecification body(InputStream body) {
        requestSpecification.body(body);
        return this;
    }

    public RestRequestSpecification body(Object body) {
        requestSpecification.body(body);
        return this;
    }

    public RestRequestSpecification body(File body) {
        requestSpecification.body(body);
        return this;
    }

    public RestRequestSpecification header(Header header) {
        requestSpecification.header(header);
        return this;
    }

    public RestRequestSpecification header(String header, String value) {
        requestSpecification.header(header, value);
        return this;
    }

    public RestRequestSpecification headers(Map<String, ?> headers) {
        requestSpecification.headers(headers);
        return this;
    }

    public RestRequestSpecification oauth2(String token) {
        requestSpecification.auth().oauth2(token);
        return this;
    }

    public RestRequestSpecification basicAuth(String user, String pass) {
        requestSpecification.auth().preemptive().basic(user, pass);
        return this;
    }

    public RestRequestSpecification basePath(String basePath) {
        requestSpecification.basePath(basePath);
        return this;
    }

    public RestRequestSpecification param(String name, String value) {
        requestSpecification.param(name, value);
        return this;
    }

    public RestRequestSpecification params(Map<String, ?> params) {
        requestSpecification.params(params);
        return this;
    }

    public RestRequestSpecification pathParam(String name, String value) {
        requestSpecification.pathParam(name, value);
        return this;
    }

    public RestRequestSpecification pathParam(Map<String, ?> pathParams) {
        requestSpecification.pathParams(pathParams);
        return this;
    }

    public RestRequestSpecification queryParam(String name, Object... values) {
        requestSpecification.queryParam(name, values);
        return this;
    }

    public RestRequestSpecification queryParam(String name, Collection<?> values) {
        requestSpecification.queryParam(name, values);
        return this;
    }

    public RestRequestSpecification queryParams(Map<String, ?> params) {
        requestSpecification.queryParams(params);
        return this;
    }

    public RestRequestSpecification multiPart(String controlName, File file, String mimeType) {
        requestSpecification.multiPart(controlName, file, mimeType);
        return this;
    }

    public RestRequestSpecification multiPart(String controlName, File file) {
        requestSpecification.multiPart(controlName, file);
        return this;
    }
    
    public RestRequestSpecification multiPart(String controlName, Object obj) {
        requestSpecification.multiPart(controlName, obj);
        return this;
    }

    public RestRequestSpecification when() {
        return this;
    }

    public RestRequestSpecification config(RestAssuredConfig config) {
        requestSpecification.config(config);
        return this;
    }

    private RestResponse sendRequest(Method method) {
        ValidatableResponse response = requestSpecification
                .request(method)
                .then();

        logHtmlCaptureRequest(method, requestWriter);
        logHtmlCaptureResponse(method, responseWriter);
        return new RestResponse(response.extract().response());
    }

    private RestResponse sendRequest(Method method, String path) {
        ValidatableResponse response = requestSpecification
                .basePath(path)
                .request(method)
                .then();

        logHtmlCaptureRequest(method, requestWriter);
        logHtmlCaptureResponse(method, responseWriter);
        return new RestResponse(response.extract().response());
    }

    public RestResponse post() {
        return sendRequest(Method.POST);
    }

    public RestResponse post(String path) {
        return sendRequest(Method.POST, path);
    }

    public RestResponse get() {
        return sendRequest(Method.GET);
    }

    public RestResponse get(String path) {
        return sendRequest(Method.GET, path);
    }

    public RestResponse put() {
        return sendRequest(Method.PUT);
    }

    public RestResponse put(String path) {
        return sendRequest(Method.PUT, path);
    }

    public RestResponse patch() {
        return sendRequest(Method.PATCH);
    }

    public RestResponse patch(String path) {
        return sendRequest(Method.PATCH, path);
    }

    public RestResponse delete() {
        return sendRequest(Method.DELETE);
    }

    public RestResponse delete(String path) {
        return sendRequest(Method.DELETE, path);
    }

    public void logHtmlCaptureRequest(Method method, StringWriter requestWriter) {
        String tmpRequest = requestWriter.toString();
        String uri = tmpRequest.substring(tmpRequest.indexOf("URI:") + 5, tmpRequest.indexOf("Proxy"));
        logHtmlCapture(method, requestWriter, "Request " + uri);
    }

    public void logHtmlCaptureResponse(Method method, StringWriter responseWriter) {
        String code = responseWriter.toString().substring(9, 12);
        logHtmlCapture(method, responseWriter, "Response " + code);
    }

    private void logHtmlCapture(Method method, StringWriter writer, String type) {
        String htmlCode = "<html>\n"
                .concat("\t<textarea rows=\"75\" cols=\"200\">\n")
                .concat(writer.toString())
                .concat("\n")
                .concat("\t</textarea>\n")
                .concat("</html>");

        String message = method.name()
                .concat(" ")
                .concat(type);

        log().htmlCode(message, htmlCode);
        writer.getBuffer().setLength(0);
    }
}
