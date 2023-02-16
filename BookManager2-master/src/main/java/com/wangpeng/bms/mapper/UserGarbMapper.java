package com.wangpeng.bms.mapper;

import com.wangpeng.bms.model.UserGarb;

public interface UserGarbMapper {
    int deleteByPrimaryKey(Integer userid);

    int insert(UserGarb record);

    int insertSelective(UserGarb record);

    UserGarb selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(UserGarb record);

    int updateByPrimaryKey(UserGarb record);
}