package com.example.myfirstapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.API.RetrofitClient;
import com.example.myfirstapp.Model.PocResponse;
import com.example.myfirstapp.POCListAdapter;
import com.example.myfirstapp.R;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myfirstapp.Storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class POCRecordsActivity extends AppCompatActivity implements View.OnClickListener{

    ListView listView;
    private String token  = SharedPrefManager.getInstance(this).getToken();
    private String bearer = ("Bearer " + token);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        findViewById(R.id.homeButton).setOnClickListener(this);

        getPocResponse();

    }

    private void recordListView(final List<PocResponse> pocResponseList) {
        POCListAdapter listAdapter = new POCListAdapter(this, pocResponseList);
        listView = findViewById(R.id.listView);
        listView.setAdapter(listAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(POCRecordsActivity.this, PocDetailActivity.class);
                intent.putExtra("com.example.myfirstapp.Model.PocResponse", pocResponseList.get(position));
                startActivity(intent);
            }
        });
    }

    private void getPocResponse() {

        Call<List<PocResponse>> call = RetrofitClient
                .getInstanceToken(bearer)
                .getApi()
                .pocList();

        call.enqueue(new Callback<List<PocResponse>>() {
            @Override
            public void onResponse(Call<List<PocResponse>> call, Response<List<PocResponse>> response) {

                if (response.code() == 200) {

                    List<PocResponse> pocResponseList = response.body();
                    recordListView(pocResponseList);
                } else {
                    Toast.makeText(POCRecordsActivity.this, "Failed to get Records", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<PocResponse>> call, Throwable t) {
                Toast.makeText(POCRecordsActivity.this, "Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void home() {

        Intent intent = new Intent(this, POCHomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.homeButton:
                home();
                break;
        }
    }
}

