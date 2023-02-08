package com.example.statusupload.model;

public class StatusModel {
    private int statusID;
    private String userName;
    private String status;

    public StatusModel(int statusID, String userName, String status) {
        this.statusID = statusID;
        this.userName = userName;
        this.status = status;
    }

    public int getStatusID() {
        return statusID;
    }

    public String getUserName() {
        return userName;
    }

    public String getStatus() {
        return status;
    }
}
