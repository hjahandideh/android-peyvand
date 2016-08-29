package com.payvand.jahandideh.payvand;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="peyvand.db";
    public static final String TBL_NAME="login";
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TBL_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS");
        onCreate(db);
    }
    public boolean insertdata(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("username",name);
        long result=db.insert(TBL_NAME,null,cv);
        if(result==-1)
            return false;
            else
        return true;
    }

    public boolean deletdata(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("username",name);
        long result=db.delete(TBL_NAME,"username=?",new String[] {name});
        if(result==-1)
            return false;
        else
            return true;

    }

    public Cursor showdata(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cr=db.rawQuery("SELECT * FROM login",null);
        return cr;
    }
}
