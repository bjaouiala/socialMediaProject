package com.example.myapplication.Activity.FriendRequest;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Helper;
import com.example.myapplication.Model.FriendRequest;
import com.example.myapplication.R;

import java.util.List;

public class FriendRequestHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private ImageView profilePicture,noFriendRequest;
    private TextView frienName,commentRequest;
    private Button accept,delete;
    FriendRequestListnner listnner;
    FriendRequest friend;

    public FriendRequestHolder(@NonNull View itemView) {
        super(itemView);
        profilePicture = itemView.findViewById(R.id.imagerequest);
        frienName = itemView.findViewById(R.id.friendnamerequest);
        accept= itemView.findViewById(R.id.acceptrequest);
        delete = itemView.findViewById(R.id.deleterequest);
        noFriendRequest= itemView.findViewById(R.id.noFriendRequest);
        commentRequest = itemView.findViewById(R.id.requestcomment);

        
    }

    public void bind(FriendRequest frienRequests,FriendRequestListnner friendRequestListnner) {
        listnner= friendRequestListnner;
        friend=frienRequests;
       if (friend != null){
           commentRequest.setVisibility(View.GONE);
           accept.setOnClickListener(this);
           delete.setOnClickListener(this);
           Bitmap bitmap = Helper.parseImage(frienRequests.getPhotoDeProfile());
           if (bitmap != null){
               profilePicture.setImageBitmap(bitmap);
           }else {
               profilePicture.setImageResource(R.drawable.defaultpicture);
           }
           frienName.setText(frienRequests.getFirstname()+" "+frienRequests.getLastname());
       }
    }

    @Override
    public void onClick(View v) {
        if (R.id.acceptrequest == v.getId()){
            listnner.acceptFrienfRequest(friend.getFollowerid(), friend.getFollowedid());
            commentRequest.setVisibility(View.VISIBLE);
            accept.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
            commentRequest.setText("vous etes maintenant amis");

        }
        else if (R.id.deleterequest == v.getId()) {
            listnner.deleteFrienfRequest(friend.getFollowerid(), friend.getFollowedid());
            commentRequest.setVisibility(View.VISIBLE);
            accept.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
            commentRequest.setText("invitation supprimer");
        }

    }
}
