package com.payvand.jahandideh.payvand;

import java.util.ArrayList;

public class SuperHeroes {
    //Data Variables
    private String mopayam;
    private String mapayam;
    private String name;
    private String lname;
    private String id;
    private ArrayList<SuperHeroes> payams;


    public String getid(){return id;}
    public void setId(String id){this.id=id;}
    public String getMopayam() {
        return mopayam;
    }

    public void setMapayam(String name) {
        this.mapayam = name;
    }

    public String getMapayam() {
        return mapayam;
    }

    public void setMopayam(String name) {
        this.mopayam = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String realName) {
        this.name = realName;
    }


    public String getLName() {
        return lname;
    }

    public void setLName(String realName) {
        this.lname = realName;
    }

    public ArrayList<SuperHeroes> getPyam() {
        return payams;
    }

    public void setPayams(ArrayList<SuperHeroes> powers) {
        this.payams = powers;
    }

}
