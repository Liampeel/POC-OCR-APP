package com.example.myfirstapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.R;
import com.example.myfirstapp.aapListAdapter;
import com.example.myfirstapp.aapListAdapter2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class question1Activity extends AppCompatActivity {


    String[] nameArray = {"Movement", "Coughing", "Respiration", "Food", "Other", "None"};
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_1);

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

        aapListAdapter2 listAdapter = new aapListAdapter2(this, nameArray);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                CheckBox cb = (CheckBox)view.findViewById(R.id.checkBox);
                cb.setChecked(!cb.isChecked());
                String name = nameArray[position];
                if(name == "None" && (cb.isChecked()==true)){
                    for ( int i=0; i < listView.getChildCount() - 1; i++) {
                        View item = listView.getChildAt(i);
                        CheckBox check = (CheckBox) item.findViewById(R.id.checkBox);
                        check.setEnabled(false);
                    }
                }
                else{
                    for ( int i=0; i < listView.getChildCount() - 1; i++) {
                        View item = listView.getChildAt(i);
                        CheckBox check = (CheckBox) item.findViewById(R.id.checkBox);
                        check.setEnabled(true);
                    }
                }
                Toast.makeText(question1Activity.this, name, Toast.LENGTH_SHORT).show();
            }
        });

        Button nextbtn = (Button) findViewById(R.id.nextButton);
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add code for going to the next multi answer question
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

}
