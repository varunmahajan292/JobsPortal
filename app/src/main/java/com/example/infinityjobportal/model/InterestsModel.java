package com.example.infinityjobportal.model;

public class InterestsModel {
     String id;
    String faltu;
    String type_int;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFaltu() {
        return faltu;
    }

    public void setFaltu(String faltu) {
        this.faltu = faltu;
    }

    public String getType_int() {
        return type_int;
    }

    public void setType_int(String type_int) {
        this.type_int = type_int;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    String userid;

    public InterestsModel() {
        this.id = id;
        this.faltu = faltu;
        this.type_int = type_int;
        this.userid = userid;
    }
}
