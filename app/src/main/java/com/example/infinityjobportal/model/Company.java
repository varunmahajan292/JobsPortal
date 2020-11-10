package com.example.infinityjobportal.model;


public class Company {


    String id, name, location, line1, line2, city, state, country, about, desc, web, email, contact, userId, industry,company_image;

    public Company() {
    }

    public Company(String id, String name, String location, String line1, String line2, String city, String state, String country, String about, String desc, String web, String email, String contact, String userId, String industry) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.line1 = line1;
        this.line2 = line2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.about = about;
        this.desc = desc;
        this.web = web;
        this.email = email;
        this.contact = contact;
        this.userId = userId;
        this.industry = industry;
    }

    public Company(String id, String name, String location, String line1, String line2, String city, String state, String country, String about, String desc, String web, String email, String contact, String userId, String industry, String company_image) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.line1 = line1;
        this.line2 = line2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.about = about;
        this.desc = desc;
        this.web = web;
        this.email = email;
        this.contact = contact;
        this.userId = userId;
        this.industry = industry;
        this.company_image = company_image;
    }

    public String getCompany_image() {
        return company_image;
    }

    public void setCompany_image(String company_image) {
        this.company_image = company_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }
}

