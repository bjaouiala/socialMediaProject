package com.example.myapplication.Activity.ManageHome;

import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.ManageProfile.ImageSelectionListnner;
import com.example.myapplication.Helper;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;

public class PubHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView pub;
    private ImageView imageView,pubImage;
    private HomeListnner homeListnner;
    public PubHolder(@NonNull View itemView) {
        super(itemView);
        pub = itemView.findViewById(R.id.pubText);
        imageView = itemView.findViewById(R.id.pubimage);
        pubImage = itemView.findViewById(R.id.setPubImage);
        pubImage.setOnClickListener(this);
        pub.setOnClickListener(this);
    }

    public void bind(User user, HomeListnner listnner) {
        homeListnner = listnner;

        if (user.getPhotoDeProfile() != null){
            imageView.setImageBitmap(Helper.parseImage(user.getPhotoDeProfile()));
        }else {
            imageView.setImageResource(R.drawable.defaultpicture);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.setPubImage){
            homeListnner.pubPicture();
        } else if (v.getId() == R.id.pubText) {
            homeListnner.pubText();
        } else if (v.getId() == R.id.pubimage) {
            homeListnner.getprofile();
        }
    }
}
