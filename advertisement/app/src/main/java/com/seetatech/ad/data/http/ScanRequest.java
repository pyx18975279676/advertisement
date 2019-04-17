package com.seetatech.ad.data.http;

import com.alibaba.fastjson.JSON;
import com.seetatech.ad.setting.AppSetting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xjh on 18-6-21.
 */

public class ScanRequest extends BaseRequest<ScanResponse> {
    private String dev_code;//设备编号
    private float threshold;//阈值
    private List<CameraImage> images;//摄像头图片

    public String getDev_code() {
        return dev_code;
    }

    public void setDev_code(String dev_code) {
        this.dev_code = dev_code;
    }

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public List<CameraImage> getImages() {
        return images;
    }

    public void setImages(List<CameraImage> images) {
        this.images = images;
    }

    @Override
    protected String getUrl() {
        return AppSetting.getScanServerAddress() + "/scan/";
    }

    @Override
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("json_str_content", JSON.toJSONString(this));
        return params;
    }

    public static class CameraImage {
        private int camera_id;//摄像头id
        private String image;//图片:Base64格式

        public int getCamera_id() {
            return camera_id;
        }

        public void setCamera_id(int camera_id) {
            this.camera_id = camera_id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
