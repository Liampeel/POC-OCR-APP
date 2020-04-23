package com.example.myfirstapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myfirstapp.Model.AAPDiagnosisResponse;

import java.util.List;

public class AAPListAdapter extends ArrayAdapter {

    private final Activity context;
    private final List<AAPDiagnosisResponse> diagnoses;

    public AAPListAdapter(Activity context, List<AAPDiagnosisResponse> diagnoses){
        super(context,R.layout.listview_row , diagnoses);

        this.context = context;
        this.diagnoses = diagnoses;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);

        TextView nameTextField = (TextView) rowView.findViewById(R.id.nameView);
        TextView infoTextField = (TextView) rowView.findViewById(R.id.infoView);

        nameTextField.setText("Created: " + diagnoses.get(position).getDate_submitted());
        infoTextField.setText("Diagnosis: " + diagnoses.get(position).getT_diagnosis());

        return rowView;
    }


}
