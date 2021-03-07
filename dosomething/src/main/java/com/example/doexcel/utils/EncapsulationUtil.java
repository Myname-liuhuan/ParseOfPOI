package com.example.doexcel.utils;

import com.alibaba.fastjson.JSONObject;
import com.example.doexcel.exception.StateException;

import java.util.HashMap;
import java.util.Map;

/**
 * 用来将数据封装为指定样式
 * @author 刘欢
 * @Date 2020/8/9
 */
public class EncapsulationUtil {
	public static Integer STATE_ERROR = 0;
	public static Integer STATE_SUCCESS = 1;

	/**
	 *  将Map类型的返回结果封装好
	 * @param state 0:错误，1:正确
	 * @param object state=0 时为错误消息，=1时为前端需要的具体数据
	 * @return
	 */
	public static Map<String, Object> toMap(Integer state, Object object){
		if (state != 0 && state != 1){
			try {
				throw new StateException("返回给前台的state字段值不合法");
			} catch (StateException e) {
				e.printStackTrace();
			}
		}
		Map<String, Object> map = new HashMap<>(10);
		map.put("state", state);
		map.put(state==0?"message":"data", object);
		return map;
	}


	/**
	 *  封装一个jsonString类型的结果
	 * @param state 0:错误，1:正确
	 * @param object state=0 时为错误消息，=1时为前端需要的具体数据
	 * @return
	 */
	public static String toJSONString(Integer state, Object object){
		JSONObject jObj = new JSONObject();
		jObj.put("state", state);
		jObj.put(state==0?"message":"data", object);
		return jObj.toJSONString();
	}

}
