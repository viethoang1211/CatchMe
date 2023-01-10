package com.example.chatapp.Class;

import java.io.Serializable;

public class Post implements Serializable {
    public String user_name;
    public Integer posting_time;
    public String description;
    public Integer duration;
    public Float pace;
    public Float distance;


    public Post(String user_name, Integer posting_time, String description, Integer duration, Float pace, Float distance) {
        this.user_name = user_name;
        this.posting_time = posting_time;
        this.description = description;
        this.duration = duration;
        this.pace = pace;
        this.distance = distance;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Integer getPosting_time() {
        return posting_time;
    }

    public void setPosting_time(Integer posting_time) {
        this.posting_time = posting_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Float getPace() {
        return pace;
    }

    public void setPace(Float pace) {
        this.pace = pace;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }
}

