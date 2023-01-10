package com.example.chatapp.Class;

public class TrackingHolder {// Singleton
    // Static variable reference of single_instance
    // of type Singleton
    private static TrackingHolder single_instance = null;

    // Declaring a variable of type String
    public Route route;
    public String Uid;

    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself
    private TrackingHolder()
    {
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
