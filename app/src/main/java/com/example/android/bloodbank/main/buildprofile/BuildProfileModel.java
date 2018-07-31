package com.example.android.bloodbank.main.buildprofile;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BuildProfileModel implements BuildProfilePresenter {

    private final BuildProfileView buildProfileView;





    public BuildProfileModel(BuildProfileView buildProfileView) {

        this.buildProfileView= buildProfileView;


    }

    @Override
    public void saveToDatabase(String number, String bloodGroup, String name, String location) {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        String userID = currentFirebaseUser.getUid();
         DatabaseReference mDatabase;
         UserModel userModel = new UserModel(number,bloodGroup,name,location);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(userID).setValue(userModel);

    }
}
