package com.example.chatapp;

import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chatapp.Class.Post;

public class postDetails extends Fragment {

    private PostDetailsViewModel mViewModel;

    public static postDetails newInstance() {
        return new postDetails();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_details, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((fragment_container)getActivity()).setup(toolbar);
        TextView title  = view.findViewById(R.id.title);
        title.setText("Post");
        TextView test = view.findViewById(R.id.post_details_dur);
        Post post = (Post) getArguments().getSerializable("testing");
        test.setText(post.getUser_name());
        Log.d("gay",post.getUser_name());
        return view;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(PostDetailsViewModel.class);
//        // TODO: Use the ViewModel
//    }
}