package com.example.chatapp.Class;

public class TrackingHolder {
    // Static variable reference of single_instance
    // of type Singleton
    private static TrackingHolder trackingHolder = null;

    // Declaring a variable of type String
    public Tracking tracking;

    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself
    private TrackingHolder()
    {
        tracking = new Tracking("00");
    }

    // Static method
    // Static method to create instance of Singleton class
    public static TrackingHolder getInstance()
    {
        if (trackingHolder == null)
            trackingHolder = new TrackingHolder();

        return trackingHolder;
    }
}
