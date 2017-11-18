package com.newtrekwang.remotecontrol.bean;

/**
 * Created by You on 2017/11/5.
 */
public class UpdateUsername {
    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UpdateUsername{" +
                "phonenum='" + phonenum + '\'' +
                ", sessionid='" + sessionid + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public UpdateUsername(String phonenum, String sessionid, String username) {
        this.phonenum = phonenum;
        this.sessionid = sessionid;
        this.username = username;
    }

    private String phonenum;
    private String sessionid;
    private String username;
}
