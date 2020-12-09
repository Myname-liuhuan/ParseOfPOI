package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 刘欢
 * @Date 2020/11/28
 */
@MapperScan("com.example.mapper")
@SpringBootApplication
public class ControlStart {
	public static void main(String[] args) {
		SpringApplication.run(ControlStart.class,args);
	}
}
