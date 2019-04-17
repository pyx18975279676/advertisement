package com.seetatech.httpserver.contorller.impl;

import com.alibaba.fastjson.JSON;
import com.seetatech.ad.data.MediaController;
import com.seetatech.ad.data.bean.AdFile;
import com.seetatech.httpserver.contorller.Controller;
import com.seetatech.httpserver.data.LoginState;
import com.seetatech.httpserver.data.bean.HttpResponse;
import com.seetatech.ad.util.FileUtil;

import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.NanoHTTPD;
import org.nanohttpd.protocols.http.response.Response;
import org.nanohttpd.protocols.http.response.Status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by xjh on 18-9-6.
 */

public class AddController implements Controller {

    @Override
    public Response handle(IHTTPSession session) {
        //检查是否登录
        if (!LoginState.checkLogin(session)) {
            return Response.newFixedLengthResponse(Status.OK, NanoHTTPD.MIME_PLAINTEXT,
                    JSON.toJSONString(new HttpResponse(HttpResponse.NOT_LOGIN)));
        }

        try {
            Map<String, String> files = new HashMap<>();
            session.parseBody(files);

            Map<String, List<String>> params = session.getParameters();
            String key = params.get("key").get(0);
            String time = params.get("time").get(0);
            String name = params.get("name").get(0);
            String file = params.get("file").get(0);

            FileUtil.copyFileToDir(files.get("file"), FileUtil.getMediaDir().getAbsolutePath(), file);

            AdFile adFile = new AdFile();
            adFile.setId(UUID.randomUUID().toString());
            adFile.setName(name);
            adFile.setTime(Integer.valueOf(time));
            adFile.setPath(FileUtil.getMediaFile(file).getAbsolutePath());

            MediaController.addAdFile(key, adFile);
            MediaController.saveConfig();
        } catch (Exception e) {
            return Response.newFixedLengthResponse(Status.OK, NanoHTTPD.MIME_PLAINTEXT,
                    JSON.toJSONString(new HttpResponse(HttpResponse.PARAM_ERROR)));
        }
        return Response.newFixedLengthResponse(Status.OK, NanoHTTPD.MIME_PLAINTEXT,
                JSON.toJSONString(new HttpResponse(HttpResponse.SUCCESS)));
    }
}
