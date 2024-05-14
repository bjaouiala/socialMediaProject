package com.example.myapplication.Activity.ManageAccount;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.User;
import com.example.myapplication.R;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<User> friends;

    public FriendsAdapter(List<User> friends) {
        this.friends = friends;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from((parent.getContext()));
        if (viewType == 0){
            View friendHolder = inflater.inflate(R.layout.friendsholder,parent,false);
            return new FriendsHolder(friendHolder);
        }else {
            throw new RuntimeException();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FriendsHolder){
            ((FriendsHolder) holder).bind(friends.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return friends.size();
    }
}
