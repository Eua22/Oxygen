package com.example.oxygen.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table user info
    public static final String TABLE_USER = "INFO";
    // Table columns
    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String BIRTHDAY = "birthday";
    public static final String EMAIL = "email";
    public static final String MOBILE = "mobile";
    public static final String SEX = "sex";
    public static final String HEIGHT = "height";
    public static final String WEIGHT = "weight";
    public static final String CITY = "city";
    public static final String PASSWORD = "password";

    //foreigkey
    public static final String USER_ID = "user_id";
    // Table medice
    public static final String TABLE_MEDICE = "MEDICE";
    // Table columns
    public static final String _IDMEDICE = "_id";
    public static final String NAMEMEDICE = "name";
    public static final String TIMES = "times";
    public static final String DOSES = "dose";

    // Table medice
    public static final String TABLE_REFILER = "REFILER";
    // Table columns
    public static final String _IDREFILER = "_id";
    public static final String NAMEREFILER = "name";
    public static final String DATE = "date";

    // Table peak flow man
    public static final String TABLE_PEAK_FLOW_MAN = "PEAKFLOWMAN";
    // Table columns
    public static final String _IDPFM = "_id";
    public static final String HEIGHTPFM = "height";
    public static final String AGEPFM = "age";
    public static final String VALUEPFM = "peakflow";

    // Table peak flow women
    public static final String TABLE_PEAK_FLOW_WOMAN = "PEAKFLOWWOMAN";
    // Table columns
    public static final String _IDPFW = "_id";
    public static final String HEIGHTPFW = "height";
    public static final String AGEPFW = "age";
    public static final String VALUEPFW = "peakflow";

    // Table peak flow user
    public static final String TABLE_PEAK_FLOW_USER = "PEAKFLOWUSER";
    // Table columns
    public static final String _IDPFU = "_id";
    public static final String HEIGHTPFU = "height";
    public static final String AGEPFU = "age";
    public static final String VALUEPFU = "peakflow";


    // Database OXYGEN
    static final String DB_NAME = "OXYGEN1.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table user info query
    private static final String CREATE_USER_INFO = "create table " + TABLE_USER + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT , " + SURNAME + " TEXT , "
            + BIRTHDAY + " TEXT , " + EMAIL + " TEXT , " + MOBILE + " TEXT , " + SEX + " TEXT , "
            + HEIGHT + " TEXT , " + WEIGHT + " TEXT , " + CITY + " TEXT , " + PASSWORD + " TEXT);";

    // Creating table medicen query
    private static final String CREATE_MEDICE = "create table " + TABLE_MEDICE + "(" + _IDMEDICE
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAMEMEDICE + " TEXT , " + TIMES + " TEXT , "+ DOSES + " TEXT ,"
            + USER_ID + " INTEGER ,"
            + " FOREIGN KEY ("+USER_ID+") REFERENCES "+TABLE_USER+"("+_ID+"));";

    // Creating table controllers query
    private static final String CREATE_REFILER = "create table " + TABLE_REFILER + "(" + _IDREFILER
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAMEREFILER + " TEXT , " + DATE + " TEXT ,"
            + USER_ID + " INTEGER ,"
            + " FOREIGN KEY ("+USER_ID+") REFERENCES "+TABLE_USER+"("+_ID+"));";

    // Creating table peak flow man query
    private static final String CREATE_PFLM = "create table " + TABLE_PEAK_FLOW_MAN + "(" + _IDPFM
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + HEIGHTPFM + " DOUBLE , " + AGEPFM + " INTEGER , " + VALUEPFM + " INTEGER ,"
            + USER_ID + " INTEGER ,"
            + " FOREIGN KEY ("+USER_ID+") REFERENCES "+TABLE_USER+"("+_ID+"));";

    // Creating table peak flow women query
    private static final String CREATE_PFLW = "create table " + TABLE_PEAK_FLOW_WOMAN + "(" + _IDPFW
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + HEIGHTPFW + " INTEGER , " + AGEPFW + " DOUBLE , " + VALUEPFW + " INTEGER ,"
            + USER_ID + " INTEGER ,"
            + " FOREIGN KEY ("+USER_ID+") REFERENCES "+TABLE_USER+"("+_ID+"));";

    // Creating table peak flow user query
    private static final String CREATE_PFLU = "create table " + TABLE_PEAK_FLOW_USER + "(" + _IDPFU
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + HEIGHTPFU + " INTEGER , " + AGEPFU + " DOUBLE , " + VALUEPFU + " INTEGER ,"+ DATE + " TEXT ,"
            + USER_ID + " INTEGER ,"
            + " FOREIGN KEY ("+USER_ID+") REFERENCES "+TABLE_USER+"("+_ID+"));";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_INFO);
        db.execSQL(CREATE_MEDICE);
        db.execSQL(CREATE_REFILER);
        db.execSQL(CREATE_PFLM);
        db.execSQL(CREATE_PFLW);
        db.execSQL(CREATE_PFLU);

        // initialize table with peak flow man values
        initMenPeakFlow(db);
        // initialize table with peak flow weman vlaues
        initWomanPeakFlow(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REFILER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PEAK_FLOW_MAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PEAK_FLOW_WOMAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PEAK_FLOW_USER);
        onCreate(db);
    }

    public void  initMenPeakFlow(SQLiteDatabase db){
        // INSERT INTO peak flow man query
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.60,20,554)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.65,20,602)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.70,20,649)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.75,20,693)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.80,20,740)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.60,25,543)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.65,25,590)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.70,25,636)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.75,25,679)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.80,25,725)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.60,30,532)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.65,30,577)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.70,30,622)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.75,30,664)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.80,30,710)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.60,35,521)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.65,35,565)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.70,35,609)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.75,35,651)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.80,35,695)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.60,40,509)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.65,40,552)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.70,40,596)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.75,40,636)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.80,40,680)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.60,45,498)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.65,45,540)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.70,45,583)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.75,45,622)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.80,45,665)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.60,50,486)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.65,50,527)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.70,50,569)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.75,50,607)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.80,50,649)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.60,55,475)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.65,55,515)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.70,55,556)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.75,55,593)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.80,55,634)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.60,60,463)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.65,60,502)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.70,60,542)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.75,60,578)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.80,60,618)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.60,65,452)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.65,65,490)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.70,65,529)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.75,65,564)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.80,65,603)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.60,70,440)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.65,70,477)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.70,70,515)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.75,70,550)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_MAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.80,70,587)");

    }
    public void  initWomanPeakFlow(SQLiteDatabase db){
        // INSERT INTO peak flow woman query
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.55,20,390)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.60,20,423)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.65,20,460)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.7,20,496)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.75,20,529)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.55,25,385)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.60,25,418)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.65,25,454)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.70,25,490)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.75,25,523)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.55,30,380)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.60,30,413)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.65,30,448)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.70,30,483)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.75,30,516)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.55,35,375)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.60,35,408)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.65,35,442)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.70,35,476)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.75,35,509)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.55,40,370)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.60,40,402)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.65,40,436)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.70,40,470)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.75,40,502)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.55,45,365)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.60,45,397)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.65,45,430)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.70,45,446)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.75,45,495)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.55,50,360)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.60,50,391)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.65,50,424)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.70,50,457)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.75,50,488)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.55,55,355)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.60,55,386)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.65,55,418)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.70,55,451)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.75,55,482)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.55,60,350)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.60,60,380)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.65,60,412)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.70,60,445)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.75,60,475)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.55,65,345)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.60,65,375)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.65,65,406)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.70,65,439)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.75,65,468)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.55,70,340)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.60,70,369)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.65,70,400)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.70,70,432)");
        db.execSQL("INSERT INTO " + TABLE_PEAK_FLOW_WOMAN + " (" +
                HEIGHTPFM + ", " +
                AGEPFM + ", " +
                VALUEPFM +
                ") VALUES(1.75,70,461)");
    }
}