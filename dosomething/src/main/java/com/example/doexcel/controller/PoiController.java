package com.example.doexcel.controller;

import com.example.doexcel.service.PoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @author huanliu
 */
@Controller
@RequestMapping("/poi")
public class PoiController {

    @Autowired
    PoiService poiService;

    @RequestMapping("/index")
    public String testSql(){
        return "redirect:/static/except.html";
    }

    /**
     * 生成文件到服务器，返回文件路径
     * @param jsonStr
     * @param sql
     * @param filename
     * @return
     */
    @RequestMapping("/doExcept")
    @ResponseBody
    public Map<String, Object> doExcept(String jsonStr, String sql, String filename){
        return poiService.doExcept(jsonStr, sql, filename);
    }

    /**
     * 根据传来的文件路径下载文件
     */
    @RequestMapping("/getFile")
    public void getFile(String filepath, HttpServletResponse response){
        try {
            File file = new File(filepath);
            if (!file.exists()){
                return;
            }
            String filename = file.getName();
            response.setHeader("content-type", "application/octet-stream");
            //将文件名转化为"ISO8859-1"编码，避免下载时中文变成下划线的情况
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(),"ISO8859-1"));
            response.setContentType("application/octet-stream");
            ServletOutputStream out = response.getOutputStream();
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            byte[] b = new byte[1024];
            while (bis.read(b) != -1){
                out.write(b);
            }
            out.close();
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
