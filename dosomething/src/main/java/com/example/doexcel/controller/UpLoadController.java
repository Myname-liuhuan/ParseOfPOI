package com.example.doexcel.controller;

import com.example.doexcel.service.UpLoadService;
import com.example.doexcel.utils.Encapsulation;
import com.example.doexcel.utils.StateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
    public Map<String, Object> parseExcel(@RequestParam("file") MultipartFile file, String tableName) throws Exception {
        return upLoadService.parseExcel(file, tableName);
    }

    /**
     * 将被确认过的sql到数据库进行插入。
     * @param sql
     * @return
     */
    @RequestMapping("doSql")
    @ResponseBody
    public boolean doSql(String sql){
        System.out.println(sql);
        if(sql == null){
            return false;
        }
        return upLoadService.insertSql(sql);
    }

}
