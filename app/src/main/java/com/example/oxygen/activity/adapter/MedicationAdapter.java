package com.example.oxygen.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.oxygen.activity.model.MedicationModel;
import com.example.oxygen.R;
import java.util.ArrayList;

public class MedicationAdapter extends ArrayAdapter<MedicationModel> {
    // View lookup cache
    private static class ViewHolder {
        TextView name;
        TextView times;
        TextView doses;
    }

    public MedicationAdapter(Context context, ArrayList<MedicationModel> medication) {
        super(context, R.layout.medicen_row, medication);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MedicationModel medicen = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.medicen_row, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.txt_name);
            viewHolder.times = (TextView) convertView.findViewById(R.id.txt_times);
            viewHolder.doses = (TextView) convertView.findViewById(R.id.txt_dose);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.name.setText("Controller:" + medicen.getName());
        viewHolder.times.setText(" " + medicen.getTimes()+" doses per day");
        viewHolder.doses.setText("Doses: " + medicen.getDose()+" mg");
        // Return the completed view to render on screen
        return convertView;
    }
}