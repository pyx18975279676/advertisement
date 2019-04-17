package com.seetatech.ad.data;

import android.content.ContextWrapper;
import android.graphics.Bitmap;

import com.alibaba.fastjson.JSON;
import com.seetatech.ad.data.http.ScanResponse;
import com.seetatech.ad.util.Logger;
import com.seetatech.ad.util.SpUtil;
import com.seetatech.ad.widget.CameraInitView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据存储和处理
 * Created by xjh on 18-6-25.
 */

public class AppData {

    private static final String TAG = "AppData";

    private static final String SP_DATA = "SP_DATA";
    private static final String SP_KEY_INIT_POINTS = "SP_KEY_INIT_POINTS";
    private static final String SP_KEY_INIT_BITMAPS = "SP_KEY_INIT_BITMAPS";
    private static final String SP_KEY_GOODS_INFO = "SP_KEY_GOODS_INFO";
    private static final String SP_KEY_CAMERA_BITMAP = "SP_KEY_CAMERA_BITMAP";
    private static final String SP_KEY_SCAN_RESULT = "SP_KEY_SCAN_RESULT";

    private static Map<String, Object> data = new HashMap<>();

    private static void put(String key, Object object) {
        data.put(key, object);
    }

    private static Object get(String key) {
        return data.get(key);
    }

    public synchronized static void putInitPoints(ContextWrapper contextWrapper, List<CameraInitView.Point[]> initPoints) {
        put(SP_KEY_INIT_POINTS, initPoints);
        SpUtil.putString(contextWrapper, SP_DATA, SP_KEY_INIT_POINTS, JSON.toJSONString(initPoints));
    }

    public synchronized static List<CameraInitView.Point[]> getInitPoints(ContextWrapper contextWrapper) {
        try {
            if (data.containsKey(SP_KEY_INIT_POINTS)) {
                return (List<CameraInitView.Point[]>) get(SP_KEY_INIT_POINTS);
            } else {
                List<CameraInitView.Point[]> initPoints = JSON.parseArray(SpUtil.getString(contextWrapper,
                        SP_DATA, SP_KEY_INIT_POINTS), CameraInitView.Point[].class);

                put(SP_KEY_INIT_POINTS, initPoints);

                return initPoints;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public synchronized static void putInitBitmap(ContextWrapper contextWrapper, List<Bitmap> initBitmaps) {
        put(SP_KEY_INIT_BITMAPS, initBitmaps);
    }

    public synchronized static List<Bitmap> getInitBitmap(ContextWrapper contextWrapper) {
        try {
            return (List<Bitmap>) get(SP_KEY_INIT_BITMAPS);
        } catch (Exception e) {
            return null;
        }
    }

    public synchronized static void putGoodsNumberInfo(ContextWrapper contextWrapper, Map<String, Integer> goodsInfo) {
        put(SP_KEY_GOODS_INFO, goodsInfo);
        SpUtil.putString(contextWrapper, SP_DATA, SP_KEY_GOODS_INFO, JSON.toJSONString(goodsInfo));
    }

    public synchronized static Map<String, Integer> getGoodsNumberInfo(ContextWrapper contextWrapper) {
        try {
            if (data.containsKey(SP_KEY_GOODS_INFO)) {
                return (Map<String, Integer>) get(SP_KEY_GOODS_INFO);
            } else {
                Map<String, Integer> goodsInfo = JSON.parseObject(SpUtil.getString(contextWrapper, SP_DATA, SP_KEY_GOODS_INFO), Map.class);

                put(SP_KEY_GOODS_INFO, goodsInfo);

                return goodsInfo;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public synchronized static void putCameraBitmaps(List<Bitmap> bitmapList) {
        put(SP_KEY_CAMERA_BITMAP, bitmapList);
    }

    public synchronized static List<Bitmap> getCameraBitmaps() {
        Object object = get(SP_KEY_CAMERA_BITMAP);
        if (object != null) {
            return (List<Bitmap>) object;
        }
        return null;
    }

    public synchronized static void putScanResults(List<ScanResponse.ScanResult> scanResultList) {
        put(SP_KEY_SCAN_RESULT, scanResultList);
    }

    public synchronized static List<ScanResponse.ScanResult> getScanResult() {
        Object object = get(SP_KEY_SCAN_RESULT);
        if (object != null) {
            return (List<ScanResponse.ScanResult>) object;
        }
        return null;
    }

    /**
     * 计算各个商品的数量
     *
     * @param scanResultList
     * @return
     */
    public static Map<String, Integer> countGoods(List<ScanResponse.ScanResult> scanResultList) {
        if (scanResultList != null) {
            Map<String, Integer> goodsInfo = new HashMap<>();
            for (ScanResponse.ScanResult scanResult : scanResultList) {
                if (scanResult != null) {
                    List<ScanResponse.ScanResult.ScanResultInfo> scanResultInfoList = scanResult.getInfo();
                    if (scanResultInfoList != null) {
                        for (ScanResponse.ScanResult.ScanResultInfo scanResultInfo : scanResultInfoList) {
                            String label = scanResultInfo.getLabel();
                            List<Float[]> rectList = scanResultInfo.getRect_list();
                            if (rectList != null && rectList.size() > 0) {
                                if (goodsInfo.containsKey(label)) {
                                    int num = goodsInfo.get(label);
                                    num += rectList.size();
                                    goodsInfo.put(label, num);
                                } else {
                                    int num = rectList.size();
                                    goodsInfo.put(label, num);
                                }
                            }
                        }
                    }
                }
            }
            return goodsInfo;
        } else {
            return null;
        }
    }

    /**
     * 获取减少的商品数量信息
     *
     * @param contextWrapper
     * @param goodsNumberInfo
     * @return
     */
    public synchronized static Map<String, Integer> getReduceGoodsNumberInfo(ContextWrapper contextWrapper, Map<String, Integer> goodsNumberInfo) {
        Map<String, Integer> goodsNumberInfoOld = getGoodsNumberInfo(contextWrapper);
        Logger.d(TAG, "GoodsNumberInfo: " + JSON.toJSONString(goodsNumberInfo));
        Logger.d(TAG, "OldGoodsNumberInfo: " + JSON.toJSONString(goodsNumberInfoOld));
        Map<String, Integer> reduceGoodsNumberInfo = new HashMap<>();
        if (goodsNumberInfo != null && goodsNumberInfoOld != null) {
            for (String key : goodsNumberInfoOld.keySet()) {
                int numberOld = goodsNumberInfoOld.get(key);
                if (goodsNumberInfo.containsKey(key)) {
                    int number = goodsNumberInfo.get(key);
                    if (number >= 0 && numberOld >= 0 && number < numberOld) {
                        reduceGoodsNumberInfo.put(key, numberOld - number);
                    }
                } else {
                    reduceGoodsNumberInfo.put(key, numberOld);
                }
            }
        }
        return reduceGoodsNumberInfo;
    }
}
