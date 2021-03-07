package com.example.controller;

import com.example.service.WeChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 刘欢
 * @Date 2020/11/29
 */
@Slf4j
@Controller
@RequestMapping("/WeChat")
public class WeChatController {

	@Autowired
	WeChatService weChatService;

	/**
	 * 创建公众号的菜单栏
	 * @param menuStr
	 * @return
	 */
	@RequestMapping("/createMenu")
	@ResponseBody
	public boolean createMenu(String menuStr){
		return weChatService.createMenu(menuStr);
	}

	/**
	 * 手动刷新access_token
	 * @return
	 */
	@RequestMapping("/flushAccessToken")
	@ResponseBody
	public boolean flushAccessToken(){
		log.info("我是打印测试日志");
		weChatService.flushAccessToken();
		return true;
	}
}
