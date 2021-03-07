package com.example.doexcel.utils;

import com.alibaba.druid.sql.visitor.functions.Char;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *操作String的工具类
 * @author huanliu
 */
public class StringUtil {

    /**
     * 判断字符串是否为空
     * @param string
     * @return true表示空，false表示非空
     */
    public static boolean isEmpty(String string){
        if (string == null){
            return true;
        }
        if ("".equals(string)) {
            return true;
        }
        return false;
    }

    /**
     * 将常见类型的对象转化为String
     * @param object
     * @return
     */
    public static String objectToString(Object object){
        try {
            if (object == null){
                return null;
            }

            //时间类型格式化
            if (object instanceof Date){
                SimpleDateFormat sdf = SdfUtil.getSdf("yyyy-MM-dd HH:mm:ss");
                return sdf.format((Date) object);
            }

            //八种基本类型和String类型 可以直接强转
            if(object instanceof String || object instanceof Byte || object instanceof Short
                    || object instanceof Integer || object instanceof Long || object instanceof Double
                    || object instanceof Float || object instanceof Boolean || object instanceof Char){
                return String.valueOf(object);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
