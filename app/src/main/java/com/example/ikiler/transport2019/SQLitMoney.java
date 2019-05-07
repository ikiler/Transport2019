package com.example.ikiler.transport2019;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLitMoney extends SQLiteOpenHelper {
    public SQLitMoney(Context context) {
        super(context, "SQLmoney", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table saveHis('id' Integer primary key autoincrement,'car_id' varchar(20),'saveMoney' varchar(20),'peo' varchar(20),'time' varchar(100))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
