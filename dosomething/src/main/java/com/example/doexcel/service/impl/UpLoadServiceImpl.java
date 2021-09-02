package com.example.doexcel.service.impl;

import com.example.doexcel.dao.UploadMapper;
import com.example.doexcel.service.UpLoadService;
import com.example.doexcel.poi.ExcelOperation;
import com.example.doexcel.poi.ExcelOperationXlsx;
import com.example.utils.EncapsulationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author 刘欢
 * @Date 2020/8/9
 */
@Service
public class UpLoadServiceImpl implements UpLoadService {

	@Autowired
	UploadMapper uploadMapper;

	@Override
	public Map<String, Object> parseExcel(MultipartFile file, String tableName) throws Exception{
		if(file == null || tableName == null){
			return  EncapsulationUtil.toMap(0,"传入的参数不能为空");
		}
		//先判断传来的是xls还是xlsx,再用不同的类去解析
		final int fileNameMinLength = 4;
		String filename = file.getOriginalFilename();
		System.out.println(filename);
		if (filename.length() > fileNameMinLength){
			String suffixName = filename.substring(filename.lastIndexOf("."));
			if(".xls".equals(suffixName)){
				ExcelOperation excelOperation = new ExcelOperation(file.getInputStream());
				String s1 = excelOperation.printCreateTableSql(tableName);
				String s2 = excelOperation.printInsertSql(tableName);
				return EncapsulationUtil.toMap(1,s1 + "\n" + s2);
			}else if(".xlsx".equals(suffixName)){
				ExcelOperationXlsx excelOperationXlsx = new ExcelOperationXlsx(file.getInputStream());
				String s1 = excelOperationXlsx.printCreateTableSql(tableName);
				String s2 = excelOperationXlsx.printInsertSql(tableName);
				return EncapsulationUtil.toMap(1,s1 + "\n" + s2);
			}
		}
		return EncapsulationUtil.toMap(0,"解析excel出错");
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
