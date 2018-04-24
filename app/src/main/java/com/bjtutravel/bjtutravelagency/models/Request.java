package com.bjtutravel.bjtutravelagency.models;

import java.io.Serializable;
import java.util.HashMap;

public class Request implements Serializable {
    private String title;
    private String message;
    private String date;

    public Request() {

    }

    public HashMap<String, String> toFirebaseObject() {
        HashMap<String, String> request = new HashMap<String, String>();

        request.put("title", title);
        request.put("message", message);
        request.put("date", date);

        return request;
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
}
