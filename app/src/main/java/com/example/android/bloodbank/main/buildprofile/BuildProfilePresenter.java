package com.example.android.bloodbank.main.buildprofile;

import android.content.Context;

public interface BuildProfilePresenter {

    void saveToDatabase(String  number,String bloodGroup,String name,String place,int level,Double latitude,Double longitude,Context context);

}
