package com.example.chatapp;

import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class chat extends Fragment {

    private ChatViewModel mViewModel;

    public static chat newInstance() {
        return new chat();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((fragment_container)getActivity()).setup(toolbar);
        TextView title  = view.findViewById(R.id.title);
        title.setText("Chat");
        return view;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
//        // TODO: Use the ViewModel
//    }

}