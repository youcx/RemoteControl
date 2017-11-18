package com.newtrekwang.remotecontrol.http;

/**
 * Created by You on 2017/11/5.
 */
public class DeletePcByMac {
    private String phonenum;
    private String mac;
    private String sessionid;
    @Override
    public String toString() {
        return "DeletePcByMac{" +
                "mac='" + mac + '\'' +
                ", phonenum='" + phonenum + '\'' +
                ", sessionid='" + sessionid + '\'' +
                '}';
    }


    public DeletePcByMac(String mac, String phonenum, String sessionid) {
        this.mac = mac;
        this.phonenum = phonenum;
        this.sessionid = sessionid;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }


}
