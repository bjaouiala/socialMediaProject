package com.example.myapplication.Activity.ManageAccount;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.PostResponse;
import com.example.myapplication.R;

import java.util.List;

public class RegistredPostAdapter extends RecyclerView.Adapter {
    List<PostResponse> posts;
    private RemovePostListnner listnner;

    public RegistredPostAdapter(List<PostResponse> posts, RemovePostListnner listnner) {
        this.posts = posts;
        this.listnner = listnner;
    }
    public void updatePostList(int position){
        posts.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from((parent.getContext()));
        if (viewType == 0){
            View registredPost = inflater.inflate(R.layout.registredpostholder,parent,false);
            return new RegistredPostHolder(registredPost);
        }else {
            throw new RuntimeException();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RegistredPostHolder){
            ((RegistredPostHolder) holder).bind(posts.get(position),listnner);
        }

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
