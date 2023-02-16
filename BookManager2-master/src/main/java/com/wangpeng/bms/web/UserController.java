package com.wangpeng.bms.web;

import com.wangpeng.bms.model.Admin;
import com.wangpeng.bms.model.User;
import com.wangpeng.bms.service.UserService;
import com.wangpeng.bms.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    // 登录
    @RequestMapping(value = "/login")
    public Map<String, Object> login(@RequestBody User user) {
        // 登录
        User userObj = userService.login(user);
        if(userObj == null) {   // 账号或密码错误
            // 返回结果对象
            return MyResult.getResultMap(420, "账号或密 码错误");
        } else {    // 账号密码正确
            // 创建token
            String token = TokenProcessor.getInstance().makeToken();
            // 保存到Redis
            userService.saveUser(token, userObj);
            // 返回结果对象
            return MyResult.getResultMap(200, "登录成功",
                    new HashMap<String, String>(){{ put("token", token); }});
        }
    }

    // 查看用户信息
    @RequestMapping(value = "/info")
    public Map<String, Object> info(String token) {
        // 从redis中取用户
//        System.out.println("redis测试开始");
//        System.out.println();
        User user = userService.getUser(token);
        if(user == null) {  // 获取失败
            return MyResult.getResultMap(420, "获取用户信息失败");
        } else {    // 获取成功
            return MyResult.getResultMap(200, "获取用户信息成功", user);
        }
    }

    // 退出登录
    @RequestMapping(value = "/logout")
    public Map<String, Object> logout(String token) {
        // 从redis中移除用户
        userService.removeUser(token);
        return MyResult.getResultMap(200, "退出登录成功" );
    }

    // 注册
    @RequestMapping(value = "/register")
    public Integer register(String nickname,String username,String email,String phone, String password,String avatarurl){
        Map<User,Integer> user = userService.register(nickname,username,email,phone, password,avatarurl);
//        System.out.println(avatarurl);
//        System.out.println(12313123);
        User userObj = new User();
        Integer ans = 0;
        for (Map.Entry<User,Integer> entry : user.entrySet()) {
//            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
            if(entry != null) {
                userObj = entry.getKey();
                ans = entry.getValue();
                break;
            }
        }
        // 创建token
        String token = TokenProcessor.getInstance().makeToken();
        // 保存到Redis
        userService.saveUser(token, userObj);
        // 返回结果对象
//        return MyResult.getResultMap(200, "登录成功",
//                new HashMap<String, String>(){{ put("token", token); }});
        if(userObj!=null)return ans;
        return 0;
    }

    // 修改密码
    @RequestMapping(value = {"/alterPassword", "reader/alterPassword"})
    public Integer alterPassword(Integer userid, String username, Byte isadmin, String oldPassword, String newPassword){
        User userObj = new User();
        userObj.setUserid(userid);
        userObj.setUsername(username);
        userObj.setUserpassword(oldPassword);
        userObj.setIsadmin(isadmin);
        User user = userService.login(userObj);
        if(user == null) {  //旧密码不正确
            return 0;
        } else {    //旧密码正确，设置新密码
            if(isadmin > 0){
                Admin admin = new Admin();
                admin.setAdminid(userid);
                admin.setAdminname(username);
                admin.setAdminpassword(newPassword);
                userService.setPassword(admin);
            }
            else userService.setPassword(userObj.getUserid(), newPassword);
            return 1;
        }
    }

    // 获得数量
    @GetMapping(value = "/getCount")
    public Integer getCount(){
        return userService.getCount();
    }

    // 查询所有用户
    @GetMapping(value = "/queryUsers")
    public List<User> queryUsers(){
        return userService.queryUsers();
    }

    // 分页查询用户 params: {page, limit, username}
    @GetMapping(value = "/queryUsersByPage")
    public Map<String, Object> queryUsersByPage(@RequestParam Map<String, Object> params){
        MyUtils.parsePageParams(params);
        int count = userService.getSearchCount(params);
        List<User> users = userService.searchUsersByPage(params);
//        for(User it : users){
//            System.out.println(it.getEmail());
//        }
        return MyResult.getListResultMap(0, "success", count, users);
    }

    // 添加用户
    @PostMapping(value = "/addUser")
    public Integer addUser(@RequestBody User user){
        user.setcountVio(0);
        user.setIsadmin((byte) 0);
        return userService.addUser(user);
    }

    // 删除用户
    @DeleteMapping(value = "/deleteUser")
    public Integer deleteUser(@RequestBody User user){
        return userService.deleteUser(user);
    }

    // 删除一些用户
    @DeleteMapping(value = "/deleteUsers")
    public Integer deleteUsers(@RequestBody List<User> users){
        return userService.deleteUsers(users);
    }

    // 更新用户
    @RequestMapping(value = "/updateUser")
    public Integer updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }



    //恢复用户
    @RequestMapping(value = "/RecoverUser")
    public Integer recoverUser(@RequestBody User user){

        user.setcountVio(0);
        int count = userService.updateUser(user);
        return count;
    }
    @RequestMapping(value = "/query")
    public User getid(Integer id){
        User ans = userService.getUserById(id);
        return ans;
    }
    @RequestMapping(value = "/UpImg")
    public Integer img(String avatar,Integer id){
        int ans =  userService.updateImg(avatar,id);
//        System.out.println(ans);
        return ans;
    }
}
