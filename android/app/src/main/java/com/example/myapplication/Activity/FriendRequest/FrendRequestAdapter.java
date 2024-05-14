package com.example.myapplication.Activity.FriendRequest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.FriendRequest;
import com.example.myapplication.R;

import java.util.List;

public class FrendRequestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<FriendRequest> frienRequests ;
    FriendRequestListnner listnner;
    public FrendRequestAdapter(List<FriendRequest> frienRequests,FriendRequestListnner listnner) {
        this.frienRequests=frienRequests;
        this.listnner=listnner;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == 0){
            View friendRequest = inflater.inflate(R.layout.friendrequestholder,parent,false);
            if (frienRequests.size() == 0 ){
                friendRequest = inflater.inflate(R.layout.no_friend_request,parent,false);
            }else {
                friendRequest = inflater.inflate(R.layout.friendrequestholder,parent,false);
            }
            return new FriendRequestHolder(friendRequest);
        }else {
            throw new IllegalArgumentException();
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FriendRequestHolder){
            if (frienRequests.size() == 0){
                ((FriendRequestHolder) holder).bind(null,listnner);
            }else {
                ((FriendRequestHolder) holder).bind(frienRequests.get(position),listnner);
            }
        }

    }

    @Override
    public int getItemCount() {
        if (frienRequests.size() == 0){
            return 1;
        }else {
            return frienRequests.size();
        }

    }
}
