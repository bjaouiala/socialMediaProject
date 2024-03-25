package com.example.myapplication.Activity.ManageProfile;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Post;
import com.example.myapplication.Model.PostResponse;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int VIEW_TYPE_Profile = 0;
    private static final int VIEW_TYPE_POST = 1;
    private static final int RESULT_CODE=1;

    private User user;
    private List<PostResponse> posts;
    ImageSelectionListnner listnner;




    public ProfileAdapter( User user,List<PostResponse> posts,ImageSelectionListnner listnner) {
        this.listnner=listnner;
        this.user = user;
        this.posts = posts;

    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_Profile: VIEW_TYPE_POST;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case VIEW_TYPE_Profile:
                View profileView = inflater.inflate(R.layout.profileheadre,parent,false);
                return new ProfileViewHolder(profileView,listnner);

            case VIEW_TYPE_POST:
                View postView = inflater.inflate(R.layout.postview,parent,false);
                return new PostViewHolder(postView,listnner );
            default:
                throw new IllegalArgumentException();
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ProfileViewHolder){
            ((ProfileViewHolder) holder).bind(user,listnner);
        }else if(holder instanceof PostViewHolder){
            PostResponse post = posts.get(position-1);
            ((PostViewHolder)holder).bind(post);
        }

    }



    @Override
    public int getItemCount() {
        return (user != null) ? posts.size() + 1 : posts.size();
    }

}
