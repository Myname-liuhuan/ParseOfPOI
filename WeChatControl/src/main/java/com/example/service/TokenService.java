package com.example.service;

import com.example.entity.MessageEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 刘欢
 * @Date 2020/11/30
 */
public interface TokenService {
	/**
	 * 解析token,验证是否是微信要求建立链接的请求
	 * @param request
	 * @return
	 */
	String parseToken(HttpServletRequest request);

	/**
	 * 当微信公众号收到消息的时候
	 * @param request
	 * @return
	 */
	String parseMessage(HttpServletRequest request);

	/**
	 * 重载 用实体类来接收参数
	 * @param messageEntity
	 * @return
	 */
	String parseMessage(MessageEntity messageEntity);

}
