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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myfirstapp.API.RetrofitClient;
import com.example.myfirstapp.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ResetPasswordActivity extends AppCompatActivity {
    private EditText editTextPassword;
    private EditText editTextReEnterPassword;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        //make translucent statusBar on kitkat devices
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            code = extras.getString("code");
        }

        editTextPassword = findViewById(R.id.passwordText);
        editTextReEnterPassword = findViewById(R.id.reEnterPasswordText);
        Button resetPasswordButton = findViewById(R.id.resetPasswordButton);

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword(v);
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

    public void resetPassword(View view) {
        String password = editTextPassword.getText().toString();
        String reEnteredPassword = editTextReEnterPassword.getText().toString();

        if (!password.matches("") || !reEnteredPassword.matches("")) {
            if (password.equals(reEnteredPassword)) {
                //Password has been validated, send the new password to the api as the user's new password
                //On response, start the login activity
                resetPassword(code, password);
            } else {
                Toast.makeText(ResetPasswordActivity.this, "The passwords entered do not match, please try again", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(ResetPasswordActivity.this, "You haven't entered a new password, please enter one and try again", Toast.LENGTH_LONG).show();

        }
    }

    private void resetPassword(String token, String newPassword) {
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .changePassword(token, newPassword);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ResetPasswordActivity.this, "Your Password Has Been Updated", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ResetPasswordActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "Please Enter a Valid Password and Code", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ResetPasswordActivity.this, PasswordCodeActivity.class));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ResetPasswordActivity.this, "Failed to update password", Toast.LENGTH_LONG).show();
                startActivity(new Intent(ResetPasswordActivity.this, PasswordCodeActivity.class));
            }
        });
    }
}
