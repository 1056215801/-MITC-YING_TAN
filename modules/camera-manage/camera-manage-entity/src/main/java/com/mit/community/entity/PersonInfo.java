package com.mit.community.entity;

public class PersonInfo {
	private Integer id;
	/**
	 * 个体编号
	 */
	private String serialnumber;
	/**
	 * 用户姓名
	 */
	private String name;
	/**
	 * 用户身份证
	 */
	private String idCardNum;
	/**
	 * uid(模拟门禁卡)
	 */
	private String doorNum;
	/**
	 * 照片url
	 */
	private String photoUrl;
	/**
	 * 照片base64
	 */
	private String photoBase64;
	private String faceToken;
	private String userInfo;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIdCardNum() {
		return idCardNum;
	}
	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}
	public String getFaceToken() {
		return faceToken;
	}
	public void setFaceToken(String faceToken) {
		this.faceToken = faceToken;
	}
	public String getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDoorNum() {
		return doorNum;
	}
	public void setDoorNum(String doorNum) {
		this.doorNum = doorNum;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String imageUrl) {
		this.photoUrl = imageUrl;
	}
	public String getPhotoBase64() {
		return photoBase64;
	}
	public void setPhotoBase64(String photoBase64) {
		this.photoBase64 = photoBase64;
	}
	
	
	
}
