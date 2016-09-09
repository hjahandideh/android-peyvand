package com.payvand.jahandideh.payvand;

import java.util.ArrayList;

public class ErjahList {
    private String erjah;

    private ArrayList<ErjahList> erjahs;


    public String geterjah() {
        return erjah;
    }

    public void seterjah(String name) {
        this.erjah = name;
    }



    public ArrayList<ErjahList> getErjahs() {
        return erjahs;
    }

    public void setErjahs(ArrayList<ErjahList> powers) {
        this.erjahs = powers;
    }

}
