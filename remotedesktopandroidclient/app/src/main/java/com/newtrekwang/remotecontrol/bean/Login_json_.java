package com.newtrekwang.remotecontrol.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class Login_json_  implements Serializable {
    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    private String phonenum;
    private String username;

    @Override
    public String toString() {
        return "Login_json_{" +
                "phonenum='" + phonenum + '\'' +
                ", username='" + username + '\'' +
                ", sessionid='" + sessionid + '\'' +
                '}';
    }

    public String getPhonenum() {
        return phonenum;
    }

    public String getUsername() {
        return username;
    }

    public String getSessionid() {
        return sessionid;
    }

    private String sessionid;

}
