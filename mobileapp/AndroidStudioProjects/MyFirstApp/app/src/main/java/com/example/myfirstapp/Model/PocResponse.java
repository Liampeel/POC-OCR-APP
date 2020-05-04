package com.example.myfirstapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PocResponse implements Serializable {

    @SerializedName("diastolic")
    private String diastolic;

    @SerializedName("systolic")
    private String systolic;

    @SerializedName("heartRate")
    private String heartRate;

    @SerializedName("time")
    private String time;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("date_submitted")
    private String date_submitted;

    @SerializedName("_id")
    private ObjectResponse id;




    public PocResponse(String diastolic, String systolic, String heartRate,
                       String time, String user_id, String date_submitted, ObjectResponse id) {
        this.diastolic = diastolic;
        this.systolic = systolic;
        this.heartRate = heartRate;
        this.time = time;
        this.user_id = user_id;
        this.date_submitted = date_submitted;
        this.id = id;

    }

    public String getDiastolic() {
        return diastolic;
    }

    public String getSystolic() {
        return systolic;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public String getTime() {
        return time;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getDate_submitted() {
        return date_submitted;
    }

    public ObjectResponse getId() {
        return id;
    }


}
