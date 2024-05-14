package com.example.myapplication.Activity.ManageAccount;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.User;
import com.example.myapplication.R;

import java.util.List;

public class BlockedFriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<User> blockedFriends ;
    private BlockFriendListnner listnner;

    public BlockedFriendAdapter(List<User> blockedFriends, BlockFriendListnner listnner) {
        this.blockedFriends = blockedFriends;
        this.listnner = listnner;
        Log.d("size",""+blockedFriends.size());
    }
    public void updateList(int position){
        Log.d("removed",""+position);
        blockedFriends.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from((parent.getContext()));
        if (viewType == 0){
            View blockedFriends = inflater.inflate(R.layout.blockedfriendholder,parent,false);
            return new BlockFriendHolder(blockedFriends);
        }else {
            throw new RuntimeException();
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BlockFriendHolder){
            ((BlockFriendHolder) holder).bind(blockedFriends.get(position),listnner);
        }

    }

    @Override
    public int getItemCount() {
        return blockedFriends.size();
    }
}
