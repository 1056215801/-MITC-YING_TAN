package com.mit.admin.vo;

import com.mit.admin.entity.User;

import java.util.List;

/**
 *
 * @author shuyy
 * @date 2018/11/8 11:23
 * @company mitesofor
*/
public class GroupUsers {
    List<User> members ;
    List<User> leaders;

    public GroupUsers() {
    }

    public GroupUsers(List<User> members, List<User> leaders) {
        this.members = members;
        this.leaders = leaders;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<User> getLeaders() {
        return leaders;
    }

    public void setLeaders(List<User> leaders) {
        this.leaders = leaders;
    }
}
