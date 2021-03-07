package com.example.doexcel.poi;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

/**
 * 测试读写excel文件的类
 * @author liuhuan
 */
public class ExcelTest {
    //excel中列名所在index( = 行数 - 1)
    private int colNameIndex;

    private void printCreateTableSql(String tableName, int colNameIndex){
        this.colNameIndex = colNameIndex;
        System.out.print(" create table " + tableName + "(" +
                "");
    }

    public void test1(){
        try {
            File file = new File("E:/resource/20200717.xls");
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            POIFSFileSystem pfs = new POIFSFileSystem(bis);
            HSSFWorkbook wb = new HSSFWorkbook(pfs);
            //System.out.println(wb.getNumberOfSheets());
            //遍历sheet
            for (int i = 0; i < wb.getNumberOfSheets(); i++){
                //获取当前sheet对象
                HSSFSheet hs = wb.getSheetAt(i);
                //获取第一行有数据的行标，从0开始
                System.out.println(hs.getFirstRowNum());
                //得到最后第一行和最后一行的索引
                int startIndex = hs.getFirstRowNum();
                int endIndex = hs.getLastRowNum();
                for (int rowIndex = startIndex; rowIndex <= endIndex; rowIndex++){
                    Row row = hs.getRow(rowIndex);
                    if (row != null){
                        int colIndexStart = row.getFirstCellNum();
                        int colIndexEnd = row.getLastCellNum();
                        for (int j = colIndexStart; j <= colIndexEnd;j++){
                            Cell cell = row.getCell(j);
                            if (cell != null){
                                System.out.print("  " + cell.toString());
                            }
                        }
                        System.out.println();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void test2(){
        HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();
        hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        String[] s = null;
        try {
            s = PinyinHelper.toHanyuPinyinStringArray('还');
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(s));
    }

    public void test3(){
        ExcelOperation excelOperation = new ExcelOperation("E:/resource/20200717.xls",1);
        String s1 = excelOperation.printCreateTableSql("school");
        String s2 = excelOperation.printInsertSql("school");
        System.out.println(s1 + "\n" + s2);
    }
}
