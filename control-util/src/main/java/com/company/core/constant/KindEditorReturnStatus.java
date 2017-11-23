package com.company.core.constant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class KindEditorReturnStatus {

    private int    error;
    private String url;
    private String message;

    public String succeed(String url) {
        this.error = 0;
        this.url = url;
        this.message = "上传成功";
        GsonBuilder gb = new GsonBuilder();
        Gson gson = gb.create();
        return gson.toJson(this);
    }

    public String error(String error) {
        this.error = 1;
        this.message = error;
        GsonBuilder gb = new GsonBuilder();
        Gson gson = gb.create();
        return gson.toJson(this);
    }

}
