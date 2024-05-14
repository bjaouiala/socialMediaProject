package com.example.myapplication.Model;

public class FriendRequest {
    private long followerId;
    private long followedId;
    private String photoDeProfile;
    private String Firstname;
    private String Lastname;
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getFollowerid() {
        return followerId;
    }

    public void setFollowerid(long followerid) {
        this.followerId = followerid;
    }

    public long getFollowedid() {
        return followedId;
    }

    public void setFollowedid(long followedid) {
        this.followedId = followedid;
    }

    public String getPhotoDeProfile() {
        return photoDeProfile;
    }

    public void setPhotoDeProfile(String photoDeProfile) {
        this.photoDeProfile = photoDeProfile;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }
}
