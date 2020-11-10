package com.example.infinityjobportal.model;

public class Query {
    private String id;
    private String feedbackQuery;
    private String editSubject;
    private String userid;
    String userProfilePic;
    String firstName;
    String lastName;

    public Query(String editSubject, String feedbackQuery, String userId) {
    }

    public String getUserProfilePic() {
        return userProfilePic;
    }

    public void setUserProfilePic(String userProfilePic) {
        this.userProfilePic = userProfilePic;
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



    public Query(String id, String feedbackQuery, String editSubject, String userid, String userProfilePic, String firstName, String lastName) {
        this.id = id;
        this.feedbackQuery = feedbackQuery;
        this.editSubject = editSubject;
        this.userid = userid;
        this.userProfilePic = userProfilePic;
        this.firstName = firstName;
        this.lastName = lastName;
    }





    public Query() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getEditSubject()
    {
        return editSubject;
    }
    public void setEditSubject(String editSubject)
    {
        this.editSubject=editSubject;
    }

    public String getFeedbackQuery() {
        return feedbackQuery;
    }

    public void setFeedbackQuery(String feedbackQuery) {
        this.feedbackQuery = feedbackQuery;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}