package com.example.myapplication.Model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String Firstname;
    private String Lastname;
    private String password;
    private String email;
    private String emailRecuperation;
    private String dateBirth;
    private String gendre;
    private String phoneNumber;
    private String CodeConfirmation;
    private String photoDeProfile;
    private String photoDeCouverture;

    public String getPhotoDeProfile() {
        return photoDeProfile;
    }

    public void setPhotoDeProfile(String photoDeProfile) {
        this.photoDeProfile = photoDeProfile;
    }

    public String getPhotoDeCouverture() {
        return photoDeCouverture;
    }

    public void setPhotoDeCouverture(String photoDeCouverture) {
        this.photoDeCouverture = photoDeCouverture;
    }

    public String getCodeConfirmation() {
        return CodeConfirmation;
    }

    public void setCodeConfirmation(String codeConfirmation) {
        CodeConfirmation = codeConfirmation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailRecuperation() {
        return emailRecuperation;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public void setEmailRecuperation(String emailRecuperation) {
        this.emailRecuperation = emailRecuperation;
    }



    public String getGendre() {
        return gendre;
    }

    public void setGendre(String gendre) {
        this.gendre = gendre;
    }


}
