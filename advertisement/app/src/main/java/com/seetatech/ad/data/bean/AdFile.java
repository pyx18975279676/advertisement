package com.seetatech.ad.data.bean;

/**
 * Created by xjh on 18-9-4.
 */

public class AdFile {
    private String id;//广告id
    private String name;//广告名称
    private String path;//广告文件路径
    private int time;//播放时长

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
