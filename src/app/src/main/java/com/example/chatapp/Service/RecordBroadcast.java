package com.example.chatapp.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.chatapp.Class.Route;
import com.example.chatapp.Class.RoutePoint;
import com.example.chatapp.Class.TrackingHolder;

public class RecordBroadcast extends BroadcastReceiver {
    private static final String TAG = RecordBroadcast.class.getSimpleName();
    TrackingHolder trackingHolder;

    public RecordBroadcast() {
        trackingHolder = TrackingHolder.getInstance();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        RoutePoint route = (RoutePoint) intent.getSerializableExtra("route");
        //String uid = intent.getStringExtra("uid");
        //Log.d(TAG, "Received broadcast " + uid);
        Log.d(TAG, String.valueOf(trackingHolder.route.calculateDistance()));

        trackingHolder.addRoutePoint(route);
    }
}
