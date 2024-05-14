package com.example.myapplication.Activity.FriendSearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.User;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class FriendSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<User> users = new ArrayList<>();
    FriendSearchAction friendSearchAction;

    public FriendSearchAdapter(List<User> users,FriendSearchAction friendSearchAction) {
        this.users = users;
        this.friendSearchAction = friendSearchAction;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       LayoutInflater inflater = LayoutInflater.from(parent.getContext());
       if (viewType == 0){
           View FrienSearch = inflater.inflate(R.layout.friend_search_holder,parent,false);
           return new FriendSearchHolder(FrienSearch,friendSearchAction);
       }else {
           throw new IllegalArgumentException();
       }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FriendSearchHolder){
            ((FriendSearchHolder) holder).bind(users.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void Search(List<User> user){
        users.clear();
        if (user != null){
            users.addAll(user);
            notifyDataSetChanged();
        }
    }
}
