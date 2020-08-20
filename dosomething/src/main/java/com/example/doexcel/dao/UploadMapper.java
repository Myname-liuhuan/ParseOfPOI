package com.example.doexcel.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author 刘欢
 * @Date 2020/8/12
 */
@Mapper
public interface UploadMapper {

	/**
	 * 将传来的插入语句执行
	 * @param sql
	 * @return
	 */
	Integer insertSql(String sql);

}
