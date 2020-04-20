package com.example.myfirstapp.Model;

import com.google.gson.annotations.SerializedName;


public class ExampleResponse {

    @SerializedName("mammal")
    private String mammal;

    @SerializedName("reptile")
    private String reptile;




    public ExampleResponse(String mammal, String reptile) {
        this.mammal = mammal;
        this.reptile = reptile;

    }

    public String getMammal() {
        return mammal;
    }

    public String getReptile() {
        return reptile;
    }
}


