package com.payvand.jahandideh.payvand;

import java.util.ArrayList;

public class Userlist {
    private String username;
    private String name;
    private String lname;

    private ArrayList<Userlist> users;

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String name) {
        this.lname = name;
    }

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
