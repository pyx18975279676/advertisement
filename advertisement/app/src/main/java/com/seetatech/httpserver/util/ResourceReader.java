package com.seetatech.httpserver.util;

import android.text.TextUtils;

import com.seetatech.ad.util.FileUtil;

import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.NanoHTTPD;
import org.nanohttpd.protocols.http.response.Response;
import org.nanohttpd.protocols.http.response.Status;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by hexuren on 18-5-14.
 */

public class ResourceReader {

    public static Response readResource(IHTTPSession session) {
        try {
            String uri = session.getUri();

            // "/"返回"/index.html"
            if ("/".equals(uri)) uri = "/index.html";

            //先判断文件是否存在
            File file = FileUtil.getHtmlFile(uri);
            if (!file.exists()) {
                //文件不存在直接返回错误
                return Response.newFixedLengthResponse(Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "URI NOT FOUND");
            }

            //获取mimeType,根据类型返回不同结果
            String mimeType = NanoHTTPD.getMimeTypeForFile(uri);

            if ("image/jpeg".equals(mimeType) || "image/png".equals(mimeType)) {
                //图片资源以流形式返回
                return Response.newChunkedResponse(Status.OK, mimeType, new FileInputStream(file));
            } else {
                //文本资源以字符串形式返回
                String resourceStr = FileUtil.read(FileUtil.getHtmlFile(uri).getAbsolutePath());

                if (TextUtils.isEmpty(resourceStr)) {
                    //如果读取的字符串为空,返回错误
                    return Response.newFixedLengthResponse(Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "URI NOT FOUND");
                }
                return Response.newFixedLengthResponse(Status.OK, mimeType, resourceStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
