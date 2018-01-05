package com.newtrekwang.remotecontrol.bean;

/**
 * Created by You on 2017/12/22.
 */

public class DeletePcByUID {
    private String phoneNum;
    private String uid;
    private String sessionId;
    @Override
    public String toString() {
        return "DeletePcByUID{" +
                "uid='" + uid + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }


    public DeletePcByUID(String phonenum, String sessionid, String uid) {
        this.uid = uid;
        this.phoneNum = phonenum;
        this.sessionId = sessionid;
    }

    public String getSessionid() {
        return sessionId;
    }

    public void setSessionid(String sessionid) {
        this.sessionId = sessionid;
    }

    public String getPhonenum() {
        return phoneNum;
    }

    public void setPhonenum(String phonenum) {
        this.phoneNum = phonenum;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


}
