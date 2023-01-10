package com.example.chatapp.adapters;

import android.content.Context;
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
        holder.time.setText((Posts.get(position).posting_time));
        holder.des.setText(Posts.get(position).description);
        holder.dur.setText(Integer.toString(Posts.get(position).duration));
        holder.pace.setText(Float.toString(Posts.get(position).pace));
        holder.dis.setText(Float.toString(Posts.get(position).distance));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.onPostClick(post);
            }
        });

    }

    @Override
    public int getItemCount() {
        return Posts.size();
    }
    public static class HomeViewHolder extends RecyclerView.ViewHolder{
        TextView name,time,des,dur,pace,dis;
        CardView layout;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.post);
            name= itemView.findViewById(R.id.user_post_name);
            time= itemView.findViewById(R.id.user_post_date);
            des= itemView.findViewById(R.id.user_post_des);
            dur= itemView.findViewById(R.id.user_post_duration);
            pace= itemView.findViewById(R.id.user_post_pace);
            dis= itemView.findViewById(R.id.user_post_distance);
        }
    }
}
