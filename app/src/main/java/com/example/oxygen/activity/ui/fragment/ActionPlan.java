package com.example.oxygen.activity.ui.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
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
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.oxygen.R;
import com.example.oxygen.activity.adapter.MedicationAdapter;
import com.example.oxygen.activity.adapter.ReliefAdapter;
import com.example.oxygen.activity.model.MedicationModel;
import com.example.oxygen.sqlite.DBManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ActionPlan extends Fragment {

    private View rootView ;
    private DBManager dbManager;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    public static final String MyPREFERENCES = "UserPrefs" ;
    private Spinner inhalers,controller,relief;
    private Button show,register;
    private EditText medicenTime, medicenDose;
    private Dialog mediceRegister;
    private int positionGlobal;
    private SimpleDateFormat sdf;
    private int user_id;
    private String refStr;
    public static int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION =1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        rootView = inflater.inflate(R.layout.fragment_action_plan, container, false);

        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());

        sharedpreferences =  getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        user_id = sharedpreferences.getInt("id", 0);
//        Toast.makeText(getActivity(), "Welcome "+user_id, Toast.LENGTH_LONG).show();

        dbManager = new DBManager(getActivity());
        dbManager.open();

        register = (Button) rootView.findViewById(R.id.btn_register_relief);
        show = rootView.findViewById(R.id.bt_show);

        controller = (Spinner) rootView.findViewById(R.id.spinnerMedication);
        ArrayAdapter<CharSequence> adapterController = ArrayAdapter.createFromResource(getActivity(), R.array.controllers, android.R.layout.simple_spinner_item);
        adapterController.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        controller.setAdapter(adapterController);

        controller.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0) {
                    Toast.makeText(getActivity(), "Chose "+position, Toast.LENGTH_LONG).show();
                    medicationDialog();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });


        relief = (Spinner) rootView.findViewById(R.id.spinnerReief);
        ArrayAdapter<CharSequence> adapterReief = ArrayAdapter.createFromResource(getActivity(), R.array.relief, android.R.layout.simple_spinner_item);
        adapterReief.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relief.setAdapter(adapterReief);

        relief.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Toast.makeText(getContext(), "Store", Toast.LENGTH_LONG).show();
                refStr = relief.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dbManager.insertRelief(refStr, sdf.format(new Date()),user_id);
//                Toast.makeText(getContext(), refStr, Toast.LENGTH_LONG).show();
            }
        });



        inhalers = (Spinner) rootView.findViewById(R.id.spinnerInhaler);
        ArrayAdapter<CharSequence> adapterInhalers = ArrayAdapter.createFromResource(getActivity(), R.array.inhalers, android.R.layout.simple_spinner_item);
        adapterInhalers.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inhalers.setAdapter(adapterInhalers);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (positionGlobal) {
                    case 1:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=W8zxdmgqVx0")));
                        break;
                    case 2:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=s3BwEpLAb0s")));
                        break;
                    case 3:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=mfiShjE9P-Q")));
                        break;
                    case 4:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=AI4ZMZL2uzI")));
                        break;
                    case 5:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=cUxqHPGEq60")));
                        break;
                    case 6:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=bXHHFmZ_DRI")));
                        break;
                    case 7:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=tp479j15x6Q")));
                        break;
                    case 8:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=BFT6b61xYdU")));
                        break;
                    case 9:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=von7cyXcj2c")));
                        break;
                    case 10:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=DqoEdW6dHaI")));
                        break;
                    case 11:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=F3u_A0b_O6s")));
                        break;
                }
            }
        });


        inhalers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                positionGlobal = position;
                switch(position) {
                    case 1:
                        ((ImageView) rootView.findViewById(R.id.imageView1)).setImageResource(R.drawable.aerolizer);
                        break;
                    case 2:
                        ((ImageView) rootView.findViewById(R.id.imageView1)).setImageResource(R.drawable.autohaler);
                        break;
                    case 3:
                        ((ImageView) rootView.findViewById(R.id.imageView1)).setImageResource(R.drawable.diskus);
                        break;
                    case 4:
                        ((ImageView) rootView.findViewById(R.id.imageView1)).setImageResource(R.drawable.ellipta);
                        break;
                    case 5:
                        ((ImageView) rootView.findViewById(R.id.imageView1)).setImageResource(R.drawable.flexhaler);
                        break;
                    case 6:
                        ((ImageView) rootView.findViewById(R.id.imageView1)).setImageResource(R.drawable.handihaler);
                        break;
                    case 7:
                        ((ImageView) rootView.findViewById(R.id.imageView1)).setImageResource(R.drawable.mdi);
                        break;
                    case 8:
                        ((ImageView) rootView.findViewById(R.id.imageView1)).setImageResource(R.drawable.respimat);
                        break;
                    case 9:
                        ((ImageView) rootView.findViewById(R.id.imageView1)).setImageResource(R.drawable.spacer);
                        break;
                    case 10:
                        ((ImageView) rootView.findViewById(R.id.imageView1)).setImageResource(R.drawable.turbuhaler);
                        break;
                    case 11:
                        ((ImageView) rootView.findViewById(R.id.imageView1)).setImageResource(R.drawable.twisthaler);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        return rootView;
    }

    private void medicationDialog() {
        mediceRegister = new Dialog(getActivity());
        mediceRegister.setContentView(R.layout.add_medication);
        mediceRegister.setCancelable(true);
        Button register = (Button) mediceRegister.findViewById(R.id.btn_register_medice);

        medicenTime = (EditText) mediceRegister.findViewById(R.id.medicen_time_txt);
        medicenDose = (EditText) mediceRegister.findViewById(R.id.medicen_dose_txt);
        mediceRegister.show();

        register.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                dbManager.insertMedicen(user_id,controller.getSelectedItem().toString(), medicenTime.getText().toString(), medicenDose.getText().toString());
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);}else if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED){

                    addReminderInCalendar(medicenDose.getText().toString(),medicenTime.getText().toString(),controller.getSelectedItem().toString());
                }


//                CalendarHelper.showReminderOneDayBeforeEvent(getContext());

            }
        });
    }

    /** Adds Events and Reminders in Calendar. */
    private void addReminderInCalendar(String dose, String times, String name) {
        Calendar cal = Calendar.getInstance();
        Uri EVENTS_URI = Uri.parse(getCalendarUriBase(true) + "events");
        ContentResolver cr = getActivity().getApplicationContext().getContentResolver();
        TimeZone timeZone = TimeZone.getDefault();

        /** Inserting an event in calendar. */
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.CALENDAR_ID, 1);
        values.put(CalendarContract.Events.TITLE, name);
        values.put(CalendarContract.Events.DESCRIPTION, "Get your "+dose+" mg");
        values.put(CalendarContract.Events.ALL_DAY, 0);
        // event starts at 11 minutes from now
        values.put(CalendarContract.Events.DTSTART, cal.getTimeInMillis());
        // ends 60 minutes from now
        values.put(CalendarContract.Events.DTEND, cal.getTimeInMillis() + 60 * 60 * 1000);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
        values.put(CalendarContract.Events.HAS_ALARM, 1);
        Uri event = cr.insert(EVENTS_URI, values);

        // Display event id
//        Toast.makeText(getActivity().getApplicationContext(), "Event added :: ID :: " + event.getLastPathSegment(), Toast.LENGTH_SHORT).show();

        /** Adding reminder for event added. */
        Uri REMINDERS_URI = Uri.parse(getCalendarUriBase(true) + "reminders");
        values = new ContentValues();
        values.put(CalendarContract.Reminders.EVENT_ID, Long.parseLong(event.getLastPathSegment()));
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        values.put(CalendarContract.Reminders.MINUTES, 5);
        cr.insert(REMINDERS_URI, values);
    }

    /** Returns Calendar Base URI, supports both new and old OS. */
    private String getCalendarUriBase(boolean eventUri) {
        Uri calendarURI = null;
        try {
//            if (android.os.Build.VERSION.SDK_INT <= 7) {
//                calendarURI = (eventUri) ? Uri.parse("content://calendar/") : Uri.parse("content://calendar/calendars");
//            } else {
            calendarURI = (eventUri) ? Uri.parse("content://com.android.calendar/") : Uri
                    .parse("content://com.android.calendar/calendars");
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendarURI.toString();
    }



}
