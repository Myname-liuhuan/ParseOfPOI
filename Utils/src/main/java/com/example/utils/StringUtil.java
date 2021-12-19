package com.example.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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
     * 不支持的格式会返回null
     * 当前支持的格式有
     * @see java.util.Date
     * @see java.lang.String
     * @see java.lang.Byte
     * @see java.lang.Short
     * @see java.lang.Integer
     * @see java.lang.Long
     * @see java.lang.Double
     * @see java.lang.Float
     * @see java.lang.Character
     * @see java.lang.Boolean
     * @param object
     * @return String or null
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
                    || object instanceof Float || object instanceof Boolean || object instanceof Character){
                return String.valueOf(object);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回32位的字母数字组成的字符串（一般用于生成表的ID）
     * @return
     */
    public static String autoID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

}
