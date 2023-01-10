package com.example.chatapp.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.chatapp.Class.Route;
import com.example.chatapp.Class.TrackingHolder;

public class RecordBroadcast extends BroadcastReceiver {
    private static final String TAG = RecordBroadcast.class.getSimpleName();
    private MutableLiveData<Route> route = new MutableLiveData<>();
    private MutableLiveData<String> uid = new MutableLiveData<>();
    TrackingHolder trackingHolder;

    public RecordBroadcast() {
        trackingHolder = TrackingHolder.getInstance();
    }

    public LiveData<Route> getRoute() {
        return route;
    }

    public LiveData<String> getUid() {
        return uid;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Route route = (Route) intent.getSerializableExtra("route");
        String uid = intent.getStringExtra("uid");
        Log.d(TAG, "Received broadcast " + uid);
        Log.d(TAG, String.valueOf(route.calculateDistance()));
        this.route.setValue(route);
        this.uid.setValue(uid);

        trackingHolder.route = route;
    }
}
