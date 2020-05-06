package com.example.myfirstapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * class to get Mongo Object ID
 */

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
