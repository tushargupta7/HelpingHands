package com.example.tushar.helpinghands.models;

import android.graphics.Bitmap;

/**
 * Created by tushar on 1/4/16.
 */
public class UserDetails {
    private String name;
    private String dob;
    private String gender;
    private String fbUserId;
    private Bitmap fbProfilePic;
    private String eMail;
    private String uuid;
    private String status;
    private String token;
    private String mobile;
    static UserDetails _instance;

    public static UserDetails getInstance(){
        if(_instance==null){
            _instance= new UserDetails();
        }
        return _instance;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public Bitmap getFbProfilePic() {
        return fbProfilePic;
    }

    public void setFbProfilePic(Bitmap fbProfilePic) {
        this.fbProfilePic = fbProfilePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFbUserId() {
        return fbUserId;
    }

    public void setFbUserId(String fbUserId) {
        this.fbUserId = fbUserId;
    }
}
