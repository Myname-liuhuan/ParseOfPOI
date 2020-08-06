package com.example.doexcel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 刘欢
 * @Date 2020/7/29
 */
@Controller
@RequestMapping()
public class ModelController {

    @RequestMapping({"index","/"})
    public String toIndex(){
        return "/static/index.html";
    }
}
