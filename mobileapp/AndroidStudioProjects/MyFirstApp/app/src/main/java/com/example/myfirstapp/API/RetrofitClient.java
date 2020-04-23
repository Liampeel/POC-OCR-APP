package com.example.myfirstapp.API;

import okhttp3.OkHttpClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://192.168.1.79:5000/api/";
    private static RetrofitClient mInstance, mInstanceAuth, mInstanceToken;
    private Retrofit retrofit;

    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    // authenticated client adds in Authorization header
    private RetrofitClient(String email, String password) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuthInterceptor(email, password))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private RetrofitClient(String bearer) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new TokenInterceptor(bearer))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    // basic instance used for registration
    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    // calls authentication instance used for other API services
    public static synchronized RetrofitClient getInstanceAuth(String email, String password) {
        if (mInstanceAuth == null) {
            mInstanceAuth = new RetrofitClient(email, password);
        }
        return mInstanceAuth;
    }

    public static synchronized RetrofitClient getInstanceToken(String bearer) {
        if (mInstanceToken == null) {
            mInstanceToken = new RetrofitClient(bearer);
        }
        return mInstanceToken;
    }

    public Api getApi(){
        return retrofit.create(Api.class);
    }
}