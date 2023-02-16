package com.wangpeng.bms.service.impl;

import com.wangpeng.bms.mapper.AdminMapper;
import com.wangpeng.bms.mapper.UserMapper;
import com.wangpeng.bms.model.Admin;
import com.wangpeng.bms.model.User;
import com.wangpeng.bms.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private AdminMapper adminMapper;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public User login(User user) {
        Byte isadmin = user.getIsadmin();
        if(isadmin > 0){
            Admin admin = adminMapper.selectByUsernameAndPasswordAndIsAdmin(user.getUsername(), user.getUserpassword());
            System.out.println(admin);
            if(admin == null)return null;
            User adm = new User();
            adm.setcountVio(0);
            adm.setIsadmin((byte) 1);
            adm.setUserid(admin.getAdminid());
            adm.setUsername(admin.getAdminname());
            adm.setUserpassword(adm.getUserpassword());
            adm.setNickname("管理员模式");
            return adm;
        }
        else {
            User reader = userMapper.selectByUsernameAndPasswordAndIsAdmin(user.getUsername(), user.getUserpassword());
            if(reader == null)return null;
            reader.setIsadmin((byte)0);
            return reader;
        }
    }

    @Override
    public void saveUser(String token, User user) {
        // 设置redisTemplate对象key的序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // key是token，value是用户保存到redis中，超时时间1小时
        redisTemplate.opsForValue().set(token, user, 1, TimeUnit.HOURS);
    }

    @Override
    public User getUser(String token) {
        // 根据token得到user
        return (User) redisTemplate.opsForValue().get(token);
    }

    @Override
    public void removeUser(String token) {
        // 移除token
        redisTemplate.delete(token);
    }

    @Override
    public Map<User,Integer> register(String nickname,String username,String email,String phone, String password,String avatarurl) {
        Map<User,Integer> mp = new HashMap<>();
        User tmp = userMapper.selectBy(username,email,phone,nickname);
//        User em =
        if(tmp != null) {
            if(tmp.getUsername().equals(username) ){
                mp.put(tmp,0);
            }
            else if(tmp.getNickname().equals(nickname)){
                mp.put(tmp,-1);
            }
            else if(tmp.getEmail().equals(email)){
                mp.put(tmp,-2);
            }
            else if(tmp.getPhone().equals(phone)){
                mp.put(tmp,-3);
            }
            return mp;//账号重复
        }

        User user = new User();
        user.setUsername(username);
        user.setUserpassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setNickname(nickname);
        user.setIsadmin((byte) 0);
        user.setcountVio(0);
        user.setAvatarurl(avatarurl);
        Integer ans = userMapper.insertSelective(user);
        if(ans>0){
            mp.put(user,1);
            return mp;
        }
        else return null;
//        return userMapper.insertSelective(user);
    }

    @Override
    public void setPassword(Integer id, String password) {
        User user = new User();
        user.setUserid(id);
        user.setUserpassword(password);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public Integer getCount() {
        return userMapper.selectCount();
    }

    @Override
    public List<User> queryUsers() {
        return userMapper.selectAll();
    }

    @Override
    public int getSearchCount(Map<String, Object> params) {
        return userMapper.selectCountBySearch(params);
    }

    @Override
    public List<User> searchUsersByPage(Map<String, Object> params) {
        return userMapper.selectBySearch(params);
    }

    @Override
    public Integer addUser(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public Integer deleteUser(User user) {
        return userMapper.deleteByPrimaryKey(user.getUserid());
    }

    @Override
    public Integer deleteUsers(List<User> users) {
        int count = 0;
        for(User user : users) {
            count += deleteUser(user);
        }
        return count;
    }

    @Override
    public Integer updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }
    @Override
    public User getUserById(Integer id){
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer setPassword(Admin admin){
        return adminMapper.setPassword(admin);
    }
    @Override
    public Integer updateImg(String avatar,Integer id){
        return userMapper.updateimg(avatar,id);
    }
}
