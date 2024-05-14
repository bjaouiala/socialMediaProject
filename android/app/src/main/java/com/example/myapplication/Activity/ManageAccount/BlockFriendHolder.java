package com.example.myapplication.Activity.ManageAccount;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Helper;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;

import java.util.List;

public class BlockFriendHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private ImageView imageView;
    private TextView textView;
    private Button button;
    private User user;
    private BlockFriendListnner listnner;

    public BlockFriendHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.blockname);
        imageView = itemView.findViewById(R.id.blockedImage);
        button = itemView.findViewById(R.id.unblock);
        button.setOnClickListener(this);

    }

    public void bind(User blockedFriends, BlockFriendListnner blockFriendListnner) {
        listnner = blockFriendListnner;
        user = blockedFriends;
        Bitmap bitmap = Helper.parseImage(blockedFriends.getPhotoDeProfile());
        if (bitmap != null){
            imageView.setImageBitmap(bitmap);
        }else {
            imageView.setImageResource(R.drawable.defaultpicture);
        }
        textView.setText(blockedFriends.getFirstname()+" "+blockedFriends.getLastname());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.unblock){
            listnner.unblockFriend(user.getFollowerId(), user.getFollowedId(), getAbsoluteAdapterPosition());
        }
    }
}
