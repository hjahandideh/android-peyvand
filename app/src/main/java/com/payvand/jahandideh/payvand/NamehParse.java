package com.payvand.jahandideh.payvand;

/**
 * Created by Jahandideh on 21/07/2016.
 */
import java.util.ArrayList;

public class NamehParse {
    private String nnameh;
    private String id;
    private String mnameh;
    private String imageUrl;
    private String name;
    private String lname;
    private String ersal;
    private String st;
    private ArrayList<NamehParse> namehs;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getid(){return id;}
    public void setId(String id){this.id=id;}
    public String getNnameh() {
        return nnameh;
    }

    public void setNnameh(String nnameh) {
        this.nnameh = nnameh;
    }


    public String getMnameh() {
        return mnameh;
    }

    public void setMnameh(String mnameh) {
        this.mnameh = mnameh;
    }

    public String getname() {
        return name;
    }

    public void setname(String recive) {
        this.name = recive;
    }

    public String getst() {
        return st;
    }

    public void setst(String recive) {
        this.st = recive;
    }

    public String getersal() {
        return ersal;
    }

    public void setersal(String recive) {
        this.ersal = recive;
    }

    public String getlname() {
        return lname;
    }

    public void setlname(String recive) {
        this.lname = recive;
    }

    public ArrayList<NamehParse> getNamehs() {
        return namehs;
    }

    public void setNamehs(ArrayList<NamehParse> namehs) {
        this.namehs = namehs;
    }

}
