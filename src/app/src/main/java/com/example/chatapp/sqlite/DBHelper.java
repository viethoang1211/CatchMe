package com.example.chatapp.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "CATCHME";
    public static final int DB_VERSION = 7;

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        String sqlTrack = "CREATE TABLE tracking(Id text primary key, route text)";

        sqLiteDatabase.execSQL(sqlTrack);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sqlTrack = "DROP TABLE IF EXISTS tracking";

        sqLiteDatabase.execSQL(sqlTrack);

        onCreate(sqLiteDatabase);
    }

}
