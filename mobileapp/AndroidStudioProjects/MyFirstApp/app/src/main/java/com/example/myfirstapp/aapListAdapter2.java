package com.example.myfirstapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class aapListAdapter2 extends ArrayAdapter {

    private final Activity context;
    private final String[] nameArray;

    public aapListAdapter2(Activity context, String[] nameArrayParam){
        super(context,R.layout.listview_aap2_row , nameArrayParam);

        this.context = context;
        this.nameArray = nameArrayParam;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_aap2_row, null,true);

        TextView nameTextField = (TextView) rowView.findViewById(R.id.nameView);
        nameTextField.setText(nameArray[position]);

        return rowView;

    };


}
