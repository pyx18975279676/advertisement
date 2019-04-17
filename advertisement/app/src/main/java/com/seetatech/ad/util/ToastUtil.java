package com.seetatech.ad.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Toast工具类
 */
public class ToastUtil {

    private static Toast mToast;

    /**
     * @param msg 内容
     */
    public static void show(Context context, String msg) {
        show(context, msg, Toast.LENGTH_SHORT);
    }

    /**
     * @param msg      内容
     * @param duration Toast 显示时间长短类型  如：Toast.LENGTH_SHORT
     */
    public static void show(Context context, String msg, int duration) {
        show(context, msg, duration, Gravity.BOTTOM);
    }

    /**
     * @param msg      内容
     * @param duration 显示时间长短类型  如：Toast.LENGTH_SHORT
     * @param gravity  对齐方式
     */
    public static void show(Context context, String msg, int duration, int gravity) {
        if (Gravity.BOTTOM == gravity) {
            //底部对齐方式默认距离底部60dp
            show(context, msg, duration, gravity, 0, UiUtil.dp2px(context, 60));
        } else {
            show(context, msg, duration, gravity, 0, 0);
        }
    }

    /**
     * @param msg      内容
     * @param duration 显示时间长短类型  如：Toast.LENGTH_SHORT
     * @param gravity  对齐方式
     * @param xOffset  x轴偏移量
     * @param yOffset  y轴偏移量
     */
    public synchronized static void show(Context context, String msg, int duration, int gravity, int xOffset, int yOffset) {
        if (mToast != null) {
            mToast.cancel();
        }

        mToast = Toast.makeText(context, msg, duration);
        mToast.setText(msg);
        mToast.setDuration(duration);
        mToast.setGravity(gravity, xOffset, yOffset);
        mToast.show();
    }

    /**
     * 取消toast
     *
     * @param
     */
    public static void cancel() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
