package com.example.utils;

import java.text.SimpleDateFormat;

/**
 * 使用静态的方式创建SimpleDateFormat类,节约资源(存在多线程访问下线程安全问题)
 * @author huanliu
 */
public class SdfUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

    public static SimpleDateFormat getSdf(String pattern){
        if("yyyy-MM-dd HH:mm:ss.SSS".equals(pattern)){
            return sdf;
        }else if("yyyy-MM-dd HH:mm:ss".equals(pattern)){
            return sdf1;
        }else if("yyyy-MM-dd".equals(pattern)){
            return sdf2;
        }
        return null;
    }

    public static SimpleDateFormat getSdf(){
        return sdf;
    }
}
