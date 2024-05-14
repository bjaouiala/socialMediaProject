package com.example.myapplication.Activity.FriendSearch;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Helper;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;

public class FriendSearchHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private ImageView imageView;
    private TextView textView;
    private ConstraintLayout constraintLayout;
    private FriendSearchAction friendSearchAction;
    private User user;
    public FriendSearchHolder(@NonNull View itemView,FriendSearchAction listnner) {
        super(itemView);
        friendSearchAction = listnner;
        imageView = itemView.findViewById(R.id.imageSearch);
        textView = itemView.findViewById(R.id.textSearch);
        constraintLayout = itemView.findViewById(R.id.friend);
        constraintLayout.setOnClickListener(this);

    }

    public void bind(User user1){
        user=user1;
        if (user.getPhotoDeProfile()!=null){
            Bitmap photo = Helper.parseImage(user.getPhotoDeProfile());
            imageView.setImageBitmap(photo);
        }else {
            imageView.setImageResource(R.drawable.defaultpicture);
        }
        textView.setText(user.getFirstname()+" "+user.getLastname());

    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.friend){
            friendSearchAction.getFriendProfile(user.getId());


        }
    }
}
