package com.example.myapplication.Activity.ManageProfile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
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
    private TextView nameLastname,dateBirth;
    private User user;
    private Button button1,button2;
    private ImageSelectionListnner listnner;
    public ProfileViewHolder(@NonNull View itemView,ImageSelectionListnner listnner) {
        super(itemView);
        couverturePicture= itemView.findViewById(R.id.couverturePicture);
        profilePicture=itemView.findViewById(R.id.profilePicture);
        nameLastname = itemView.findViewById(R.id.NameLastname);
        setProfile = itemView.findViewById(R.id.imageView3);
        setCouverture = itemView.findViewById(R.id.imageView4);

        dateBirth = itemView.findViewById(R.id.textView16);
        dateBirth.setVisibility(View.VISIBLE);
        button1 = itemView.findViewById(R.id.state);
        button2 = itemView.findViewById(R.id.message);
        button1.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
        profilePicture.setOnClickListener(this);
       setCouverture.setOnClickListener(this);
       setProfile.setOnClickListener(this);
       couverturePicture.setOnClickListener(this);


    }
    void bind(User userResponse,ImageSelectionListnner imageSelectionListnner){
        listnner = imageSelectionListnner;
        user = userResponse;
        String date = user.getDateBirth();
        String convertedDate = date.substring(0,10);
        dateBirth.setText("Date de naissance\n"+Helper.convertDate(convertedDate));
        Bitmap profile = Helper.parseImage(user.getPhotoDeProfile());
        Bitmap cover = Helper.parseImage(user.getPhotoDeCouverture());
        if (profile != null){
            profilePicture.setImageBitmap(profile);
        }else {
            profilePicture.setImageResource(R.drawable.defaultpicture);
        }
        if (cover != null){
            couverturePicture.setImageBitmap(cover);
        }else {
            couverturePicture.setImageResource(R.drawable.defaultpicture);
        }
        nameLastname.setText(user.getFirstname()+" "+user.getLastname());
    }

    @Override
    public void onClick(View v) {
        if (R.id.profilePicture == v.getId()){
           listnner.displayPicture(user.getProfileId());

        } else if (R.id.couverturePicture == v.getId()) {
            listnner.displayPicture(user.getCoverId());

        } else if (R.id.imageView3 == v.getId()) {
            listnner.pictureType("photoDeProfile");

        } else if (R.id.imageView4 == v. getId()) {

                listnner.pictureType("photoDeCouverture");


        }
    }
}
