package com.example.doexcel.controller;

import com.example.doexcel.service.impl.UpLoadService;
import com.example.doexcel.utils.Encapsulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘欢
 * @Date 2020/8/4
 */
@Controller
@RequestMapping("upload")
public class UpLoadController {

    @Autowired
    UpLoadService upLoadService;

    /**
     *
     * @param file 接收的上传来的文件
     * @param tableName 表名
     * @return {'state':0, 'message':'错误提示'} 或者 {'state':1, 'data':'返回的数据'}
     */
    @RequestMapping("parseExcel")
    @ResponseBody
    public Map<String, Object> parseExcel(@RequestParam("file") MultipartFile file, String tableName){
        Map<String, Object> map = new HashMap<>();
        try {
            map = Encapsulation.toMap(1,upLoadService.parseExcel(file, tableName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}
