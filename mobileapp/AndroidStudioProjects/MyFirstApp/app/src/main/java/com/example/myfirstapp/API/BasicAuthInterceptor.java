package com.example.myfirstapp.API;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * class for adding the token to authorization header by converting the email and password to binary
 */

public class BasicAuthInterceptor implements Interceptor {


    private String credentials;

    public BasicAuthInterceptor(String email, String password) {
        this.credentials = Credentials.basic(email, password);

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request authenticatedRequest = request.newBuilder()
                .header("Authorization", credentials).build();
        return chain.proceed(authenticatedRequest);
    }

}