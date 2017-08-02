package com.newtrekwang.remotecontrol.bean;
/**
 * auther    : WJX
 * classname : SessionUser
 * desc      : 包含Session信息的实体类
 * createTime: 2017/7/24 14:29
 */
public class SessionUser {
    private String phonenum;
    private String sessionid;

    public SessionUser(String phonenum, String sessionid) {
        this.phonenum = phonenum;
        this.sessionid = sessionid;
    }

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

    @Override
    public String toString() {
        return "SessionUser{" +
                "phonenum='" + phonenum + '\'' +
                ", sessionid='" + sessionid + '\'' +
                '}';
    }
}
