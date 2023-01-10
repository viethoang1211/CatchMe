package com.example.chatapp.Class;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;

import com.example.chatapp.sqlite.TrackingDAO;
import com.example.chatapp.utilities.Constants;
import com.example.chatapp.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class UpdateTrackingToOnline {
    Context context;

    public UpdateTrackingToOnline(Context context){
        this.context = context;
    }
    public void updateTrackingHistory() {
        if (!isOnline()) return;
        PreferenceManager preferenceManager = new PreferenceManager(context);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        Gson gson = new Gson();
        String tmp = preferenceManager.getString(Constants.KEY_TRACK_OFF_LIST);
        ArrayList<Integer> lst;
        if (tmp == null) {
            return;
        } else {
            Type type = new TypeToken<ArrayList<Integer>>() {
            }.getType();

            lst = gson.fromJson(tmp, type);
        }

        TrackingDAO trackingDAO = new TrackingDAO(context);
        for (int i = 0; i < lst.size(); ++i) {
            Route route = trackingDAO.get(String.valueOf(lst.get(i)));
            if (route == null) {
                lst.remove(i);
                --i;
            } else {
                database.collection(Constants.KEY_TRACKING)
                        .add(route.toHashMap())
                        .addOnSuccessListener(documentReference -> {
                        })
                        .addOnFailureListener(exception -> {
                        });
                trackingDAO.delete(String.valueOf(lst.get(i)));


                lst.remove(i);
                --i;
            }
        }
        String inputString= gson.toJson(lst);
        preferenceManager.putString(Constants.KEY_TRACK_OFF_LIST, inputString);

    }

    boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager)           context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return  true;
            }
        } else {
            return false;
        }
        return false;
    }
}
