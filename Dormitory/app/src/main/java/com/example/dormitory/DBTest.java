package com.example.dormitory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBTest extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=1;
    public DBTest(Context context){
        super(context, "datadb", null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        /*db test*/
        String test_Table="create table tb_test ("+
                "_id integer primary key autoincrement," +
                "title," +
                "time," +
                "name)";
        db.execSQL(test_Table);
        db.execSQL("insert into tb_test (title,time,name) values ('서승훈','8:10','서승훈')");// 1 2 3
        db.execSQL("insert into tb_test (title,time,name) values ('유형곤','8:20','유형곤')");
        db.execSQL("insert into tb_test (title,time,name) values ('문정현','8:30','문정현')");
        db.execSQL("insert into tb_test (title,time,name) values ('공냥이','8:40','공냥이')");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion == DATABASE_VERSION){
            db.execSQL("drop table tb_test");
            onCreate(db);
        }
    }
}
