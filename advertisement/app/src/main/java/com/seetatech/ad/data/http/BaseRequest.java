package com.seetatech.ad.data.http;

import com.alibaba.fastjson.JSON;
import com.seetatech.ad.data.Callback;
import com.seetatech.ad.http.HttpCallback;
import com.seetatech.ad.http.HttpClient;
import com.seetatech.ad.http.HttpResponse;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseRequest<T> {

    /**
     * 泛型类型信息
     */
    private Class<T> clazz = null;

    {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) type;
            Type claz = pType.getActualTypeArguments()[0];
            if (claz instanceof Class) {
                this.clazz = (Class<T>) claz;
            }
        }
    }

    /**
     * 键值对形式请求
     *
     * @param callback
     */
    public void requestMap(final Callback<T> callback) {
        HttpClient.postAsync(getUrl(), getParams(), new HttpCallback() {

            @Override
            public void onResponse(HttpResponse httpResponse) {
                try {
                    if (httpResponse.isSuccessful()) {
                        T response = JSON.parseObject(httpResponse.getBodyString(), clazz);
                        if (response != null) {
                            if (callback != null) callback.onResponse(response);
                        } else {
                            if (callback != null)
                                callback.onError(new Exception("JSON解析异常"));
                        }
                    } else {
                        if (callback != null)
                            callback.onError(new Throwable("网络异常" + httpResponse.getCode()));
                    }
                } catch (Exception e) {
                    if (callback != null) callback.onError(e);
                }
            }

            @Override
            public void onError(Throwable t) {
                if (callback != null) callback.onError(t);
            }
        });
    }

    /**
     * Json形式请求
     *
     * @param callback
     */
    public void requestJson(final Callback<T> callback) {
        HttpClient.postAsync(getUrl(), JSON.toJSONString(this), new HttpCallback() {

            @Override
            public void onResponse(HttpResponse httpResponse) {
                try {
                    if (httpResponse.isSuccessful()) {
                        T response = JSON.parseObject(httpResponse.getBodyString(), clazz);
                        if (response != null) {
                            if (callback != null) callback.onResponse(response);
                        } else {
                            if (callback != null)
                                callback.onError(new Exception("JSON解析异常"));
                        }
                    } else {
                        if (callback != null)
                            callback.onError(new Throwable("网络异常" + httpResponse.getCode()));
                    }
                } catch (Exception e) {
                    if (callback != null) callback.onError(e);
                }
            }

            @Override
            public void onError(Throwable t) {
                if (callback != null) callback.onError(t);
            }
        });
    }

    /**
     * 返回http请求的url
     * @return
     */
    protected abstract String getUrl();

    /**
     * 返回键值对形式的http请求参数
     * 默认是以参数名为键,参数值转为String为值
     * 如果这种形式不符合要求,请重写此方法
     * @return
     */
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        try {
            for(Field field : getClass().getDeclaredFields()) {
                params.put(field.getName(), field.get(this).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }
}
