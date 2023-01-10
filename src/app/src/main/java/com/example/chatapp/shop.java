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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.Class.shopItem;
import com.example.chatapp.adapters.shopAdapter;
import com.example.chatapp.listeners.shopItemListener;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class shop extends Fragment {

    View view;
    shopAdapter shopAdap;
    MaterialButton physical,digital;

    private ShopViewModel mViewModel;

    public static shop newInstance() {
        return new shop();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shop, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((fragment_container)getActivity()).setup(toolbar);
        TextView title  = view.findViewById(R.id.title);
        title.setText("Shop");

        ArrayList<shopItem> shopItems2 = new ArrayList<>();
        ArrayList<shopItem> shopItems = new ArrayList<>();
        shopItems.add(new shopItem("Viet Hoang",1231,"testing1",Boolean.TRUE));
        shopItems.add(new shopItem("Viet Hoang2",1232,"testing2",Boolean.TRUE));
        shopItems2.add(new shopItem("Viet Hoang3",1233,"testing3",Boolean.TRUE));
        shopItems2.add(new shopItem("Viet Hoang4",1234,"testing4",Boolean.FALSE));


        physical = view.findViewById(R.id.physical);
        digital = view.findViewById(R.id.digital);

        choose(shopItems);

        physical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose(shopItems);
            }
        });

        digital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose(shopItems2);
            }
        });
        return view;
    }

    private void choose(ArrayList l){
        shopAdap =  new shopAdapter(l, new shopItemListener() {
            @Override
            public void onClickShopItem(shopItem item) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("testingShop",item);
                Navigation.findNavController(view).navigate(R.id.action_global_shop_item_detail,bundle);
            }
        });
        Log.d("tag","phy");
        RecyclerView RecyclerView= view.findViewById(R.id.recycler);
        RecyclerView.setAdapter(shopAdap);
        RecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(ShopViewModel.class);
//        // TODO: Use the ViewModel
//    }

}