package com.example.doexcel.poi;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.doexcel.utils.StringUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 导出数据为Excel文件
 * 导出数据为excel
 * @author huanliu
 */
@Configuration
public class ExceptExcel {

    @Value("${poi.excel.defaultPath}")
    private String defaultPath;

    /**
     * 导出数据为excel文件
     * @param filename 文件名称
     * @param title 每列的头,每个json中只保存两个值，"key":"导出文件列名（中文）"，"value":"对应的表中的字段名（英文名）"
     * @param list sql查询的结果
     * @return
     */
    public String except(String filename, JSONArray title, List<Map<String, Object>> list){
        try {
            //将JSONArray转化为List<Map>
            List<Map<String, String>> titleList = new ArrayList<>();
            for (int i = 0; i < title.size(); i++){
                JSONObject jObj = title.getJSONObject(i);
                Map<String, String> map = new HashMap<>(5);
                map.put("key", jObj.getString("key"));
                map.put("value", jObj.getString("value"));
                titleList.add(map);
            }
            return except(filename, titleList, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 导出数据为excel文件
     * @param filename 文件名称
     * @param title 每列的头,每个map中只保存两个值，"key":"导出文件列名（中文）"，"value":"对应的表中的字段名（英文名）"
     * @param list sql查询的结果
     * @return filepath
     */
    public String except(String filename, List<Map<String, String>> title, List<Map<String, Object>> list){
        try{
            if(filename == null){
                System.out.println("导出文件名称不能为空");
                return null;
            }
            if (title == null){
                System.out.println("导出文件列选择不能为空");
                return null;
            }
            if (list == null){
                System.out.println("导出数据不能为空");
                return null;
            }

            File file = new File(defaultPath + new SimpleDateFormat("yyyyMMdd").format(new Date()));
            if(!file.exists()){
                file.mkdirs();
            }
            file = new File(file,filename + new SimpleDateFormat("HHmmssSSS").format(new Date()) + ".xlsx");
            XSSFWorkbook xwb = new XSSFWorkbook();
            XSSFSheet sheet = xwb.createSheet();
            //新建细胞样式
            CellStyle style = xwb.createCellStyle();
            //水平居中
            style.setAlignment(HorizontalAlignment.CENTER);
            //垂直居中
            style.setVerticalAlignment(VerticalAlignment.CENTER);

            for(int i = 0; i < list.size() + 1; i++){
                if(i == 0){
                    XSSFRow row = sheet.createRow(i);
                    for (int k = 0; k < title.size(); k++){
                        XSSFCell cell = row.createCell(k);
                        cell.setCellValue(title.get(k).get("key"));
                    }
                    row.setRowStyle(style);
                }else{
                    Map<String, Object> map = list.get(i - 1);
                    XSSFRow row = sheet.createRow(i);
                    for(int j = 0; j < title.size(); j++){
                        XSSFCell cell = row.createCell(j);
                        cell.setCellValue(StringUtil.objectToString(map.get(title.get(j).get("value"))));
                    }
                    row.setRowStyle(style);
                }
            }
            for (int m = 0; m < title.size(); m++){
                //设置每列的宽度自动
                sheet.autoSizeColumn(m);
            }

            FileOutputStream outputStream = new FileOutputStream(file);
            xwb.write(outputStream);
            outputStream.close();
            return file.getPath();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 导出数据库数据为excel文件
     * @param exceptPath
     * @param filename
     * @param title
     * @param rs
     * @return
     */
    public boolean except(String exceptPath, String filename, JSONArray title, ResultSet rs){
        try{
            File file = new File(exceptPath + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date()));
            if(!file.exists()){
                file.mkdirs();
            }
            file = new File(file,filename + new SimpleDateFormat("HHmmssSSS").format(new Date()) + ".xlsx");
            XSSFWorkbook xwb = new XSSFWorkbook();
            XSSFSheet sheet = xwb.createSheet();

            //1 表头样式
            XSSFCellStyle headerStyle = xwb.createCellStyle();
            //水平居中
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            //垂直居中
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            //设置边框
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);

            XSSFFont headerFont = xwb.createFont();
            //字体加粗
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            //2 表格样式
            XSSFCellStyle bodyStyle = xwb.createCellStyle();
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            XSSFRow row = sheet.createRow(0);
            row.setRowStyle(headerStyle);
            for (int k = 0; k < title.size(); k++){
                XSSFCell cell = row.createCell(k);
                cell.setCellValue(title.getJSONObject(k).getString("key"));
            }
            int i = 1;
            while(rs.next()){
                XSSFRow row1 = sheet.createRow(i);
                for (int k = 0; k < title.size(); k++){
                    XSSFCell cell = row1.createCell(k);
                    cell.setCellValue(rs.getString(title.getJSONObject(k).getString("value")));
                }
                i++;
            }
            for (int m = 0; m < title.size(); m ++){
                //设置每列的宽度自动
                sheet.autoSizeColumn(m, true);
            }

            FileOutputStream outputStream = new FileOutputStream(file);
            xwb.write(outputStream);
            outputStream.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
