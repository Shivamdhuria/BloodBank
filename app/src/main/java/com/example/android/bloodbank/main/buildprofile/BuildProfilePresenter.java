package com.example.android.bloodbank.main.buildprofile;

public interface BuildProfilePresenter {

    void saveToDatabase(String  number,String bloodGroup,String name,String place,Double latitude,Double longitude);
}
