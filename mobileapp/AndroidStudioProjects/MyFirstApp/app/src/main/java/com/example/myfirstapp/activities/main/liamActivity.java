package com.example.myfirstapp.activities.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.Model.User;
import com.example.myfirstapp.R;
import com.example.myfirstapp.Storage.SharedPrefManager;
import com.example.myfirstapp.activities.FireBaseOCRActivity;
import com.example.myfirstapp.activities.ImageActivity;
import com.example.myfirstapp.activities.MainActivity;
import com.example.myfirstapp.activities.aapdiagnosisActivity;
import com.example.myfirstapp.activities.recordsActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class liamActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liam);
        findViewById(R.id.newSub).setOnClickListener(this);
        findViewById(R.id.homeButton).setOnClickListener(this);

        User user = SharedPrefManager.getInstance(this).getUser();

        textView = findViewById(R.id.text_display2);

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


    public void goToPOC(){
        Intent intent = new Intent(this, FireBaseOCRActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void goToHome(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
                case R.id.newSub:
                    goToPOC();
                    break;
                case R.id.homeButton:
                    goToHome();
                    break;
    }



}
}