package com.example.cst2335finalgroupproject;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/*
this class handles the database things: create database, add/delete an entity
 */
public class MyDatabase extends SQLiteOpenHelper {

    protected static final String DATABASE_NAME = "PexelsDB";
    protected static final int VERSION_NUM = 1;
    public static final String TABLE_NAME = "Image";
    public static final String COL_ID = "_id";
    public static final String COL_IMAGE_URL = "MAGE_URL";

    /*
    class constructor
     */
    public MyDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUM);
    }

    /*
    creates the database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY ," +
                COL_IMAGE_URL + " text);");
    }

    /*
    upgrades the database
    */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /*
    downgrade the database
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
