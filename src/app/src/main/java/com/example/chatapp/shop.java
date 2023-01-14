package com.example.chatapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.chatapp.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class shop extends Fragment {

    ArrayList<shopItem> shopItems;
    ArrayList<shopItem> shopItems2;

//    String[] s = {"shop","shop2"};
    String[] t = {"image","name","price","description"};
    View view;
    shopAdapter shopAdap;
    MaterialButton physical,digital;
    FirebaseFirestore db =FirebaseFirestore.getInstance();
    private PreferenceManager preferenceManager;

    public static shop newInstance() {
        return new shop();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shop, container, false);
        preferenceManager=new PreferenceManager(getActivity().getApplicationContext());

        shopItems = new ArrayList<>();
        shopItems2 = new ArrayList<>();

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((fragment_container)getActivity()).setup(toolbar);
        TextView title  = view.findViewById(R.id.title);
        title.setText("Shop");
        TextView coin = view.findViewById(R.id.coin);
        coin.setText(preferenceManager.getString("ccoin"));

        addItem();

        physical = view.findViewById(R.id.physical);
        digital = view.findViewById(R.id.digital);
        CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                choose(shopItems);
            }
        }.start();
//        choose(shopItems);

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
        RecyclerView RecyclerView= view.findViewById(R.id.recycler);
        RecyclerView.setAdapter(shopAdap);
        RecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    private void addItem(){
        db.collection("shop")
                .get()
                .addOnCompleteListener(addShop);
        db.collection("shop2")
                .get()
                .addOnCompleteListener(addShop2);
    }

    private final OnCompleteListener<QuerySnapshot> addShop = task -> {
        if (task.isSuccessful() &&task.getResult()!=null &&task.getResult().getDocuments().size()>0){
            for(int i =0; i<4;i++) {
                String description="";
                String name="";
                Integer price=0;
                Bitmap bitmap=null;
                for(int j=0; j<t.length;j++){
                    String documentSnapshot = (String) task.getResult().getDocuments().get(i).get(t[j]);
                    if (j==0){
                        byte[] bytes = Base64.decode(documentSnapshot,Base64.DEFAULT);
                        bitmap= BitmapFactory.decodeByteArray(bytes,0, bytes.length);
                    }
                    else if(j==1){
                        name = documentSnapshot;
                    }
                    else if(j==2){
                        price = Integer.valueOf(documentSnapshot);
                    }
                    else {
                        description = documentSnapshot;
                    }
                }
                shopItems.add(new shopItem(name,price,bitmap,description));
            }
        }
    };
    private final OnCompleteListener<QuerySnapshot> addShop2 = task -> {
        if (task.isSuccessful() &&task.getResult()!=null &&task.getResult().getDocuments().size()>0){
            for(int i =0; i<2;i++) {
                String description="";
                String name="";
                Integer price=0;
                Bitmap bitmap=null;
                for(int j=0; j<t.length;j++){
                    String documentSnapshot = (String) task.getResult().getDocuments().get(i).get(t[j]);
                    if (j==0){
                        byte[] bytes = Base64.decode(documentSnapshot,Base64.DEFAULT);
                        bitmap = BitmapFactory.decodeByteArray(bytes,0, bytes.length);
                    }
                    else if(j==1){
                        name = documentSnapshot;
                    }
                    else if(j==2){
                        price = Integer.valueOf(documentSnapshot);
                    }
                    else {
                        description = documentSnapshot;
                    }
                }
                shopItems2.add(new shopItem(name,price,bitmap,description));
            }
        }
    };
    private void showToast(String message){
        Toast.makeText(getActivity().getApplicationContext(),message, Toast.LENGTH_SHORT).show();
    }

}