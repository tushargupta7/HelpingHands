package com.example.tushar.helpinghands.models;

/**
 * Created by tushar on 24/5/16.
 */
public class Requirements {
    private String name;
    private String status;
    private int viewId;
    private String prodId;
    private final String REQUIRE="notrequired";
    private int amount;

    public Requirements(String name,String prodId,String amount){
        this.prodId=prodId;
        this.name=name;
        this.amount= Integer.parseInt(amount);
        this.status=REQUIRE;
    }


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
