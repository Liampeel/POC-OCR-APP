package com.example.myfirstapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.API.RetrofitClient;
import com.example.myfirstapp.R;
import com.example.myfirstapp.Storage.SharedPrefManager;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pop extends AppCompatActivity implements View.OnClickListener{

    EditText timeView, sysView, diaView, heartView;
    Button confirmBtn, cancelButton;
    Bitmap bmp;
    ImageView thumbnail;

    private String token  = SharedPrefManager.getInstance(this).getToken();
    private String bearer = ("Bearer " + token);


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);

        timeView = findViewById(R.id.Time);
        confirmBtn = findViewById(R.id.confirmResult);

        sysView = findViewById(R.id.Systolic);
        diaView = findViewById(R.id.Diastolic);
        heartView = findViewById(R.id.HeartRate);
        thumbnail = findViewById(R.id.popImage);


        findViewById(R.id.cancelBtn).setOnClickListener(this);
        findViewById(R.id.confirmResult).setOnClickListener(this);

//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//        int width = dm.widthPixels;
//        int height = dm.heightPixels;
//
//        getWindow().setLayout((int)(width*.8), (int)(height*.8));
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        timeView.setText(getIntent().getStringExtra("Time"));
        sysView.setText(getIntent().getStringExtra("Systolic"));
        diaView.setText(getIntent().getStringExtra("Diastolic"));
        heartView.setText(getIntent().getStringExtra("Heartrate"));
        thumbnail.setImageBitmap(bmp);



    }


    public void submit(){

        String time = timeView.getText().toString().trim();
        String systolic = timeView.getText().toString().trim();
        String diastolic = timeView.getText().toString().trim();
        String heartRate = timeView.getText().toString().trim();


        Call<ResponseBody> call = RetrofitClient
                .getInstanceToken(bearer)
                .getApi()
                .ocr(time, systolic, diastolic, heartRate);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("token", bearer);

                if(response.code() == 201){
                    Toast.makeText(Pop.this, "Successfully submitted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Pop.this, successActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(Pop.this, "Bad request", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Pop.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Cancel(){
        Intent intent = new Intent(this, FireBaseOCRActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirmResult:
                submit();
                break;
            case R.id.cancelBtn:
                Cancel();
                break;

        }
    }
}

