package com.example.android.bloodbank.main.buildprofile;

public class UserModel {

    public String name;
    public String phoneNumber;
    public String bloodGroup;
    public String location;

    public UserModel() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserModel(String phoneNumber, String bloodGroup,String name,String location) {
        this.name=name;
        this.phoneNumber=phoneNumber;
        this.bloodGroup=bloodGroup;
        this.location=location;
    }
}
