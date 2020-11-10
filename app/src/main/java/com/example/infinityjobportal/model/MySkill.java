package com.example.infinityjobportal.model;


public class MySkill {

    String id, name, skillId, userId;

    public MySkill() {
    }

    public MySkill(String id, String name, String skillId, String userId) {
        this.id = id;
        this.name = name;
        this.skillId = skillId;
        this.userId = userId;
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

    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
