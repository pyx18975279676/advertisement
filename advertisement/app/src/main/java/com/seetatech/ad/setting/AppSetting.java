package com.seetatech.ad.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import com.seetatech.ad.R;

/**
 * Created by hexuren on 18-5-9.
 */

public class AppSetting {
    private static AppSetting appSetting = new AppSetting();
    private static SharedPreferences sp;
    private static Resources rs;

    private String scanServerAddress;//识别服务器地址
    private String shopServerAddress;//业务服务器地址
    private String adminPassword;//管理员密码
    private String threshold;//识别阈值
    private String shopId;//店铺id
    private String cameraCount;//摄像头个数
    private String cameraFPS;//摄像头帧数
    private String cameraBandWidthFactor;//摄像头带宽

    public static synchronized void load(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        rs = context.getResources();

        appSetting.scanServerAddress = sp.getString(rs.getString(R.string.pref_key_scan_server_address), rs.getString(R.string.pref_default_scan_server_address));
        appSetting.shopServerAddress = sp.getString(rs.getString(R.string.pref_key_shop_server_address), rs.getString(R.string.pref_default_shop_server_address));
        appSetting.adminPassword = sp.getString(rs.getString(R.string.pref_key_admin_password), rs.getString(R.string.pref_default_admin_password));
        appSetting.threshold = sp.getString(rs.getString(R.string.pref_key_threshold), rs.getString(R.string.pref_default_threshold));
        appSetting.shopId = sp.getString(rs.getString(R.string.pref_key_shop_id), rs.getString(R.string.pref_default_shop_id));
        appSetting.cameraCount = sp.getString(rs.getString(R.string.pref_key_camera_count), rs.getString(R.string.pref_default_camera_count));
        appSetting.cameraFPS = sp.getString(rs.getString(R.string.pref_key_camera_fps), rs.getString(R.string.pref_default_camera_fps));
        appSetting.cameraBandWidthFactor = sp.getString(rs.getString(R.string.pref_key_camera_band_width_factor), rs.getString(R.string.pref_default_camera_band_width_factor));
    }

    public static String getScanServerAddress() {
        return appSetting.scanServerAddress;
    }

    public static String getShopServerAddress() {
        return appSetting.shopServerAddress;
    }

    public static String getAdminPassword() {
        return appSetting.adminPassword;
    }

    public static float getThreshold() {
        try {
            return Float.valueOf(appSetting.threshold);
        } catch (Exception e) {
            return 0;
        }
    }

    public static String getShopId() {
        return appSetting.shopId;
    }

    public static int getCameraCount() {
        try {
            return Integer.valueOf(appSetting.cameraCount);
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getCameraFPS() {
        try {
            return Integer.valueOf(appSetting.cameraFPS);
        } catch (Exception e) {
            return 1;
        }
    }

    public static float getCameraBandWidthFactor() {
        try {
            return Float.valueOf(appSetting.cameraBandWidthFactor);
        } catch (Exception e) {
            return 0;
        }
    }
}
