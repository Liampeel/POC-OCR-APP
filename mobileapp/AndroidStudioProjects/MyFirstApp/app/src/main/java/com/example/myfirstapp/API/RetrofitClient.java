package com.example.myfirstapp.API;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * HTTP Client for making the API calls to the API
 * Includes client for registering a user, logging in, and another client for authenticated requests
 */

public class RetrofitClient {

    /**
     * CHANGE IP HERE TO THE IP THAT SERVER IS RUNNING ON
     */
    private static final String BASE_URL = "http://000.000.000:5000/api/";
    private static RetrofitClient mInstance, mInstanceAuth, mInstanceToken;
    private Retrofit retrofit;


    /**
     * Basic client for registering user
     */
    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Client used for logging in
     * uses the interceptor to add a token by converting the email and password
     * @param email
     * @param password
     */
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

    /**
     * Client using the stored token to make an authenticated request
     * @param bearer
     */
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
        mInstanceAuth = new RetrofitClient(email, password);
        mInstanceToken = null;
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