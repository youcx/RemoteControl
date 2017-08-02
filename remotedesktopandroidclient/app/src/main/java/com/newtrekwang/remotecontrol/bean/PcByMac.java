package com.newtrekwang.remotecontrol.bean;
/**
 * auther    : WJX
 * classname : PcByMac
 * desc      : 通过mac添加PC
 * createTime: 2017/7/24 14:30
 */
public class PcByMac {
    private String phonenum;
    private String mac;
    private String sessionid;

    public PcByMac(String phonenum, String mac, String sessionid) {
        this.phonenum = phonenum;
        this.mac = mac;
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

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    @Override
    public String toString() {
        return "PcByMac{" +
                "phonenum='" + phonenum + '\'' +
                ", mac='" + mac + '\'' +
                ", sessionid='" + sessionid + '\'' +
                '}';
    }
}
