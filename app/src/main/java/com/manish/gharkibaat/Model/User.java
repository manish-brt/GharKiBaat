package com.manish.gharkibaat.Model;

/**********************************
 * Created by Manish on 01-Oct-18
 ***********************************/
public class User {

    String userId;
    String userName;
    String MobileNo;
    String dob;
    String gender;
    String timeStamp;
    Boolean available4Fud;

    public User(String userId, String userName, String mobileNo, String dob, String gender, String timeStamp, Boolean available4Fud) {
        this.userId = userId;
        this.userName = userName;
        MobileNo = mobileNo;
        this.dob = dob;
        this.gender = gender;
        this.timeStamp = timeStamp;
        this.available4Fud = available4Fud;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Boolean getAvailable4Fud() {
        return available4Fud;
    }

    public void setAvailable4Fud(Boolean available4Fud) {
        this.available4Fud = available4Fud;
    }
}
