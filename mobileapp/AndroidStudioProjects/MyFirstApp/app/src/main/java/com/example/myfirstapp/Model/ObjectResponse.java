package com.example.myfirstapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ObjectResponse implements Serializable {

    @SerializedName("$oid")
    private String objectID;

    public ObjectResponse(String objectID) {
        this.objectID = objectID;
    }

    public String getObjectID() {
        return objectID;
    }
}
