package com.example.myfirstapp.activities.Records;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myfirstapp.Model.PocResponse;
import com.example.myfirstapp.R;

import java.util.List;

public class POCListAdapter extends ArrayAdapter {

    private final Activity context;
    private final List<PocResponse> pocList;

    public POCListAdapter(Activity context, List<PocResponse> pocList){
        super(context, R.layout.listview_poc_row , pocList);

        this.context = context;
        this.pocList = pocList;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_poc_row, null,true);

        TextView nameTextField = (TextView) rowView.findViewById(R.id.nameView);
        nameTextField.setText("Created: " + pocList.get(position).getDate_submitted());

        return rowView;
    }


}
