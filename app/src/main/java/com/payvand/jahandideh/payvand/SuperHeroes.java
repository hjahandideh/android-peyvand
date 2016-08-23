package com.payvand.jahandideh.payvand;

import java.util.ArrayList;

/**
 * Created by Jahandideh on 19/07/2016.
 */
public class SuperHeroes {
    //Data Variables
    private String mopayam;

    private String ersal;
    private ArrayList<SuperHeroes> payams;

    //Getters and Setters

    public String getMopayam() {
        return mopayam;
    }

    public void setMopayam(String name) {
        this.mopayam = name;
    }


    public String getErsal() {
        return ersal;
    }

    public void setErsal(String realName) {
        this.ersal = realName;
    }

    public ArrayList<SuperHeroes> getPyam() {
        return payams;
    }

    public void setPayams(ArrayList<SuperHeroes> powers) {
        this.payams = powers;
    }

}
