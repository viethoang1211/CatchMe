package com.example.chatapp.Class;


import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Route implements Serializable {
    private ArrayList<RoutePoint> route;

    public Route() {
        route = new ArrayList<>();
    }

    public void add(RoutePoint point) {
        route.add(point);
    }

    public RoutePoint getRoutePoint(int index) {
        return route.get(index);
    }

    public double calculatePace() {
        // TODO: calculate pace
        long time = getTotalTime();
        double dis = calculateDistance();
        return 1.0 * time / 60 / dis;
    }

    public double calculateDistance() {
        if (route.isEmpty()) return 0;
        RoutePoint cur = route.get(0);
        float[] res = new float[5];
        double dis = 0;
        for (int i = 1; i < route.size(); ++i) {
            RoutePoint next = route.get(i);
            Location.distanceBetween(cur.getLat(), cur.getLng(), next.getLat(), next.getLng(), res);
            if (res[0] > 1.5 * (next.getTime() - cur.getTime()) / 1000) {
                dis += res[0];
                cur = next;
            }
        }
        return dis / 1000;
    }

    public long getTotalTime() {
        long time = 0;
        if (route.size() <= 1) return 0;
        time = route.get(route.size() - 1).getTime() - route.get(0).getTime();
        return time / 1000;
    }

    public String encodeRoute() {
        return PolyUtil.encode(getListLatLng());
    }

    public ArrayList<LatLng> getListLatLng() {
        ArrayList<LatLng> res = new ArrayList<>();
        for (RoutePoint i : route) {
            res.add(i.getLatLng());
        }
        return res;
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("distance", calculateDistance());
        map.put("pace", calculatePace());
        map.put("total_time", getTotalTime());
        map.put("route", encodeRoute());
        return map;
    }
}
