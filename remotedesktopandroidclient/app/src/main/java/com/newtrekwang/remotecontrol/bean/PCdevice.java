package com.newtrekwang.remotecontrol.bean;

import java.io.Serializable;
/**
 * auther    : WJX
 * classname : PCdevice
 * desc      : PC信息类
 * createTime: 2017/7/24 14:30
 */
public class PCdevice implements Serializable {

    /**
     * flag : 0(1在线，0离线)
     * ipaddr : 119
     * mac : ccc
     */

    private int flag;
    private String uid;
    private String mac;
    private String pcName;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String pcnum) {
        this.uid = pcnum;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public String toString() {
        return "PCdevice{" +
                "flag=" + flag +
                ", uid='" + uid + '\'' +
                ", mac='" + mac + '\'' +
                ", pcName='" + pcName + '\'' +
                '}';
    }

    public String getPcname() {
        return pcName;
    }

    public void setPcname(String pcname) {
        this.pcName = pcname;
    }
}
