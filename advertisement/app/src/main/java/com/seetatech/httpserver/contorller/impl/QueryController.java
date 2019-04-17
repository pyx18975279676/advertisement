package com.seetatech.httpserver.contorller.impl;

import com.alibaba.fastjson.JSON;
import com.seetatech.ad.data.MediaController;
import com.seetatech.httpserver.contorller.Controller;
import com.seetatech.httpserver.data.LoginState;
import com.seetatech.httpserver.data.bean.HttpResponse;

import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.NanoHTTPD;
import org.nanohttpd.protocols.http.response.Response;
import org.nanohttpd.protocols.http.response.Status;

/**
 * Created by xjh on 18-9-10.
 */

public class QueryController implements Controller {

    @Override
    public Response handle(IHTTPSession session) throws Exception {
        //检查是否登录
        if (!LoginState.checkLogin(session)) {
            return Response.newFixedLengthResponse(Status.OK, NanoHTTPD.MIME_PLAINTEXT,
                    JSON.toJSONString(new HttpResponse(HttpResponse.NOT_LOGIN)));
        }

        return Response.newFixedLengthResponse(Status.OK, NanoHTTPD.MIME_PLAINTEXT,
                JSON.toJSONString(new HttpResponse(HttpResponse.SUCCESS, MediaController.adFileTable)));
    }
}
