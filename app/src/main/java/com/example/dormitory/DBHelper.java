package com.example.dormitory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=1;
    public DBHelper(Context context){
        super(context, "datadb", null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String bus_Table="create table tb_bus ("+
                "_id integer primary key autoincrement," +
                "type," +
                "hour," +
                "min)";
        db.execSQL(bus_Table);
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','8','35')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','8','50')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','9','05')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','9','20')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','9','35')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','9','50')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','10','05')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','10','20')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','10','35')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','10','50')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','11','5')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','11','20')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','11','35')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','12','50')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','13','05')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','13','20')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','13','35')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','13','50')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','14','05')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','14','20')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','14','35')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','14','50')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','15','05')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','15','20')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','15','35')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','15','50')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','16','05')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','16','20')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','16','35')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','16','50')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','17','05')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','17','20')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','17','35')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('A','18','00')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','8','50')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','8','55')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','9','00')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','9','05')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','9','20')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','9','30')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','9','40')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','9','50')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','10','00')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','10','10')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','10','20')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','10','30')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','10','40')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','10','50')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','11','00')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','11','10')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','11','20')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','11','30')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','11','40')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','11','50')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','12','00')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','13','00')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','13','10')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','13','20')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','13','30')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','13','40')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','13','50')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','14','00')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','14','10')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','14','20')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','14','30')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','14','40')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','14','50')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','15','00')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','15','10')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','15','20')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','15','30')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','15','40')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','15','50')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','16','00')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','16','10')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','16','20')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','16','30')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','16','40')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','16','50')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','17','00')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','17','10')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','17','20')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','17','30')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','17','40')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','17','50')");
        db.execSQL("insert into tb_bus (type,hour,min) values ('B','18','15')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion == DATABASE_VERSION){
            db.execSQL("drop table tb_bus");
            onCreate(db);
        }
    }
}
