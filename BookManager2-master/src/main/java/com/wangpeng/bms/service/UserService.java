package com.wangpeng.bms.service;

import com.wangpeng.bms.model.Admin;
import com.wangpeng.bms.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User login(User user);

    void saveUser(String token, User user);

    User getUser(String token);

    void removeUser(String token);

    Map<User,Integer> register(String nickname,String username,String email,String phone, String password,String avatarurl);

    void setPassword(Integer id, String password);

    Integer getCount();

    List<User> queryUsers();

    int getSearchCount(Map<String, Object> searchParam);

    List<User> searchUsersByPage(Map<String, Object> params);

    Integer addUser(User user);

    Integer deleteUser(User user);

    Integer deleteUsers(List<User> users);

    Integer updateUser(User user);
    User getUserById(Integer id);
    Integer setPassword(Admin admin);
    Integer updateImg(String avatar,Integer id);
}
