package com.example.tushar.helpinghands;

import android.media.Image;

import org.json.JSONArray;

import java.io.Serializable;

/**
 * Created by hussain on 7/4/16.
 */
public class StudentEntries implements Serializable {

    private String id;
    private String imageUrl;
    private int age;
    private int mClass;
    private String name;
    private float performance;
    private int income;
    private String gender;
    private String school;
    private String orphanage;
    private String email;
    private String phoneNo;
    private String address;
    private String description;
    private String registeredAt;
    private float latitude;
    private float longitude;
    private transient JSONArray requirements;
    private transient JSONArray fulfilled;

    public StudentEntries(String id, String imageUrl, int age, int mClass, String name, float performance, int income, String gender, String school, String orphanage, String email, String phoneNo, String address, String description, String registeredAt, float latitude, float longitude, JSONArray requirements, JSONArray fulfilled){
        this.id = id;
        this.imageUrl = imageUrl;
        this.age = age;
        this.mClass = mClass;
        this.name = name;
        this.performance = performance;
        this.income = income;
        this.gender = gender;
        this.school = school;
        this.orphanage = orphanage;
        this.email = email;
        this.phoneNo = phoneNo;
        this.address = address;
        this.description = description;
        this.registeredAt = registeredAt;
        this.latitude = latitude;
        this.longitude = longitude;
        this.requirements = requirements;
        this.fulfilled = fulfilled;
    }

    public int getAge(){
        return age;
    }

    public int getmClass(){
        return mClass;
    }

    public String getName(){
        return name;
    }

    public String getSchool(){
        return school;
    }

    public String getGender(){
        return gender;
    }

    public String getPhoneNo(){
        return phoneNo;
    }

    public float getPerformance(){
        return performance;
    }

    public int getIncome(){
        return income;
    }

    public String getAddress(){
        return address;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public String getOrphanage(){
        return orphanage;
    }

    public String getEmail(){
        return email;
    }

    public String getDescription(){
        return description;
    }

    public String getRegisteredAt(){
        return registeredAt;
    }

}
