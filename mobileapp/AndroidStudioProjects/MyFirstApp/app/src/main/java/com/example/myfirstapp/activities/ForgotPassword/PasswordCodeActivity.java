package com.example.myfirstapp.activities.ForgotPassword;

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
import com.example.myfirstapp.activities.Main.RegisterActivity;


/**
 * Enter the code the user has received from email
 */

public class PasswordCodeActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_code);

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

        editTextCode = findViewById(R.id.codeText);
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

    private void sendCode(){
        String code = editTextCode.getText().toString();
        if(!code.matches("")){
            Intent intent = new Intent(this, ResetPasswordActivity.class);
            intent.putExtra("code",code);
            startActivity(intent);
        }
        else{
            Toast.makeText(PasswordCodeActivity.this, "You haven't entered a code, please enter one and try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sendCodeButton:
                sendCode();
                break;
            case R.id.registerButton:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

}
