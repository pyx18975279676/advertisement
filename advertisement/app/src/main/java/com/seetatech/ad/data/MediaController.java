package com.seetatech.ad.data;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.seetatech.ad.data.bean.AdFile;
import com.seetatech.ad.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xjh on 18-8-20.
 */

public class MediaController {

    //所有文件列表
    public static LinkedHashMap<String, List<AdFile>> adFileTable = new LinkedHashMap<>();
    //已播文件列表,保存文件列表当前正在播放文件的下标
    private static Map<String, Integer> adFileIndexMap = new HashMap<>();
    //当前正在播放的文件的key
    private static String currentKey;

    private static String MEDIA_CONFIG_FILE_NAME = "/media_config";

    public static boolean loadMediaConfig() {
        try {
            adFileTable = JSON.parseObject(FileUtil.read(FileUtil.MEDIA_ROOT_PATH + MEDIA_CONFIG_FILE_NAME),
                    new TypeReference<LinkedHashMap<String, List<AdFile>>>() {});
            if (adFileTable == null) {
                adFileTable = new LinkedHashMap<>();
                return false;
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public synchronized static void addAdFile(String key, AdFile adFile) {
        if (adFileTable != null) {
            if (adFileTable.containsKey(key)) {
                adFileTable.get(key).add(adFile);
            } else {
                List<AdFile> adFileList = new ArrayList<>();
                adFileList.add(adFile);
                adFileTable.put(key, adFileList);
            }
        }
    }

    public synchronized static void deleteAdFile(String id) {
        if (adFileTable != null) {
            for (String key : adFileTable.keySet()) {
                List<AdFile> adFileList = adFileTable.get(key);

                for (AdFile adFile : adFileList) {
                    if (adFile.getId().equals(id)) {
                        adFileList.remove(adFile);
                        FileUtil.deleteFile(new File(adFile.getPath()));
                        break;
                    }
                }

                if (adFileList.size() == 0) {
                    adFileTable.remove(key);
                    break;
                }
            }
        }
    }

    public synchronized static void saveConfig() {
        FileUtil.write(FileUtil.MEDIA_ROOT_PATH, MEDIA_CONFIG_FILE_NAME, JSON.toJSONString(adFileTable));
    }

    public synchronized static AdFile getNextMediaFile() {
        if (!TextUtils.isEmpty(currentKey)) {
            //先从当前正在播放的key开始找下一个文件
            return getNextMediaFile(currentKey);
        } else {
            //待寻找的另外一个播放的文件
            AdFile anotherMediaFile = null;
            //遍历已播文件列表,寻找下一个没有播放的文件
            Iterator iterator = adFileTable.keySet().iterator();
            while (iterator.hasNext()) {
                String anotherKey = (String) iterator.next();
                if (!adFileIndexMap.containsKey(anotherKey)) {
                    List<AdFile> anotherMediaFileList = adFileTable.get(anotherKey);
                    if (anotherMediaFileList != null && anotherMediaFileList.size() > 0 && anotherMediaFileList.get(0) != null) {
                        //找到未播放key后,返回文件列表的第一个文件
                        adFileIndexMap.put(anotherKey, 0);
                        anotherMediaFile = anotherMediaFileList.get(0);
                        currentKey = anotherKey;
                        break;
                    }
                }
            }

            if (anotherMediaFile != null) {
                //如果能够找到另外一个播放文件
                return anotherMediaFile;
            } else {
                //如果找不到任何可播放文件,说明所有文件都已经播放一遍,把已播列表清空,再重新查找一遍
                adFileIndexMap.clear();
                return getNextMediaFile();
            }
        }
    }

    public synchronized static AdFile getNextMediaFile(String key) {
        if (!TextUtils.isEmpty(key)) {
            if (adFileTable.containsKey(key)) {
                //找到key对应的媒体文件列表
                List<AdFile> mediaFileList = adFileTable.get(key);
                if (mediaFileList != null && mediaFileList.size() > 0) {
                    if (!key.equals(currentKey)) {
                        currentKey = key;
                        adFileIndexMap.put(key, 0);
                        return mediaFileList.get(0);
                    }

                    //如果key在已播文件列表中
                    if (adFileIndexMap.containsKey(key)) {
                        //得到当前播放的下标
                        int index = adFileIndexMap.get(key);
                        //如果当前播放的文件存在下一个文件,则返回下一个文件
                        if (index + 1 >= 0 && mediaFileList.size() >= index + 1) {
                            if (mediaFileList.size() == index + 1) {
                                currentKey = null;
                                adFileIndexMap.put(key, index + 1);
                                return getNextMediaFile();
                            } else {
                                adFileIndexMap.put(key, index + 1);
                                return mediaFileList.get(index + 1);
                            }
                        } else if (index + 1 >= 0) {
                            //如果该key的文件已经播放完成,则找下一个没有播放的文件
                            currentKey = null;
                            adFileIndexMap.put(key, -1);
                            return getNextMediaFile();
                        }
                    } else {
                        //如果key不在已播列表中,加入已播列表,下表设为0
                        adFileIndexMap.put(key, 0);
                        return mediaFileList.get(0);
                    }
                }
            }
        } else {
            return getNextMediaFile();
        }
        return null;
    }

    public synchronized static AdFile getMediaFile(String key) {
        try {
            if (adFileTable != null && adFileTable.containsKey(key)) {
                return adFileTable.get(key).get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
