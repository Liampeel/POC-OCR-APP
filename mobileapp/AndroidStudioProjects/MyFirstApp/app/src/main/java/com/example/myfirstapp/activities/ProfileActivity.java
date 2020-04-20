package com.example.myfirstapp.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.Model.User;
import com.example.myfirstapp.R;
import com.example.myfirstapp.Storage.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView textView;
    Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        findViewById(R.id.logoutButton).setOnClickListener(this);

        textView = findViewById(R.id.textView);


        User user = SharedPrefManager.getInstance(this).getUser();
        textView.setText("Welcome Back " + user.getEmail());
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


        }
    }
}
