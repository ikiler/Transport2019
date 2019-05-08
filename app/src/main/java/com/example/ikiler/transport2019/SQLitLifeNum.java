package com.example.ikiler.transport2019;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xiyank on 19-5-8.
 */

public class SQLitLifeNum extends SQLiteOpenHelper{

    public SQLitLifeNum(Context context) {
        super(context, "LifeNum", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table lifeNum('id' Integer primary key autoincrement,'wendu' varchar(20),'shudu' varchar(20),'sun' varchar(20),'co' varchar(20),'pm' varchar(20),'load' varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
