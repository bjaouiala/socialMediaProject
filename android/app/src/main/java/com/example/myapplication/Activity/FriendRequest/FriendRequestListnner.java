package com.example.myapplication.Activity.FriendRequest;

public interface FriendRequestListnner {
    void acceptFrienfRequest(long followerid,long followedid);
    void deleteFrienfRequest(long followerid,long folloedid);
}
