package com.seetatech.ad.http;

/**
 * Created by hexuren on 18-5-9.
 */

public interface HttpCallback {
    /**
     * 成功的回调
     */
    void onResponse(HttpResponse httpResponse);

    /**
     * 失败的回调
     */
    void onError(Throwable t);
}
