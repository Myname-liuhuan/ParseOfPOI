package com.example.doexcel.service;

import com.example.doexcel.service.impl.UpLoadService;
import com.example.doexcel.utils.ExcelOperation;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 刘欢
 * @Date 2020/8/9
 */
@Service
public class UpLoadServiceImpl implements UpLoadService {


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
}
