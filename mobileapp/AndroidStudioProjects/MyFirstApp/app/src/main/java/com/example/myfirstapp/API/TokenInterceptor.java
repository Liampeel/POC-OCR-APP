package com.example.myfirstapp.API;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    String bearer;

    public TokenInterceptor(String bearer) {
        this.bearer = bearer;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request authenticatedRequest = request.newBuilder()
                .header("Authorization", bearer).build();
        return chain.proceed(authenticatedRequest);
    }

}

