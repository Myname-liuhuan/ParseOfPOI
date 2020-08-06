package com.example.doexcel.controller;

import com.example.doexcel.utils.ExcelOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 刘欢
 * @Date 2020/8/4
 */
@Controller
@RequestMapping("upload")
public class UpLoadController {

    @RequestMapping("parseExcel")
    @ResponseBody
    public String parseExcel(@RequestParam("file") MultipartFile file, String tableName){
        String s1 = "";
        String s2 = "";
        try {
            if(file == null || tableName == null){
                return null;
            }
            ExcelOperation excelOperation = new ExcelOperation(file.getInputStream());
            s1 = excelOperation.printCreateTableSql(tableName);
            s2 = excelOperation.printInsertSql(tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s1 + "/n" + s2;
    }

    @RequestMapping("printSQL")
    @ResponseBody
    public String printSQL(){
        return null;
    }

}
