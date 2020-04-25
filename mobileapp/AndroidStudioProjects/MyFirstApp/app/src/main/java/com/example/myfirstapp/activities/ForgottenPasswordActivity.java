package com.example.myfirstapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myfirstapp.API.RetrofitClient;
import com.example.myfirstapp.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgottenPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

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

        editTextEmail = findViewById(R.id.emailText);
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

    private void sendEmail(){
        String email = editTextEmail.getText().toString();
        if(!email.matches("")){
            //Send the email to the api so that an email can be sent to the user
            //On response, start the passwordCodeActivity
            resetPassword(email);
        }
        else{
            Toast.makeText(ForgottenPasswordActivity.this, "You haven't entered an email, please enter one and try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetPassword(String email) {
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .passwordReset(email);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ForgottenPasswordActivity.this, "Email Sent", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ForgottenPasswordActivity.this, PasswordCodeActivity.class));
                } else {
                    Toast.makeText(ForgottenPasswordActivity.this, "Please Enter a Valid Email", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ForgottenPasswordActivity.this, "Failed to Make the Request", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.resetPasswordButton:
                sendEmail();
                break;
            case R.id.registerButton:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }
}
