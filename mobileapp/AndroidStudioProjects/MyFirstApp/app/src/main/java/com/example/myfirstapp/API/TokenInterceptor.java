package com.example.myfirstapp.API;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    private String bearer;

    TokenInterceptor(String bearer) {
        this.bearer = "Bearer " + bearer;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request authenticatedRequest = request.newBuilder()
                .header("Authorization", bearer).build();
        return chain.proceed(authenticatedRequest);
    }

}

