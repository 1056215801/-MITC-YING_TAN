package com.mit.community.entity.com.mit.community.entity.hik;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;



public class FaceInfo {

    private int id;
    private String faceGroupIndexCode; // 	指定人脸添加到的人脸分组的唯一标识
    private String name ;   //人脸的名称
    private String sex;    //人脸的性别信息
    private String certificateType; //人脸的证件类别信息
    private String certificateNum ;  //人脸的证件号码信息
    private String faceUrl;           //人脸的图片
    private String indexCode;        //人脸的唯一标识
    private String faceGroupName;
    private String mobile;



    public FaceInfo(){

    }

    public FaceInfo(int id, String faceGroupIndexCode, String name, String sex, String certificateType, String certificateNum, String faceUrl) {
        this.id = id;
        this.faceGroupIndexCode = faceGroupIndexCode;
        this.name = name;
        this.sex = sex;
        this.certificateType = certificateType;
        this.certificateNum = certificateNum;
        this.faceUrl = faceUrl;
    }

    public FaceInfo(int id, String faceGroupIndexCode, String name, String sex, String certificateType, String certificateNum, String faceUrl, String indexCode) {
        this.id = id;
        this.faceGroupIndexCode = faceGroupIndexCode;
        this.name = name;
        this.sex = sex;
        this.certificateType = certificateType;
        this.certificateNum = certificateNum;
        this.faceUrl = faceUrl;
        this.indexCode = indexCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFaceGroupIndexCode() {
        return faceGroupIndexCode;
    }

    public void setFaceGroupIndexCode(String faceGroupIndexCode) {
        this.faceGroupIndexCode = faceGroupIndexCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateNum() {
        return certificateNum;
    }

    public void setCertificateNum(String certificateNum) {
        this.certificateNum = certificateNum;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public String getFaceGroupName() {
        return faceGroupName;
    }

    public void setFaceGroupName(String faceGroupName) {
        this.faceGroupName = faceGroupName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
