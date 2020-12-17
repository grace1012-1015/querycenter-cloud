package com.goldwater.querycenter.common.source;

import com.alibaba.fastjson.JSONArray;

import java.util.*;

public class DicEnum {
    // 天气预报
    public static Map<String, String> weathMap = new HashMap<String, String>();
    // 河水特征码（闸水特征码）
    public static Map<String, String> chrcdMap = new HashMap<String, String>();
    // 水势
    public static Map<String, String> wptnMap = new HashMap<String, String>();

    // 测流方法
    public static Map<String, String> msqmtMap = new HashMap<String, String>();
    // 测积方法
    public static Map<String, String> msamtMap = new HashMap<String, String>();
    // 测速方法
    public static Map<String, String> msvmtMap = new HashMap<String, String>();
    static {
        weathMap.put("5", "雪");
        weathMap.put("6", "雨夹雪");
        weathMap.put("7", "雨");
        weathMap.put("8", "阴");
        weathMap.put("9", "晴");

        chrcdMap.put("1", "干涸");
        chrcdMap.put("2", "断流");
        chrcdMap.put("3", "流向不定");
        chrcdMap.put("4", "逆流");
        chrcdMap.put("5", "起涨");
        chrcdMap.put("6", "洪峰");
        chrcdMap.put("7", "水电厂发电流量");

        wptnMap.put("4", "落");
        wptnMap.put("5", "涨");
        wptnMap.put("6", "平");

        msqmtMap.put("1", "水位流量关系曲线");
        msqmtMap.put("2", "浮标及溶液测流法");
        msqmtMap.put("3", "流速仪及量水建筑物");
        msqmtMap.put("4", "估算法");
        msqmtMap.put("5", "ADCP");
        msqmtMap.put("6", "电功率反推法");
        msqmtMap.put("9", "其它方法");

        msamtMap.put("1", "水位面积关系曲线");
        msamtMap.put("2", "测深杆或测深锤、铅鱼");
        msamtMap.put("3", "回声测深仪");
        msamtMap.put("4", "ADCP");
        msamtMap.put("9", "其它方法");

        // 测速方法
        msvmtMap.put("1", "流速仪");
        msvmtMap.put("2", "浮标法");
        msvmtMap.put("3", "声学法");
        msvmtMap.put("5", "ADCP");
        msvmtMap.put("9", "其它方法");
    }

    /**
     *
     * @param map
     * @return
     */
    public static List<Map<String, String>> changeToList(Map map) {
        Set set = map.entrySet();
        Iterator i = set.iterator();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        while (i.hasNext()) {
            Map.Entry<String, String> entry1 = (Map.Entry<String, String>) i
                    .next();
            Map<String, String> dataMap = new HashMap<String, String>();
            dataMap.put("key", entry1.getKey());
            dataMap.put("val", entry1.getValue());
            list.add(dataMap);
        }
        return list;
    }
}
