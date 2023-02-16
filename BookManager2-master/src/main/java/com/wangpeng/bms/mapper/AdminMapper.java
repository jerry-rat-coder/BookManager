package com.wangpeng.bms.mapper;

import com.wangpeng.bms.model.Admin;
import com.wangpeng.bms.model.User;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper {
    int insert(Admin record);

    int insertSelective(Admin record);
    Admin selectByUsernameAndPasswordAndIsAdmin(@Param("username") String username,
                                               @Param("password") String password);
    Integer setPassword(Admin admin);
}