package com.newtrekwang.remotecontrol.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class Login_json_  implements Serializable {

    private String phoneNum;
    private String userName;
    private String sessionId;
    public void setPhonenum(String phonenum) {
        this.phoneNum = phonenum;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public void setSessionid(String sessionid) {
        this.sessionId = sessionid;
    }



    @Override
    public String toString() {
        return "Login_json_{" +
                "phoneNum='" + phoneNum + '\'' +
                ", userName='" + userName + '\'' +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }

    public String getPhonenum() {
        return phoneNum;
    }

    public String getUsername() {
        return userName;
    }

    public String getSessionid() {
        return sessionId;
    }



}
