package com.example.myfirstapp.API;

import android.util.Log;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Interceptor for adding an authorization token to the header when the user is logging in
 */

public class TokenInterceptor implements Interceptor {

    String bearer;

    public TokenInterceptor(String bearer) {
        this.bearer = bearer;
        Log.d("Bearer", bearer);

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request authenticatedRequest = request.newBuilder()
                .header("Authorization", bearer).build();
        return chain.proceed(authenticatedRequest);
    }

}

