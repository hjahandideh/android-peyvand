package com.payvand.jahandideh.payvand;

import java.util.ArrayList;

/**
 * Created by Jahandideh on 19/07/2016.
 */
public class Userlist {
    //Data Variables
    private String username;


    private ArrayList<Userlist> users;

    //Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }



    public ArrayList<Userlist> getuser() {
        return users;
    }

    public void setuser(ArrayList<Userlist> powers) {
        this.users = powers;
    }

}
