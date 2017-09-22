package com.goldenictsolutions.win.goldenictjob365.DB_Controller;

/**
 * Created by Kurio Tetsuya on 6/25/2017.
 */


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Kyrie on 5/2/2017.
 */
public class DB_Control {

    Dbase dbase;
    SQLiteDatabase sqLiteDatabase;
    Context context;

    ArrayList<DB_USERDATA> data = new ArrayList<>();
    String save_user_id, save_user_password, save_username;

    public DB_Control(Context context) {
        this.context = context;
    }

    public void openDb() {
        dbase = new Dbase(context);
        sqLiteDatabase = dbase.getWritableDatabase();
    }


    public void insertData(String save_user_id, String save_username, String save_user_password) {
        sqLiteDatabase.execSQL("INSERT INTO " + Dbase.TB_NAME + " VALUES('" + save_user_id + "','" + save_username + "','" + save_user_password + "');'");

    }

    public void deleteData(String delete_user){
        sqLiteDatabase.execSQL("DELETE FROM "+ Dbase.TB_NAME + " WHERE "+Dbase.USER_ID+"= '"+delete_user+"'");
    }
    public void closeDb() {
//        dbase.close();
    }

    public ArrayList<DB_USERDATA> getUserid() {
        Cursor cursor = sqLiteDatabase.query(Dbase.TB_NAME, null, null, null, null, null, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int user_id = cursor.getColumnIndex(Dbase.USER_ID);
            int user_password = cursor.getColumnIndex(Dbase.USER_PASSWORD);
            int user_name = cursor.getColumnIndex(Dbase.USER_NAME);

            data.add(new DB_USERDATA(cursor.getString(user_id), cursor.getString(user_name), cursor.getString(user_password)));
            Log.e("DATA SIZE", "DATA SIZE" + data.size());
        }
        return data;
    }

    public static class Dbase extends SQLiteOpenHelper {
        static final String TB_NAME = "User_Data";
        static final String DB_NAME = "db_Bookmark";
        static final String USER_ID = "user_id";
        static final String USER_PASSWORD = "user_password";
        static final String USER_NAME = "user_name";

        static final int VERSION = 1;
        static final String TABLE_CREATE = "CREATE TABLE " +
                TB_NAME + "(" +
                USER_ID + " VARCHAR(100)," +
                USER_NAME + " VARCHAR(100)," +
                USER_PASSWORD + " VARCHAR(100));";

        Context context;

        public Dbase(Context context) {
            super(context, DB_NAME, null, VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_CREATE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("Drop Table If Exists" + TB_NAME);
            onCreate(db);
            Toast.makeText(context, "TB Upgrade", Toast.LENGTH_SHORT).show();
        }


    }


}

