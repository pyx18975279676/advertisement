package com.seetatech.ad;

import android.app.Application;

import com.seetatech.ad.setting.AppSetting;
import com.seetatech.httpserver.HttpServer;

/**
 * Created by xjh on 18-6-20.
 */

public class AdApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //加载配置
        AppSetting.load(this);

        //开启http服务
        HttpServer.init(this, 8000);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        HttpServer.destroy();
    }
}
