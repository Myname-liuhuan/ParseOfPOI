package com.example.doexcel.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @author 刘欢
 * @Date 2020/8/9
 */
public interface UpLoadService {

	/**
	 *将前台传来的excel流解析
	 * @param file
	 * @param tableName
	 * @return
	 * @throws IOException
	 */
    Map<String, Object> parseExcel(MultipartFile file, String tableName) throws Exception;

	/**
	 * 根据sql向数据库中插入数据
	 * @param sql
	 * @return 成功或者失败
	 */
	boolean insertSql(String sql);
}
