package com.example.praba1110.contacts20;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by praba1110 on 1/7/15.
 */
    public class SQLhandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    Cursor c;


    public SQLhandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "contacts", factory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE CONTACTS(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "picid TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS CONTACTS");
        onCreate(db);
        db.close();
    }

    public void addcontact(String name, String pid) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("picid", pid);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("CONTACTS", null, values);


    }

    public void deletecontact(String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM CONTACTS WHERE name=\"" + name + "\";");
        db.close();
    }

    public List<String> getnames() {
        String temp = "";
        List<String> names = new ArrayList<String>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT name FROM CONTACTS WHERE 1;";
        c = db.rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            temp = c.getString(c.getColumnIndex("name"));
            if (temp != null) {
                names.add(temp);

            }
            c.moveToNext();

        }

        return names;
    }

    public List<String> getpicid() {
        List<String> names = new ArrayList<String>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT picid FROM CONTACTS WHERE 1;";
        c = db.rawQuery(query, null);
        c.moveToFirst();
        String temp;
        while (!c.isAfterLast()) {
            temp = c.getString(c.getColumnIndex("picid"));
            if (temp != null) {
                names.add(temp);

            }
            c.moveToNext();

        }
        return names;
    }
}

