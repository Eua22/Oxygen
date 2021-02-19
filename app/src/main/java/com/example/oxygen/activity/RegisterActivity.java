package com.example.oxygen.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oxygen.MainActivity;
import com.example.oxygen.R;
import com.example.oxygen.sqlite.DBManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    private EditText name,surname,email,mobile,heigh,weight,city,birthday,password;
    private Spinner sex;
    private DatePickerDialog picker;
    private DBManager dbManager;
    private String sexText;
    private Button _signupButton;
    private TextView _loginLink;
    private Calendar myCalendar;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myCalendar = Calendar.getInstance();

        dbManager = new DBManager(this);
        dbManager.open();

        name = findViewById(R.id.et_name);
        surname = findViewById(R.id.et_surname);
        birthday = findViewById(R.id.et_birthday);
        email = findViewById(R.id.et_email);
        mobile = findViewById(R.id.et_mobile);
        heigh = findViewById(R.id.et_height);
        weight = findViewById(R.id.et_weight);
        city = findViewById(R.id.et_city);
        sex = (Spinner) findViewById(R.id.et_sex);
        password = (EditText) findViewById(R.id.et_password);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sex, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sex.setAdapter(adapter);



//        EditText edittext= (EditText) findViewById(R.id.et_birthday);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        birthday.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegisterActivity.this,  new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                birthday.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year); // set date picked in textfield birthdate
                            }
                        }, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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


        _signupButton = (Button) findViewById(R.id.btn_signup);
        _loginLink = (TextView) findViewById(R.id.link_login);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        birthday.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onBackPressed() {
        //moveTaskToBack(false);
        Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
        startActivity(intent);
        finish();
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String nameTxt = name.getText().toString();
        String surnameTxt = surname.getText().toString();
        String birthdayTxt = birthday.getText().toString();
        String emailTxt = email.getText().toString();
        String mobileTxt = mobile.getText().toString();
        String heighTxt = heigh.getText().toString();
        String weightTxt = weight.getText().toString();
        String cityTxt = city.getText().toString();
        String passwordText = password.getText().toString();

        // TODO: Implement your own signup logic here.

        dbManager.insertInfoUser(nameTxt,surnameTxt,birthdayTxt,emailTxt,mobileTxt,sexText,heighTxt,weightTxt,cityTxt,passwordText);
        Toast.makeText(this, "insert ", Toast.LENGTH_SHORT).show();
        dbManager.close();


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
//                        onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Register failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String nameTxt = name.getText().toString();
        String surnameTxt = surname.getText().toString();
        String birthdayTxt = birthday.getText().toString();
        String emailTxt = email.getText().toString();
        String mobileTxt = mobile.getText().toString();
        String heighTxt = heigh.getText().toString();
        String weightTxt = weight.getText().toString();
        String cityTxt = city.getText().toString();
        String passwordText = password.getText().toString();

        if (nameTxt.isEmpty() || nameTxt.length() < 4) {
            name.setError("at least 4 characters");
            valid = false;
        } else {
            name.setError(null);
        }

        if (surnameTxt.isEmpty() || surnameTxt.length() < 4) {
            surname.setError("at least 4 characters");
            valid = false;
        } else {
            surname.setError(null);
        }

        if (mobileTxt.isEmpty() || mobileTxt.length() < 8) {
            mobile.setError("at least 8 numbers");
            valid = false;
        } else if(!mobileTxt.matches("[0-9]+")){
            mobile.setError("is not a valude number phone");
            valid = false;
        }else{
            mobile.setError(null);
        }


        if (emailTxt.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }

        if (passwordText.isEmpty() || passwordText.length() < 4 || passwordText.length() > 10) {
            password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }


}