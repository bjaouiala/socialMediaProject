package com.example.myapplication.Activity.ManageProfile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Helper;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;

public class ProfileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private ImageView couverturePicture,setProfile,setCouverture;
    private ImageView profilePicture;
    private TextView nameLastname;
    private User user;
    private ImageSelectionListnner listnner;
    public ProfileViewHolder(@NonNull View itemView,ImageSelectionListnner listnner) {
        super(itemView);
        couverturePicture= itemView.findViewById(R.id.couverturePicture);
        profilePicture=itemView.findViewById(R.id.profilePicture);
        nameLastname = itemView.findViewById(R.id.NameLastname);
        setProfile = itemView.findViewById(R.id.imageView3);
        setCouverture = itemView.findViewById(R.id.imageView4);
        profilePicture.setOnClickListener(this);
       setCouverture.setOnClickListener(this);
       setProfile.setOnClickListener(this);
       couverturePicture.setOnClickListener(this);

    }
    void bind(User userResponse,ImageSelectionListnner imageSelectionListnner){
        listnner = imageSelectionListnner;
        user = userResponse;
        couverturePicture.setImageBitmap(Helper.parseImage(user.getPhotoDeCouverture()));
        profilePicture.setImageBitmap(Helper.parseImage((user.getPhotoDeProfile())));
        nameLastname.setText(user.getFirstname()+" "+user.getLastname());
    }

    @Override
    public void onClick(View v) {
        if (R.id.profilePicture == v.getId()){
           

        } else if (R.id.couverturePicture == v.getId()) {

        } else if (R.id.imageView3 == v.getId()) {
            listnner.pictureType("photoDeProfile");

        } else if (R.id.imageView4 == v. getId()) {

                listnner.pictureType("photoDeCouverture");


        }
    }
}
