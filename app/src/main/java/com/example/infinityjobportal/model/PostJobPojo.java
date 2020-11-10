package com.example.infinityjobportal.model;

public class PostJobPojo {

    private String companyName;
    private String jobCategory;
    private String jobTitle;
    private String streetAddress;
    private String cityAddress;
    private String provinceAddress;
    private String language;
    private Double minSalary;
    private Double maxSalary;
    private String joiningDate;
    private String applicationDeadline;
    private String jobDescription;
    private String skillsRequired;
    private String qualificationRequired;
    private String status;
    private String date;
    private double latitude;
    private double longitude;
    String id;
    private String Monday, Tuesday, Wednessday, Thursday, Friday, Saturday, Sunday, UserId;
    String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public PostJobPojo() {
    }

    public PostJobPojo(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public PostJobPojo(String companyName, String jobCategory, String jobTitle, String streetAddress, String cityAddress, String provinceAddress, String language, Double minSalary, Double maxSalary, String joiningDate, String applicationDeadline, String jobDescription, String skillsRequired, String qualificationRequired, String status, String date, double latitude, double longitude, String monday, String tuesday, String wednessday, String thursday, String friday, String saturday, String sunday, String uid) {
        this.companyName = companyName;
        this.jobCategory = jobCategory;
        this.jobTitle = jobTitle;
        this.streetAddress = streetAddress;
        this.cityAddress = cityAddress;
        this.provinceAddress = provinceAddress;
        this.language = language;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.joiningDate = joiningDate;
        this.applicationDeadline = applicationDeadline;
        this.jobDescription = jobDescription;
        this.skillsRequired = skillsRequired;
        this.qualificationRequired = qualificationRequired;
        this.status = status;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        Monday = monday;
        Tuesday = tuesday;
        Wednessday = wednessday;
        Thursday = thursday;
        Friday = friday;
        Saturday = saturday;
        Sunday = sunday;
        this.uid = uid;
    }

    public PostJobPojo(String companyName, String jobCategory, String jobTitle, String streetAddress, String cityAddress, String provinceAddress, String language, Double minSalary, Double maxSalary, String availability, String joiningDate, String applicationDeadline, String jobDescription, String skillsRequired, String qualificationRequired, double latitude, double longitude) {
        this.companyName = companyName;
        this.jobCategory = jobCategory;
        this.jobTitle = jobTitle;
        this.streetAddress = streetAddress;
        this.cityAddress = cityAddress;
        this.provinceAddress = provinceAddress;
        this.language = language;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.joiningDate = joiningDate;
        this.applicationDeadline = applicationDeadline;
        this.jobDescription = jobDescription;
        this.skillsRequired = skillsRequired;
        this.qualificationRequired = qualificationRequired;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getJobCategory() {
        return jobCategory;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getCityAddress() {
        return cityAddress;
    }

    public String getProvinceAddress() {
        return provinceAddress;
    }

    public String getLanguage() {
        return language;
    }

    public Double getMinSalary() {
        return minSalary;
    }

    public Double getMaxSalary() {
        return maxSalary;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public String getApplicationDeadline() {
        return applicationDeadline;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public String getSkillsRequired() {
        return skillsRequired;
    }

    public String getQualificationRequired() {
        return qualificationRequired;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setJobCategory(String jobCategory) {
        this.jobCategory = jobCategory;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public void setCityAddress(String cityAddress) {
        this.cityAddress = cityAddress;
    }

    public void setProvinceAddress(String provinceAddress) {
        this.provinceAddress = provinceAddress;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setMinSalary(Double minSalary) {
        this.minSalary = minSalary;
    }

    public void setMaxSalary(Double maxSalary) {
        this.maxSalary = maxSalary;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public void setApplicationDeadline(String applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public void setSkillsRequired(String skillsRequired) {
        this.skillsRequired = skillsRequired;
    }

    public void setQualificationRequired(String qualificationRequired) {
        this.qualificationRequired = qualificationRequired;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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