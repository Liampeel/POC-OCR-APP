package com.example.myfirstapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import com.example.myfirstapp.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import static com.example.myfirstapp.activities.main.domActivity.EXTRA_MESSAGE;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

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

    public void goodResult(View view){
        Intent oldIntent = getIntent();
        String filepath = oldIntent.getStringExtra(EXTRA_MESSAGE);

        Intent newIntent = new Intent(this,goodresultActivity.class);
        newIntent.putExtra(EXTRA_MESSAGE, filepath);
        startActivity(newIntent);
    }

    public void badResult(View view){
        Intent oldIntent = getIntent();
        String filepath = oldIntent.getStringExtra(EXTRA_MESSAGE);

        Intent newIntent = new Intent(this,resultbadActivity.class);
        newIntent.putExtra(EXTRA_MESSAGE, filepath);
        startActivity(newIntent);
    }
    // when loading is complete, use this line of code below and the loading screen should disappear
    //findViewById(R.id.loadingPanel).setVisibility(View.GONE);
}
