package com.bjtutravel.bjtutravelagency.models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;

public class Request implements Serializable {
    private String userName;
    private String title;
    private String message;
    private String date;
    @Exclude
    private String key;

    public Request() {

    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> request = new HashMap<String, Object>();

        request.put("userName", userName);
        request.put("title", title);
        request.put("message", message);
        request.put("date", date);

        return request;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
}
