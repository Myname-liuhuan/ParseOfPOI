package com.example.doexcel.service.impl;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    String parseExcel(MultipartFile file, String tableName) throws IOException;

	/**
	 * 根据sql向数据库中插入数据
	 * @param sql
	 * @return 成功或者失败
	 */
	boolean insertSql(String sql);
}
