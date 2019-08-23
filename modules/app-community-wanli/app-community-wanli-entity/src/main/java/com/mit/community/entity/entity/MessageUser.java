package com.mit.community.entity.entity;

public class MessageUser {
    private Integer userId;
    private String label;
    private Integer person_baseinfo_id;

    public Integer getPerson_baseinfo_id() {
        return person_baseinfo_id;
    }

    public void setPerson_baseinfo_id(Integer person_baseinfo_id) {
        this.person_baseinfo_id = person_baseinfo_id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
