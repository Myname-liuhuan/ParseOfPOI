package com.example.doexcel;

import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author 刘欢
 * @Date 2020/7/23
 */
@ServletComponentScan
@SpringBootApplication
public class DoStart {
    public static void main(String[] args) {
        SpringApplication.run(DoStart.class,args);
    }
}
