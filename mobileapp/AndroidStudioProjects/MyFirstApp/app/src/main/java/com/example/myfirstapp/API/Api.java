package com.example.myfirstapp.API;

import com.example.myfirstapp.Model.DefaultResponse;
import com.example.myfirstapp.Model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {
    @FormUrlEncoded
    @POST("users")
    Call<DefaultResponse> createUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("name") String name


    );

    @FormUrlEncoded
    @POST("tokens")
    Call<LoginResponse> userLogin(
            @Field("email") String email,
            @Field("password") String password

    );


}
