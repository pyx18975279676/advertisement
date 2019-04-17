package com.seetatech.httpserver;

import android.content.Context;

import com.seetatech.httpserver.contorller.Controller;
import com.seetatech.ad.util.FileUtil;
import com.seetatech.httpserver.util.RequestDispatcher;
import com.seetatech.httpserver.util.ResourceReader;

import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.NanoHTTPD;
import org.nanohttpd.protocols.http.request.Method;
import org.nanohttpd.protocols.http.response.Response;
import org.nanohttpd.protocols.http.response.Status;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by hexuren on 18-5-14.
 */

public class HttpServer extends NanoHTTPD {

    private static HttpServer httpServer;
    private static Context context;
    private ByteArrayOutputStream exceptionBos = new ByteArrayOutputStream();
    private PrintWriter exceptionPw = new PrintWriter(exceptionBos);

    private HttpServer(int port) {
        super(port);
    }

    public static synchronized void init(Context context, int port) {
        HttpServer.context = context;
        httpServer = new HttpServer(port);

        //生成html文件夹
        FileUtil.makeHtmlDir(context);

        //写入http服务器html文件
        FileUtil.copyFromAsset(context, "bootstrap.min.css", FileUtil.getHtmlFile("bootstrap.min.css"), false);
        FileUtil.copyFromAsset(context, "index.html", FileUtil.getHtmlFile("index.html"), false);
        FileUtil.copyFromAsset(context, "login.html", FileUtil.getHtmlFile("login.html"), false);
        FileUtil.copyFromAsset(context, "lx.css", FileUtil.getHtmlFile("lx.css"), false);
        FileUtil.copyFromAsset(context, "lx.js", FileUtil.getHtmlFile("lx.js"), false);

        try {
            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void destroy() {
        httpServer = null;
    }

    public static HttpServer getInstance() {
        if (httpServer == null) {
            throw new IllegalAccessError("Please initiate instance before using");
        }
        return httpServer;
    }

    public static Context getContext() {
        return context;
    }

    @Override
    public Response handle(IHTTPSession session) {
        try {
            if (Method.POST.equals(session.getMethod())) {
                //根据uri生成处理器
                Controller controller = RequestDispatcher.dispatch(session.getUri());

                if (controller == null) {
                    return Response.newFixedLengthResponse(Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "URI NOT FOUND");
                }

                //处理请求,返回结果
                Response response = controller.handle(session);

                return response;
            } else if (Method.GET.equals(session.getMethod())) {
                return ResourceReader.readResource(session);
            }
            return null;
        } catch (Exception e) {
            exceptionBos.reset();
            e.printStackTrace(exceptionPw);
            exceptionPw.flush();
            return Response.newFixedLengthResponse(Status.INTERNAL_ERROR, NanoHTTPD.MIME_PLAINTEXT, "SERVER ERROR:\n" + exceptionBos.toString());
        }
    }
}
