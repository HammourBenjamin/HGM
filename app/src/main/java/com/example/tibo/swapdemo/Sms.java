package com.example.tibo.swapdemo;

/**
 * Created by Ben on 31/05/2017.
 */

public class Sms {

    private String body;
    private String date;
    private String type;

    public Sms(String body, String date, String type) {
        this.body = body;
        this.date = date;
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
