package com.example.myapplication.Activity.ManageAccount;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Helper;
import com.example.myapplication.Model.PostResponse;
import com.example.myapplication.R;

public class RegistredPostHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView post,icone ;
    TextView name;
    private RemovePostListnner removePostListnner ;
    private PostResponse posts;

    public RegistredPostHolder(@NonNull View itemView) {
        super(itemView);
        post = itemView.findViewById(R.id.registredpost);
        icone = itemView.findViewById(R.id.unregister);
        name = itemView.findViewById(R.id.registredusername);
        icone.setOnClickListener(this);
    }

    public void bind(PostResponse postResponse, RemovePostListnner listnner) {
        removePostListnner = listnner;
        posts = postResponse;
        Bitmap bitmap = Helper.parseImage(postResponse.getPostFile());
        if (bitmap != null){
            post.setImageBitmap(bitmap);
        }else {
            post.setImageResource(R.drawable.defaultpicture);
        }
        name.setText(postResponse.getFirstname()+" "+postResponse.getLastname());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.unregister){
            removePostListnner.removePost(posts.getUserId(),posts.getPost_id(),getAbsoluteAdapterPosition());

        }
    }
}
