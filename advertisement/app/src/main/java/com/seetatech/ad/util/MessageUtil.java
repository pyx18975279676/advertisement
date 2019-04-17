package com.seetatech.ad.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 输出提示信息
 * Created by xjh on 18-3-12.
 */

public class MessageUtil {

    public static final int OK = 0;
    public static final int SYSTEM_BUSY = 1;
    public static final int NETWORK_NONE = 2;
    public static final int NETWORK_ERROR = 3;
    public static final int SERVER_ERROR = 4;
    public static final int PARAM_ERROR = 5;
    public static final int JSON_ERROR = 6;
    public static final int DELETE_ERROR = 7;
    public static final int PAY_ERROR = 8;

    private static Map<Integer, String> messages = new HashMap<>();

    static {
        messages.put(OK, "成功");
        //程序内部错误
        messages.put(SYSTEM_BUSY, "系统繁忙");
        messages.put(NETWORK_NONE, "没有网络");
        messages.put(NETWORK_ERROR, "网络异常");
        messages.put(SERVER_ERROR, "服务器异常");
        messages.put(PARAM_ERROR, "参数错误");
        messages.put(JSON_ERROR, "数据解析异常");
        messages.put(DELETE_ERROR, "删除商品失败，请重试");
        messages.put(PAY_ERROR, "支付失败");

        //服务器返回错误码
        messages.put(1001, "未知错误");
        messages.put(1002, "参数错误");
        messages.put(1003, "验证码错误");
        messages.put(1007, "数据格式错误");
        messages.put(1011, "数据库连接异常");
        messages.put(1012, "数据库操作异常");
        messages.put(1013, "识别服务器连接异常");
        messages.put(1021, "图片不存在");
        messages.put(1022, "图片已经存在");
        messages.put(1023, "图片上传失败");
        messages.put(1024, "图片加载失败");
        messages.put(1025, "图片尺寸超限");
        messages.put(1031, "商品不存在");
        messages.put(1032, "商品已经存在");
        messages.put(1041, "订单不存在");
        messages.put(1051, "订单中的商品项不存在");
        messages.put(1061, "画布初始化失败");
        messages.put(1071, "柜台不存在");
        messages.put(1072, "柜台已经存在");
        messages.put(1101, "会员不存在");
        messages.put(1102, "会员已经存在");
        messages.put(1231, "JSON解析失败");
        messages.put(1401, "多种称重商品同时称重");
        messages.put(1402, "商品总重不符合");
        messages.put(1403, "一种称重商品与非称重商品混合");
        messages.put(1501, "识别失败");
        messages.put(1502, "商品识别概率过低");
        messages.put(1503, "商品识别矫正失败");
    }

    public static String getMessage(int code) {
        if (messages.containsKey(code)) {
            return messages.get(code);
        } else {
            return "未知错误";
        }
    }
}
