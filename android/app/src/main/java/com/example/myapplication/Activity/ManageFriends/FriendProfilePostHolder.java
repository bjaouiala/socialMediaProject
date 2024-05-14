package com.example.myapplication.Activity.ManageFriends;

import android.graphics.Bitmap;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Helper;
import com.example.myapplication.Model.PostResponse;
import com.example.myapplication.R;

import java.util.List;

public class FriendProfilePostHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ImageView postView,icone,menu;
    private TextView commentaire,jaime,description,namepost,date;
    private PostResponse post ;
    private OnFriendProfileClick listnner;
    public FriendProfilePostHolder(@NonNull View itemView,OnFriendProfileClick friendProfileAction) {
        super(itemView);

        listnner=friendProfileAction;
            postView = itemView.findViewById(R.id.post);
            commentaire = itemView.findViewById(R.id.commentaireNum);
            jaime = itemView.findViewById(R.id.jaimeNum);
            description = itemView.findViewById(R.id.description);
            icone = itemView.findViewById(R.id.pictureIcone);
            namepost = itemView.findViewById(R.id.namep);
            date = itemView.findViewById(R.id.date);
        postView.setOnClickListener(this);
    }

    public void bind(PostResponse postResponse) {
        post = postResponse;
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

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.post){listnner.displayPicture(post.getPost_id());

        }
    }
}
