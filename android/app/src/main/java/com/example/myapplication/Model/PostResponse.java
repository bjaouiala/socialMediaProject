package com.example.myapplication.Model;

public class PostResponse {
    private String description;

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

    private long post_id;
            private long userId;
            private String dateCreate;
            private String postFile;
            private String Firstname;
            private String Lastname;
            private String photoDeProfile;
}
