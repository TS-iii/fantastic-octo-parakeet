package com.example.tsone;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class PersonDatabase {


    //TAG for debugging
    public static final String TAG = "PersonDatabase";


    // singleton instance
    private static PersonDatabase database;

    // 데이터베이스 이름
    public static String DATABASE_NAME = "person2.db";

    // table 이름
    public static String TABLE_PERSON_INFO = "PERSON_INFO";

    //버전
    public static int DATABASE_VERSION = 1;

    //헬퍼 클래스
    private DatabaseHelper dbHelper;

    private SQLiteDatabase db;
    private Context context;

    private PersonDatabase(Context context) {
        this.context = context;
    }


    public static PersonDatabase getInstance(Context context) {
        if (database == null) {
            database = new PersonDatabase(context);
        }
        return database;
    }


    //데이터베이스 오픈

    public boolean open(){

        dbHelper=new DatabaseHelper(context);
        db=dbHelper.getWritableDatabase();

        return true;
    }

    //데이터 베이스 닫기

    public void close(){
        db.close();
        database=null;
    }


    public Cursor rawQuery(String SQL){
        println("\nexecuteQuery called.\n");


        Cursor c1 = null;
        try {
            c1 = db.rawQuery(SQL, null);
            println("cursor count : " + c1.getCount());
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executeQuery", ex);
        }

        return c1;



    }


    public boolean execSQL(String SQL){
        println("\nexecute called.\n");

        try{
            Log.d(TAG,"SQL:"+SQL);
            db.execSQL(SQL);
        } catch(Exception ex){
            Log.e(TAG,"Exception in executeQuery",ex);
            return false;
        }

        return true;
    }


    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }

        public void onCreate(SQLiteDatabase _db) {

            println("creating table [" + TABLE_PERSON_INFO);

            //테이블 존재하면 버림
            String DROP_SQL = "drop table if exists " + TABLE_PERSON_INFO;

            try {
                _db.execSQL(DROP_SQL);

            } catch (Exception ex) {
                Log.e(TAG, "Exception in DROP_SQL", ex);
            }

            // create table

            String CREATE_SQL = "create table " + TABLE_PERSON_INFO + "("
                    + " _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                    + " NAME TEXT,"
                    + " CONTENTS TEXT,"
                    + " MAC TEXT,"
                    + " CREATE_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                    + ")";

            try {
                _db.execSQL(CREATE_SQL);
            } catch (Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL", ex);

            }
//            insertRecord(_db, "홍길동", 1, "안드로이드 기본서로 이지스퍼블리싱 출판사에서 출판했습니다.");
//            insertRecord(_db, "김재현", 2, "Oreilly Associates Inc에서 2011년 04월에 출판했습니다.");
//            insertRecord(_db, "김영희", 3, "에이콘출판사에서 2011년 10월에 출판했습니다.");
//            insertRecord(_db, "박승희", 4, "위키북스에서 2011년 09월에 출판했습니다.");
//            insertRecord(_db, "나정연", 5, "DW Wave에서 2010년 10월에 출판했습니다.");
        } // oncreate end

        public void onOpen(SQLiteDatabase db) {
            println("opened database [" + DATABASE_NAME + "].");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            println("Upgrading database from version " + oldVersion + " to " + newVersion + ".");

            if (oldVersion < 2) {   // version 1

            }
        }
        private void insertRecord(SQLiteDatabase _db, String name, String contents, String mac) {
            try {
                _db.execSQL( "insert into " + TABLE_PERSON_INFO + "(NAME, CONTENTS, MAC) values ('" + name + "',  '" + contents + "' , '" + mac +"');" );
            } catch(Exception ex) {
                Log.e(TAG, "Exception in executing insert SQL.", ex);
            }
        }
    }
    public void insertRecord(String name, String contents,String mac){
        try{
            db.execSQL("insert into "+ TABLE_PERSON_INFO + "(NAME, CONTENTS , MAC) values ('" + name + "',  '" + contents + "' , '"+ mac + "');" );
        } catch(Exception ex){
            Log.e(TAG, "Exception in executing insert SQL.", ex);

        }
    }

    public void deleteRecord(String mac){

        //  int temp=Integer.parseInt(device);

        try{
            db.execSQL("delete from " + TABLE_PERSON_INFO + " WHERE MAC = '" + mac + "' ;");
        } catch(Exception ex){
            Log.e(TAG,"Exception in executing delete SQL.",ex);
        }

    }

    public void updateRecord(String name, String contents , String mac){


        try{
            db.execSQL("update "+ TABLE_PERSON_INFO + " SET CONTENTS= '"+contents+ "', NAME='"+name+ "' WHERE MAC='"+mac +"' ;");
        } catch(Exception ex){
            Log.e(TAG,"Exception in executing updat SQL",ex);
        }


    }



    public ArrayList<PersonInfo> selectAll(){
        ArrayList<PersonInfo> result=new ArrayList<PersonInfo>();
        try {
            Cursor cursor = db.rawQuery("select NAME, CONTENTS, MAC from " + TABLE_PERSON_INFO, null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                String name = cursor.getString(0);
                String contents = cursor.getString(1);
                String mac=cursor.getString(2);

                PersonInfo info = new PersonInfo(name, contents,mac);
                result.add(info);
            }

        } catch(Exception ex) {
            Log.e(TAG, "Exception in executing insert SQL.", ex);
        }
        return result;
    }



    private void println(String msg) {
        Log.d(TAG, msg);
    }
}