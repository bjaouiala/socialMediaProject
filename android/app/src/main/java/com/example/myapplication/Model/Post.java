package com.example.myapplication.Model;

public class Post {
    private long id;
    private long user_id;
    private String description;
    private String dateCreate;
    private String postFile;
    private Boolean isProfile;
    private Boolean isCover;

    public Boolean getProfile() {
        return isProfile;
    }

    public void setProfile(Boolean profile) {
        isProfile = profile;
    }

    public Boolean getCover() {
        return isCover;
    }

    public void setCover(Boolean cover) {
        isCover = cover;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
