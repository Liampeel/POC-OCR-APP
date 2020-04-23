package com.example.myfirstapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AAPDiagnosisResponse implements Serializable {

    @SerializedName("l_actual_diagnosis")
    private String l_actual_diagnosis;
    @SerializedName("t_diagnosis")
    private String t_diagnosis;

    @SerializedName("date_modified")
    private String date_modified;
    @SerializedName("date_submitted")
    private String date_submitted;

    @SerializedName("_id")
    private ObjectResponse id;

    public AAPDiagnosisResponse(
            String l_actual_diagnosis, String t_diagnosis, String date_modified,
            String date_registered, ObjectResponse id
    ) {

        this.l_actual_diagnosis = l_actual_diagnosis;
        this.t_diagnosis = t_diagnosis;
        this.date_modified = date_modified;
        this.date_submitted = date_registered;
        this.id = id;
    }

    public String getL_actual_diagnosis() {
        return l_actual_diagnosis;
    }

    public String getT_diagnosis() {
        if (t_diagnosis == null) {
            return "Calculating Results...";
        }
        return t_diagnosis;
    }

    public String getDate_modified() {
        return formatDate(date_modified);
    }

    public String getDate_submitted() {
        return formatDate(date_submitted);
    }

    private String formatDate(String date) {
        if (date != null) {
            String[] components = date.split(",");

            if (components.length >= 3) {
                return components[2] + "." + components[1] + "." + components[0];
            }
        }
        return date;
    }

    public ObjectResponse getId() {
        return id;
    }
}
