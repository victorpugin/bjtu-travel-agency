package com.bjtutravel.bjtutravelagency.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;

public class ItemPlan {
    @Exclude
    public static final Integer TYPE_EDIT_TEXT = 1;

    private Integer type;
    private String content;

    public ItemPlan() {

    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> plan = new HashMap<String, Object>();

        plan.put("type", type);
        plan.put("content", content);

        return plan;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
