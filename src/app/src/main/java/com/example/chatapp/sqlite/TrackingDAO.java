package com.example.chatapp.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.chatapp.Class.Route;
import com.example.chatapp.Class.RoutePoint;
import com.example.chatapp.R;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TrackingDAO {
    private SQLiteDatabase db;

    public TrackingDAO(Context context){
        DBHelper dbHelper = new DBHelper(context);

        db = dbHelper.getWritableDatabase();
    }

    public Route get(String Id){
        String sql = "SELECT * FROM tracking WHERE Id = " + Id;
        Route route = null;
        Cursor cursor = db.rawQuery(sql, null);
        Gson gson = new Gson();
        while (cursor.moveToNext()){
            @SuppressLint("Range") String tmp = cursor.getString(cursor.getColumnIndex("route"));
            Type type = new TypeToken<Route>() {}.getType();

            route = gson.fromJson(tmp, type);
        }
        return route;
    }

    public long insert(String Id, Route route){
        ContentValues values = new ContentValues();
        Gson gson = new Gson();

        String inputString= gson.toJson(route);
        values.put("Id", Id);
        values.put("route", inputString);

        return db.insert("tracking", null, values);
    }

    public long update(String Id, Route route){
        ContentValues values = new ContentValues();
        Gson gson = new Gson();

        String inputString= gson.toJson(route);
        values.put("Id", Id);
        values.put("route", inputString);

        return db.update("tracking", values, "Id=?", new String[]{Id});

    }

    public int delete(String Id){
        return db.delete("tracking", "Id=?", new String[]{Id});
    }


}
