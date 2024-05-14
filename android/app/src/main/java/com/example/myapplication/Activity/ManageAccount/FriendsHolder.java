package com.example.myapplication.Activity.ManageAccount;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Helper;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;

public class FriendsHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;
    private TextView textView;
    public FriendsHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.profile);
        textView = itemView.findViewById(R.id.acceptedFriend);
    }
    public void bind(User friend) {
        Bitmap bitmap = Helper.parseImage(friend.getPhotoDeProfile());
        if (bitmap != null){
            imageView.setImageBitmap(bitmap);
        }else {
            imageView.setImageResource(R.drawable.defaultpicture);
        }
        textView.setText(friend.getFirstname()+" "+friend.getLastname());
    }
}

