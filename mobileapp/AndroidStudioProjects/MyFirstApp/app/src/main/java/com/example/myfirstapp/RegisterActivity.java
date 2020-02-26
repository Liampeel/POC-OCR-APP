package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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

    public void LoginDirect(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void Register(View view)
    {
        Resources res = getResources();
        String[] terms = res.getStringArray(R.array.terms_and_conditions);
        AlertDialog.Builder termsDialogBuilder = new AlertDialog.Builder(this);
        termsDialogBuilder.setTitle("Terms and Conditions");
        termsDialogBuilder.setMessage(terms[0] + terms[1] + terms[2]+ terms[3] + terms[4] + terms[5] + terms[6]);
        termsDialogBuilder.setPositiveButton("Accept", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface arg0, int arg1)
            {
                Toast.makeText(RegisterActivity.this,"Thank you for Registering",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                EditText emailText = findViewById(R.id.emailText);
                EditText passwordText = findViewById(R.id.passwordText);
                EditText nameText = findViewById(R.id.nameText);
                EditText ageText = findViewById(R.id.ageText);

                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                String name = nameText.getText().toString();
                String age = ageText.getText().toString();

                startActivity(intent);
            }
        });

        termsDialogBuilder.setNegativeButton("Decline",new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                finish();
                startActivity(intent);
                Toast.makeText(RegisterActivity.this,"Please Accept the Terms and Conditions",Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = termsDialogBuilder.create();
        alertDialog.show();

    }

}
