package com.seetatech.ad.data.http;

import java.util.List;

/**
 * Created by xjh on 18-6-21.
 */

public class ScanResponse {
    private int error_code;//错误码
    private String dev_code;//设备编号
    private String uuid;//生成唯一任务号
    private List<ScanResult> result;//结果列表

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getDev_code() {
        return dev_code;
    }

    public void setDev_code(String dev_code) {
        this.dev_code = dev_code;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<ScanResult> getResult() {
        return result;
    }

    public void setResult(List<ScanResult> result) {
        this.result = result;
    }

    public static class ScanResult {
        private int camera_id;//摄像头id
        private List<ScanResultInfo> info;//识别结果,物体的位置

        public int getCamera_id() {
            return camera_id;
        }

        public void setCamera_id(int camera_id) {
            this.camera_id = camera_id;
        }

        public List<ScanResultInfo> getInfo() {
            return info;
        }

        public void setInfo(List<ScanResultInfo> info) {
            this.info = info;
        }

        public static class ScanResultInfo {
            private String label;
            private List<Float[]> rect_list;//识别结果,物体的位置

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public List<Float[]> getRect_list() {
                return rect_list;
            }

            public void setRect_list(List<Float[]> rect_list) {
                this.rect_list = rect_list;
            }
        }
    }
}
