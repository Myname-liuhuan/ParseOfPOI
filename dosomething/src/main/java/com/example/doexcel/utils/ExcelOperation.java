package com.example.doexcel.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 待处理问题：
 * 1,Cell为属性名称或者属性值时为空,是否会造成列和对应值错位
 * 2,如果字段名得到的拼音首字符冲突该如何解决
 * 操作Excel的类 2003版本 xls
 * @author liuhuan
 */
public class ExcelOperation extends PoiFactory {
    /**
     * 给定本工具当前创建对象的使用情形
     * upload 在文件上传的业务情形下使用
     * jar
     */
    public static int UPLOAD = 0;
    public static int JAR = 1;

    private HSSFWorkbook hwb = null;
    private List<String> list = new ArrayList<>();
    private int colIndex;
    private int sheetIndex = 0;

    public ExcelOperation(String filePath, int colNameIndex) {
        super(filePath);
        try {
            hwb = new HSSFWorkbook(pfs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        colIndex = colNameIndex;
        getColList(colNameIndex);
    }

    /**
     * 该构造方法设置默认值
     * @param inputStream
     */
    public ExcelOperation(InputStream inputStream){
        super(inputStream);
        try {
            hwb = new HSSFWorkbook(pfs);
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

        Sheet sheet = hwb.getSheetAt(sheetIndex);
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
        Sheet sheet = hwb.getSheetAt(sheetIndex);
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
