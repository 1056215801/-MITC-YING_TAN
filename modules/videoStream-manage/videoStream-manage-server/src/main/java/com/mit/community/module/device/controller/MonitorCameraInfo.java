package com.mit.community.module.device.controller;


import com.sun.jna.NativeLong;

public class MonitorCameraInfo {

    private NativeLong id;

    private String ip;
    private int port;
    private String userName;
    private String password;
    private String channelNo;

    public MonitorCameraInfo(){}
    public MonitorCameraInfo(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public NativeLong getId() {
        return id;
    }

    public void setId(NativeLong id) {
        this.id = id;
    }

    public String getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }
}
