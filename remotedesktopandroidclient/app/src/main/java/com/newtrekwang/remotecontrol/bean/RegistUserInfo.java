package com.newtrekwang.remotecontrol.bean;
/**
 * auther    : WJX
 * classname : RegistUserInfo
 * desc      : 注册信息实体类
 * createTime: 2017/7/24 14:29
 */
public class RegistUserInfo {
    //    手机号
    private  String phonenum;
    //    密码
    private String password;
//    用户名
    private String username;

    public RegistUserInfo(String phonenum, String password,String username) {
        this.phonenum = phonenum;
        this.password = password;
        this.username=username;
    }
    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "RegistUserInfo{" +
                "phonenum='" + phonenum + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
