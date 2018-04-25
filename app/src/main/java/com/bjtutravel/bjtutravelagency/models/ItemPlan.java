package com.bjtutravel.bjtutravelagency.models;

import java.util.HashMap;

public class ItemPlan {
    private String type;
    private String content;

    public ItemPlan() {

    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> plan = new HashMap<String, Object>();

        plan.put("type", type);
        plan.put("content", content);

        return plan;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
