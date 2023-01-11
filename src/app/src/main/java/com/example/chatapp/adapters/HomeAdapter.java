package com.example.chatapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.Class.Post;
import com.example.chatapp.R;
import com.example.chatapp.listeners.postListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    ArrayList<Post> Posts;
    private postListener listner;

    public HomeAdapter( ArrayList<Post> Posts, postListener listner) {
        this.Posts= Posts;
        this.listner=listner;
    }

    @NonNull
    @Override
    public HomeAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_home_row,parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        final Post post = Posts.get(position);
        holder.name.setText(Posts.get(position).user_name);
        holder.time.setText(String.valueOf((Posts.get(position).dateObject)));
        holder.des.setText(Posts.get(position).description);
        holder.dur.setText(toTimeForm(Math.toIntExact(Posts.get(position).duration)));
        holder.pace.setText(Double.toString(Math.round(Posts.get(position).pace*10)/10.0));
        holder.dis.setText(Double.toString(Math.round(Posts.get(position).distance*10)/10.0));
        if (Posts.get(position).avatar != null)
            holder.avatar.setImageBitmap(decodeImage(Posts.get(position).avatar));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.onPostClick(post);
            }
        });

    }

    String toTimeForm(int u){ // second
        String time = String.valueOf(u/3600);
        u%=3600;
        String min = String.valueOf(u/60);
        u%=60;
        String sec = String.valueOf(u);
        while (min.length()<2) min = '0' + min;
        while (sec.length()<2) sec = '0' + sec;
        if (time.charAt(0) == '0')
            return min + " : " + sec;
        return  time + " : " + min + " : " + sec;
    }

    @Override
    public int getItemCount() {
        return Posts.size();
    }
    public static class HomeViewHolder extends RecyclerView.ViewHolder{
        TextView name,time,des,dur,pace,dis;
        CardView layout;
        RoundedImageView avatar;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.post);
            name= itemView.findViewById(R.id.user_post_name);
            time= itemView.findViewById(R.id.user_post_date);
            des= itemView.findViewById(R.id.user_post_des);
            dur= itemView.findViewById(R.id.user_post_duration);
            pace= itemView.findViewById(R.id.user_post_pace);
            dis= itemView.findViewById(R.id.user_post_distance);
            avatar = itemView.findViewById(R.id.imageProfile);
        }
    }
    private Bitmap decodeImage(String u){
        byte[] bytes = android.util.Base64.decode(u, android.util.Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0, bytes.length);
    }
}
