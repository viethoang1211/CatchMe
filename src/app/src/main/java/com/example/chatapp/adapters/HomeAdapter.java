package com.example.chatapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.Class.Post;
import com.example.chatapp.R;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    Context context;
    ArrayList<Post> Posts;
    public HomeAdapter(Context context, ArrayList<Post> Posts) {
        this.context= context;
        this.Posts= Posts;
    }

    @NonNull
    @Override
    public HomeAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_home_row,parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.HomeViewHolder holder, int position) {
        holder.name.setText(Posts.get(position).user_name);
        holder.time.setText(Integer.toString(Posts.get(position).posting_time));
        holder.des.setText(Posts.get(position).description);
        holder.dur.setText(Integer.toString(Posts.get(position).duration));
        holder.pace.setText(Float.toString(Posts.get(position).pace));
        holder.dis.setText(Float.toString(Posts.get(position).distance));
    }

    @Override
    public int getItemCount() {
        return Posts.size();
    }
    public static class HomeViewHolder extends RecyclerView.ViewHolder{
        TextView name,time,des,dur,pace,dis;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.user_post_name);
            time= itemView.findViewById(R.id.user_post_date);
            des= itemView.findViewById(R.id.user_post_des);
            dur= itemView.findViewById(R.id.user_post_duration);
            pace= itemView.findViewById(R.id.user_post_pace);
            dis= itemView.findViewById(R.id.user_post_distance);
        }
    }
}
