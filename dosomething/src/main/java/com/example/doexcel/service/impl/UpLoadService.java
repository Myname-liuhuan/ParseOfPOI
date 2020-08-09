package com.example.doexcel.service.impl;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 刘欢
 * @Date 2020/8/9
 */
public interface UpLoadService {

	/**
	 *
	 * @param file
	 * @param tableName
	 * @return
	 * @throws IOException
	 */
    String parseExcel(MultipartFile file, String tableName) throws IOException;
}
