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

import com.example.myfirstapp.R;

public class resetPasswordActivity extends AppCompatActivity {
    private EditText editTextPassword;
    private EditText editTextReEnterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        editTextPassword = findViewById(R.id.passwordText);
        editTextReEnterPassword = findViewById(R.id.reEnterPasswordText);

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

    public void resetPassword(View view){
        String password = editTextPassword.getText().toString();
        String reEnteredPassword = editTextReEnterPassword.getText().toString();
        System.out.println("'" + password + "'");
        System.out.println("'" + reEnteredPassword + "'");

        if(!password.matches("") || !reEnteredPassword.matches("")){
            if(password.equals(reEnteredPassword)){
                //Password has been validated, send the new password to the api as the user's new password
                //On response, start the login activity
                startActivity(new Intent( this, MainActivity.class));
                }
            else{
                Toast.makeText(resetPasswordActivity.this, "The passwords entered do not match, please try again", Toast.LENGTH_SHORT).show();
                }
        }
        else{
            Toast.makeText(resetPasswordActivity.this, "You haven't entered a new password, please enter one and try again", Toast.LENGTH_SHORT).show();
            }
    }
}
