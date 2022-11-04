package com.example.catchme.Class;

public class Achievement{
    private Integer id;
    private String name;
    private String description;
    //private String pircture;
    private Integer length, time, pace, speed;
    private String location;

    //getter
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getLength() {
        return length;
    }

    public Integer getPace() {
        return pace;
    }

    public Integer getSpeed() {
        return speed;
    }

    public Integer getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }


    //setter

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public void setPace(Integer pace) {
        this.pace = pace;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Achievement(Integer id, String name, String dis, Integer length, Integer pace, Integer speed, Integer time, String location){
        this.id=id;
        this.name=name;
        this.description=dis;
        this.length=length;
        this.pace=pace;
        this.speed=speed;
        this.time=time;
        this.location=location;
    }
}