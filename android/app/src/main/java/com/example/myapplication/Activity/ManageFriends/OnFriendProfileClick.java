package com.example.myapplication.Activity.ManageFriends;

public interface OnFriendProfileClick {
    default void displayPicture(long id){}
    void onFriendStateChange(String state);
}
