package com.example.myfirstapp.Model;

public class User {

    private int id;

    private String email, token;

    public User(int id, String email, String token) {
        this.id = id;
        this.email = email;
        this.token = token;

    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

//    public int getToken() {
//        return token;
//    }
}
