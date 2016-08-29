package com.payvand.jahandideh.payvand;

import java.util.ArrayList;

public class Chatlist {
    private String mapayam;
    private String imageUrl;
    private String name;
    private ArrayList<Chatlist> chats;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMapayam() {
        return mapayam;
    }

    public void setMapayam(String name) {
        this.mapayam = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String realName) {
        this.name = realName;
    }

    public ArrayList<Chatlist> getchat() {
        return chats;
    }

    public void setchats(ArrayList<Chatlist> powers) {
        this.chats = powers;
    }

}
