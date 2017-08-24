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
        // FIXME: 24.08.2017 Наименование таблиц и полей лучше выносить в констатны - позволяет избежать опечаток
        // FIXME: 24.08.2017 Обязательным полям стоит прописывать ограничение NOT NULL
        // FIXME: 24.08.2017 Поскольку таблицы связаны, то необходимо использовать ограничение внешнего ключа (FOREIGN KEY), желательно с указанием действий при удалении/изменении родителя
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
