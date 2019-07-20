package com.mit.community.entity;

public class UploadFaceComparisonData {
	private String command;
	private String datatype;
	private String msgid;
	private UploadFaceComparisonPersonData data;
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	public UploadFaceComparisonPersonData getData() {
		return data;
	}
	public void setData(UploadFaceComparisonPersonData data) {
		this.data = data;
	}
	
	
}
