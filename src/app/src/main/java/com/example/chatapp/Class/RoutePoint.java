package com.example.chatapp.Class;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class RoutePoint implements Serializable {
    private final double lat;
    private final double lng;
    private final long time;

    public RoutePoint(double lat, double lng, long time) {
        this.lat = lat;
        this.lng = lng;
        this.time = time;
    }
    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public LatLng getLatLng() {
        return new LatLng(lat, lng);
    }

    public long getTime() {
        return time;
    }
}