package com.example.doexcel.utils;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.*;

/**
 * POI操作的工程类
 * @author liuhuan
 */
public class PoiFactory {
    POIFSFileSystem pfs = null;

    PoiFactory(String filePath){
        //File file = new File("E:/resource/20200717.xls");
        try {
            File file = new File(filePath);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            pfs = new POIFSFileSystem(bis);
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    PoiFactory(InputStream inputStream){
        try {
            pfs = new POIFSFileSystem(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void close(){
        try {
            if(pfs != null){
                pfs.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
