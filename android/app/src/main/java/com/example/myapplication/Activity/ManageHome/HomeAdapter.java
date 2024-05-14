package com.example.myapplication.Activity.ManageHome;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.ManageProfile.ImageSelectionListnner;
import com.example.myapplication.Model.PostResponse;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int VIEW_TYPE_PUB_POST = 1;
    private static final int VIEW_TYPE_PUB = 0;
    private List<PostResponse> list;
    private User user;
    private HomeListnner listnner;
    private Context context;
    private long id;



    public HomeAdapter(List<PostResponse> list, User user,HomeListnner listnner,long id) {
        this.list = list;
        this.user = user;
        this.listnner = listnner;
        this.id=id;
    }
    public void removePost(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case VIEW_TYPE_PUB:

                View pubView = inflater.inflate(R.layout.pub_view,parent,false);
                return new PubHolder(pubView);
            case  VIEW_TYPE_PUB_POST:
                Log.d("postsSize",list.size()+"");
                View posts ;
                if (list.size() == 0 ){
                    posts = inflater.inflate(R.layout.nopicturefound,parent,false);
                }else {
                    posts = inflater.inflate(R.layout.postview,parent,false);

                }
                return new HomePostsHolder(posts);
            default:
                throw new IllegalArgumentException();
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_PUB : VIEW_TYPE_PUB_POST;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PubHolder){
            ((PubHolder) holder).bind(user,listnner);
        } else if (holder instanceof HomePostsHolder) {
            if (list.size() > 0){
                ((HomePostsHolder) holder).bind(list.get(position-1),listnner,position+1, context,id);
            }else {
                ((HomePostsHolder) holder).bind(null,listnner,position+1,context,id);
            }


        }

    }

    @Override
    public int getItemCount() {
        if(user != null){
            if (list.size() == 0){
                return 2;
            }else {
                return list.size()+1;
            }
        }else {
            throw new RuntimeException("user not found");
        }

    }
}
