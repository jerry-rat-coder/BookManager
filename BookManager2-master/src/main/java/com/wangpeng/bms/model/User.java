package com.wangpeng.bms.model;

import java.io.Serializable;

public class User implements Serializable {
    private Integer userid;

    private String username;

    private String userpassword;

    private Byte isadmin;

    private Integer countvio;
    private String avatarurl;

    public String getAvatarurl() {
        return avatarurl;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }

    private String email;
    private String phone;
    private String nickname;
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public Byte getIsadmin() {
        return isadmin;
    }

    public void setIsadmin(Byte isadmin) {
        this.isadmin = isadmin;
    }

    public Integer getcountVio() {
        return countvio;
    }

    public void setcountVio(Integer countvio) {
        this.countvio = countvio;
    }
}