package com.sfs.global.qa.api.framework.response;

import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@AllArgsConstructor
public class RestResponse {
    private Response response;

    public String getValueFromKey(String key) {
        return response.path(key).toString();
    }

    public String printBody() {
        return response.prettyPrint();
    }

	public Response getResponse() {
		return response;
	}
}