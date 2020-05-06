package com.example.myfirstapp.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myfirstapp.Model.User;

import java.net.ConnectException;

/**
 * Class for storing the users data in a cache when they log in, so can be access throuhgout the rest
 * of the application
 */

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "my_shared_preff";

    private static SharedPrefManager mInstance;
    private Context mCtx;

    private SharedPrefManager(Context mCtx){
        this.mCtx = mCtx;

    }

    /**
     * Create a new instance per logno
     * @param mCtx
     * @return
     */
    public static synchronized SharedPrefManager getInstance(Context mCtx){
        if(mInstance == null){
            mInstance  = new SharedPrefManager(mCtx);
        }
        return mInstance;
    }

    /**
     * Save the user (Email, Name, Date of Birth)
     * @param user
     */
    public void saveUser (User user){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id", user.getId());
        editor.putString("email", user.getEmail());
        editor.apply();
    }

    /**
     * Check if the user is logged on
     * @return
     */
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

//        -1 = user is not logged in, not = -1 user is logged in
        return sharedPreferences.getInt("id", -1) != -1;

    }

    /**
     * Get the user that is logged on
     * @return
     */
    public User getUser(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("email", null)
        );

    }

    /**
     * Save the token received from the API, so can be used for authenticated requests
     * @param token
     */
    public void saveToken (String token){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putString("token", token);
        editor.apply();
    }

    /**
     * Get the token from API login response
     * @return
     */
    public String getToken (){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);


        return sharedPreferences.getString("token", null);

    }

    /**
     * Method for clearing all data when a user logs out
     */
    public void clear(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

    }

}
