package com.newtrekwang.remotecontrol.bean;

/**
 * Created by You on 2017/12/10.
 */

public class UpdateUserpasswd {
    private String phoneNum;
    private String sessionId;
    private String oldPassword;
    private String newPassword;

    public UpdateUserpasswd(String phonenum, String sessionid, String oldpasswd,String newpasswd ) {

        this.newPassword = newpasswd;
        this.oldPassword = oldpasswd;
        this.phoneNum = phonenum;
        this.sessionId = sessionid;
    }

    public String getNewpasswd() {
        return newPassword;
    }

    public String getOldpasswd() {
        return oldPassword;
    }

    public String getSessionid() {
        return sessionId;
    }

    public String getPhonenum() {
        return phoneNum;
    }

    public void setNewpasswd(String newpasswd) {
        this.newPassword = newpasswd;
    }

    public void setOldpasswd(String oldpasswd) {
        this.oldPassword = oldpasswd;
    }

    public void setPhonenum(String phonenum) {
        this.phoneNum = phonenum;
    }

    public void setSessionid(String sessionid) {
        this.sessionId = sessionid;
    }

    @Override
    public String toString() {
        return "UpdateUserpasswd{" +
                "phoneNum='" + phoneNum + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
