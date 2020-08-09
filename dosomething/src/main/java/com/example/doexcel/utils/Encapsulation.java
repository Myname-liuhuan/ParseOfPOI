package com.example.doexcel.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 用来将数据封装为指定样式
 * @author 刘欢
 * @Date 2020/8/9
 */
public class Encapsulation {
	public static Integer STATE_ERROR = 0;
	public static Integer STATE_SUCCESS = 1;

	/**
	 *  将Map类型的返回结果封装好
	 * @param state 0:错误，1:正确
	 * @param object state=0 时为错误消息，=1时为前端需要的具体数据
	 * @return
	 */
	public static Map toMap(Integer state,Object object) throws StateException{
		if (state != 0 && state != 1){
			throw new StateException("返回给前台的state字段值不合法");
		}
		Map map = new HashMap();
		map.put("state", state);
		map.put(state==0?"message":"data", object);
		return map;
	}

}
