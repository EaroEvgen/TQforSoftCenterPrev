package com.home.earoevgen.tqforsoftcenterprev;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Earo.Evgen on 22.08.2017.
 */

public class mySQLiteHelper extends SQLiteOpenHelper{
    public mySQLiteHelper (Context context){
        super(context, "myDB", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table cardstable("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "type integer,"
                + "number integer" + ");");
        db.execSQL("create table typecardstable("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "percent integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
