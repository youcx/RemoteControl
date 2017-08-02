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
     * flag : 0
     * ipaddr : 119
     * mac : ccc
     */

    private int flag;
    private String ipaddr;
    private String mac;
    private String pcname;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
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
                ", ipaddr='" + ipaddr + '\'' +
                ", mac='" + mac + '\'' +
                ", pcname='" + pcname + '\'' +
                '}';
    }

    public String getPcname() {
        return pcname;
    }

    public void setPcname(String pcname) {
        this.pcname = pcname;
    }
}
