package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 刘欢
 * @Date 2020/11/29
 */
@Controller
@RequestMapping("/msg")
public class MessageController {

	@RequestMapping("/text")
	@ResponseBody
	public String getText(HttpServletRequest request){
		return "你好";
	}
}
