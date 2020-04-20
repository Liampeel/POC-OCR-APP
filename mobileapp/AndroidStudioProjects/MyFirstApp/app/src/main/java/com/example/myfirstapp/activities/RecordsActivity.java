package com.example.myfirstapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.API.RetrofitClient;
import com.example.myfirstapp.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myfirstapp.CustomListAdapter;
import com.example.myfirstapp.Storage.SharedPrefManager;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordsActivity extends AppCompatActivity {

    String[] nameArray = {"Octopus","Pig","Sheep","Rabbit","Snake","Spider" };
    String[] infoArray = {
            "8 tentacled monster",
            "Delicious in rolls",
            "Great for jumpers",
            "Nice in a stew",
            "Great for shoes",
            "Scary."
    };
    int imageArray = R.drawable.syringe;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        //make fully Android Transparent Status bar
        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        fetchDiagnoses();

        CustomListAdapter listAdapter = new CustomListAdapter(this, nameArray, infoArray, imageArray);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(listAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(RecordsActivity.this, DetailActivity.class);
                String message = nameArray[position];
                intent.putExtra("Record", message);
                startActivity(intent);

            }
        });

    }

    private void fetchDiagnoses() {
        String token = SharedPrefManager.getInstance(this).getUser().getToken();
        Call<ResponseBody> call = RetrofitClient
                .getInstanceToken(token)
                .getApi()
                .aapList();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Toast.makeText(RecordsActivity.this, response.body().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(RecordsActivity.this, "Failed to Fetch Records", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RecordsActivity.this, "Error Fetch Records Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
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
}

