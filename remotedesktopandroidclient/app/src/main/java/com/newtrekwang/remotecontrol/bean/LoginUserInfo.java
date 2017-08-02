package com.newtrekwang.remotecontrol.bean;
/**
 * auther    : WJX
 * classname : LoginUserInfo
 * desc      : 登录信息实体类
 * createTime: 2017/7/24 14:33
 */
public class LoginUserInfo {
//    手机号
    private  String phonenum;
//    密码
    private String password;

    public LoginUserInfo(String phone, String password) {
        this.phonenum = phone;
        this.password = password;
    }

    public String getPhone() {
        return phonenum;
    }

    public void setPhone(String phone) {
        this.phonenum = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginUserInfo{" +
                "phonenum='" + phonenum + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
