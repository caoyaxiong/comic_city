package com.bawei.caoyaxiong.db_help;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bawei.caoyaxiong.bean.TypeGrid;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/1/10.
 */
public class UserDao  {

    private final SQLiteDatabase db;
    private List<TypeGrid> list;

    public UserDao(Context context){
        UserDatabaseOpenHelp openhelp=new UserDatabaseOpenHelp(context);
        db = openhelp.getWritableDatabase();        
    }
    //数据库添加用户信息
    public  boolean add(String accounts,String pwd){
        ContentValues values=new ContentValues();
        values.put("accounts",accounts);
        values.put("pwd",pwd);
        long result=db.insert("users",null,values);
        if(result!=-1){
            return true;
        }else{
            return false;
        }
    }

    public  boolean addType(String name,String type,String area,String des,String coverImg){
        ContentValues values=new ContentValues();
        values.put("name",name);
        values.put("type",type);
        values.put("area",area);
        values.put("des",des);
        values.put("coverImg",coverImg);
        
        long result=db.insert("users",null,values);
        if(result!=-1){
            return true;
        }else{
            return false;
        }
    }
    public List<TypeGrid> findType(){
       Cursor cursor=db.query("users",null,null,null,null,null,null);
        list = new ArrayList<>();
       while(cursor.moveToNext()){
           String name=cursor.getString(cursor.getColumnIndex("name"));
           String type=cursor.getString(cursor.getColumnIndex("type"));
           String area=cursor.getString(cursor.getColumnIndex("area"));
           String des=cursor.getString(cursor.getColumnIndex("des"));
           String coverImg=cursor.getString(cursor.getColumnIndex("coverImg"));
           list.add(new TypeGrid(name,type,area,des,coverImg));
       }
       Log.e("sss",list.toString());
        return list;
    }
    public boolean find(String accounts,String pwd){
        Cursor cursor=db.rawQuery("select * from users where accounts=? and pwd=?",new String[] {accounts,pwd});
        if(cursor.moveToNext()){
            return true;
        }
        return false;
    }
}
