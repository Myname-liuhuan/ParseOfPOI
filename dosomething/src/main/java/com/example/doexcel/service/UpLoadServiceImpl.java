package com.example.doexcel.service;

import com.example.doexcel.dao.UploadMapper;
import com.example.doexcel.service.impl.UpLoadService;
import com.example.doexcel.utils.ExcelOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 刘欢
 * @Date 2020/8/9
 */
@Service
public class UpLoadServiceImpl implements UpLoadService {

	@Autowired
	UploadMapper uploadMapper;

	@Override
	public String parseExcel(MultipartFile file, String tableName) throws IOException{
		if(file == null || tableName == null){
			return null;
		}
		ExcelOperation excelOperation = new ExcelOperation(file.getInputStream());
		String s1 = excelOperation.printCreateTableSql(tableName);
		String s2 = excelOperation.printInsertSql(tableName);
		return s1 + "\n" + s2;
	}


	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean insertSql(String sql) {
		//因为一个sql中同时有建表和插入操作数据库会报错，所以分开执行
		String createSql = sql.substring(0,sql.indexOf("insert"));
		String insertSql = sql.substring(sql.indexOf("insert"));
		uploadMapper.insertSql(createSql);
		Integer i = uploadMapper.insertSql(insertSql);
		if (i != null){
			return true;
		}
		return false;
	}
}
