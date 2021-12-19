package com.example.utils;

import java.util.Map;

/**
 * 在市面上提供的转换工具无法提供需要的转化样式的情况下
 * 自定义转换工具类
 * @author huanliu
 */
public class FormatConvertUtil {

    /**
     * 将map转化为使用于get请求的字符串格式
     * @param map
     * @return
     */
    public static String mapToGetString(Map<String, Object> map){
        String res = "";
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            res += entry.getKey() + "=" + entry.getValue() + "&";
        }
        //去掉最后一个 & 符
        if (res.indexOf("&") > -1){
            res.substring(0, res.lastIndexOf("&"));
        }
        return res;
    }
}
