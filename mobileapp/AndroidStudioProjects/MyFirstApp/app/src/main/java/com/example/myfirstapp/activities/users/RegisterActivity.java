package com.example.myfirstapp.activities.users;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;

import android.os.Bundle;

import com.example.myfirstapp.API.RetrofitClient;
import com.example.myfirstapp.Model.DefaultResponse;
import com.example.myfirstapp.R;

import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextEmail, editTextPassword, editTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);

        findViewById(R.id.registerButton).setOnClickListener(this);
        findViewById(R.id.loginButton).setOnClickListener(this);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        ArrayList<String> years = new ArrayList<String>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for(int i = currentYear; i >= 1900; i-=1){
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        Spinner spinYear = (Spinner)findViewById(R.id.yearspin);
        spinYear.setAdapter(adapterYear);

        ArrayList<String> days = new ArrayList<String>();
        for(int i = 1; i <= 31; i++){
            days.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapterDay = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, days);
        Spinner spinDay = (Spinner)findViewById(R.id.dayspin);
        spinDay.setAdapter(adapterDay);

        ArrayList<String> months = new ArrayList<String>();
        for(int i = 1; i <= 12; i++){
            months.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapterMonth = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, months);
        Spinner spinmonth = (Spinner) findViewById(R.id.monthspin);
        spinmonth.setAdapter(adapterMonth);
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

    private void userSignUp(String dateOfBirth) {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }
        else if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        else if (name.isEmpty()) {
            editTextName.setError("Name is required");
            editTextName.requestFocus();
            return;
        }
        else if (password.length() < 2) {
            editTextPassword.setError("Password must be at least 2 characters");
            editTextPassword.requestFocus();
            return;
        }

        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .createUser(email, password, name, dateOfBirth);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.code() == 201) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    Toast.makeText(RegisterActivity.this, "Thank you for Registering", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Log.d("Register", t.getMessage());
            }
        });
    }

    public void register()
    {
        Resources res = getResources();
        String[] terms = res.getStringArray(R.array.terms_and_conditions);
        AlertDialog.Builder termsDialogBuilder = new AlertDialog.Builder(this);
        termsDialogBuilder.setTitle("Terms and Conditions");
        termsDialogBuilder.setMessage(terms[0] + terms[1] + terms[2] + terms[3] + terms[4] + terms[5] + terms[6]);
        termsDialogBuilder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Spinner yearSpinItem = findViewById(R.id.yearspin);
                Spinner monthSpinItem = findViewById(R.id.monthspin);
                Spinner daySpinItem = findViewById(R.id.dayspin);

                Integer day = Integer.parseInt((String) daySpinItem.getSelectedItem());
                String month = monthSpinItem.getSelectedItem().toString();
                Integer year = Integer.parseInt((String) yearSpinItem.getSelectedItem());

                String dateOfBirth = year + "/" + month + "/" + day;
                System.out.println("Date of Birth = " + dateOfBirth);

                userSignUp(dateOfBirth);
            }
        });

        termsDialogBuilder.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                finish();
                startActivity(intent);
                Toast.makeText(RegisterActivity.this, "Please Accept the Terms and Conditions", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = termsDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerButton:
                register();
                break;
            case R.id.loginButton:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

}
