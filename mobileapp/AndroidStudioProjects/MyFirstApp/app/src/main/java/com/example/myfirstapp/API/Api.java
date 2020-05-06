package com.example.myfirstapp.API;

import com.example.myfirstapp.Model.DefaultResponse;
import com.example.myfirstapp.Model.LoginResponse;
import com.example.myfirstapp.Model.PocResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * API interface for sending a JSON response to the endpoints
 */

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


    @FormUrlEncoded
    @PUT("users/password-reset")
    Call<ResponseBody> passwordReset(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("pocresult")
    Call<PocResponse> ocr(
            @Field("time") String time,
            @Field("systolic") String systolic,
            @Field("diastolic") String diastolic,
            @Field("heartRate") String heartRate

    );


    @GET("pocresult")
    Call<List<PocResponse>> pocList();

    @DELETE("pocresult/{doc_id}")
    Call<ResponseBody> pocDelete(
            @Path("doc_id") String id
    );


    @PUT("users/password")
    Call<ResponseBody> changePassword(
            @Field("token") String token,
            @Field("new_password") String newPassword
    );

}
