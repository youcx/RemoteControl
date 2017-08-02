package com.newtrekwang.remotecontrol.bean;

import java.io.Serializable;

/**
 * 设备信息
 */
public class Device implements Serializable{
//    IP
    private String ip;
//    名字
    private String name;
//    端口
    private int port;

    public Device(String ip, String name, int port) {
        this.ip = ip;
        this.name = name;
        this.port = port;
    }

    public Device() {
    }

    @Override
    public String toString() {
        return "Device{" +
                "ip='" + ip + '\'' +
                ", name='" + name + '\'' +
                ", port=" + port +
                '}';
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
