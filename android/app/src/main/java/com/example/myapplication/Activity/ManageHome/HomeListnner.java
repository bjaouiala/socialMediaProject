package com.example.myapplication.Activity.ManageHome;

import android.view.View;

public interface HomeListnner {
    void pubPicture();
    void displayPicture(long id);
    void  getprofile();
    void listMenuItem();
    void pubText();
    void getFriendProfile(long id,long followedid);

    void listMenuItem(int position,long postId);

    void like(long userId, long postId, View itemView);
}
