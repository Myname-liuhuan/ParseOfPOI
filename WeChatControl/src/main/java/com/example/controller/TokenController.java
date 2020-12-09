package com.example.controller;

import com.example.entity.MessageEntity;
import com.example.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 刘欢
 * @Date 2020/11/28
 */
@Controller
@RequestMapping("/token")
public class TokenController {

	@Autowired
	TokenService tokenService;
	/**
	 * 验证token方法
	 * 微信会以get方式请求过来
	 * @param request
	 * @return
	 */
	@RequestMapping(value="", method = RequestMethod.GET)
	@ResponseBody
	public String parseToken(HttpServletRequest request){
		return tokenService.parseToken(request);
	}

	/**
	 * 以post方法过来的是当用户给公众号发消息的时候，微信会以xml的形式将数据post到相同的url上
	 * @param messageEntity
	 * @return
	 */
	@RequestMapping(value="", method = RequestMethod.POST)
	@ResponseBody
	public String parseMessage(@RequestBody MessageEntity messageEntity){
		System.out.println(messageEntity);
		//return "success";
		return tokenService.parseMessage(messageEntity);
	}

}
