package com.bjtutravel.bjtutravelagency.models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;

public class InfoPlan implements Serializable {
    private String userId;
    private String userName;
    private String title;
    private String date;
    private String staffName;
    @Exclude
    private String key;

    public InfoPlan() {

    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> plan = new HashMap<String, Object>();

        plan.put("userId", userId);
        plan.put("userName", userName);
        plan.put("title", title);
        plan.put("date", date);
        plan.put("staffName", staffName);

        return plan;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
}
