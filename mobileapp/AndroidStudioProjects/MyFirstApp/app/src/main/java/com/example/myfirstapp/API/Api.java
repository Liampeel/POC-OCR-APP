package com.example.myfirstapp.API;

import com.example.myfirstapp.Model.DefaultResponse;
import com.example.myfirstapp.Model.ExampleResponse;
import com.example.myfirstapp.Model.LoginResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Api {
    @FormUrlEncoded
    @POST("users")
    Call<DefaultResponse> createUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("name") String name,
            @Field("date_of_birth") String dateOfBirth


    );

    @FormUrlEncoded
    @POST("tokens")
    Call<LoginResponse> userLogin(
            @Field("email") String email,
            @Field("password") String password

    );


    @GET("example/labels")
    Call<ExampleResponse> example(
    );

    @FormUrlEncoded
    @POST("pocresult")
    Call<ResponseBody> ocr(
            @Field("time") String time,
            @Field("systolic") String systolic,
            @Field("diastolic") String diastolic,
            @Field("heartRate") String heartRate



    );


}
