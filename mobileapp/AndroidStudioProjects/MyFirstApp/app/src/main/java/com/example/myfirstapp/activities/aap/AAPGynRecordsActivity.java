package com.example.myfirstapp.activities.aap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.AAPListAdapter;
import com.example.myfirstapp.API.RetrofitClient;
import com.example.myfirstapp.Model.AAPDiagnosisResponse;
import com.example.myfirstapp.R;
import com.example.myfirstapp.Storage.SharedPrefManager;
import com.example.myfirstapp.activities.users.LoginActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AAPGynRecordsActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        fetchDiagnoses();

        ImageButton homeButton = findViewById(R.id.homeButton);
        Button refreshButton = findViewById(R.id.refreshButton);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AAPGynRecordsActivity.this, AAPHomePageActivity.class));
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDiagnoses();
            }
        });

        AlertDialog.Builder clearDialogBuilder = new AlertDialog.Builder(this);
        clearDialogBuilder.setTitle("AAP Gynae Diagnoses");
        clearDialogBuilder.setMessage("The diagnoses calculated are estimates, please see your doctor for a proper diagnosis");
        clearDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {}
        });
        AlertDialog alertDialog = clearDialogBuilder.create();
        alertDialog.show();
    }

    private void setListView(final List<AAPDiagnosisResponse> aapResponse) {
        AAPListAdapter listAdapter = new AAPListAdapter(this, aapResponse);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(AAPGynRecordsActivity.this, AAPGynDetailActivity.class);
                intent.putExtra("com.example.myfirstapp.Model.AAPDiagnosisResponse", aapResponse.get(position));
                startActivity(intent);
            }
        });
    }

    private void fetchDiagnoses() {
        String token = SharedPrefManager.getInstance(this).getToken();
        Call<List<AAPDiagnosisResponse>> call = RetrofitClient
                .getInstanceToken(token)
                .getApi()
                .aapGynList();

        call.enqueue(new Callback<List<AAPDiagnosisResponse>>() {
            @Override
            public void onResponse(Call<List<AAPDiagnosisResponse>> call, Response<List<AAPDiagnosisResponse>> response) {
                if (response.isSuccessful()) {
                    List<AAPDiagnosisResponse> aapResponse = response.body();
                    setListView(aapResponse);
                } else {
                    Toast.makeText(AAPGynRecordsActivity.this, "Failed to Fetch Records", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<AAPDiagnosisResponse>> call, Throwable t) {
                Toast.makeText(AAPGynRecordsActivity.this, "Error Fetch Records Failed", Toast.LENGTH_LONG).show();
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
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}

