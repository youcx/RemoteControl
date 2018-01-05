package com.newtrekwang.remotecontrol.bean;

/**
 * Created by You on 2017/11/5.
 */
public class UpdateUsername {
    private String phoneNum;
    private String sessionId;
    private String userName;
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

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    @Override
    public String toString() {
        return "UpdateUsername{" +
                "phoneNum='" + phoneNum + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    public UpdateUsername(String phonenum, String sessionid, String username) {
        this.phoneNum = phonenum;
        this.sessionId = sessionid;
        this.userName = username;
    }


}
