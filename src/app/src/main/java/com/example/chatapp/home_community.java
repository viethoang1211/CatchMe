package com.example.chatapp;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chatapp.Class.Post;
import com.example.chatapp.adapters.HomeAdapter;
import com.example.chatapp.listeners.postListener;

import java.util.ArrayList;

public class home_community extends Fragment {
    ArrayList<Post> Posts = new ArrayList<>();


    public static home_community newInstance() {
        return new home_community();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_community, container, false);
        Posts= new ArrayList<>();
//        Posts.add(new Post("Viet Hoang",123,"just up",123,55.05F,70.05F));
//        Posts.add(new Post("Lac Viet",123,"just woke up",123,55.05F,70.05F));
//        Posts.add(new Post("Lac Viet",123,"just woke up",123,55.05F,70.05F));
//        Posts.add(new Post("Lac Viet",123,"just woke up",123,55.05F,70.05F));
//        Posts.add(new Post("Lac Viet",123,"just woke up",123,55.05F,70.05F));
//        Posts.add(new Post("Lac Viet",123,"just woke up",123,55.05F,70.05F));
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

        return view;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(HomeCommunityViewModel.class);
//        // TODO: Use the ViewModel
//    }

}