package com.hulk.models;

public class Member {
    private String name;
    private String gname;
    private Role role;
    private int userId;

    public enum Role {
        Admin,
        User
    }

    public Member(String name, String gname, Role role, int userId) {
        this.name = name;
        this.gname = gname;
        this.role = role;
        this.userId = userId;
    }

    public int getId() {
        return userId;
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
