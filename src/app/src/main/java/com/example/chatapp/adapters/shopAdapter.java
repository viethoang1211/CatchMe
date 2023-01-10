package com.example.chatapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.Class.shopItem;
import com.example.chatapp.R;
import com.example.chatapp.listeners.shopItemListener;

import java.util.ArrayList;

public class shopAdapter extends RecyclerView.Adapter<shopAdapter.shopViewHolder>{
    ArrayList<shopItem> shopItems;
    shopItemListener itemListener;
    public shopAdapter(ArrayList<shopItem> shopItems,shopItemListener itemListener) {
        this.shopItems = shopItems;
        this.itemListener=itemListener;
    }

    @NonNull
    @Override
    public shopAdapter.shopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_shop_row,parent,false);
        return new shopAdapter.shopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull shopViewHolder holder, int position) {
        final shopItem item = shopItems.get(position);
        holder.name.setText(shopItems.get(position).getName());
        holder.price.setText(Integer.toString(shopItems.get(position).getPrice()));

        if(shopItems.get(position).getImage()!=null){
            holder.image.setImageBitmap(shopItems.get(position).getImage());}
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemListener.onClickShopItem(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopItems.size();
    }
    public static class shopViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView price,name;
        CardView card;
        public shopViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.item_card);
            image = itemView.findViewById((R.id.image));
            name= itemView.findViewById(R.id.item_name);
            price = itemView.findViewById((R.id.price));
        }
    }
}
