package com.example.myapplication.Activity.ManageProfile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Helper;
import com.example.myapplication.Model.Post;
import com.example.myapplication.Model.PostResponse;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;

public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ImageView postView,icone;
    private TextView commentaire,jaime,description,namepost,date;
    private PostResponse post ;
    private ImageSelectionListnner listnner;


    public PostViewHolder(@NonNull View itemView ,ImageSelectionListnner l) {
        super(itemView);
        listnner=l;
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
        description.setText(post.getDescription());
        postView.setImageBitmap(Helper.parseImage(post.getPostFile()));
        icone.setImageBitmap(Helper.parseImage(post.getPhotoDeProfile()));
        namepost.setText(post.getFirstname()+" "+post.getLastname());
        date.setText(post.getDateCreate().substring(0,10));

    }

    @Override
    public void onClick(View v) {
        long id = post.getPost_id();
        if (v.getId() == R.id.post){
            listnner.displayPicture(id);
        }

    }
}
