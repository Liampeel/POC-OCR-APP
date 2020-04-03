package com.example.myfirstapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter {

    private final Activity context;
    private final int imageID;
    private final String[] nameArray;
    private final String[] infoArray;

    public CustomListAdapter(Activity context, String[] nameArrayParam, String[] infoArrayParam, int imageIDParam){
        super(context,R.layout.listview_row , nameArrayParam);

        this.context = context;
        this.imageID = imageIDParam;
        this.nameArray = nameArrayParam;
        this.infoArray = infoArrayParam;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);
        TextView nameTextField = (TextView) rowView.findViewById(R.id.nameView);
        TextView infoTextField = (TextView) rowView.findViewById(R.id.infoView);

        imageView.setImageResource(imageID);
        nameTextField.setText(nameArray[position]);
        infoTextField.setText(infoArray[position]);

        return rowView;
    }


}
