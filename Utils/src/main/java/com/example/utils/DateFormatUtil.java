package com.example.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 解析，格式化时间工具类
 * 多线程可用
 * @author huanliu
 */
public class DateFormatUtil {

    private static SimpleDateFormat ymdhmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");

    public static String format(String patten, Date date){
        if ("yyyy-MM-dd HH:mm:ss.SSS".equalsIgnoreCase(patten)) {
            synchronized(ymdhmss){
                return ymdhmss.format(date);
            }
        }else if ("yyyy-MM-dd HH:mm:ss".equalsIgnoreCase(patten)){
            synchronized (ymdhms){
                return ymdhms.format(date);
            }
        }else if ("yyyy-MM-dd".equalsIgnoreCase(patten)){
            synchronized(ymd){
                return ymd.format(date);
            }
        }
        return null;
    }

    public static Date parse(String patten, String dateStr) throws ParseException {
        if ("yyyy-MM-dd HH:mm:ss.SSS".equalsIgnoreCase(patten)) {
            synchronized(ymdhmss){
                return ymdhmss.parse(dateStr);
            }
        }else if ("yyyy-MM-dd HH:mm:ss".equalsIgnoreCase(patten)){
            synchronized (ymdhms){
                return ymdhms.parse(dateStr);
            }
        }else if ("yyyy-MM-dd".equalsIgnoreCase(patten)){
            synchronized(ymd){
                return ymd.parse(dateStr);
            }
        }
        return null;
    }

}
