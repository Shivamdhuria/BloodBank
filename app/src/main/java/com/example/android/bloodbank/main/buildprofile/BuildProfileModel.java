package com.example.android.bloodbank.main.buildprofile;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BuildProfileModel implements BuildProfilePresenter {

    private final BuildProfileView buildProfileView;
    String TAG = "BuildProfileModel";






    public BuildProfileModel(BuildProfileView buildProfileView) {

        this.buildProfileView= buildProfileView;


    }

    @Override
    public void saveToDatabase(String number, String bloodGroup, String name, String location) {

        //TODO Find errors in data entered and show if details incorrect



        //else save this to firebase

        //showing progress bar
        buildProfileView.progressBarView();

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        String userID = currentFirebaseUser.getUid();
        Log.e(TAG,"USer Id  "+userID);
         DatabaseReference mDatabase;
         UserModel userModel = new UserModel(number,bloodGroup,name,location);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(userID).setValue(userModel, new DatabaseReference.CompletionListener() {
            public void onComplete(DatabaseError error, DatabaseReference ref) {

                //Hiding progress Bar after completion
                buildProfileView.progressBarHide();
                if(error!= null){
                    //Database Push failed
                    buildProfileView.databaseNotWritten();

                }else{

                    buildProfileView.databaseSuccessfullyWritten();
                }
            }

        });

    }
}
