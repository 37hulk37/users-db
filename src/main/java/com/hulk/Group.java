package com.hulk;

enum Role {
    Admin,
    User
}

public class Group {
    private String name;
    private int id;
    boolean isClosed;

    public Group(int id, String name, Boolean isClosed) {
        this.id = id;
        this.name = name;
        this.isClosed = isClosed;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", isClosed=" + isClosed +
                '}';
    }
}
