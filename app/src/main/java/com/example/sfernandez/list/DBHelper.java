package com.example.sfernandez.list;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "List.db";
    public static final String LIST_TABLE_NAME = "list";
    public static final String ITEMS_TABLE_NAME = "items";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String cmd =  "create table " + LIST_TABLE_NAME + "(id integer primary key, list_id id, title text, reset_schedule datetime);";
        cmd += "create table " + ITEMS_TABLE_NAME + "(id integer primary key, description text, is_check integer, FOREIGN KEY (list_id) REFERENCES list(id));";
        db.execSQL(cmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS list; DROP TABLE IF EXISTS items");
        onCreate(db);
    }

    public boolean insertList(String title, String resetSched) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("title", title);
            contentValues.put("reset_sched", resetSched);
            db.insert("list", null, contentValues);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean insertItem(String listId, String description, String checked) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("list_id", listId);
            contentValues.put("description", description);
            contentValues.put("is_check", checked);
            db.insert("items", null, contentValues);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public ArrayList<String> getAllList() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from list", null);
        res.moveToFirst();

        while(res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("title")));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getAllItems(String list_id) {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from items where list_id = '" + list_id + "'", null);
        res.moveToFirst();

        while(res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("description")));
            res.moveToNext();
        }
        return array_list;
    }
}




