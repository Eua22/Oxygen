package com.example.oxygen.activity.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.oxygen.R;
import com.example.oxygen.activity.adapter.MedicationAdapter;
import com.example.oxygen.activity.adapter.ReliefAdapter;
import com.example.oxygen.activity.model.MedicationModel;
import com.example.oxygen.activity.model.ReliefModel;
import com.example.oxygen.sqlite.DBManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PlanList extends Fragment {

    private View rootView;

    private DBManager dbManager;
    private SimpleDateFormat sdf;
    private ArrayList<ReliefModel> reliefsList;
    private ReliefAdapter relifAdapter;
    private ArrayList<MedicationModel> mediationsList;
    private MedicationAdapter medicationAdapter;
    private int user_id;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    public static final String MyPREFERENCES = "UserPrefs" ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        rootView = inflater.inflate(R.layout.fragment_plan_list, container, false);

        sharedpreferences =  getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        user_id = sharedpreferences.getInt("id", 0);
        Toast.makeText(getActivity(), "Welcome "+user_id, Toast.LENGTH_LONG).show();

        dbManager = new DBManager(getActivity());
        dbManager.open();

        reliefsList = dbManager.getReliefById(user_id);
        // Create the adapter
        relifAdapter = new ReliefAdapter(getActivity(), reliefsList);
        // Attach the adapter to a ListView
        ListView listViewRefil = (ListView) rootView.findViewById(R.id.listview_relief);
        //set adapter
        listViewRefil.setAdapter(relifAdapter);

        weeklyUsageLimit();

        listViewRefil.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Reliever")
                        .setMessage("Do you really want to delete?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                ReliefModel toRemove = relifAdapter.getItem(position);
                                Log.d("############", toRemove.getId() + "");
                                relifAdapter.remove(toRemove);
                                dbManager.deleteRelief(toRemove.getId());
                                Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        mediationsList = dbManager.getMedicationByUserId(user_id);
        // Create the adapter
        medicationAdapter = new MedicationAdapter(getActivity(), mediationsList);
        // Attach the adapter to a ListView
        ListView listViewMedication = (ListView) rootView.findViewById(R.id.listview_medicen);
        //set adapter
        listViewMedication.setAdapter(medicationAdapter);
        // set click event in list view
        listViewMedication.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(getContext()) // when maje click on list view item show a pop up windo
                        .setTitle("Treatment")
                        .setMessage("Do you really want to delete?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) { // if click on yes then the delete the medice
                                MedicationModel toRemove = medicationAdapter.getItem(position);

                                medicationAdapter.remove(toRemove); // get the item and delete from list view
                                dbManager.deleteMedicen(toRemove.getId()); // remove also and from database
                                Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        return  rootView;
    }

    public void weeklyUsageLimit() {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        int counter = 0 ;
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);

        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        Date monday = c.getTime();

        Date nextMonday= new Date(monday.getTime()+7*24*60*60*1000);
        System.out.println(monday);
        System.out.println(nextMonday);
        for (ReliefModel rel: reliefsList
        ) {
            try {
                date = format.parse(rel.getDate());
                System.out.println(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(date.after(monday) && date.before(nextMonday))
                counter++;
        }

        System.out.println(counter);
        if(counter >= 5){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
            builder1.setMessage("Caution you have exceeded the weekly usage limit!!!!!!!!!!\n"+counter+" Uses");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }

    }
}
