package com.example.myfirstapp.Model;

public class LoginResponse {

    private String token, error;

    private User user;

    public LoginResponse(String token, User user, String error) {
        this.token = token;
        this.user = user;
        this.error = error;

    }

    public String getToken() {
        return token;
    }

    public String getError() {
        return error;
    }

    public User getUser() {
        return user;
    }
}


