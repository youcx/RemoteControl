package com.newtrekwang.remotecontrol.bean;
/**
 * auther    : WJX
 * classname : SessionUser
 * desc      : 包含Session信息的实体类
 * createTime: 2017/7/24 14:29
 */
public class SessionUser {
    private String phoneNum;
    private String sessionId;

    public SessionUser(String phonenum, String sessionid) {
        this.phoneNum = phonenum;
        this.sessionId = sessionid;
    }

    public String getPhonenum() {
        return phoneNum;
    }

    public void setPhonenum(String phonenum) {
        this.phoneNum = phonenum;
    }

    public String getSessionid() {
        return sessionId;
    }

    public void setSessionid(String sessionid) {
        this.sessionId = sessionid;
    }

    @Override
    public String toString() {
        return "SessionUser{" +
                "phonenum='" + phoneNum + '\'' +
                ", sessionid='" + sessionId + '\'' +
                '}';
    }
}
