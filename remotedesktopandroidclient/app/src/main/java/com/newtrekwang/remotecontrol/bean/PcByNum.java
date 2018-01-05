package com.newtrekwang.remotecontrol.bean;

/**
 * Created by You on 2017/12/9.
 */

public class PcByNum {
    private String phoneNum;
    private String sessionId;
    private String uid;
    private String pcName;

    public PcByNum(String phonenum,String pcnum,String sessionid,String pcname) {
        this.pcName = pcname;
        this.uid = pcnum;
        this.phoneNum = phonenum;
        this.sessionId = sessionid;
    }

    public String getPcname() {
        return pcName;
    }

    public void setPcname(String pcname) {
        this.pcName = pcname;
    }

    public void setPcnum(String pcnum) {
        this.uid = pcnum;
    }

    public void setPhonenum(String phonenum) {
        this.phoneNum = phonenum;
    }

    public void setSessionid(String sessionid) {
        this.sessionId = sessionid;
    }

    public String getPcnum() {
        return uid;
    }

    public String getPhonenum() {
        return phoneNum;
    }

    public String getSessionid() {
        return sessionId;
    }

    @Override
    public String toString() {
        return "PcByNum{" +
                "phoneNum='" + phoneNum + '\'' +
                ",uid='"+uid+'\''+
                ", sessionId='" + sessionId + '\'' +
                ", pcName='" + pcName + '\'' +
                '}';
    }
}
