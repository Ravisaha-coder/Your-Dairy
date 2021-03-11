package com.example.yourdairy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "DairyData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table myDairy(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,createts DATETIME DEFAULT CURRENT_TIMESTAMP, data TXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists myDairy");
    }

    public boolean insertData(String data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Cursor cursor = db.rawQuery("Select * from myDairy", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            String first = cursor.getString(cursor.getColumnIndex("data"));
            String newData = first +" "+ data;
            String id = cursor.getString(cursor.getColumnIndex("id"));
            contentValues.put("data", newData);
            Cursor cursor2 = db.rawQuery("Select * from myDairy where id =?", new String[]{id});
            if (cursor2.getCount() > 0) {

                long result = db.update("myDairy", contentValues, "id=?", new String[]{id});
                if (result == -1) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        }
        else{
            contentValues.put("data", data);
            long result=db.insert("myDairy", null, contentValues);
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }

    }

    public ArrayList getdata ()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<String>();
        Cursor cursor = db.rawQuery("Select * from myDairy", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            arrayList.add(cursor.getString(cursor.getColumnIndex("data")));
            cursor.moveToNext();
        }
        return arrayList;

    }
}
