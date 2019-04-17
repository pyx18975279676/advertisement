package com.seetatech.httpserver.data;

import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.content.Cookie;

import java.util.UUID;

/**
 * Created by xjh on 18-9-7.
 */

public class LoginState {
    private static String cookieValue;
    private static final String KEY_COOKIE = "KEY_COOKIE";

    public static boolean checkLogin(IHTTPSession session) {
        if (cookieValue != null && cookieValue.equals(session.getCookies().read(KEY_COOKIE))) {
            return true;
        }
        return false;
    }

    public static void setLoginState(IHTTPSession session) {
        String cookieValue = UUID.randomUUID().toString();
        Cookie cookie = new Cookie(KEY_COOKIE, cookieValue);
        session.getCookies().set(cookie);
        LoginState.cookieValue = cookieValue;
    }
}
