package com.seetatech.httpserver.data.bean;

/**
 * Created by xjh on 18-9-10.
 */

public class HttpResponse {
    private int code;
    public static final int SUCCESS = 0;//成功
    public static final int PARAM_ERROR = 1;//参数错误
    public static final int PASSWORD_ERROR = 2;//密码错误
    public static final int NOT_LOGIN = 3;//没有登录

    private Object result;

    public HttpResponse() {
    }

    public HttpResponse(int code) {
        this.code = code;
    }

    public HttpResponse(int code, Object result) {
        this.code = code;
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
