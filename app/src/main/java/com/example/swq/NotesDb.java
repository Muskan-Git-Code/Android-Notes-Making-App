package com.example.swq;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotesDb {
    myDbHelper h;
    public NotesDb(Context context) {
        h = new myDbHelper(context);
    }

    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "myDatabase";
        private static final String TABLE_NAME = "myTable1";
        private static final int DATABASE_VERSION = 2;
        private static final String UID="id";
        private static final String TITLE = "title";
        private static final String DESCRIPTION= "description";
        private static final String DATE= "date";
        private Context context;

        public myDbHelper(Context c) {
            super(c, DATABASE_NAME, null, DATABASE_VERSION);
            this.context =c;
        }

        public void onCreate(SQLiteDatabase db) {
            try{
                db.execSQL("CREATE TABLE "+TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TITLE+" TEXT , "+DESCRIPTION+" TEXT, "+DATE+" TEXT)");
            }
            catch(Exception e) {}
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
                onCreate(db);
            }
            catch(Exception e) {}
        }
    }


    public long insert(String n1, String n2, String n3) {
        SQLiteDatabase db = h.getWritableDatabase();
        ContentValues c = new ContentValues();  c.put(myDbHelper.TITLE, n1);   c.put(myDbHelper.DESCRIPTION, n2);     c.put(myDbHelper.DATE, n3);
        return db.insert(myDbHelper.TABLE_NAME, null , c);
    }

    public Cursor viewData(){
        SQLiteDatabase db= h.getWritableDatabase();

        Cursor c= db.rawQuery("SELECT * FROM "+myDbHelper.TABLE_NAME, null);
        return c;
    }


    public  int delete(String n) {
        SQLiteDatabase db = h.getWritableDatabase();
        String[] s= {n};
        return  db.delete(myDbHelper.TABLE_NAME ,myDbHelper.TITLE+" = ?", s);
    }

    public int update(String n, String p, String d) {
        SQLiteDatabase db = h.getWritableDatabase();
        ContentValues c = new ContentValues();  c.put(myDbHelper.DESCRIPTION,p);    c.put(myDbHelper.DATE, d);
        String[] s= {n};
        return db.update(myDbHelper.TABLE_NAME,c, myDbHelper.TITLE+" = ?", s);
    }
}


