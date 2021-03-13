package com.example.yourdairy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String FILE_DATA = "fileData";
    public DBHelper(Context context) {
        super(context, "DairyData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table myData(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, data TEXT)");
        db.execSQL("create Table myDairy(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,createts DATETIME DEFAULT CURRENT_TIMESTAMP, "+FILE_DATA+" TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists myData");
        db.execSQL("drop Table if exists myDairy");
    }

    public boolean insertData(String data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Cursor cursor = db.rawQuery("Select * from myData", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            String first = cursor.getString(cursor.getColumnIndex("data"));
            String newData = first +" "+ data;
            String id = cursor.getString(cursor.getColumnIndex("id"));
            contentValues.put("data", newData);
            Cursor cursor2 = db.rawQuery("Select * from myData where id =?", new String[]{id});
            if (cursor2.getCount() > 0) {

                long result = db.update("myData", contentValues, "id=?", new String[]{id});
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
            long result=db.insert("myData", null, contentValues);
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }

    }

    public boolean saveFile(String data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(FILE_DATA, data);
        long result = db.insert("myDairy",null, contentValues);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }

    }

    public String getdata ()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from myData", null);
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            String data = cursor.getString(cursor.getColumnIndex("data"));
            return data;
        }
        else{
            return "";
        }
    }

    public ArrayList<String> getFileNames(){
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from myDairy", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            arrayList.add(cursor.getString(cursor.getColumnIndex("createts")));
            cursor.moveToNext();
        }
        return arrayList;
    }

    public boolean resetData(){
        SQLiteDatabase db = this.getReadableDatabase();
        long result = db.delete("myData",null,null);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public FileObj getFileByCreatets(String createts){
        FileObj fileObj = new FileObj();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from myDairy where createts='"+createts+"'", null);
        /*Cursor cursor = db.query("myDairy", new String[] {createts, FILE_DATA }, createts + "=?",
                new String[] { String.valueOf(createts) }, null, null, null, null);
        */cursor.moveToFirst();
        fileObj.setFileName(cursor.getString(cursor.getColumnIndex("createts")));
        fileObj.setFileData(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
        return fileObj;
    }
}
