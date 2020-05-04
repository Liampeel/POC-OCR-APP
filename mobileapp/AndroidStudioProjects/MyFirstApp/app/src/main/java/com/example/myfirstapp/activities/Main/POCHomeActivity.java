package com.example.myfirstapp.activities.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myfirstapp.Model.User;
import com.example.myfirstapp.R;
import com.example.myfirstapp.Storage.SharedPrefManager;
import com.example.myfirstapp.activities.OCR.FireBaseOCRActivity;
import com.example.myfirstapp.activities.Records.POCRecordsActivity;

public class POCHomeActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liam);
        findViewById(R.id.newSub).setOnClickListener(this);
        findViewById(R.id.homeButton).setOnClickListener(this);
        findViewById(R.id.recordsButton).setOnClickListener(this);
        findViewById(R.id.logoutLiam).setOnClickListener(this);

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

    public void goToRecords(){
        Intent intent = new Intent(this, POCRecordsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void goToHome(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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
                case R.id.newSub:
                    goToPOC();
                    break;
                case R.id.homeButton:
                    goToHome();
                    break;
                case R.id.recordsButton:
                    goToRecords();
                    break;
                case R.id.logoutLiam:
                    logout();
                    break;
        }



}
}