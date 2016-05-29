package com.example.tushar.helpinghands;

import android.media.Image;

import org.json.JSONArray;

import java.io.Serializable;

/**
 * Created by hussain on 7/4/16.
 */
public class StudentEntries implements Serializable {

    private String id;
    private String cclass;
    private String name;
    private String pname;
    private String performance;
    private String income;
    private String gender;
    private String school;
    private String orphanage;
    private String email;
    private String phoneNo;
    private String address;
    private String description;
    private String registeredAt;
    private String dob;
    private String highClass;
 /*   private float latitude;
    private float longitude;
    private transient JSONArray requirements;
    private transient JSONArray fulfilled;*/

    public StudentEntries(String id, String name, String dob, String pname, String orphanageId,String orphanageName, String email,
                           String perfo, String pincome, String cclass, String hclass){
        this.id = id;
        this.dob=dob;
        /*this.imageUrl = imageUrl;
        this.age = age;*/
        this.cclass = cclass;
        this.name = name;
        this.pname=pname;
        this.performance = perfo;
        this.income = pincome;
        this.email = email;
        this.orphanage=orphanageId+" "+orphanageName;
        this.highClass=hclass;/*
        this.latitude = latitude;
        this.longitude = longitude;
        this.requirements = requirements;
        this.fulfilled = fulfilled;*/
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCclass() {
        return cclass;
    }

    public void setCclass(String cclass) {
        this.cclass = cclass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getOrphanage() {
        return orphanage;
    }

    public void setOrphanage(String orphanage) {
        this.orphanage = orphanage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(String registeredAt) {
        this.registeredAt = registeredAt;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getHighClass() {
        return highClass;
    }

    public void setHighClass(String highClass) {
        this.highClass = highClass;
    }
}
