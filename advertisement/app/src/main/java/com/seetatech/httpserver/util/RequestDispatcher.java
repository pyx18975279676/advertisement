package com.seetatech.httpserver.util;

import com.seetatech.httpserver.contorller.Controller;
import com.seetatech.httpserver.contorller.impl.DeleteController;
import com.seetatech.httpserver.contorller.impl.LoginController;
import com.seetatech.httpserver.contorller.impl.AddController;
import com.seetatech.httpserver.contorller.impl.QueryController;

/**
 * Created by hexuren on 18-5-14.
 */

public class RequestDispatcher {

    private static LoginController loginController = new LoginController();
    private static AddController addController = new AddController();
    private static DeleteController deleteController = new DeleteController();
    private static QueryController queryController = new QueryController();

    public static Controller dispatch(String uri) {

        if ("/login".equals(uri)) {
            return loginController;
        } else if ("/add".equals(uri)) {
            return addController;
        } else if ("/delete".equals(uri)) {
            return deleteController;
        } else if ("/query".equals(uri)) {
            return queryController;
        }

        return null;
    }
}
