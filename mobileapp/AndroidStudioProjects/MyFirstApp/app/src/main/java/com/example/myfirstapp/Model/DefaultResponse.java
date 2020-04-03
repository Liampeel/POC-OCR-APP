package com.example.myfirstapp.Model;

import com.google.gson.annotations.SerializedName;

public class DefaultResponse {

    @SerializedName("error")
    private String err;

    @SerializedName("message")
    private String msg;

    @SerializedName("email")
    private String emailMsg;


    public DefaultResponse(String err, String msg, String emailMsg) {
        this.err = err;
        this.msg = msg;
        this.emailMsg = emailMsg;
    }

    public String getErr() {
        return err;
    }

    public String getMsg() {
        return msg;
    }

    public String getEmailMsg() {
        return emailMsg;
    }
}
