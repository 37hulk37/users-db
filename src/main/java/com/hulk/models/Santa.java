package com.hulk.models;

public class Santa {
    private int recipientId;
    private String recipientName;
    private int selfId;
    private String selfName;
    private int groupId;

    public Santa(String recipientName, String selfName, int groupId) {
        this.recipientName = recipientName;
        this.selfName = selfName;
        this.groupId = groupId;
    }

    public Santa(int recipientId, String recipientName, int selfId, String selfName, int groupId) {
        this.recipientId = recipientId;
        this.recipientName = recipientName;
        this.selfId = selfId;
        this.selfName = selfName;
        this.groupId = groupId;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public int getSelfId() {
        return selfId;
    }

    public int getGroupId() {
        return groupId;
    }

    @Override
    public String toString() {
        return "Santa{" +
                "recipientId=" + recipientName +
                ", selfId=" + selfName +
                '}';
    }
}
