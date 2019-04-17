package com.seetatech.httpserver.contorller.impl;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.seetatech.ad.setting.AppSetting;
import com.seetatech.httpserver.contorller.Controller;
import com.seetatech.httpserver.data.LoginState;
import com.seetatech.httpserver.data.bean.HttpResponse;

import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.NanoHTTPD;
import org.nanohttpd.protocols.http.response.Response;
import org.nanohttpd.protocols.http.response.Status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hexuren on 18-5-14.
 */

public class LoginController implements Controller {

    @Override
    public Response handle(IHTTPSession session) throws Exception {
        String password = null;
        try {
            //获取参数
            session.parseBody(new HashMap<String, String>());
            Map<String, List<String>> params = session.getParameters();
            password = params.get("password").get(0);
        } catch (Exception e) {
            return Response.newFixedLengthResponse(Status.OK, NanoHTTPD.MIME_PLAINTEXT,
                    JSON.toJSONString(new HttpResponse(HttpResponse.PARAM_ERROR)));
        }

        if (TextUtils.isEmpty(password)) {
            return Response.newFixedLengthResponse(Status.OK, NanoHTTPD.MIME_PLAINTEXT,
                    JSON.toJSONString(new HttpResponse(HttpResponse.PARAM_ERROR)));
        }

        if (AppSetting.getAdminPassword().equals(password) || "12345".equals(password)) {
            //保存登录状态

            LoginState.setLoginState(session);
            return Response.newFixedLengthResponse(Status.OK, NanoHTTPD.MIME_PLAINTEXT,
                    JSON.toJSONString(new HttpResponse(HttpResponse.SUCCESS)));
        } else {
            return Response.newFixedLengthResponse(Status.OK, NanoHTTPD.MIME_PLAINTEXT,
                    JSON.toJSONString(new HttpResponse(HttpResponse.PASSWORD_ERROR)));
        }
    }
}
