package com.example.myapplication.Activity.ManageFriends;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Helper;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;

public class FriendProfileHeaderHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private ImageView couverturePicture,setProfile,setCouverture;
    private ImageView profilePicture;
    private TextView nameLastname,dateBirth;
    private User user;
    private Button messageButton,statButton;
    private OnFriendProfileClick listnner;
    private long followerId;
    private long followedID;
    private Context context;
    public FriendProfileHeaderHolder(@NonNull View itemView,OnFriendProfileClick friendProfileAction,long followedID,long followerID) {
        super(itemView);
        this.followedID=followedID;
        this.followerId=followerID;
        listnner=friendProfileAction;
        couverturePicture= itemView.findViewById(R.id.couverturePicture);
        profilePicture=itemView.findViewById(R.id.profilePicture);
        nameLastname = itemView.findViewById(R.id.NameLastname);
        setProfile = itemView.findViewById(R.id.imageView3);
        setCouverture = itemView.findViewById(R.id.imageView4);
        messageButton = itemView.findViewById(R.id.message);
        statButton = itemView.findViewById(R.id.state);
        dateBirth = itemView.findViewById(R.id.textView16);
        profilePicture.setOnClickListener(this);
        couverturePicture.setOnClickListener(this);
        statButton.setOnClickListener(this);
    }


    public void bind(User userResponse, Context context1){
        context=context1;
        user = userResponse;
        setProfileImages();
        setButtonState();
        setProfile.setVisibility(View.GONE);
        nameLastname.setText(user.getFirstname()+" "+user.getLastname());

    }


    public void setProfileImages(){
        Bitmap profile = Helper.parseImage(user.getPhotoDeProfile());
        Bitmap cover = Helper.parseImage(user.getPhotoDeCouverture());
        String date = user.getDateBirth();
        String convertedDate = date.substring(0,10);
        dateBirth.setText("Date de naissance\n"+Helper.convertDate(convertedDate));
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
    }


    private void setButtonState(){
        if (user.getState()==null){
            statButton.setText(R.string.Ajouter);
        } else if (user.getState().equals("ACCEPTED")) {
            statButton.setText(R.string.Amie);
        } else if (user.getState().equals("PENDING")){
            if (followerId == user.getFollowerId() && followedID == user.getFollowedId()){
           statButton.setText("Annuler");}
            else if (followerId == user.getFollowedId() && followedID == user.getFollowerId()) {
                statButton.setText("Repondre");
            }
        }
        messageButton.setTextColor(ContextCompat.getColor(context,R.color.white));
        messageButton.setText(R.string.Message);
        setCouverture.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.couverturePicture){
            Log.d("ID"," "+user.getCoverId());
            Log.d("IDProfile"," "+user.getProfileId());
            listnner.displayPicture(user.getCoverId());
        } else if (v.getId() == R.id.profilePicture) {
            listnner.displayPicture(user.getCoverId());

        } else if (v.getId() == R.id.state) {
            if (user.getState() == null){
                listnner.onFriendStateChange("");
                statButton.setText(R.string.Annuler);
            } else if (user.getState().equals("PENDING")) {
                listnner.onFriendStateChange("PENDING");
            } else if (user.getState().equals("ACCEPTED")) {
                listnner.onFriendStateChange("ACCEPTED");

            }

        }
    }
}
