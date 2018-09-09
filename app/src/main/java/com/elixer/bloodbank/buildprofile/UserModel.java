package com.elixer.bloodbank.buildprofile;

public class UserModel {

    public String name;
    public String phoneNumber;
    public String bloodGroup;
    public String city;
    public int level;


    public UserModel() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserModel(String phoneNumber, String bloodGroup,String name,String city,int level) {
        this.name=name;
        this.phoneNumber=phoneNumber;
        this.bloodGroup=bloodGroup;
        this.city=city;
        this.level=level;
    }

    public String getName() {
        return name;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getLevel() {
        return level;
    }


}
