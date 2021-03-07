package com.example.doexcel.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author huanliu
 */
@Mapper
public interface PoiMapper {

    /**
     * 通过sql查出任意表数据（用于导出Excel）
     * @param sql
     * @return
     */
    List<Map<String, Object>> queryBySql(String sql);

}
