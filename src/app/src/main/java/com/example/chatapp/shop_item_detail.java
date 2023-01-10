package com.example.chatapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.chatapp.Class.shopItem;

public class shop_item_detail extends Fragment {

    private ShopItemDetailViewModel mViewModel;

    public static shop_item_detail newInstance() {
        return new shop_item_detail();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_item_detail, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((fragment_container)getActivity()).setup(toolbar);
        TextView title  = view.findViewById(R.id.title);
        title.setText("Details");

        TextView test = view.findViewById(R.id.item_name);
        shopItem item = (shopItem) getArguments().getSerializable("testingShop");
        test.setText(item.getName());
        Log.d("gay",item.getName());

        return view;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(ShopItemDetailViewModel.class);
//        // TODO: Use the ViewModel
//    }

}