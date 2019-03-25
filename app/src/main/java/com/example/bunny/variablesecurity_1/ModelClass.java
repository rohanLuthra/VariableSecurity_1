package com.example.bunny.variablesecurity_1;

import android.view.View;

import java.sql.Timestamp;

public class ModelClass {
    private Timestamp timestamp;
    private String url;
    private String result;
    private String criminal;

    public ModelClass() {
    }

    public String getCriminal() {
        return criminal;
    }

    public void setCriminal(String criminal) {
        this.criminal = criminal;
    }

    public ModelClass(Timestamp timestamp, String url, String result, String criminal) {

        this.timestamp = timestamp;
        this.url = url;
        this.result = result;
        this.criminal = criminal;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
