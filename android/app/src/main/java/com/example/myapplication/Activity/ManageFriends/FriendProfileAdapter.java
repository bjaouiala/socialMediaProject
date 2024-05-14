package com.example.myapplication.Activity.ManageFriends;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.PostResponse;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;

import java.util.List;

public class FriendProfileAdapter extends RecyclerView.Adapter {
    private  final int PROFILE_HEADER_VIEW=0;
    private final int POST_VIEW=1;
    private List<PostResponse> posts;
    private User user;
    private OnFriendProfileClick listnner;
    private Context context;
    private long followerId;
    private long followedID;

    public FriendProfileAdapter(List<PostResponse> posts, User user,OnFriendProfileClick listnner,long followedId,long followerID) {
        this.posts = posts;
        this.user = user;
        this.listnner=listnner;
        this.followedID=followedId;
        this.followerId=followerID;
    }

    public void updateDataSet(String state){
        user.setState(state);
        notifyItemChanged(0);
    }
    public void updateFollowers(long followerId,long followedID){
        this.followedID=followedID;
        this.followerId=followerId;
        user.setFollowedId(followedID);
        user.setFollowerId(followerId);
        notifyItemChanged(0);
    }

    @Override
    public int getItemViewType(int position) {
        return position==0?PROFILE_HEADER_VIEW : POST_VIEW;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == PROFILE_HEADER_VIEW ){
            View FriendProfileHeaderView = inflater.inflate(R.layout.profileheadre,parent,false);
            return new FriendProfileHeaderHolder(FriendProfileHeaderView,listnner,followedID,followerId);
        }else if (viewType == POST_VIEW){
            View FriendPostView = inflater.inflate(R.layout.postview,parent,false);
            return new FriendProfilePostHolder(FriendPostView,listnner);
        }else {
            throw  new IllegalArgumentException();
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FriendProfileHeaderHolder){
            ((FriendProfileHeaderHolder)holder).bind(user,context);
        }else ((FriendProfilePostHolder)holder).bind(posts.get(position-1));

    }

    @Override
    public int getItemCount() {
        if(user != null){
            if (posts.size() == 0){
                return 1;
            }else {
                return posts.size()+1;
            }
        }else {
            throw new RuntimeException("user not found");
        }
    }
}
