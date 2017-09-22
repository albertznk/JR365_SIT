package com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Skill implements Serializable {

    @SerializedName("user_id")
    private String userId;

    @SerializedName("id")
    private String skillId;

    public String getGetwritten_lvl() {
        return getwritten_lvl;
    }

    public void setGetwritten_lvl(String getwritten_lvl) {
        this.getwritten_lvl = getwritten_lvl;
    }

    @SerializedName("written_level")
    private String getwritten_lvl;

    public String getGetspoken_lvl() {
        return getspoken_lvl;
    }

    public void setGetspoken_lvl(String getspoken_lvl) {
        this.getspoken_lvl = getspoken_lvl;
    }

    @SerializedName("spoken_level")
    private String getspoken_lvl;

    @SerializedName("language")
    private String type;
    @SerializedName("slevel")
    private String level;


    @SerializedName("wlevel")
    private String skillWLevel;

    public Skill(String userId, String skillId, String type, String level, String skillWLevel) {
        this.skillId = skillId;
        this.userId = userId;
        this.type = type;
        this.level = level;
        this.skillWLevel = skillWLevel;
    }

    public Skill(String userId, String type, String level, String skillWLevel) {

        this.userId = userId;
        this.type = type;
        this.level = level;
        this.skillWLevel = skillWLevel;
    }

    public Skill(String type,String level,String skillWLevel){
        this.type=type;
        this.level=level;
        this.skillWLevel=skillWLevel;
    }

    public Skill() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSkillWLevel() {
        return skillWLevel;
    }

    public void setSkillWLevel(String skillWLevel) {
        this.skillWLevel = skillWLevel;
    }

    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }
}
