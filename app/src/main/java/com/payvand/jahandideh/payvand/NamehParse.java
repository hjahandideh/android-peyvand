package com.payvand.jahandideh.payvand;

/**
 * Created by Jahandideh on 21/07/2016.
 */
import java.util.ArrayList;


public class NamehParse {
    //Data Variables
    private String nnameh;
    private String id;
    private String mnameh;
    private String recive;
    private ArrayList<NamehParse> namehs;

    //Getters and Setters
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

    public String getRecive() {
        return recive;
    }

    public void setRecive(String recive) {
        this.recive = recive;
    }


    public ArrayList<NamehParse> getNamehs() {
        return namehs;
    }

    public void setNamehs(ArrayList<NamehParse> namehs) {
        this.namehs = namehs;
    }

}
