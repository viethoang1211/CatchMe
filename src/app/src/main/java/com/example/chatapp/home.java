package com.example.chatapp;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class home extends Fragment {

    private String[] tabs_title = new String[]{"Community", "Group", "Friend"};
    private HomeViewModel mViewModel;
    ViewPager2 pager;
    TabLayout tabs;
    pageAdapter adapter;
    public static home newInstance() {
        return new home();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((fragment_container)getActivity()).setup(toolbar);
        TextView title  = view.findViewById(R.id.title);
        title.setText("Home");

        pager =view.findViewById(R.id.pager);
        tabs = view.findViewById(R.id.tab_layout);
        adapter= new pageAdapter(this);
        pager.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        new TabLayoutMediator(tabs, pager,
                (tab, position) -> tab.setText(tabs_title[position])
        ).attach();
    }

    class pageAdapter extends FragmentStateAdapter {
        public pageAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        public pageAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        public pageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new home_community();
                case 1:
                    return new home_group();
                case 2:
                    return new home_friend();
            }
            return new home_community();

        }

        @Override
        public int getItemCount() {
            return tabs_title.length;
    }
    }
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
//        // TODO: Use the ViewModel
//    }
}