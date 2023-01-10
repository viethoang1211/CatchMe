package com.example.chatapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapp.Class.Route;
import com.example.chatapp.Class.RoutePoint;
import com.example.chatapp.Class.TrackingHolder;
import com.example.chatapp.R;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MediatorLiveData;

import com.example.chatapp.Service.RecordBroadcast;
import com.example.chatapp.Service.RecordService;
import com.example.chatapp.sqlite.DBHelper;
import com.example.chatapp.sqlite.TrackingDAO;
import com.example.chatapp.utilities.Constants;
import com.example.chatapp.utilities.PreferenceManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.example.chatapp.databinding.ActivityTrackingBinding;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TrackingActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = RecordService.class.getSimpleName();
    private GoogleMap mMap;
    private ActivityTrackingBinding binding;
    private Intent intent;
    private IntentFilter intentFilter;
    private RecordBroadcast broadcast;
    private String uid;

    CountDownTimer countDownTimer;
    TrackingHolder trackingHolder;

    TextView dis, pace, duration;
    LatLng lastPos;
    FloatingActionButton btnStart;
    Button btnStop;

    PreferenceManager preferenceManager;

    boolean isPause;

    private final static int REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        preferenceManager = new PreferenceManager(getApplicationContext());
        uid = preferenceManager.getString(Constants.KEY_USER_ID);

        intent = new Intent(this, RecordService.class);
        intent.putExtra("uid", uid);
        intentFilter = new IntentFilter();
        intentFilter.addAction(getString(R.string.intent_action));
        broadcast = new RecordBroadcast();

        trackingHolder = TrackingHolder.getInstance();
        trackingHolder.setUID(uid);

        dis = findViewById(R.id.user_tracking_dis);
        duration = findViewById(R.id.user_tracking_dur);
        pace = findViewById(R.id.user_tracking_pace);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.stopTracking);

        isPause = true;

        askPermission();

        countDownTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                updateUI();
                countDownTimer.start();
            }
        }.start();

        setOnClick();
    }

    void setOnClick(){
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPause){
                    isPause = false;
                    startRunning();
                    btnStop.setVisibility(View.GONE);
                }else{
                    isPause = true;
                    btnStop.setVisibility(View.VISIBLE);
                    pauseRunning();
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopRunning();
                getGoogleMapImage();
                finish();
            }
        });
    }

    void getGoogleMapImage(){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(trackingHolder.route.getCenterRoutePoint() ,(float) trackingHolder.route.bestZoom()));
        mMap.snapshot(new GoogleMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(@Nullable Bitmap bitmap) {
                trackingHolder.route.setBitmap(bitmap);
                startActivity(new Intent(getApplicationContext(), PostDetails.class));
            }
        });
    }

    void updateUI(){

        dis.setText(trackingHolder.route.getStringDistance());
        duration.setText(trackingHolder.route.getStringDuration());
        pace.setText(trackingHolder.route.getStringPace());

        RoutePoint curRP = trackingHolder.route.getLastRoutePoint();
        if (curRP != null){
            LatLng cur = curRP.getLatLng();
            if( lastPos != null){
                mMap.addPolyline(new PolylineOptions()
                        .add(lastPos, cur)
                        .width(10)
                        .color(Color.WHITE)
                );
            }
            lastPos = cur;
        }
        focusCamera();
    }

    void startRunning(){
        lastPos = null;
        trackingHolder.Continue();
    }

    void pauseRunning(){
        trackingHolder.Pause();
    }

    void stopRunning(){
        trackingHolder.Stop();
    }

    void focusCamera(){
        if (lastPos == null) return;
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lastPos));
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
        updateToSQL();
        super.onStop();
    }

    void updateToSQL(){
        TrackingDAO trackingDAO = new TrackingDAO(getApplicationContext());
        String id = preferenceManager.getString(Constants.KEY_TRACK_COUNT);
        int cntID = 0;
        if (id != null){
            cntID = Integer.getInteger(id);
        }
        ++cntID;
        id = String.valueOf(cntID);
        preferenceManager.putString(Constants.KEY_TRACK_COUNT, id);
        trackingDAO.insert(id, trackingHolder.route);
        trackingHolder.newRun();
        // Add to preference
        Gson gson = new Gson();
        String tmp = preferenceManager.getString(Constants.KEY_TRACK_OFF_LIST);
        ArrayList<Integer> lst;
        if (tmp == null){
            lst = new ArrayList<>();
        }else{
            Type type = new TypeToken<ArrayList<Integer> >() {}.getType();

            lst = gson.fromJson(tmp, type);
        }
        lst.add(cntID);

        String inputString= gson.toJson(lst);
        preferenceManager.putString(Constants.KEY_TRACK_OFF_LIST, inputString);
        Toast.makeText(getApplicationContext(), "Save to SQLite", Toast.LENGTH_SHORT).show();
    }

    public void stopRecordActivity(View view) {
        stopService(intent);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.zoomBy(15));
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_map_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
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