package com.example.tushar.helpinghands;

import java.io.Serializable;

/**
 * Created by hussain on 13/4/16.
 */
public class SchoolOrphanageEntries implements Serializable {

    private String schoolName, contactNo, emailId, address;
    private Integer strength;


    public SchoolOrphanageEntries(String schoolName, Integer strength, String contactNo, String emailId, String address){
        this.schoolName = schoolName;
        this.strength = strength;
        this.contactNo = contactNo;
        this.emailId = emailId;
        this.address = address;
    }

    public String getName(){
        return schoolName;
    }

    public Integer getStrength(){
        return strength;
    }

    public String getContactNo(){
        return contactNo;
    }

    public String getEmailId(){
        return emailId;
    }

    public String getAddress(){
        return address;
    }
}
