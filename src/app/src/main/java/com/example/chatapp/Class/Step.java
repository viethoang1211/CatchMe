package com.example.chatapp.Class;

import java.sql.Time;

public class Step {
    private Time startTime, endTime;
    private float distance;

    public Step(Time startTime, Time endTime, float distance) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.distance = distance;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getTimeDuration(){
        return (int) (endTime.getTime() - startTime.getTime());
    }
}
