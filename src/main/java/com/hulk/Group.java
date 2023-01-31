package com.hulk;

enum Role {
    Admin,
    User
}

public class Group {
    private String gname;
    private int id;
    boolean isClosed;

    public Group(int id, String gname, Boolean isClosed) {
        this.id = id;
        this.gname = gname;
        this.isClosed = isClosed;
    }

    public int getId() {
        return id;
    }
}