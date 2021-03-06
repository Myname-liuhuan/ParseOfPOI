package com.example.doexcel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 刘欢
 * @Date 2020/7/29
 */
@Controller
@RequestMapping()
public class ModelController {

    @RequestMapping({"index","/"})
    public String toIndex(){
        return "redirect:/static/index.html";
    }

    /**
     * 测试过滤器
     * @param request
     */
    @RequestMapping("pr")
    @ResponseBody
    public void printRequest(HttpServletRequest request){
        System.out.println("controller-prs");
    }
}
