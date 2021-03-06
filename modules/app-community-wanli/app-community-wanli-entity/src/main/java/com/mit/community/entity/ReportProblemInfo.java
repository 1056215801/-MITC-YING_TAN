package com.mit.community.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public class ReportProblemInfo {
    private Integer id;
    private String content;
    private String problemType;
    private String address;
    private int isOpen;
    private int status;
    private int mqlzd;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime gmtCreate;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime gmtModified;
    private List<ReportProblemPhotos> photos;
    private Integer userId;
    private String iconUrl;
    private String userName;
    private List<ReportProblemLzInfo>  lzdList;

    public List<ReportProblemLzInfo> getLzdList() {
        return lzdList;
    }

    public void setLzdList(List<ReportProblemLzInfo> lzdList) {
        this.lzdList = lzdList;
    }

    public int getMqlzd() {
        return mqlzd;
    }

    public void setMqlzd(int mqlzd) {
        this.mqlzd = mqlzd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProblemType() {
        return problemType;
    }

    public void setProblemType(String problemType) {
        this.problemType = problemType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    public List<ReportProblemPhotos> getPhotos() {
        return photos;
    }

    public void setPhotos(List<ReportProblemPhotos> photos) {
        this.photos = photos;
    }
}

