package com.example.chatapp.activities;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapp.Class.Route;
import com.example.chatapp.R;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.chatapp.Service.RecordBroadcast;
import com.example.chatapp.Service.RecordService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.chatapp.databinding.ActivityTrackingBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TrackingActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ActivityTrackingBinding binding;
    private Intent intent;
    private IntentFilter intentFilter;
    private RecordBroadcast broadcast;
    private MediatorLiveData<Route> route = new MediatorLiveData<>();
    private MediatorLiveData<String> uid = new MediatorLiveData<>();
    private MediatorLiveData<String> info = new MediatorLiveData<>();

    private final static int REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        uid.setValue("RANDOM");

        intent = new Intent(this, RecordService.class);
        intent.putExtra("uid", uid.getValue());
        intentFilter = new IntentFilter();
        intentFilter.addAction(getString(R.string.intent_action));
        broadcast = new RecordBroadcast();

        binding = ActivityTrackingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.uid.addSource(broadcast.getUid(), newUid -> {
            this.uid.setValue(newUid);
        });

        this.route.addSource(broadcast.getRoute(), newRoute -> {
            this.route.setValue(newRoute);
        });

        this.info.addSource(broadcast.getRoute(), new Observer<Route>() {
            @Override
            public void onChanged(Route route) {
                binding.userTrackingDis.setText(String.valueOf(route.calculateDistance()));
                binding.userTrackingPace.setText(String.valueOf(route.calculatePace()));
                binding.userTrackingDur.setText(String.valueOf(route.getTotalTime()));
            }
        });
    }

    @Override
    protected void onStart() {
        startService(intent);
        registerReceiver(broadcast, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(broadcast);
        super.onStop();
    }

    public void stopRecordActivity(View view) {
        stopService(intent);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

}