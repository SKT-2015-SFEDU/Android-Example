package ru.keyran.netwatcher;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class BDLog {
    public BDLog(Context c) {
        cv = new ContentValues();
        this.c = c;
        DBHelper dbHelper = new DBHelper(c);
        db = dbHelper.getWritableDatabase();
    }

    public void append(String message) {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Intent intent = new Intent(c.getString(R.string.DB_ITEM_ADDED));


        cv.put("date", format.format(new Date()));
        cv.put("message", message);
        intent.putExtra("date", (String) cv.get("Date"));
        intent.putExtra("message", message);
        c.sendBroadcast(intent);
        db.insert("log", null, cv);
    }

    public Vector<Pair<String, String>> get(int n) {
        cv.clear();
        Vector<Pair<String, String>> v = new Vector<>();
        Cursor c = db.query("log", null, null, null, null, null, "date DESC", String.valueOf(n));
        if (c.moveToFirst()) {
            do {
                v.add(new Pair<>(
                        c.getString(c.getColumnIndex("date")),
                        c.getString(c.getColumnIndex("message"))
                ));
            } while (c.moveToNext());
        }
        return v;
    }

    SQLiteDatabase db;
    ContentValues cv;
    Context c;

    class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context c) {
            super(c, "logDB", null, 2);
        }

        public void createTable(SQLiteDatabase db){
            db.execSQL("create table log(" +
                            "id integer primary key autoincrement," +
                            "date text," +
                            "message text)"
            );
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            createTable(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table log");
            createTable(db);
        }
    }
}