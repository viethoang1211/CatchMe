package com.example.chatapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.Class.Post;
import com.example.chatapp.Service.RecordService;
import com.example.chatapp.adapters.HomeAdapter;
import com.example.chatapp.listeners.postListener;
import com.example.chatapp.utilities.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

public class home_community extends Fragment {
    private static final String TAG = RecordService.class.getSimpleName();
    ArrayList<Post> Posts;
    public static home_community newInstance() {
        return new home_community();
    }
    FirebaseFirestore firestore;
    CountDownTimer countDownTimer;
    String tmpBitmap, tmpName;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_community, container, false);
//        Posts.add(new Post("Viet Hoang",123,"just up",123,55.05F,70.05F));
//        Posts.add(new Post("Lac Viet",123,"just woke up",123,55.05F,70.05F));
//        Posts.add(new Post("Lac Viet",123,"just woke up",123,55.05F,70.05F));
//        Posts.add(new Post("Lac Viet",123,"just woke up",123,55.05F,70.05F));
//        Posts.add(new Post("Lac Viet",123,"just woke up",123,55.05F,70.05F));
//        Posts.add(new Post("Lac Viet",123,"just woke up",123,55.05F,70.05F));
        firestore = FirebaseFirestore.getInstance();
        getLstPost();
        Posts = new ArrayList<>();
        countDownTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                if (Posts.isEmpty()){
                    countDownTimer.start();
                    return;
                }
                RecyclerView RecyclerView= view.findViewById(R.id.home_recyclerview);
                HomeAdapter homeAdapter = new HomeAdapter(Posts, new postListener() {
                    @Override
                    public void onPostClick(Post post) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("testing",post);
                        Navigation.findNavController(view).navigate(R.id.action_global_postDetails,bundle);
                    }
                });
                RecyclerView.setAdapter(homeAdapter);
                RecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            }
        }.start();
        return view;
    }

    public void getLstPost(){
        firestore.collection("trackings").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Post tmp = new Post();
                        tmp.id = document.getId();
                        tmp.bitmap = document.getString("bitmap");
                        tmp.distance = document.getDouble("distance");
                        tmp.pace = document.getDouble("pace");
                        tmp.dateObject = document.getDate("startDate");
                        tmp.duration = document.getLong("total_time");
                        tmp.userID = document.getString("uId");

                        tmp.visibility = 1;
                        Posts.add(tmp);
                    }
                    Log.d(TAG, list.toString());
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(HomeCommunityViewModel.class);
//        // TODO: Use the ViewModel
//    }

}