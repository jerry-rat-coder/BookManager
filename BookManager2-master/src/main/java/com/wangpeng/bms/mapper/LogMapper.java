package com.wangpeng.bms.mapper;

import com.wangpeng.bms.model.Log;

import java.util.List;
import java.util.Map;

public interface LogMapper {
    int deleteByPrimaryKey(Integer logId);

    int insert(Log record);

    int insertSelective(Log record);

    Log selectByPrimaryKey(Integer logId);

    int updateByPrimaryKeySelective(Log record);

    int updateByPrimaryKey(Log record);

    List<Log> selectBySearch(Map<String, Object> params);

    Integer selectCount(Map<String, Object> params);

    Integer selectCounts();

    List<Log> selectAll();
}