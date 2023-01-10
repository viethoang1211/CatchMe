package com.example.chatapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatapp.Class.TrackingHolder;
import com.example.chatapp.R;

public class PostDetails extends AppCompatActivity {
    TextView pace, dis, duration;
    ImageView image;
    TrackingHolder trackingHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        pace = findViewById(R.id.user_post_details_pace);
        dis = findViewById(R.id.user_post_details_dis);
        duration = findViewById(R.id.user_post_details_dur);
        image = findViewById(R.id.post_details_map);

        trackingHolder = TrackingHolder.getInstance();
        pace.setText(trackingHolder.route.getStringPace());
        dis.setText(trackingHolder.route.getStringDistance());
        duration.setText(trackingHolder.route.getStringDuration());

        image.setImageBitmap(trackingHolder.route.getBitmap());
    }
}