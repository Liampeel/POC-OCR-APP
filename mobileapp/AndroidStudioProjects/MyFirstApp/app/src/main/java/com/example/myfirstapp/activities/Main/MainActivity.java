package com.example.myfirstapp.activities.Main;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.API.RetrofitClient;
import com.example.myfirstapp.Model.LoginResponse;
import com.example.myfirstapp.R;
import com.example.myfirstapp.Storage.SharedPrefManager;
import com.example.myfirstapp.activities.ForgotPassword.ForgottenPasswordActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class for logging in a user
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String EXTRA_MESSAGE = "Email Address";
    public static final String EXTRA_MESSAGE2 = "password";
    private EditText editTextEmail;
    private EditText editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

//        findViewById(R.id.loginButton).setOnClickListener(this);
        findViewById(R.id.registerText).setOnClickListener(this);
        findViewById(R.id.loginBtn).setOnClickListener(this);
        findViewById(R.id.forgottenPasswordButton).setOnClickListener(this);
//        findViewById(R.id.tessbtn).setOnClickListener(this);
//        findViewById(R.id.google).setOnClickListener(this);


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

    /**
     * Validators for logging in
     * Make request to API to log in
     */

    private void userLogin(){
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 2){
            editTextPassword.setError("Password must be atleast 2 characters");
            editTextPassword.requestFocus();
            return;
        }

        Call<LoginResponse> call = RetrofitClient
                .getInstanceAuth(email, password)
                .getApi()
                .userLogin(email, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();

                if(response.code() == 200){
                    if (loginResponse != null) {
                        String token = loginResponse.getToken();
                        SharedPrefManager.getInstance(MainActivity.this).saveUser(loginResponse.getUser());
                        SharedPrefManager.getInstance(MainActivity.this).saveToken(token);
                    }


                    Toast.makeText(MainActivity.this, loginResponse.getToken(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, POCHomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else if(response.code() == 401) {
                    Toast.makeText(MainActivity.this, "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error Logging in", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Call Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginBtn:
                userLogin();
                break;
            case R.id.registerText:
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.forgottenPasswordButton:
                Intent intent2 = new Intent(this, ForgottenPasswordActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent2);
                break;

        }
    }


}