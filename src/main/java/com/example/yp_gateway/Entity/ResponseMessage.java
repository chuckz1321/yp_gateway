package com.example.yp_gateway.Entity;

import com.google.gson.JsonObject;

public class ResponseMessage<T> {

    private String httpCode;

    private T responseBody;

    public String getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(String httpCode) {
        this.httpCode = httpCode;
    }

    public T getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(T responseBody) {
        this.responseBody = responseBody;
    }
}
