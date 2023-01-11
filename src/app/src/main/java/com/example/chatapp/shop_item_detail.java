package com.example.chatapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.chatapp.Class.shopItem;
import com.example.chatapp.utilities.Constants;
import com.example.chatapp.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class shop_item_detail extends Fragment {
    CountDownTimer countDownTimer;
    PreferenceManager preferenceManager;
    FirebaseFirestore database;
    public static shop_item_detail newInstance() {
        return new shop_item_detail();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        preferenceManager = new PreferenceManager(getActivity().getApplicationContext());
        database = FirebaseFirestore.getInstance();


        View view = inflater.inflate(R.layout.fragment_shop_item_detail, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((fragment_container)getActivity()).setup(toolbar);
        TextView title  = view.findViewById(R.id.title);
        title.setText("Details");
//        TextView coin = view.findViewById(R.id.coin);
//        coin.setText(preferenceManager.getString("ccoin"));
//        coin.setText("54321");



        shopItem item = (shopItem) getArguments().getSerializable("testingShop");

        TextView name = view.findViewById(R.id.item_name);
        name.setText(item.getName());

        ImageView image = view.findViewById(R.id.item_image);

        countDownTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                if (item.getImage() == null)
                    countDownTimer.start();
                else{
                    Log.d("hey","hello");
                    image.setImageBitmap(item.getImage());
                }
            }
        }.start();



        TextView price = view.findViewById(R.id.item_price);
        price.setText(item.getPrice().toString());

        Integer i_price = Integer.parseInt(price.getText().toString());
//        showToast(String.valueOf(i_price));
        TextView des = view.findViewById(R.id.detail_des);
        des.setText(item.getDescription());

        TextView purchase = view.findViewById(R.id.purchase);
        purchase.setOnClickListener(v->{
            if (i_price< Integer.parseInt(preferenceManager.getString("ccoin"))){
                showToast("Buy successfully");
                Integer new_money= Integer.parseInt(preferenceManager.getString("ccoin"))- i_price;
                preferenceManager.putString("ccoin",String.valueOf(new_money));

                database.collection(Constants.KEY_COLLECTION_USERS).document(preferenceManager.getString(Constants.KEY_USER_ID)).update("ccoin",String.valueOf(new_money));

            }
            else{
                showToast("not enough money");
            }
        });

        return view;
    }
    private void showToast(String message){
        Toast.makeText(getActivity().getApplicationContext(),message, Toast.LENGTH_SHORT).show();
    }


//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(ShopItemDetailViewModel.class);
//        // TODO: Use the ViewModel
//    }

}