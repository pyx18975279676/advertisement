package com.seetatech.ad.http;

/**
 * http请求的响应
 * Created by xjh on 2017/1/11.
 */
public class HttpResponse {
    private boolean isSuccessful;//是否请求成功
    private int code;//返回码
    private String bodyString;//请求的结果
    private Exception exception;//异常

    public HttpResponse() {
    }

    public HttpResponse(int code) {
        this.code = code;
    }

    public HttpResponse(String bodyString) {
        this.bodyString = bodyString;
    }

    public HttpResponse(Exception exception) {
        this.exception = exception;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getBodyString() {
        return bodyString;
    }

    public void setBodyString(String bodyString) {
        this.bodyString = bodyString;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
