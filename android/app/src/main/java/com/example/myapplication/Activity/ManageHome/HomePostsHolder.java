package com.example.myapplication.Activity.ManageHome;





import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Helper;
import com.example.myapplication.Model.PostResponse;
import com.example.myapplication.R;
import com.example.myapplication.config.RetrofitClient;
import com.example.myapplication.services.LikeService;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePostsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private ImageView postView,icone,menu,nopicture;
    private TextView commentaire,jaime,description,namepost,date;
    private PostResponse post ;
    private HomeListnner listnner;
    private int position;
    private Button jaimeb;
    private View view;
    private boolean state;
    private long count;
    public HomePostsHolder(@NonNull View itemView) {
        super(itemView);
        postView = itemView.findViewById(R.id.post);
        commentaire = itemView.findViewById(R.id.commentaireNum);
        jaime = itemView.findViewById(R.id.jaimeNum);
        jaimeb = itemView.findViewById(R.id.jaime);
        description = itemView.findViewById(R.id.description);
        icone = itemView.findViewById(R.id.pictureIcone);
        namepost = itemView.findViewById(R.id.namep);
        date = itemView.findViewById(R.id.date);
        menu = itemView.findViewById(R.id.menu);
        nopicture = itemView.findViewById(R.id.nopicture);
        view = itemView;
        Context context;


    }

    public void bind(PostResponse postResponse , HomeListnner homeListnner,int pos, Context context,long id) {
        position = pos;
        listnner= homeListnner;
        post = postResponse;
        getcount();



        if (post != null){

            menu.setOnClickListener(this);
            icone.setOnClickListener(this);
            postView.setOnClickListener(this);
            jaimeb.setOnClickListener(this);
            Bitmap postFile = Helper.parseImage(post.getPostFile());
            Bitmap postIcone = Helper.parseImage(post.getPhotoDeProfile());
            if (postFile != null){
                postView.setImageBitmap(postFile);
            }else {
                postView.setVisibility(View.GONE);
                description.setTextSize(TypedValue.COMPLEX_UNIT_DIP,25);
            }
            if (postIcone != null){
                icone.setImageBitmap(postIcone);
            }else {
                icone.setImageResource(R.drawable.defaultpicture);
            }



            description.setText(post.getDescription());
            namepost.setText(post.getFirstname()+" "+post.getLastname());
            String date1 = post.getDateCreate();
            String convertedDate = date1.substring(0,10);
            date.setText(Helper.convertDate(convertedDate));
            if (post.getLikedPostId() == post.getPost_id() &&  post.getLikedUserId() ==id ){
                jaimeb.setTextColor(ContextCompat.getColor(context, R.color.blue));
                Drawable img = context.getResources().getDrawable(R.drawable.bluelike);
                jaimeb.setCompoundDrawablesWithIntrinsicBounds(img,null,null,null);
                state = true;
            }else {
                jaimeb.setTextColor(ContextCompat.getColor(context, R.color.black));
                Drawable img = context.getResources().getDrawable(R.drawable.jaime);
                jaimeb.setCompoundDrawablesWithIntrinsicBounds(img,null,null,null);
                state = false;
            }
        }

    }
    public void getcount(){
        if (post != null){
            LikeService likeService = RetrofitClient.getRetrofitInsantce().create(LikeService.class);
            Call<PostResponse> call = likeService.count(post.getPost_id());
            call.enqueue(new Callback<PostResponse>() {
                @Override
                public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                    if(response.isSuccessful() && response.body() != null){
                        PostResponse postResponse = response.body();
                        count = postResponse.getLikeCount();
                        jaime.setText(postResponse.getLikeCount()+" "+"j'aime");
                    }
                }

                @Override
                public void onFailure(Call<PostResponse> call, Throwable t) {

                }
            });
        }
    }




    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.pictureIcone){
            listnner.getFriendProfile(post.getUserId(),post.getFollowerId());
        } else if (v.getId() == R.id.post) {
            listnner.displayPicture(post.getPost_id());
        }
        else if (v.getId() == R.id.menu){
            listnner.listMenuItem(position,post.getPost_id());
        } else if (v.getId() == R.id.jaime) {
            if (state){
                count--;
                state = !state;
            }else {
                count++;
                state = !state;
            }
            jaime.setText(count+" "+"j'aime");
            listnner.like(post.getUserId(),post.getPost_id(),view);
        }
    }
}
