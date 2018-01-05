package com.newtrekwang.remotecontrol.bean;
/**
 * auther    : WJX
 * classname : RegistUserInfo
 * desc      : 注册信息实体类
 * createTime: 2017/7/24 14:29
 */
public class RegistUserInfo {
    //    手机号
    private  String phoneNum;
    //    密码
    private String password;
//    用户名
    private String userName;

    public RegistUserInfo(String phonenum, String password,String username) {
        this.phoneNum = phonenum;
        this.password = password;
        this.userName=username;
    }
    public String getPhonenum() {
        return phoneNum;
    }

    public void setPhonenum(String phonenum) {
        this.phoneNum = phonenum;
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
                "phoneNum='" + phoneNum + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }
}
