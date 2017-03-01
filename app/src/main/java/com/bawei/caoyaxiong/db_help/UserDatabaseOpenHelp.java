package com.bawei.caoyaxiong.db_help;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.jar.Attributes;

/**
 * Created by dell on 2017/1/10.
 */
public class UserDatabaseOpenHelp extends SQLiteOpenHelper {
    public UserDatabaseOpenHelp(Context context) {
        super(context, "user.db", null, 1);
    }


    /**
     * private String name;
     private String type;
     private String area;
     private String des;
     private int  coverImg;

     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(id Integer primary key autoincrement,accounts varchar(20),pwd varchar(20),name varchar(20),type varchar(20),area varchar(20),des varchar(100),coverImg varchar(200))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
