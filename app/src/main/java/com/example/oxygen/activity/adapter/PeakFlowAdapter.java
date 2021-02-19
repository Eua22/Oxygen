package com.example.oxygen.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.oxygen.R;
import com.example.oxygen.activity.model.MedicationModel;
import com.example.oxygen.activity.model.PeakFlow;

import java.util.ArrayList;

public class PeakFlowAdapter extends ArrayAdapter<PeakFlow> {
    // View lookup cache
    private static class ViewHolder {
        TextView peakflow;
        TextView percent;

    }

    public PeakFlowAdapter(Context context, ArrayList<PeakFlow> medication) {
        super(context, R.layout.peak_flow_row, medication);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        PeakFlow peakFlow = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.medicen_row, parent, false);
            viewHolder.peakflow = (TextView) convertView.findViewById(R.id.txt_name);
            viewHolder.percent = (TextView) convertView.findViewById(R.id.txt_times);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.peakflow.setText("Value peak flow " + peakFlow.getPeakValue());
        viewHolder.percent.setText("" + peakFlow.getTimesShowPeakValue()+"%");
        // Return the completed view to render on screen
        return convertView;
    }
}