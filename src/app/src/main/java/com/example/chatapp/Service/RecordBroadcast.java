package com.example.chatapp.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.chatapp.Class.Route;

public class RecordBroadcast extends BroadcastReceiver {
    private static final String TAG = RecordBroadcast.class.getSimpleName();
    private Route route;
    private String uid;

    public RecordBroadcast() {
    }

    public Route getRoute() {
        return route;
    }

    public String getUid() {
        return uid;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Route route = (Route) intent.getSerializableExtra("route");
        String uid = intent.getStringExtra("uid");
        Log.d(TAG, "Received broadcast " + uid);
        this.route = route;
        this.uid = uid;
    }
}
