package com.honeygaincash.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="Registration.sqlite";
    private static final int DATABASE_VERSION= 1;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table student_record (id integer primary key autoincrement , Username text , Email text , Password text , MobileNo int) ";
        db.execSQL(query);

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists student_record");
        onCreate(db);
    }

    public boolean check_email(String email) {

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from student_record where Email=?", new String[]{email});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public  boolean insert_record(String ful , String emi , String pass , String mobileno){

        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("UserName" , ful);
        values.put("Email" , emi);
        values.put("Password"  , pass);
        values.put("MobileNo" , mobileno);

        long r=db.insert("student_record" , null , values);
        if (r==-1){
            return false;
        }else {
            return true;
        }

    }

    public boolean checkemailandpassword(String mobileno , String password) {

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from student_record where mobileno=? and Password=?", new String[]{mobileno, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

}
