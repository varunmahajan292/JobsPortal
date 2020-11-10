package com.example.infinityjobportal.model;

public class User {
    String firstName;
    String lastName;
    String number;
    String email;
    String tagLine;
    String about;
    String website;

    String apartment;
    String building;
    String city;
    String province;
    String country;
    String zipCode;
    String street;
    String userProfilePic;
    boolean admin;

    public User() {
    }

    public User(String firstName, String lastName, String number, String email, String tagLine, String about, String website, String apartment, String building, String city, String province, String country, String zipCode, String userProfilePic, boolean admin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.email = email;
        this.tagLine = tagLine;
        this.about = about;
        this.website = website;
        this.apartment = apartment;
        this.building = building;
        this.city = city;
        this.province = province;
        this.country = country;
        this.zipCode = zipCode;
        this.userProfilePic = userProfilePic;
        this.admin = admin;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getUserProfilePic() {
        return userProfilePic;
    }

    public void setUserProfilePic(String userProfilePic) {
        this.userProfilePic = userProfilePic;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
