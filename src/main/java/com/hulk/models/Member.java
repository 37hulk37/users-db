package com.hulk.models;

public class Member {
    private String name;
    private String gname;
    private Role role;
    private int userId;
    private int groupId;

    public enum Role {
        Admin,
        User
    }

    public Member(String name, String gname, Role role, int userId, int groupId) {
        this.name = name;
        this.gname = gname;
        this.role = role;
        this.userId = userId;
        this.groupId = groupId;
    }

    public int getId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", gname='" + gname + '\'' +
                ", role=" + role +
                ", userId=" + userId +
                '}';
    }
}
