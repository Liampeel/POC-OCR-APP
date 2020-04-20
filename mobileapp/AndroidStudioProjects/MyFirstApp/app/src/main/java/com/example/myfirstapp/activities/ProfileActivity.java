package com.example.myfirstapp.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.API.RetrofitClient;
import com.example.myfirstapp.Model.ExampleResponse;
import com.example.myfirstapp.Model.LoginResponse;
import com.example.myfirstapp.Model.User;
import com.example.myfirstapp.R;
import com.example.myfirstapp.Storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView textView;
//    private Button logoutBtn, requestBtn ;
    private String token  = SharedPrefManager.getInstance(this).getToken();
    private String bearer = ("Bearer " + token);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        findViewById(R.id.requestButton).setOnClickListener(this);
        findViewById(R.id.logoutButton).setOnClickListener(this);

        textView = findViewById(R.id.textView);

        User user = SharedPrefManager.getInstance(this).getUser();

//        String retrivedToken  = SharedPrefManager.getInstance(this).getToken();

        textView.setText("Welcome Back " + bearer);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
    }


    private void requestTest() {


        Call<ExampleResponse> call = RetrofitClient
                .getInstanceToken(bearer)
                .getApi()
                .example();

        call.enqueue(new Callback<ExampleResponse>() {
            @Override
            public void onResponse(Call<ExampleResponse> call, Response<ExampleResponse> response) {
                ExampleResponse exampleResponse = response.body();
                Log.d("token", bearer);
                if(response.code() == 200){
                    Toast.makeText(ProfileActivity.this, exampleResponse.getMammal(), Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(ProfileActivity.this, "Wrong response code", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ExampleResponse> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });



    }


    private void logout(){
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logoutButton:
                logout();
                break;
            case R.id.requestButton:
                requestTest();
                break;


        }
    }

}
