package com.example.tushar.helpinghands;

/**
 * Created by hussain on 13/4/16.
 */
public class RecentTransactionEntries {

    private String sponsorName, sponsoredTo, sponsoredId, sponsoredItem, sponsoredTime;

    public RecentTransactionEntries(String sponsorName, String sponsoredTo, String sponsoredId, String sponsoredItem, String sponsoredTime){
        this.sponsorName = sponsorName;
        this.sponsoredTo = sponsoredTo;
        this.sponsoredId = sponsoredId;
        this.sponsoredItem = sponsoredItem;
        this.sponsoredTime = sponsoredTime;
    }

    public String getSponsorName(){
        return sponsorName;
    }

    public String getSponsoredTo(){
        return sponsoredTo;
    }

    public String getSponsoredId(){
        return sponsoredId;
    }

    public String getSponsoredItem(){
        return sponsoredItem;
    }

    public String getSponsoredTime() {
        return sponsoredTime;
    }

}
