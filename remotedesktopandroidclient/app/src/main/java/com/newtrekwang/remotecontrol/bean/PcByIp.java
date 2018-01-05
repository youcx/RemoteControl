package com.newtrekwang.remotecontrol.bean;
/**
 * auther    : WJX
 * classname : PcByIp
 * desc      : 通过ip添加PC
 * createTime: 2017/7/24 14:33
 */
public class PcByIp {
    private String phonenum;
    private String ip;
    private String sessionid;
    private String pcname;

    public PcByIp(String phonenum, String ip, String sessionid, String pcname) {
        this.phonenum = phonenum;
        this.ip = ip;
        this.sessionid = sessionid;
        this.pcname = pcname;
    }


    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    @Override
    public String toString() {
        return "PcByIp{" +
                "phonenum='" + phonenum + '\'' +
                ", ip='" + ip + '\'' +
                ", sessionid='" + sessionid + '\'' +
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
