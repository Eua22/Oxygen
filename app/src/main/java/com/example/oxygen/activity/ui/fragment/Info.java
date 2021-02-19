package com.example.oxygen.activity.ui.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.oxygen.R;
import com.example.oxygen.activity.model.User;
import com.example.oxygen.sqlite.DBManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class Info extends Fragment {

    private View rootView ;
    private Button register;
    private EditText name,surname,email,mobile,heigh,weight,city,birthday,password;
    private Spinner sex;
    private DatePickerDialog picker;
    private DBManager dbManager;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    public static final String MyPREFERENCES = "UserPrefs" ;
    private String sexText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        rootView = inflater.inflate(R.layout.fragment_user_info, container, false);

        dbManager = new DBManager(getActivity());
        dbManager.open();

        sharedpreferences =  getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        final int user_id = sharedpreferences.getInt("id", 0);
        System.out.println(user_id);
        //initialized ui object to get,set and other action in activite
        register = rootView.findViewById(R.id.btn_register);

        name = rootView.findViewById(R.id.et_name);
        surname = rootView.findViewById(R.id.et_surname);
        birthday = rootView.findViewById(R.id.et_birthday);
        email = rootView.findViewById(R.id.et_email);
        mobile = rootView.findViewById(R.id.et_mobile);
        password = rootView.findViewById(R.id.et_password);
        heigh = rootView.findViewById(R.id.et_height);
        weight = rootView.findViewById(R.id.et_weight);
        city = rootView.findViewById(R.id.et_city);
        sex = (Spinner) rootView.findViewById(R.id.et_sex);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.sex, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sex.setAdapter(adapter);

        User user = dbManager.getUserById(user_id);

        name.setText(user.getName());
        Toast.makeText(getActivity(), "Welcome "+user.getName(), Toast.LENGTH_LONG).show();
        surname.setText(user.getSurname());
        birthday.setText(user.getBirthday());
        email.setText(user.getEmail());
        mobile.setText(user.getMobile());
        String sexStore = user.getSex();
        if(sexStore.equals("male"))
        sex.setSelection(1);
                else
            sex.setSelection(2);
        heigh.setText(user.getHeight());
        weight.setText(user.getWeight());
        city.setText(user.getCity());



        birthday.setInputType(InputType.TYPE_NULL); //set on click in birthdayedittext to open a datepicker
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                birthday.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year); // set date picked in textfield birthdate
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                sexText = sex.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        // on click in register button triget event get value from all edittext and store in user database
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ///get values from editextfields
                String nameTxt = name.getText().toString();
                String surnameTxt = surname.getText().toString();
                String birthdayTxt = birthday.getText().toString();
                String emailTxt = email.getText().toString();
                String mobileTxt = mobile.getText().toString();
//                String sexTxt = sex.getText().toString();
                String heighTxt = heigh.getText().toString();
                String weightTxt = weight.getText().toString();
                String cityTxt = city.getText().toString();
                String passwordText = password.getText().toString();

                if(passwordText.equals("")){ // if user ad new values update the info
                    dbManager.updateInfoUser(user_id,nameTxt,surnameTxt,birthdayTxt,emailTxt,mobileTxt,sexText,heighTxt,weightTxt,cityTxt);
                    Toast.makeText(getContext(), "update ", Toast.LENGTH_SHORT).show();
                    dbManager.close();
                }
                else
                {   ///if user has not store his info then store them
                    dbManager.updateInfoUser(user_id,nameTxt,surnameTxt,birthdayTxt,emailTxt,mobileTxt,sexText,heighTxt,weightTxt,cityTxt,passwordText);
                    Toast.makeText(getContext(), "update ", Toast.LENGTH_SHORT).show();
                    dbManager.close();
                }

            }
        });

        return rootView;
    }
}