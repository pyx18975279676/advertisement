package com.seetatech.ad.data;

/**
 * Created by hexuren on 18-5-9.
 */

public interface Callback<T> {
    /**
     * 成功的回调
     */
    void onResponse(T response);

    /**
     * 失败的回调
     */
    void onError(Throwable t);
}
