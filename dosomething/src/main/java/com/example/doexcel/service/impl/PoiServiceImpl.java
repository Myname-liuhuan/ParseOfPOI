package com.example.doexcel.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.doexcel.dao.PoiMapper;
import com.example.doexcel.poi.ExportExcel;
import com.example.doexcel.service.PoiService;

import com.example.utils.EncapsulationUtil;
import com.example.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huanliu
 */
@Service
public class PoiServiceImpl implements PoiService {

    @Autowired
    PoiMapper poiMapper;

    @Autowired
    ExportExcel exUtil;

    @Override
    public String generateExcelBySql(String sql){
        try{
            sql = "select * from messagelog order by create_time desc";
            List<Map<String, Object>> maps = poiMapper.queryBySql(sql);
            List<Map<String, String>> title = new ArrayList<>();
            {
                Map<String, String> map = new HashMap<>();
                map.put("key","消息类型");
                map.put("value", "MsgType");
                title.add(map);
            }
            {
                Map<String, String> map = new HashMap<>();
                map.put("key","消息创建时间");
                map.put("value", "CreateTime");
                title.add(map);
            }
            String filepath = exUtil.except("导出测试表", title,maps);

            System.out.println("文件已导出，路径为：" + filepath);
            return filepath;
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> doExcept(String jsonStr, String sql, String filename) {
        try{
            //查数据库
            List<Map<String, Object>> maps = poiMapper.queryBySql(sql);
            //将字符串解析为json数组
            JSONArray jArr = JSON.parseArray(jsonStr);
            String filepath = exUtil.except(filename == null ? "" : filename, jArr, maps);
            if (!StringUtil.isEmpty(filepath)){
                return EncapsulationUtil.toMap(1,filepath);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return EncapsulationUtil.toMap(0,"下载失败！");
    }

}
