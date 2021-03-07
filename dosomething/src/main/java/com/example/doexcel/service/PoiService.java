package com.example.doexcel.service;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author huanliu
 */
public interface PoiService {

    /**
     * 通过sql生成Excel文件，并返回生成文件的路径，名称
     * @param sql SQL语句
     * @return 文件路径
     */
    String generateExcelBySql(String sql);

    /**
     * 导出数据为Excel文件并返回给浏览器下载
     * @param jsonStr
     * @param sql
     * @param filename
     * @param response
     * @return
     */
    Map<String, Object> doExcept(String jsonStr, String sql, String filename);
}
