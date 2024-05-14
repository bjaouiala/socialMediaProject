package com.example.myapplication.Model;

public class PostResponse {
    private String description;
    private long post_id;
    private long userId;
    private String dateCreate;
    private long state;
    private long likeCount;
    private long likedUserId;
    private long likedPostId;

    public long getLikedUserId() {
        return likedUserId;
    }


    public void setLikedUserId(long likedUserId) {
        this.likedUserId = likedUserId;
    }

    public long getLikedPostId() {
        return likedPostId;
    }

    public void setLikedPostId(long likedPostId) {
        this.likedPostId = likedPostId;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    private String postFile;
    private String Firstname;
    private String Lastname;
    private String photoDeProfile;
    private long followedId;

    private long followerId;

    public long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(long followerId) {
        this.followerId = followerId;
    }

    public void setPost_id(long post_id) {
        this.post_id = post_id;
    }

    public long getFollowedId() {
        return followedId;
    }

    public void setFollowedId(long followedId) {
        this.followedId = followedId;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPost_id() {
        return post_id;
    }

    public void setPost_idid(long postid) {
        this.post_id = postid;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getPostFile() {
        return postFile;
    }

    public void setPostFile(String postFile) {
        this.postFile = postFile;
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

    public String getPhotoDeProfile() {
        return photoDeProfile;
    }

    public void setPhotoDeProfile(String photoDeProfile) {
        this.photoDeProfile = photoDeProfile;
    }
    public long getState() {
        return state;
    }

    public void setState(long state) {
        this.state = state;
    }



}
