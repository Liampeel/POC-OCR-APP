//package com.example.myfirstapp.activities;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.myfirstapp.API.RetrofitClient;
//import com.example.myfirstapp.Model.AAPDiagnosisResponse;
//import com.example.myfirstapp.R;
//import com.example.myfirstapp.Storage.SharedPrefManager;
//
//import okhttp3.ResponseBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class PocDetailActivity extends AppCompatActivity {
//    private AAPDiagnosisResponse diagnosis;
//    private String[] diseases = {
//            "Confirm Diagnosis", "Appendicitis", "Diverticular Disease", "Perforated Ulcer",
//            "Non Specific Abdominal Pain", "Cholecystitis", "Bowel Obstruction",
//            "Pancreatitis", "Renal Colic", "Dyspepsia"
//    };
//    private Spinner confirmedDiagnosis;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail);
//
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
//        getWindow().setStatusBarColor(Color.TRANSPARENT);
//
//        diagnosis = (AAPDiagnosisResponse) getIntent().getSerializableExtra(
//                "com.example.myfirstapp.Model.AAPDiagnosisResponse");
//        TextView dateSubmittedText = (TextView) findViewById(R.id.dateView);
//        TextView diagnosisText = (TextView) findViewById(R.id.diagnosisView);
//        dateSubmittedText.setText(diagnosis.getDate_submitted());
//        diagnosisText.setText(diagnosis.getT_diagnosis());
//        ImageButton homeButton = findViewById(R.id.homeButton);
//        Button saveButton = findViewById(R.id.saveButton);
//
//        homeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(PocDetailActivity.this, AAPHomePageActivity.class));
//            }
//        });
//
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                confirmDiagnosis();
//            }
//        });
//
//        setConfirmDiagnosisSpinner();
//    }
//
//    private void setConfirmDiagnosisSpinner() {
//        int prompt = 0;
//        if (diagnosis.getL_actual_diagnosis() != null) {
//            for (int i=0; i<diseases.length; i++) {
//                if (diseases[i].toLowerCase().equals(diagnosis.getL_actual_diagnosis().toLowerCase())) {
//                    prompt = i;
//                }
//            }
//        }
//
//        confirmedDiagnosis = findViewById(R.id.diagnosisSpinner);
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_spinner_item, diseases);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        confirmedDiagnosis.setAdapter(dataAdapter);
//        confirmedDiagnosis.setSelection(prompt);
//    }
//
//    public void setWindowFlag(Activity activity, final int bits, boolean on) {
//        Window win = activity.getWindow();
//        WindowManager.LayoutParams winParams = win.getAttributes();
//        if (on) {
//            winParams.flags |= bits;
//        } else {
//            winParams.flags &= ~bits;
//        }
//        win.setAttributes(winParams);
//    }
//
//    private void confirmDiagnosis() {
//        confirmedDiagnosis = findViewById(R.id.diagnosisSpinner);
//        String selectedItem = String.valueOf(confirmedDiagnosis.getSelectedItem());
//        String currentDiagnosis = diagnosis.getL_actual_diagnosis();
//        if (selectedItem.toLowerCase().equals(diseases[0].toLowerCase()) || ((currentDiagnosis != null) && currentDiagnosis.toLowerCase().equals(selectedItem.toLowerCase()))) {
//            Toast.makeText(PocDetailActivity.this, "There's nothing to update!", Toast.LENGTH_LONG).show();
//        } else {
//            updateRecord(selectedItem);
//        }
//    }
//
//    private void updateRecord(String actualDiagnosis) {
//        String token = SharedPrefManager.getInstance(this).getToken();
//        Call<AAPDiagnosisResponse> call = RetrofitClient
//                .getInstanceToken(token)
//                .getApi()
//                .aapConfirmDiagnosis(diagnosis.getId().getObjectID(), actualDiagnosis);
//
//        call.enqueue(new Callback<AAPDiagnosisResponse>() {
//            @Override
//            public void onResponse(Call<AAPDiagnosisResponse> call, Response<AAPDiagnosisResponse> response) {
//                if (response.isSuccessful()) {
//                    startActivity(new Intent(PocDetailActivity.this, AAPRecordsActivity.class));
//                    Toast.makeText(PocDetailActivity.this, "The record has been successfully updated", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(PocDetailActivity.this, "Failed to update the confirmed diagnosis", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AAPDiagnosisResponse> call, Throwable t) {
//                Toast.makeText(PocDetailActivity.this, "Failed to update the record", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    private void deleteRecord() {
//        String token = SharedPrefManager.getInstance(this).getToken();
//        Call<ResponseBody> call = RetrofitClient
//                .getInstanceToken(token)
//                .getApi()
//                .aapDelete(diagnosis.getId().getObjectID());
//
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful()) {
//                    Toast.makeText(PocDetailActivity.this, "The record has been successfully deleted", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(PocDetailActivity.this, "This record is unable to be deleted", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(PocDetailActivity.this, "Failed to delete the record", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    public void clearData(View view) {
//        AlertDialog.Builder clearDialogBuilder = new AlertDialog.Builder(this);
//        clearDialogBuilder.setTitle("Are you sure you want to delete the record?");
//        clearDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface arg0, int arg1) {
//                // Delete record from database
//                deleteRecord();
//                Intent intent = new Intent(PocDetailActivity.this, AAPRecordsActivity.class);
//                startActivity(intent);
//            }
//        });
//        clearDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {    }
//        });
//
//        AlertDialog alertDialog = clearDialogBuilder.create();
//        alertDialog.show();
//    }
//}
//
