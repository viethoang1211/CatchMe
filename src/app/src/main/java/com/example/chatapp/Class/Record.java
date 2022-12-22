package com.example.chatapp.Class;

import java.util.ArrayList;

public class Record {
    private int duration;
    private float distance;
    ArrayList<Step> stepList;

    public Record() {
        duration = 0;
        distance = 0;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public ArrayList<Step> getStepList() {
        return stepList;
    }

    public void setStepList(ArrayList<Step> stepList) {
        this.stepList = stepList;
    }

    public Record(int duration, float distance, ArrayList<Step> stepList) {
        this.duration = duration;
        this.distance = distance;
        this.stepList = stepList;
    }

    public void updateStep(Step u){
        stepList.add(u);
        duration += u.getTimeDuration();
        distance += u.getDistance();

    }
}
