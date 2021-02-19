package com.example.oxygen.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.oxygen.R;
import com.example.oxygen.activity.model.ReliefModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ReliefAdapter extends ArrayAdapter<ReliefModel> {
    private static Context c;

    // View lookup cache
    private static class ViewHolder {
        TextView name;
        TextView date;
    }

    public ReliefAdapter(Context context, ArrayList<ReliefModel> relief) {
        super(context, R.layout.relief_row, relief);
        this.c = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ReliefModel relief = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.relief_row, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.txt_name);
            viewHolder.date = (TextView) convertView.findViewById(R.id.txt_date);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.name.setText("Medicine:" + relief.getName());
        try {
            viewHolder.date.setText("Used before " + gettimelapse(relief.getDate())+"");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Return the completed view to render on screen
        return convertView;
    }

    ////get the difference by time string and Datetime now
    public static String gettimelapse(String date) throws ParseException {
        {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
            Date dt1 = sdf.parse(date);
            Date dt2 = sdf.parse(sdf.format(new Date()));


            long diff = dt2.getTime() - dt1.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            int diffInDays = (int) ((dt2.getTime() - dt1.getTime()) / (1000 * 60 * 60 * 24));
            String difRet = "";


            if (diffInDays > 1) {
                System.err.println("Difference in number of days (2) : " + diffInDays);
                difRet+= diffInDays+" Days ";
            }
            if (diffHours > 0) {
                System.err.println("diffHours "+diffHours   );
                difRet+= diffHours  +" Hours ";
            }
            if (diffMinutes >= 1) {
                System.err.println("minutes ");
                difRet+= diffMinutes+" Minutes";
            }
            return difRet;
        }
    }
}