package com.example.chatapp.Class;


import android.graphics.Bitmap;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import java.io.Serializable;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;

public class Route implements Serializable {
    private ArrayList<RoutePoint> route;
    private ArrayList<ArrayList<RoutePoint>> lstRoute;
    private String uId;
    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Route() {
        route = new ArrayList<>();
        lstRoute = new ArrayList<>();
        bitmap = null;
        uId = "";
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
        if (dis == 0)
            return 0;
        return 1.0 * time / 60 / dis;
    }

    public double calculateDistance(){
        double dis = calculateDistance(route);
        for (ArrayList<RoutePoint> u:lstRoute) {
            dis += calculateDistance(u);
        }
        return dis;
    }

    private double calculateDistance(ArrayList<RoutePoint> tmpRoute) {
        if (tmpRoute.isEmpty()) return 0;
        RoutePoint cur = tmpRoute.get(0);
        float[] res = new float[5];
        double dis = 0;
        for (int i = 1; i < tmpRoute.size(); ++i) {
            RoutePoint next = tmpRoute.get(i);
            Location.distanceBetween(cur.getLat(), cur.getLng(), next.getLat(), next.getLng(), res);
            if (res[0] > 1.5 * (next.getTime() - cur.getTime()) / 1000) {
                dis += res[0];
                cur = next;
            }
        }
        return dis / 1000;
    }

    public long getTotalTime(){
        long time = getTotalTime(route);
        for (ArrayList<RoutePoint> u:lstRoute) {
            time += getTotalTime(u);
        }
        return time;
    }

    private long getTotalTime(ArrayList<RoutePoint> tmpRoute) {
        long time = 0;
        if (tmpRoute.size() <= 1) return 0;
        time = tmpRoute.get(tmpRoute.size() - 1).getTime() - tmpRoute.get(0).getTime();
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

    public void pause(){
        if (route.isEmpty()) return;
        lstRoute.add(route);
        route = new ArrayList<>();
    }
    public void stop(){
        if (route.isEmpty()) return;
        lstRoute.add(route);
        route = null;
    }

    public RoutePoint getLastRoutePoint(){
        if (route.isEmpty()){
            if (lstRoute.isEmpty()){
                return null;
            }
            return getLastRoutePoint(lstRoute.get(lstRoute.size()-1));
        }
        return getLastRoutePoint(route);
    }
    private RoutePoint getLastRoutePoint(ArrayList<RoutePoint> tmpRoute){
        return tmpRoute.get(tmpRoute.size()-1);
    }

    public String getStringPace(){
        double tmp = calculatePace();
        return toTimeForm((int) (tmp*60));
    }

    public String getStringDuration(){
        long tmp = getTotalTime();
        return toTimeForm((int) tmp);
    }

    public String getStringDistance(){
        double tmp = calculateDistance();
        return String.valueOf(Math.round(tmp*10)/10.0);
    }


    String toTimeForm(int u){ // second
        String time = String.valueOf(u/3600);
        u%=3600;
        String min = String.valueOf(u/60);
        u%=60;
        String sec = String.valueOf(u);
        while (min.length()<2) min = '0' + min;
        while (sec.length()<2) sec = '0' + sec;
        if (time.charAt(0) == '0')
            return min + " : " + sec;
        return  time + " : " + min + " : " + sec;
    }

    public LatLng getCenterRoutePoint(){
        double latMin = 100000, latMax = -100000;
        double lngMin = 100000, lngMax = -100000;
        for (RoutePoint u: route) {
            latMin = Math.min(latMin, u.getLat());
            latMax = Math.max(latMax, u.getLat());
            lngMin = Math.min(lngMin, u.getLng());
            lngMax = Math.max(lngMax, u.getLng());
        }
        return new LatLng((latMin + latMax)/2, (lngMin + lngMax)/2);
    }

    public double bestZoom(){
        double latMin = 100000, latMax = -100000;
        double lngMin = 100000, lngMax = -100000;
        for (RoutePoint u: route) {
            latMin = Math.min(latMin, u.getLat());
            latMax = Math.max(latMax, u.getLat());
            lngMin = Math.min(lngMin, u.getLng());
            lngMax = Math.max(lngMax, u.getLng());
        }
        double tmp = Math.max(latMax-latMin, lngMax-lngMin);
        return 15 - Math.log(tmp*10);
    }

}
