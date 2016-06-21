package com.example.tushar.helpinghands;

import java.io.Serializable;

/**
 * Created by hussain on 13/4/16.
 */
public class OrphanageEntries implements Serializable {

    private String OrphanageName, contactNo, emailId, address,id,pincode;
    private Integer strength;


    public OrphanageEntries(String OrphanageName, Integer strength, String id, String contactNo,String pincode, String address){
        this.id=id;
        this.OrphanageName = OrphanageName;
        this.strength = strength;
        this.contactNo = contactNo;
        this.address = address;
        this.pincode=pincode;
    }

    public String getName(){
        return OrphanageName;
    }

    public Integer getStrength(){
        return strength;
    }

    public String getContactNo(){
        return contactNo;
    }

    public String getAddress(){
        return address;
    }

    public String getId() {
        return id;
    }

    public String getPincode() {
        return pincode;
    }
}
