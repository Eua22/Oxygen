package com.example.oxygen.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.oxygen.activity.model.MedicationModel;
import com.example.oxygen.activity.model.PeakFlow;
import com.example.oxygen.activity.model.ReliefModel;
import com.example.oxygen.activity.model.User;

import java.util.ArrayList;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insertInfoUser(String name, String surname, String birthday, String email, String mobile, String sex, String height, String weight, String city, String password) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.NAME, name);
        contentValue.put(DatabaseHelper.SURNAME, surname);
        contentValue.put(DatabaseHelper.BIRTHDAY, birthday);
        contentValue.put(DatabaseHelper.EMAIL, email);
        contentValue.put(DatabaseHelper.MOBILE, mobile);
        contentValue.put(DatabaseHelper.SEX, sex);
        contentValue.put(DatabaseHelper.HEIGHT, height);
        contentValue.put(DatabaseHelper.WEIGHT, weight);
        contentValue.put(DatabaseHelper.CITY, city);
        contentValue.put(DatabaseHelper.PASSWORD, password);
        database.insert(DatabaseHelper.TABLE_USER, null, contentValue);
    }

    public void insertMedicen(int id_user,String name, String times,String dose) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.NAMEMEDICE, name);
        contentValue.put(DatabaseHelper.TIMES, times);
        contentValue.put(DatabaseHelper.DOSES, dose);
        contentValue.put(DatabaseHelper.USER_ID, id_user);
        database.insert(DatabaseHelper.TABLE_MEDICE, null, contentValue);
    }


    public void insertRelief(String name, String date) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.NAMEMEDICE, name);
        contentValue.put(DatabaseHelper.DATE, String.valueOf(date));
        database.insert(DatabaseHelper.TABLE_REFILER, null, contentValue);
    }

    public void insertRelief(String name, String date,int user_id) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.NAMEMEDICE, name);
        contentValue.put(DatabaseHelper.DATE, String.valueOf(date));
        contentValue.put(DatabaseHelper.USER_ID, user_id);
        database.insert(DatabaseHelper.TABLE_REFILER, null, contentValue);
    }

    public long insertPeakFlow(String value, String date, String height, String age,int user_id) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.HEIGHTPFU, height);
        contentValue.put(DatabaseHelper.AGEPFU, age);
        contentValue.put(DatabaseHelper.VALUEPFU, value);
        contentValue.put(DatabaseHelper.DATE, date);
        contentValue.put(DatabaseHelper.USER_ID, user_id);
        return database.insert(DatabaseHelper.TABLE_PEAK_FLOW_USER, null, contentValue);
    }

    public Cursor fetchInfoUser() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.NAME, DatabaseHelper.SURNAME, DatabaseHelper.BIRTHDAY,
                DatabaseHelper.EMAIL, DatabaseHelper.MOBILE, DatabaseHelper.SEX, DatabaseHelper.HEIGHT, DatabaseHelper.WEIGHT,
                DatabaseHelper.CITY, DatabaseHelper.PASSWORD};
        Cursor cursor = database.query(DatabaseHelper.TABLE_USER, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchMedicen() {
        String[] columns = new String[] { DatabaseHelper._IDMEDICE, DatabaseHelper.NAMEMEDICE, DatabaseHelper.TIMES };
        Cursor cursor = database.query(DatabaseHelper.TABLE_MEDICE, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public ArrayList<MedicationModel> getMedication(){

        String[] columns = new String[] { DatabaseHelper._IDMEDICE, DatabaseHelper.NAMEMEDICE, DatabaseHelper.TIMES,DatabaseHelper.DOSES };
        Cursor cursor = database.query(DatabaseHelper.TABLE_MEDICE, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        ArrayList<MedicationModel> medicans = new ArrayList<MedicationModel>();
        while(cursor.moveToNext()){
            MedicationModel medican = new MedicationModel();
            medican.setId(cursor.getInt(0));
            medican.setName(cursor.getString(1));
            medican.setTimes(cursor.getString(2));
            medican.setDose(cursor.getString(3));
            medicans.add(medican);
        }
        cursor.close();
        return medicans;
    }

    public ArrayList<MedicationModel> getMedicationByUserId(int id){

        String[] columns = new String[] { DatabaseHelper._IDMEDICE, DatabaseHelper.NAMEMEDICE, DatabaseHelper.TIMES,DatabaseHelper.DOSES,DatabaseHelper.USER_ID };
        Cursor cursor = database.query(DatabaseHelper.TABLE_MEDICE, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        ArrayList<MedicationModel> medicans = new ArrayList<MedicationModel>();
        while(cursor.moveToNext()){
            MedicationModel medican = new MedicationModel();
            if(cursor.getInt(4) == id) {
                medican.setId(cursor.getInt(0));
                medican.setName(cursor.getString(1));
                medican.setTimes(cursor.getString(2));
                medican.setDose(cursor.getString(3));
                medicans.add(medican);
            }
        }
        cursor.close();
        return medicans;
    }

    public ArrayList<ReliefModel> getReliefById(int id){

        String[] columns = new String[] { DatabaseHelper._IDMEDICE, DatabaseHelper.NAMEMEDICE, DatabaseHelper.DATE,DatabaseHelper.USER_ID };
        Cursor cursor = database.query(DatabaseHelper.TABLE_REFILER, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        ArrayList<ReliefModel> reliefs = new ArrayList<ReliefModel>();
        while(cursor.moveToNext()){
            ReliefModel relief = new ReliefModel();
            if(cursor.getInt(3) == id) {
                relief.setId(cursor.getInt(0));
                relief.setName(cursor.getString(1));
                relief.setDate(cursor.getString(2));
                reliefs.add(relief);
            }
        }
        cursor.close();
        return reliefs;
    }

    public ArrayList<ReliefModel> getRelief(){

        String[] columns = new String[] { DatabaseHelper._IDMEDICE, DatabaseHelper.NAMEMEDICE, DatabaseHelper.DATE};
        Cursor cursor = database.query(DatabaseHelper.TABLE_REFILER, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        ArrayList<ReliefModel> reliefs = new ArrayList<ReliefModel>();
        while(cursor.moveToNext()){
            ReliefModel relief = new ReliefModel();
            relief.setId(cursor.getInt(0));
            relief.setName(cursor.getString(1));
            relief.setDate(cursor.getString(2));
            reliefs.add(relief);
        }
        cursor.close();
        return reliefs;
    }

    public User getUser(){

        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.NAME, DatabaseHelper.SURNAME, DatabaseHelper.BIRTHDAY,
                DatabaseHelper.EMAIL, DatabaseHelper.MOBILE, DatabaseHelper.SEX, DatabaseHelper.HEIGHT, DatabaseHelper.WEIGHT, DatabaseHelper.CITY};
        Cursor cursor = database.query(DatabaseHelper.TABLE_USER, columns, null, null, null, null, null);
        User user = new User();
        if (cursor != null) {
            cursor.moveToFirst();
            user.setId(cursor.getInt(0));
            user.setHeight(cursor.getString(7));
            user.setBirthday(cursor.getString(3));
            user.setSex(cursor.getString(6));
        }


            Log.d("sdaas",cursor.getInt(0) + " "+cursor.getString(8));


        cursor.close();
        return user;
    }

    public User getUserById(int id){

        String sql = "SELECT * FROM " + DatabaseHelper.TABLE_USER
                + " WHERE " + DatabaseHelper._ID + " = " + id;
        Cursor cursor = database.rawQuery(sql, null);
        User user = new User();

        // Read data, I simplify cursor in one line
        if (cursor.moveToFirst()) {

            // Get imageData in byte[]. Easy, right?
            user.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME)));
            user.setSurname(cursor.getString(cursor.getColumnIndex(DatabaseHelper.SURNAME)));
            user.setBirthday(cursor.getString(cursor.getColumnIndex(DatabaseHelper.BIRTHDAY)));
            user.setMobile(cursor.getString(cursor.getColumnIndex(DatabaseHelper.MOBILE)));
            user.setSex(cursor.getString(cursor.getColumnIndex(DatabaseHelper.SEX)));
            user.setHeight(cursor.getString(cursor.getColumnIndex(DatabaseHelper.HEIGHT)));
            user.setWeight(cursor.getString(cursor.getColumnIndex(DatabaseHelper.WEIGHT)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(DatabaseHelper.EMAIL)));
            user.setCity(cursor.getString(cursor.getColumnIndex(DatabaseHelper.CITY)));
        }

        Log.d("sdaas","dsadasd");


        cursor.close();
        return user;
    }

    public ArrayList<PeakFlow> getPeakFlow(){

        String[] columns = new String[] { DatabaseHelper._IDPFU, DatabaseHelper.HEIGHTPFU, DatabaseHelper.AGEPFU, DatabaseHelper.VALUEPFU, DatabaseHelper.DATE};
        Cursor cursor = database.query(DatabaseHelper.TABLE_PEAK_FLOW_USER, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        ArrayList<PeakFlow> peakFlows = new ArrayList<PeakFlow>();
        while(cursor.moveToNext()){
            PeakFlow peakFlow = new PeakFlow();
            peakFlow.setId(cursor.getInt(0));
            peakFlow.setHeight(cursor.getInt(1));
            peakFlow.setAge(cursor.getInt(2));
            peakFlow.setPeakValue(cursor.getInt(3));
            peakFlow.setDate(cursor.getString(4));
            peakFlows.add(peakFlow);
        }
        cursor.close();
        return peakFlows;
    }


    public ArrayList<PeakFlow> getPeakFlowByUserId(int id){

        String sql = "SELECT * FROM " + DatabaseHelper.TABLE_PEAK_FLOW_USER
                + " WHERE " + DatabaseHelper.USER_ID + " = " + id;
        Cursor cursor = database.rawQuery(sql, null);
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
        ArrayList<PeakFlow> peakFlows = new ArrayList<PeakFlow>();
        while(cursor.moveToNext()){
            PeakFlow peakFlow = new PeakFlow();
            peakFlow.setId(cursor.getInt(0));
            peakFlow.setHeight(cursor.getInt(1));
            peakFlow.setAge(cursor.getInt(2));
            peakFlow.setPeakValue(cursor.getInt(3));
            peakFlow.setDate(cursor.getString(4));
            peakFlows.add(peakFlow);
        }
        cursor.close();
        return peakFlows;
    }

    public int updateInfoUser(long _id, String name, String surname, String birthday, String email, String mobile, String sex, String height, String weight, String city) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME, name);
        contentValues.put(DatabaseHelper.SURNAME, surname);
        contentValues.put(DatabaseHelper.BIRTHDAY, birthday);
        contentValues.put(DatabaseHelper.EMAIL, email);
        contentValues.put(DatabaseHelper.MOBILE, mobile);
        contentValues.put(DatabaseHelper.SEX, sex);
        contentValues.put(DatabaseHelper.HEIGHT, height);
        contentValues.put(DatabaseHelper.WEIGHT, weight);
        contentValues.put(DatabaseHelper.CITY, city);
        int i = database.update(DatabaseHelper.TABLE_USER, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public int updateInfoUser(long _id, String name, String surname, String birthday, String email, String mobile, String sex, String height, String weight, String city, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME, name);
        contentValues.put(DatabaseHelper.SURNAME, surname);
        contentValues.put(DatabaseHelper.BIRTHDAY, birthday);
        contentValues.put(DatabaseHelper.EMAIL, email);
        contentValues.put(DatabaseHelper.MOBILE, mobile);
        contentValues.put(DatabaseHelper.SEX, sex);
        contentValues.put(DatabaseHelper.HEIGHT, height);
        contentValues.put(DatabaseHelper.WEIGHT, weight);
        contentValues.put(DatabaseHelper.CITY, city);
        contentValues.put(DatabaseHelper.PASSWORD, password);
        int i = database.update(DatabaseHelper.TABLE_USER, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public int updateMedicen(long _id, String name, String time) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAMEMEDICE, name);
        contentValues.put(DatabaseHelper.TIMES, time);
        int i = database.update(DatabaseHelper.TABLE_MEDICE, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }
    public int isRegister(String email, String password) {
        int number = -1;
        Cursor cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.TABLE_USER+" WHERE "+DatabaseHelper.EMAIL+" =='"+email+"' AND "+ DatabaseHelper.PASSWORD+" =='"+password+"'", null);
        int i = 0;
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do {
                number = cursor.getInt(cursor.getColumnIndex("_id"));
                i++;
            } while (cursor.moveToNext());
            cursor.close();
        }
//        number = cursor.getCount();
        return number;
    }


    public void deleteInfoUser(long _id) {
        database.delete(DatabaseHelper.TABLE_USER, DatabaseHelper._ID + "=" + _id, null);
    }

    public void deleteMedicen(long _id) {
        database.delete(DatabaseHelper.TABLE_MEDICE, DatabaseHelper._IDMEDICE + "=" + _id, null);
    }

    public void deleteRelief(long _id) {
        database.delete(DatabaseHelper.TABLE_REFILER, DatabaseHelper._IDREFILER + "=" + _id, null);
    }

    public int countInfoUser(){
        int number = 0;
        Cursor cursor = database.rawQuery("SELECT * FROM INFO ", null);
        number = cursor.getCount();
        return number;
    }

}