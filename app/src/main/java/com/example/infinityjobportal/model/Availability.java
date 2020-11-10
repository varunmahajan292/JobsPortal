package com.example.infinityjobportal.model;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;


public class Availability implements Serializable {
    @Exclude

    private String Monday, Tuesday, Wednessday, Thursday, Friday, Saturday, Sunday,UserId;

    public Availability(String monday, String tuesday, String wednessday, String thursday, String friday, String saturday, String sunday) {
        Monday = monday;
        Tuesday = tuesday;
        Wednessday = wednessday;
        Thursday = thursday;
        Friday = friday;
        Saturday = saturday;
        Sunday = sunday;
    }

    public Availability() {
    }

    public String getMonday() {
        return Monday;
    }

    public void setMonday(String monday) {
        Monday = monday;
    }

    public String getTuesday() {
        return Tuesday;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setTuesday(String tuesday) {
        Tuesday = tuesday;
    }

    public String getWednessday() {
        return Wednessday;
    }

    public void setWednessday(String wednessday) {
        Wednessday = wednessday;
    }

    public String getThursday() {
        return Thursday;
    }

    public void setThursday(String thursday) {
        Thursday = thursday;
    }

    public String getFriday() {
        return Friday;
    }

    public void setFriday(String friday) {
        Friday = friday;
    }

    public String getSaturday() {
        return Saturday;
    }

    public void setSaturday(String saturday) {
        Saturday = saturday;
    }

    public String getSunday() {
        return Sunday;
    }

    public void setSunday(String sunday) {
        Sunday = sunday;
    }


}