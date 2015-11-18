package ru.keyran.netwatcher;

import android.content.ContentValues;
import android.content.Context;
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
        DateFormat format = new SimpleDateFormat("DD-MM-yyyy HH:mm:ss");
        cv.put("date", format.format(new Date()));
        cv.put("message", message);
        db.insert("log", null, cv);
    }

    public Vector<Pair<String, String>> get(int n) {
        cv.clear();
        Vector<Pair<String, String>> v = new Vector<>();
        Cursor c = db.query("log", null, null, null, null, null, null, String.valueOf(n));
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
            super(c, "logDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table log(" +
                            "id integer primary key autoincrement," +
                            "date text," +
                            "message text)"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}