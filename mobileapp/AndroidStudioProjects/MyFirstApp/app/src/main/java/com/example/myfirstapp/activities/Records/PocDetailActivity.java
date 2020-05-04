package com.example.myfirstapp.activities.Records;
import com.example.myfirstapp.API.RetrofitClient;
import com.example.myfirstapp.Storage.SharedPrefManager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.Model.PocResponse;
import com.example.myfirstapp.R;
import com.example.myfirstapp.activities.Main.POCHomeActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PocDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private PocResponse response;
    private String token = SharedPrefManager.getInstance(this).getToken();
    private String bearer = ("Bearer " + token);


    TextView dateSubmittedText, diastolic, systolic, heartrate;
    Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocdetail);


        response = (PocResponse) getIntent().getSerializableExtra(
                "com.example.myfirstapp.Model.PocResponse");

        dateSubmittedText = findViewById(R.id.dateView);
        diastolic = findViewById(R.id.diastolicView);
        systolic = findViewById(R.id.systolicView);
        heartrate = findViewById(R.id.heartrateView);

        findViewById(R.id.homeButton);


        String date = response.getDate_submitted();
        String[] result = date.split(",");
        String format = (result[0] + "/" + result[1] + "/" + result[2] + " " + result[3] + ":" + result[4]);
        dateSubmittedText.setText(format);
        diastolic.setText(response.getDiastolic());
        systolic.setText(response.getSystolic());
        heartrate.setText(response.getHeartRate());


        ImageButton homeButton = findViewById(R.id.homeButton);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PocDetailActivity.this, POCHomeActivity.class));
            }
        });


    }


    public void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    private void deleteRecord() {
        Call<ResponseBody> call = RetrofitClient
                .getInstanceToken(bearer)
                .getApi()
                .pocDelete(response.getId().getObjectID());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PocDetailActivity.this, "The record has been successfully deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(PocDetailActivity.this, "This record is unable to be deleted", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(PocDetailActivity.this, "Failed to delete the record", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void home() {

        Intent intent = new Intent(this, POCHomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void clearData(View view) {
        AlertDialog.Builder clearDialogBuilder = new AlertDialog.Builder(this);
        clearDialogBuilder.setTitle("Are you sure you want to delete the record?");
        clearDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // Delete record from database
                deleteRecord();
                Intent intent = new Intent(PocDetailActivity.this, POCRecordsActivity.class);
                startActivity(intent);
            }
        });
        clearDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {    }
        });

        AlertDialog alertDialog = clearDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.homeButton:
                home();
                break;
    }
}
}

