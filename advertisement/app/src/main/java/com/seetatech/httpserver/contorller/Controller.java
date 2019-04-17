package com.seetatech.httpserver.contorller;

import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.NanoHTTPD;
import org.nanohttpd.protocols.http.response.Response;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by hexuren on 18-5-14.
 */

public interface Controller {

    Response handle(IHTTPSession session) throws Exception;
}
