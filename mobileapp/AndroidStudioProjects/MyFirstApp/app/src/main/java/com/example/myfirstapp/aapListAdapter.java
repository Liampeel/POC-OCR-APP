package com.example.myfirstapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class aapListAdapter extends ArrayAdapter {

    private final Activity context;
    private final String[] nameArray;

    public aapListAdapter(Activity context, String[] nameArrayParam){
        super(context,R.layout.listview_aap_row , nameArrayParam);

        this.context = context;
        this.nameArray = nameArrayParam;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_aap_row, null,true);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.answers, android.R.layout.simple_spinner_item);
        Spinner spinner = (Spinner) rowView.findViewById(R.id.answerSpinner);
        spinner.setAdapter(adapter);

        TextView nameTextField = (TextView) rowView.findViewById(R.id.nameView);
        nameTextField.setText(nameArray[position]);

        return rowView;

    };


}
