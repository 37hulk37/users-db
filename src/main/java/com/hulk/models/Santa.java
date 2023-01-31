package com.hulk.models;

public class Santa {
    private int recipientId;
    private int selfId;
    private int groupId;

    public Santa(int recipientId, int selfId, int groupId) {
        this.recipientId = recipientId;
        this.selfId = selfId;
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "Santa{" +
                "recipientId=" + recipientId +
                ", selfId=" + selfId +
                ", groupId=" + groupId +
                '}';
    }
}
