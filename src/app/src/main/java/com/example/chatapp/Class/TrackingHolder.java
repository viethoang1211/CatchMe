package com.example.chatapp.Class;

import javax.xml.parsers.FactoryConfigurationError;

public class TrackingHolder {// Singleton
    // Static variable reference of single_instance
    // of type Singleton
    private static TrackingHolder single_instance = null;

    // Declaring a variable of type String
    public Route route;
    public String Uid;
    public boolean isPause;
    public boolean isStop;

    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself
    private TrackingHolder()
    {
        route = new Route();
        Uid = "";
        isPause = true;
        isStop = false;
    }

    public void addRoutePoint(RoutePoint u){
        if (isPause || isStop) return;
        route.add(u);
    }

    public void Pause(){
        if (isPause || isStop) return;
        isPause = true;
        route.pause();
    }

    public void Continue(){
        if (!isPause || isStop) return;
        isPause = false;
    }

    public void Stop(){
        if (isStop) return;
        isStop = true;
        route.stop();
    }

    public void setUID(String id){
        Uid = id;
        route.setuId(id);
    }

    public void newRun(){
        isPause = true;
        isStop = false;
        route = new Route();
        Uid = "";
    }



    // Static method
    // Static method to create instance of Singleton class
    public static TrackingHolder getInstance()
    {
        if (single_instance == null)
            single_instance = new TrackingHolder();

        return single_instance;
    }
}
