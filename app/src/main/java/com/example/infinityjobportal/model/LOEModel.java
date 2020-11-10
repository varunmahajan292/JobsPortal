package com.example.infinityjobportal.model;

import java.io.Serializable;

public class LOEModel implements Serializable {
    String id;
    String designation;



    String institute;
    String startdate;
    String enddate;
    String userId;
    String b;
    public LOEModel() {
        this.id = id;
        this.designation = designation;
        this.institute = institute;
        this.startdate = startdate;
        this.enddate = enddate;
      this.userId=userId;
        this.b = b;

    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }



    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }







}
