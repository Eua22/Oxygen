package com.example.oxygen.activity.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.oxygen.R;
import com.example.oxygen.activity.adapter.MedicationAdapter;
import com.example.oxygen.activity.adapter.PeakFlowAdapter;
import com.example.oxygen.activity.model.MedicationModel;
import com.example.oxygen.activity.model.PeakFlow;
import com.example.oxygen.activity.model.User;
import com.example.oxygen.sqlite.DBManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class PeakFlowValues extends Fragment {

    private View rootView;
    private LineChart lineChart;
    private LineData lineData;
    private LineDataSet lineDataSet;
    private ArrayList lineEntries;
    private EditText pfv;
    private Button add;
    private int listSize;
    private DBManager dbManager;
    private Dialog chartDialogs;
    private ArrayList<PeakFlow> peakFlowList;
    private PeakFlowAdapter peakFlowAdapter;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    public static final String MyPREFERENCES = "UserPrefs" ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        rootView = inflater.inflate(R.layout.fragment_peak_flow, container, false);

        sharedpreferences =  getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        final int user_id = sharedpreferences.getInt("id", 0);
        System.out.println("sasas!!!! "+user_id);
        dbManager = new DBManager(getActivity());
        dbManager.open();

//        peakFlowList = dbManager.getPeakFlow();
        peakFlowList = dbManager.getPeakFlowByUserId(user_id);

        Map<Integer ,Integer> map = new HashMap<>();

        listSize = 0;
        for(PeakFlow p  : peakFlowList) {
            listSize = listSize + 1;
            if(  map.containsKey(p.getPeakValue())   ) {
                map.put(p.getPeakValue(), map.get(p.getPeakValue()) + 1);
            }//if
            else {
                map.put(p.getPeakValue(), 1);
            }
        }//fo

        Set< Map.Entry<Integer ,Integer> > entrySet = map.entrySet();
        ArrayList<PeakFlow> newPeakflowList = new ArrayList<>();
        for(    Map.Entry<Integer ,Integer>  entry : entrySet     ) {
            newPeakflowList.add(new PeakFlow(entry.getKey(),(double)entry.getValue()/listSize));
        }//for
        // Create the adapter
        peakFlowAdapter = new PeakFlowAdapter(getActivity(), newPeakflowList);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_peakflow);
        //set adapter
        listView.setAdapter(peakFlowAdapter);

        pfv = (EditText) rootView.findViewById(R.id.et_pfv);

        add = rootView.findViewById(R.id.btn_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = pfv.getText().toString();
                String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                User user = dbManager.getUserById(user_id);
                Log.d("user",user.getBirthday());
                Log.d("!!!!!!!!!!!!!",findDifference(user.getBirthday(),currentDate)+"");
                long id = dbManager.insertPeakFlow(value,currentDate,user.getHeight(), findDifference(user.getBirthday(),currentDate),user_id);
                if(id >= 0) {
                    Toast.makeText(getContext(), "Peak Flow Store", Toast.LENGTH_LONG).show();
//                    chartDialog(getContext());
                    //        peakFlowList = dbManager.getPeakFlow();
                    peakFlowList = dbManager.getPeakFlowByUserId(user_id);
                    // Create the adapter
                    Map<Integer ,Integer> map = new HashMap<>();

                    listSize = 0;
                    for( PeakFlow p  : peakFlowList) {
                        listSize = listSize + 1;
                        if(  map.containsKey(p.getPeakValue())   ) {
                            map.put(p.getPeakValue(), map.get(p.getPeakValue()) + 1);
                        }//if
                        else {
                            map.put(p.getPeakValue(), 1);
                        }
                    }//fo

                    Set< Map.Entry<Integer ,Integer> > entrySet = map.entrySet();
                    ArrayList<PeakFlow> newPeakflowList = new ArrayList<>();
                    for(    Map.Entry<Integer ,Integer>  entry : entrySet     ) {
                        newPeakflowList.add(new PeakFlow(entry.getKey(),(double)entry.getValue()/listSize));
                    }//for
                    // Create the adapter
                    peakFlowAdapter = new PeakFlowAdapter(getActivity(), newPeakflowList);

                    ListView listView = (ListView) rootView.findViewById(R.id.listview_peakflow);
                    //set adapter
                    listView.setAdapter(peakFlowAdapter);
                }
            }
        });

        return rootView;
    }

//    private void chartDialog(final Context context) {
//        chartDialogs = new Dialog(getActivity());
//        chartDialogs.setContentView(R.layout.fragment_chart);
//        chartDialogs.setCancelable(true);
//
//        lineChart = chartDialogs.findViewById(R.id.lineChart);
//        getEntries();
//        lineDataSet = new LineDataSet(lineEntries, "");
//        lineData = new LineData(lineDataSet);
//        lineChart.setData(lineData);
//        lineDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
//        lineDataSet.setValueTextColor(Color.BLACK);
//        lineDataSet.setValueTextSize(18f);
//        chartDialogs.show();
//    }
//
//    private void getEntries() {
//        Log.d("sasas",dbManager.getPeakFlow()+"");
//        lineEntries = new ArrayList<>();
//        for (PeakFlowValues peak: dbManager.getPeakFlow()
//             ) {
//            lineEntries.add(new Entry(peak.getPeakValue(), peak.getAge()));
//        }
////        lineEntries.add(new Entry(2f, 0));
////        lineEntries.add(new Entry(4f, 1));
////        lineEntries.add(new Entry(6f, 1));
////        lineEntries.add(new Entry(8f, 3));
////        lineEntries.add(new Entry(7f, 4));
////        lineEntries.add(new Entry(3f, 3));
//    }

    static String findDifference(String start_date,String end_date)
    {

        long difference_In_Years = 0;
        // SimpleDateFormat converts the
        // string format to date object
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Try Block
        try {

            // parse method is used to parse
            // the text from a string to
            // produce the date
            Date d1 = sdf.parse(start_date);
            Date d2 = sdf.parse(end_date);

            // Calucalte time difference
            // in milliseconds
            long difference_In_Time
                    = d2.getTime() - d1.getTime();

            // Calucalte time difference in
            // years

            difference_In_Years
                    = (difference_In_Time
                    / (1000l * 60 * 60 * 24 * 365));




        }        // Catch the Exception
        catch (ParseException e) {
            e.printStackTrace();
        }
        return  String.valueOf(difference_In_Years);
    }

}