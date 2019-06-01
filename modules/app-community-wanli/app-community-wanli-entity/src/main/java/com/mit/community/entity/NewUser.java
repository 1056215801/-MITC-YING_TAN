package com.mit.community.entity;

import java.util.List;

public class NewUser {
    private List<String> labels;
    private User user;

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
