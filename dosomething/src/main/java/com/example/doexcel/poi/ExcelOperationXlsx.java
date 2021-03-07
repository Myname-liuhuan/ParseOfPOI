package com.example.doexcel.poi;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 操作Excel的类 2007版本 xlsx
 * @author 刘欢
 * @Date 2020/11/16
 */
public class ExcelOperationXlsx {
	private XSSFWorkbook xwb = null;
	private List<String> list = new ArrayList<>();
	private int colIndex;
	private int sheetIndex = 0;

	/**
	 * 该构造方法设置默认值
	 * @param inputStream
	 */
	public ExcelOperationXlsx(InputStream inputStream){
		try {
			xwb = new XSSFWorkbook(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		colIndex = 0;//默认excel的第一行为列名
		getColList(0);
	}

	public void setSheetIndex(int sheetIndex){
		this.sheetIndex = sheetIndex;
	}


	/**
	 * 打印建表语句
	 * @param tableName
	 */
	public String printCreateTableSql(String tableName){
		if (tableName == null || "".equals(tableName)){
			System.err.println(ExcelOperation.class.getName() + "printCreateTableSql:" + " 表名不能为空");
			return null;
		}
		if (list == null || list.size() <= 0){
			System.err.println(ExcelOperation.class.getName() + "printCreateTableSql:" + " 列名不能为空");
		}
		String createSql = " create table " + tableName + "(\nid VARCHAR(200),\n";
		for (int i = 0; i < list.size(); i++) {
			createSql += getFirstSpell(list.get(i)) + " VARCHAR(200) COMMENT '" + list.get(i) + "'" + (i == list.size() - 1?"":",\n");
		}
		createSql += ");";
		return createSql;
	}

	/**
	 * 打印插入语句
	 * @param tableName
	 */
	public String printInsertSql(String tableName){
		if (tableName == null || "".equals(tableName)){
			System.err.println(ExcelOperation.class.getName() + "printInsertSql:" + " 表名不能为空");
			return null;
		}
		if (list == null || list.size() <= 0){
			System.err.println(ExcelOperation.class.getName() + "printInsertSql:" + " 列名不能为空");
			return null;
		}
		StringBuffer insertSql = new StringBuffer(" insert into " + tableName + "(id,");
		for (String s : list) {
			insertSql.append(getFirstSpell(s) + ",");
		}
		//去掉多余的1个逗号(,)
		insertSql.deleteCharAt(insertSql.lastIndexOf(","));
		insertSql.append(") values\n('");

		Sheet sheet = xwb.getSheetAt(sheetIndex);
		int rowStartIndex = colIndex + 1;
		int rowEndIndex = sheet.getLastRowNum();
		for (int i = rowStartIndex; i <= rowEndIndex; i++){
			insertSql.append(UUID.randomUUID().toString().replaceAll("-","") + "','");
			Row row = sheet.getRow(i);
			int colStartIndex = row.getFirstCellNum();
			int colEndIndex = list.size();
			for (int m = colStartIndex; m < colEndIndex; m++){
				Cell cell = row.getCell(m);
				String s = cell==null?"":cell.toString();
				insertSql.append(s + "','");
			}
			//去除最后多余的逗号和单引号,'
			insertSql.delete(insertSql.length()-2,insertSql.length());
			insertSql.append("),\n('");
		}
		insertSql.delete(insertSql.lastIndexOf(","),insertSql.length());

		return insertSql.toString();
	}


	/**
	 * 根据列明所在列获取列名集合
	 * @param colNameIndex
	 * @return
	 */
	public List<String> getColList(int colNameIndex){
		List<String> list = new ArrayList<>();

		if (colNameIndex == -1){
			return null;
		}
		//先读取列名
		Sheet sheet = xwb.getSheetAt(sheetIndex);
		Row row = sheet.getRow(colNameIndex);
		int startColIndex = row.getFirstCellNum();
		int endColIndex = row.getLastCellNum();
		for (int i = startColIndex; i <= endColIndex; i++){
			Cell cell = row.getCell(i);
			if (cell != null){
				//去首尾空格
				String s = cell.toString().trim();
				//去中间空格
				while(true){
					int index = s.indexOf(" ");
					if (index > -1){
						s = s.substring(0,index) + s.substring(index + 1);
					}else{
						break;
					}
				}
				list.add(s);
			}
		}
		this.list = list;
		return list;
	}

	/**
	 * 获取列名的每个汉字对应拼音的首字母
	 * @param colName
	 * @return
	 */
	private String getFirstSpell(String colName){
		HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();
		hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		char[] names = colName.toCharArray();

		StringBuffer sb = new StringBuffer();
		try {
			for (char name: names){
				String[] ss = PinyinHelper.toHanyuPinyinStringArray(name);
				if (ss != null){
					sb.append(ss[0].substring(0,1));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
