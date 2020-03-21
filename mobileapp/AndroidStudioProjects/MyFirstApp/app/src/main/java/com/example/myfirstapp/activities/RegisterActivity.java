package com.example.myfirstapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

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

import com.example.myfirstapp.activities.MainActivity;

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


        //make translucent statusBar on kitkat devices
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21)
        {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19)
        {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21)
        {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        ArrayList<String> years = new ArrayList<String>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for(int i = 1900; i <= currentYear; i++){
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

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.months, android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinmonth = (Spinner) findViewById(R.id.monthspin);
        spinmonth.setAdapter(adapter);

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

//    public void LoginDirect(View view)
//    {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//    }
//
//    public void Register(View view)
//    {
//        EditText emailText = findViewById(R.id.emailText);
//        EditText passwordText = findViewById(R.id.passwordText);
//        EditText nameText = findViewById(R.id.nameText);
//
//        String email = emailText.getText().toString();
//        String password = passwordText.getText().toString();
//        String name = nameText.getText().toString();
//
//        Intent checkIntent = new Intent(getApplicationContext(), RegisterActivity.class);
//
//        if (TextUtils.isEmpty(email)) {
//            Toast.makeText(RegisterActivity.this,"No email was entered, please enter an email to continue",Toast.LENGTH_LONG).show();
//        }
//        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            Toast.makeText(RegisterActivity.this,"The email address entered is not valid",Toast.LENGTH_LONG).show();
//        }
//        else if(TextUtils.isEmpty(password)){
//            Toast.makeText(RegisterActivity.this,"No password was entered, please enter an password to continue",Toast.LENGTH_LONG).show();
//        }
//        else if (TextUtils.isEmpty(name)){
//            startActivity(checkIntent);
//            Toast.makeText(RegisterActivity.this,"No name was entered, please enter an name to continue",Toast.LENGTH_LONG).show();
//        }
//        else {
//            Resources res = getResources();
//            String[] terms = res.getStringArray(R.array.terms_and_conditions);
//            AlertDialog.Builder termsDialogBuilder = new AlertDialog.Builder(this);
//            termsDialogBuilder.setTitle("Terms and Conditions");
//            termsDialogBuilder.setMessage(terms[0] + terms[1] + terms[2] + terms[3] + terms[4] + terms[5] + terms[6]);
//            termsDialogBuilder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface arg0, int arg1) {
//                    Toast.makeText(RegisterActivity.this, "Thank you for Registering", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//
//                    Spinner yearSpinItem = findViewById(R.id.yearspin);
//                    Spinner monthSpinItem = findViewById(R.id.monthspin);
//                    Spinner daySpinItem = findViewById(R.id.dayspin);
//
//                    Integer day = Integer.parseInt((String) daySpinItem.getSelectedItem());
//                    String month = monthSpinItem.getSelectedItem().toString();
//                    Integer year = Integer.parseInt((String) yearSpinItem.getSelectedItem());
//
//                    String dateOfBirth = year + "/" + month + "/" + day;
//
//                    startActivity(intent);
//                }
//            });
//
//            termsDialogBuilder.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
//                    finish();
//                    startActivity(intent);
//                    Toast.makeText(RegisterActivity.this, "Please Accept the Terms and Conditions", Toast.LENGTH_LONG).show();
//                }
//            });
//
//            AlertDialog alertDialog = termsDialogBuilder.create();
//            alertDialog.show();
//
//        }
//
//    }

    private void userSignUp() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (name.isEmpty()) {
            editTextName.setError("Name is required");
            editTextName.requestFocus();
            return;
        }

        if (password.length() < 2) {
            editTextPassword.setError("Password must be atleast 2 characters");
            editTextPassword.requestFocus();
            return;
        }

        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .createUser(email, password, name);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if(response.code() == 201){
                    DefaultResponse dr = response.body();
                    Toast.makeText(RegisterActivity.this, dr.getEmailMsg(), Toast.LENGTH_SHORT).show();
                }else{

                    Toast.makeText(RegisterActivity.this, "User already Exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Log.d("Register", t.getMessage());
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerButton:
                userSignUp();
                startActivity(new Intent(this, DisplayFeaturesActivity.class));
                break;
            case R.id.loginButton:
                startActivity(new Intent(this, MainActivity.class));

                break;
        }
    }

}
