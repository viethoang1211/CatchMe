package com.example.chatapp.Class;

import java.sql.Time;

public class Tracking {
    public Tracking(String ID, Time startTime, Time endTime, Record record) {
        this.ID = ID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.record = record;
    }

    public Tracking(String ID) {
        this.ID = ID;
        this.record = new Record();
    }

    private String ID;
    private Time startTime, endTime;
    private Record record;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
    public void updateStep(Step u){
        this.record.updateStep(u);
    }

    public float getDistance(){
        return record.getDistance();
    }
}
