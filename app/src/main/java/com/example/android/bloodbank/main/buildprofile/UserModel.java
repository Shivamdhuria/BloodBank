package com.example.android.bloodbank.main.buildprofile;

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
}
