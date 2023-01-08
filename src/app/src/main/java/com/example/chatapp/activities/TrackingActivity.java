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
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapp.Class.Route;
import com.example.chatapp.Class.TrackingHolder;
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

import java.util.ArrayList;

public class TrackingActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ActivityTrackingBinding binding;
    private Intent intent;
    private IntentFilter intentFilter;
    private RecordBroadcast broadcast;
    private MediatorLiveData<Route> route = new MediatorLiveData<>();
    private MediatorLiveData<String> uid = new MediatorLiveData<>();
    private MediatorLiveData<Route> info = new MediatorLiveData<>();

    CountDownTimer countDownTimer;
    TrackingHolder trackingHolder;

    TextView dis, pace, dir;

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

        trackingHolder = TrackingHolder.getInstance();

//        binding = ActivityTrackingBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

        dis = findViewById(R.id.user_tracking_dis);
        dir = findViewById(R.id.user_tracking_dur);
        pace = findViewById(R.id.user_tracking_pace);
        dis.setText("????");

//        dis.setText(String.valueOf(broadcast.getRoute().getValue().calculateDistance()));
//        dir.setText(String.valueOf(broadcast.getRoute().getValue().calculatePace()));
//        pace.setText(String.valueOf(broadcast.getRoute().getValue().getTotalTime()));

//        uid.addSource(broadcast.getUid(), newUid -> {
//            this.uid.setValue(newUid);
//        });
//
//        route.addSource(broadcast.getRoute(), newRoute -> {
//            this.route.setValue(newRoute);
//        });
//
//        info.addSource(broadcast.getRoute(), new Observer<Route>() {
//            @Override
//            public void onChanged(Route route) {
////                binding.userTrackingDis.setText(String.valueOf(route.calculateDistance()));
////                binding.userTrackingPace.setText(String.valueOf(route.calculatePace()));
////                binding.userTrackingDur.setText(String.valueOf(route.getTotalTime()));
//                dis.setText(String.valueOf(route.calculateDistance()));
//                dir.setText(String.valueOf(route.calculatePace()));
//                pace.setText(String.valueOf(route.getTotalTime()));
//                ArrayList<LatLng> tmp = route.getListLatLng();
//                LatLng cur = tmp.get(tmp.size()-1);
//                mMap.addMarker(new MarkerOptions().position(cur).title("?"));
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(cur));
//            }
//        });

        askPermission();

        countDownTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                dis.setText(String.valueOf(trackingHolder.route.calculateDistance()));
                //dir.setText(String.valueOf(trackingHolder.route.calculatePace()));
                pace.setText(String.valueOf(trackingHolder.route.getTotalTime()));
                ArrayList<LatLng> tmp = trackingHolder.route.getListLatLng();
                LatLng cur = tmp.get(tmp.size()-1);
                mMap.addMarker(new MarkerOptions().position(cur).title("?"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(cur));
                countDownTimer.start();
            }
        }.start();
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
        LatLng sydney = new LatLng(-40, 90);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void askPermission() {

        ActivityCompat.requestPermissions(TrackingActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {

        if (requestCode == REQUEST_CODE){

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){




            }else {


                Toast.makeText(TrackingActivity.this,"Please provide the required permission",Toast.LENGTH_SHORT).show();

            }



        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}