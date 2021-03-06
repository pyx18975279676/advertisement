package com.seetatech.ad.http;

import com.alibaba.fastjson.JSON;
import com.seetatech.ad.util.Logger;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * https请求客户端
 * Created by xjh on 2017/1/11.
 */
public class HttpsClient {
    private static final long TIME_OUT = 5;//超时时间
    private static OkHttpClient mClient;
    private static String TAG = "HttpsClient";

    static {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        /** 连接超时时间*/
        okHttpBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        /** 读取超时时间*/
        okHttpBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        /** 写入超时时间*/
        okHttpBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        /** 允许重定向*/
        okHttpBuilder.followRedirects(true);

        okHttpBuilder.hostnameVerifier(new TrustAllHostnameVerifier());
        okHttpBuilder.sslSocketFactory(createSSLSocketFactory());
        mClient = okHttpBuilder.build();
    }

    private HttpsClient() {
    }

    private static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ssfFactory;
    }

    /**
     * 同步get请求
     *
     * @param url
     */
    public static HttpResponse getSync(String url) {
        try {
            Logger.d(TAG, "HttpRequest: " + url);

            Request request = new Request.Builder().url(url).build();

            Response response = mClient.newCall(request).execute();

            if (response.isSuccessful()) {
                HttpResponse httpResponse = new HttpResponse(response.body().string());
                httpResponse.setSuccessful(true);

                Logger.d(TAG, "HttpResponse: " + httpResponse.getBodyString());

                return httpResponse;
            } else {
                return new HttpResponse(response.code());
            }
        } catch (Exception e) {
            return new HttpResponse(e);
        }
    }

    /**
     * 异步get请求
     *
     * @param url
     * @param callback
     */
    public static <T> void getAsync(String url, final HttpCallback callback) {
        try {
            Logger.d(TAG, "HttpRequest: " + url);

            Request request = new Request.Builder().url(url).build();

            mClient.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (callback != null) callback.onError(e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        if (response.isSuccessful()) {
                            HttpResponse httpResponse = new HttpResponse(response.body().string());
                            httpResponse.setSuccessful(true);

                            Logger.d(TAG, "HttpResponse: " + httpResponse.getBodyString());

                            if (callback != null) callback.onResponse(httpResponse);
                        } else {
                            if (callback != null)
                                callback.onResponse(new HttpResponse(response.code()));
                        }
                    } catch (Exception e) {
                        if (callback != null) callback.onError(e);
                    }
                }
            });
        } catch (Exception e) {
            if (callback != null) callback.onError(e);
        }
    }

    /**
     * 同步post请求(参数为键值对)
     *
     * @param url
     * @param params
     */
    public static HttpResponse postSync(String url, Map<String, String> params) {
        try {
            Logger.d(TAG, "HttpRequest: " + JSON.toJSONString(params));

            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.addFormDataPart(entry.getKey(), entry.getValue());
                }
            }

            MultipartBody multipartBody = builder.build();

            Request request = new Request.Builder().url(url).post(multipartBody).build();

            Response response = mClient.newCall(request).execute();
            if (response.isSuccessful()) {
                HttpResponse httpResponse = new HttpResponse(response.body().string());
                httpResponse.setSuccessful(true);

                Logger.d(TAG, "HttpResponse: " + httpResponse.getBodyString());

                return httpResponse;
            } else {
                return new HttpResponse(response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpResponse(e);
        }
    }

    /**
     * 同步post请求（参数为json）
     *
     * @param url
     * @param json
     * @return
     */
    public static HttpResponse postSync(String url, String json) {
        try {
            Logger.d(TAG, "HttpRequest: " + json);

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
            Request request = new Request.Builder().url(url).post(body).build();

            Response response = mClient.newCall(request).execute();
            if (response.isSuccessful()) {
                HttpResponse httpResponse = new HttpResponse(response.body().string());
                httpResponse.setSuccessful(true);

                Logger.d(TAG, "HttpResponse: " + httpResponse.getBodyString());

                return httpResponse;
            } else {
                return new HttpResponse(response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new HttpResponse(e);
        }
    }

    /**
     * 异步post请求(参数为键值对)
     *
     * @param url
     * @param params
     * @param callback
     */
    public static void postAsync(String url, Map<String, String> params, final HttpCallback callback) {
        try {
            Logger.d(TAG, "HttpRequest: " + JSON.toJSONString(params));

            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.addFormDataPart(entry.getKey(), entry.getValue());
                }
            }

            MultipartBody multipartBody = builder.build();

            Request request = new Request.Builder().url(url).post(multipartBody).build();

            mClient.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (callback != null) callback.onError(e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        if (response.isSuccessful()) {
                            HttpResponse httpResponse = new HttpResponse(response.body().string());
                            httpResponse.setSuccessful(true);

                            Logger.d(TAG, "HttpResponse: " + httpResponse.getBodyString());

                            if (callback != null) callback.onResponse(httpResponse);
                        } else {
                            if (callback != null)
                                callback.onResponse(new HttpResponse(response.code()));
                        }
                    } catch (Exception e) {
                        if (callback != null) callback.onError(e);
                    }
                }
            });
        } catch (Exception e) {
            if (callback != null) callback.onError(e);
        }
    }

    /**
     * 异步post请求(参数为json)
     *
     * @param url
     * @param json
     * @param callback
     */
    public static void postAsync(String url, String json, final HttpCallback callback) {
        try {
            Logger.d(TAG, "HttpRequest: " + json);

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
            Request request = new Request.Builder().url(url).post(body).build();

            mClient.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (callback != null) callback.onError(e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        if (response.isSuccessful()) {
                            HttpResponse httpResponse = new HttpResponse(response.body().string());
                            httpResponse.setSuccessful(true);

                            Logger.d(TAG, "HttpResponse: " + httpResponse.getBodyString());

                            if (callback != null) callback.onResponse(httpResponse);
                        } else {
                            if (callback != null)
                                callback.onResponse(new HttpResponse(response.code()));
                        }
                    } catch (Exception e) {
                        if (callback != null) callback.onError(e);
                    }
                }
            });
        } catch (Exception e) {
            if (callback != null) callback.onError(e);
        }
    }
}
